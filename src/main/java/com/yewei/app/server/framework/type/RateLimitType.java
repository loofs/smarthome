package com.yewei.app.server.framework.type;

import com.yewei.app.server.framework.exception.ExcepFactor;
import com.yewei.app.server.framework.RequestContext;

import java.util.Map;

/**
 * @author sofn
 * @version 1.0 Created at: 2015-10-08 12:03
 */
public enum RateLimitType {
    IP(ExcepFactor.E_IP_RATE_LIMIT),
    USER(ExcepFactor.E_USER_RATE_LIMIT),
    API(ExcepFactor.E_API_RATE_LIMIT);

    private ExcepFactor excepFactor;

    RateLimitType(ExcepFactor excepFactor) {
        this.excepFactor = excepFactor;
    }

    public ExcepFactor getExcepFactor() {
        return excepFactor;
    }

    public String getParamString(RequestContext rc) {
        switch (this) {
            case IP:
                return "i_" + rc.getRemoteHost();
            case USER:
                return "u_" + rc.getUserId();
            case API:
                return "p_" + rc.getOriginRequest().getRequestURI();
            default:
                return "error";
        }
    }

    private String getRequestParamString(Map<String, String[]> parameters) {
        StringBuilder paramBuf = new StringBuilder();

        for (Map.Entry<String, String[]> e : parameters.entrySet()) {
            String key = e.getKey();
            String[] values = e.getValue();
            for (String value : values) {
                paramBuf.append(key).append(":").append(value);
                paramBuf.append("_");
            }
        }
        if (paramBuf.length() > 0 && paramBuf.charAt(paramBuf.length() - 1) == '_') {
            paramBuf.deleteCharAt(paramBuf.length() - 1);
        }
        return paramBuf.toString();
    }


}
