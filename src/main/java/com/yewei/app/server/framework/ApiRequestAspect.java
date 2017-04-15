package com.yewei.app.server.framework;

import com.yewei.app.server.framework.exception.EngineException;
import com.yewei.app.server.framework.exception.ExcepFactor;
import com.yewei.app.server.pojo.dto.RequestParam;
import com.yewei.app.server.pojo.dto.ResponseParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by lenovo on 2017/4/8.
 */
@Aspect
@Component
public class ApiRequestAspect {
    private static Logger LOGGER = LoggerFactory.getLogger(ApiRequestAspect.class);

    @Pointcut("execution(public * com.yewei.app.server.controller..*.*(..))")
    public void apiRequest() {}

    @Around("apiRequest()")
    public Object aroundLogCalls(ProceedingJoinPoint jp) throws Throwable {
        LOGGER.info("执行方法:" + jp.getSignature());
        RequestContext context = ThreadLocalContext.getRequestContext();

        Object result = null;
        try {
            if (jp.getArgs().length >= 1) {
                String requestParams = "";
                try {
                    for (Object arg : jp.getArgs()) {
                        requestParams += arg;
                        if (arg instanceof RequestParam) {
                            checkRequestParam((RequestParam) arg);
                        }
                    }
                } finally {
                    context.setRequestParam(requestParams);
                }
            }

            // 执行实际业务方法
            result = jp.proceed();
        } catch (EngineException e) {
            LOGGER.error(jp.getSignature().getName() + " error: " + e.getFactor());
            context.setExcepFactor(e.getFactor());
            result = new ResponseParam(e.getFactor());
        }  catch (Throwable e) {
            LOGGER.error(jp.getSignature().getName() + " error: " + e.getMessage(), e);
            context.setExcepFactor(ExcepFactor.E_DEFAULT);
            result = new ResponseParam(ExcepFactor.E_DEFAULT);
        }
        context.setResponseParam("" + result);
        return result;
    }


    /**
     * 验证请求参数
     * @param requestParam
     */
    private void checkRequestParam(RequestParam requestParam) {
        if (requestParam != null) {
            for (Field field : requestParam.getClass().getFields()) {
                try {
                    if (field.get(requestParam) == null) {
                        throw new EngineException(ExcepFactor.E_PARAM_MISS_ERROR, field.getName());
                    }
                } catch (IllegalAccessException e) {
                    LOGGER.warn("请求参数" + requestParam + "中[" + field.getName() + "]访问出错：" + e.getMessage());
                }
            }
        }
    }

}
