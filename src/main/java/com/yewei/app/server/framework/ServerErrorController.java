package com.yewei.app.server.framework;


import com.yewei.app.server.framework.exception.EngineException;
import com.yewei.app.server.framework.exception.ExcepFactor;
import com.yewei.app.server.pojo.dto.ResponseParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 错误处理
 * @author sofn
 * @version 1.0 Created at: 2015-04-29 16:19
 */
@RestController
public class ServerErrorController implements ErrorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerErrorController.class);

    public static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH)
    public ResponseParam error(HttpServletRequest request) {
        String path = (String) request.getAttribute("javax.servlet.error.request_uri");
        String errorMsg = (String) request.getAttribute("javax.servlet.error.message");
        MediaType mediaType = (MediaType) request.getAttribute("org.springframework.web.servlet.View.selectedContentType");
        int status = (int) request.getAttribute("javax.servlet.error.status_code");

        Exception exception = (Exception) request.getAttribute(ServerHandlerExceptionResolver.EXCEPTION_ATTR);
        if (exception == null) {
            exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        }
        LOGGER.error("path:" + path + ", errorMsg:" + errorMsg + ", mediaType:" + mediaType + ", status:" + status + ", exception:", exception);

        EngineException apiException;
        String pageError = "500 - System error.";
        if (exception != null && exception instanceof EngineException) {
            apiException = (EngineException) exception;
        } else if (status == 405) {
            apiException =  new EngineException(ExcepFactor.E_METHOD_ERROR);
        } else if (status == 404) {
            pageError = "404 - Page not Found: " + errorMsg;
            apiException =  new EngineException(ExcepFactor.E_API_NOT_EXIST);
        } else if (status == 415) {
            apiException =  new EngineException(ExcepFactor.E_UNSUPPORT_MEDIATYPE_ERROR);
        } else if (status >= 400 && status < 500) {
            apiException =  new EngineException(ExcepFactor.E_ILLEGAL_REQUEST, errorMsg);
        } else if (status == 503) {
            apiException =  new EngineException(ExcepFactor.E_SERVICE_UNAVAILABLE);
        } else {
            apiException =  new EngineException(ExcepFactor.E_DEFAULT);
            LOGGER.error(errorMsg, exception);
        }

        return new ResponseParam(apiException.getFactor());

    }
}
