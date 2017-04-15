package com.yewei.app.server.framework;

import com.yewei.app.server.framework.exception.ExcepFactor;
import com.yewei.app.server.util.GlobalConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@ControllerAdvice(basePackages = "com.yewei.app.server")
public class ApiResponseBodyAdvice implements ResponseBodyAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
       LOGGER.info("beforeBodyWrite:" + body);
//        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        ExcepFactor factor =  ThreadLocalContext.getRequestContext().getExcepFactor();
        if (factor != null) {
            response.setStatusCode(factor.getHttpStatus());
        }
        return body;
    }
}
