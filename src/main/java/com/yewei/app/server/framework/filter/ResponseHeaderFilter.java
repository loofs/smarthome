package com.yewei.app.server.framework.filter;

import com.yewei.app.server.framework.ThreadLocalContext;
import com.yewei.app.server.framework.type.HttpHeader;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sofn
 * @version 1.0 Created at: 2015-04-30 18:46
 */
@WebFilter(urlPatterns = "/api/*", filterName = "responseHeaderFilter")
public class ResponseHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletResponse) response).setHeader(HttpHeader.LOCAL_IP, request.getLocalAddr());
        ((HttpServletResponse) response).setHeader(HttpHeader.REQUEST_ID, ThreadLocalContext.getRequestContext().getRequestId());
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
