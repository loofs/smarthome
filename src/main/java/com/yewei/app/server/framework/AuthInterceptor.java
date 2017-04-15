package com.yewei.app.server.framework;

import com.yewei.app.server.framework.type.BaseInfo;
import com.yewei.app.server.framework.type.RateLimit;
import com.yewei.app.server.framework.service.RequestAuthService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Created by lenovo on 2017/3/26.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private RequestAuthService requestAuthService;

    @Resource
    private CounterService counterService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        LOGGER.info("preHandle:" + handler);
        if (StringUtils.equals(httpServletRequest.getRequestURI(), ServerErrorController.ERROR_PATH)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        BaseInfo baseInfo = null;
        if (method.isAnnotationPresent(BaseInfo.class)) {
            baseInfo = method.getAnnotation(BaseInfo.class);
        }
        RateLimit rateLimit = null;
        if (method.isAnnotationPresent(RateLimit.class)) {
            rateLimit = method.getAnnotation(RateLimit.class);
        }

        requestAuthService.authAccessible(httpServletRequest, Optional.ofNullable(baseInfo));
        counterService.increment(StringUtils.substring(StringUtils.replace(httpServletRequest.getRequestURI(), "/", "."), 1));
        if (rateLimit != null && !rateLimit.internalIgnore()) {
            requestAuthService.authAccessRateLimit(rateLimit);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
