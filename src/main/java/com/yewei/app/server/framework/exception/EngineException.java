package com.yewei.app.server.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jolestar@gmail.com
 */
public class EngineException extends RuntimeException {
    public static final Logger LOGGER = LoggerFactory.getLogger(EngineException.class);

    /**
     * 异常因素
     */
    private ExcepFactor factor;


    public EngineException(ExcepFactor factor) {
        this.factor = factor;
    }

    public EngineException(ExcepFactor factor, Object... args) {
        this.factor = factor;
        this.factor.setErrorMsgArgs(args);
    }

    public ExcepFactor getFactor() {
        return factor;
    }
}
