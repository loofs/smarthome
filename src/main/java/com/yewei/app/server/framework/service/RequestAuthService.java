package com.yewei.app.server.framework.service;


import com.yewei.app.server.framework.type.BaseInfo;
import com.yewei.app.server.framework.type.RateLimit;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 请求认证服务
 */
public interface RequestAuthService {

    /**
     * 验证请求用户身份是否具有访问接口权限
     * @param request 请求信息
     * @param baseInfo 接口认证信息
     */
    void authAccessible(HttpServletRequest request, Optional<BaseInfo> baseInfo);

    /**
     * 验证接口访问频率是否超过限制
     * @param rateLimit
     */
    void authAccessRateLimit(RateLimit rateLimit);


}
