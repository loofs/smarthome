package com.yewei.app.server.framework;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jolestar
 */
public class ThreadLocalContext extends InheritableThreadLocal<RequestContext> {

    private static final ThreadLocalContext instance = new ThreadLocalContext();

    @Override
    protected RequestContext initialValue() {
        return new RequestContext();
    }

    public static HttpServletRequest getServletRequest() {
        return instance.get().getOriginRequest();
    }

    public static RequestContext getRequestContext() {
        return instance.get();
    }

    public static void clear() {
        instance.remove();
    }
}
