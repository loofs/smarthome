package com.yewei.app.server.framework.filter;

import com.yewei.app.server.framework.ClientVersion;
import com.yewei.app.server.framework.RequestContext;
import com.yewei.app.server.framework.ThreadLocalContext;
import com.yewei.app.server.framework.type.HttpHeader;
import com.yewei.app.server.framework.type.RequestFrom;
import com.yewei.app.server.util.IPUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 请求日志过滤器
 */
@WebFilter(urlPatterns = "/api/*", filterName = "apiRequestLogFilter")
public class ApiRequestFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequestFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        RequestContext context = ThreadLocalContext.getRequestContext();
        context.setRequestId(UUID.randomUUID().toString());
        context.setRequestTime(new Date());
        context.setOriginRequest(request);
        context.setMethod(request.getMethod());
        context.setUri(request.getRequestURI());
        context.setUrlParameter(JSONObject.fromObject(request.getParameterMap()).toString());
        context.setLocalHost(request.getLocalAddr());

        // 获取访问令牌
        String accessToken = request.getHeader(HttpHeader.ACCESS_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            accessToken = request.getParameter(HttpHeader.ACCESS_TOKEN);
        }
        context.setAccessToken(accessToken);

        // 获取请求方IP地址
        String remoteIp = request.getHeader(HttpHeader.REMOTE_IP);
        if (remoteIp == null) {
            remoteIp = IPUtils.getRealIpAddr(request);
        }
        context.setRemoteHost(remoteIp);

        context.setRequestFrom(RequestFrom.valueOf(request.getHeader(HttpHeader.FROM_HEADER), RequestFrom.OUTER));
        context.setAppId(NumberUtils.toLong(request.getHeader(HttpHeader.APP_ID)));
        context.setClientVersion(ClientVersion.valueOf(request.getHeader(HttpHeader.CLIENT_VERSION)));

        MDC.put("requestId", context.getRequestId());
        LOGGER.info("doFilter before a");
        try {
            filterChain.doFilter(request, response);
        } catch (Throwable e) {
            LOGGER.error("doFilter Exception ", e);
            response.getWriter().append("{}");
        } finally {
            LOGGER.info("doFilter after a");
            context.setResponseStatus(response.getStatus());
            context.setResponseTime(new Date());
            context.setUseTime(context.getResponseTime().getTime() - context.getRequestTime().getTime());

            MDC.put("CUSTOM_LOG", "request");
            LOGGER.info(context.toString());
            MDC.remove("CUSTOM_LOG");
            ThreadLocalContext.clear();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}