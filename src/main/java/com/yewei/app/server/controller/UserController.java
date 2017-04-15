package com.yewei.app.server.controller;

import com.yewei.app.server.framework.service.CacheService;
import com.yewei.app.server.framework.service.VerificationCodeService;
import com.yewei.app.server.framework.type.AuthType;
import com.yewei.app.server.framework.type.BaseInfo;
import com.yewei.app.server.framework.ThreadLocalContext;
import com.yewei.app.server.pojo.dto.*;
import com.yewei.app.server.pojo.domain.User;
import com.yewei.app.server.framework.type.VerificationAction;
import com.yewei.app.server.service.UserService;
import com.yewei.app.server.util.JwtHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2017/3/26.
 * 用户管理相关接口
 */
@RestController
@RequestMapping(value = "/api/user", method = RequestMethod.POST)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @RequestMapping("register")
    @BaseInfo(needAuth = AuthType.OPTION)
    @ApiOperation(value = "注册新用户")
    public ResponseParam register(@RequestBody RegisterRequest request) {
        verificationCodeService.validateCode(request.phoneNumber, VerificationAction.REGISTER, request.verificationCode);
        userService.addUser(request.phoneNumber, request.password, ThreadLocalContext.getRequestContext().getRemoteHost());
        return new ResponseParam();
    }

    @RequestMapping(value = "login")
    @BaseInfo(needAuth = AuthType.OPTION)
    @ApiOperation(value = "用户登录")
    public ResponseParam<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.getAuthenticatedUser(loginRequest.phoneNumber, loginRequest.password);

        LoginResponse response = new LoginResponse();
        response.userId = user.getId();
        long expires = System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000;
        response.accessToken = JwtHelper.createJWT("" + user.getId(), expires, null);
        response.expires = expires;
        cacheService.cacheData(response.accessToken, "" + user.getId(), expires);
        return new ResponseParam<>(response);
    }

    @RequestMapping("logout")
    @ApiOperation(value = "用户登出")
    public ResponseParam logout() {
        String accessToken = ThreadLocalContext.getRequestContext().getAccessToken();
        if (!StringUtils.isBlank(accessToken)) {
            cacheService.deleteCacheData(accessToken);
        }
        return new ResponseParam();
    }

    @RequestMapping("resetPassword")
    @BaseInfo(needAuth = AuthType.OPTION)
    @ApiOperation(value = "重置登录密码", notes = "忘记密码时通过手机验证码重置登录密码")
    public ResponseParam resetPassword(@RequestBody ResetPasswordRequest request) {
        verificationCodeService.validateCode(request.phoneNumber,VerificationAction.RESET_PASSWORD, request.verificationCode);
        User user = userService.getUser(request.phoneNumber);
        userService.modifyPassword(user.getId(), request.newPassword);
        return new ResponseParam();
    }

    @RequestMapping("modifyPassword")
    @ApiOperation(value = "修改登录密码", notes = "用户在已登录状态下修改登录密码")
    public ResponseParam modifyPassword(@RequestBody ModifyPasswordRequest request) {
        long userId = ThreadLocalContext.getRequestContext().getUserId();
        userService.getAuthenticatedUser(userId, request.password);
        userService.modifyPassword(userId, request.newPassword);
        return new ResponseParam();
    }

    @RequestMapping("sendVerificationCode")
    @BaseInfo(needAuth = AuthType.OPTION)
    @ApiOperation(value = "发送手机验证码")
    public ResponseParam sendVerificationCode(@RequestBody SendVerificationCodeRequest request) {
        VerificationAction action = VerificationAction.valueOf(request.verificationAction);
        if (action == VerificationAction.RESET_PASSWORD) {
            userService.getUser(request.phoneNumber);
        }
        verificationCodeService.sendVerifactionCode(request.phoneNumber, action);
        return new ResponseParam();
    }

}
