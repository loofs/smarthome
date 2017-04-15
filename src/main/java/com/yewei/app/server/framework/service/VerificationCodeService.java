package com.yewei.app.server.framework.service;

import com.yewei.app.server.framework.type.VerificationAction;

/**
 * Created by lenovo on 2017/4/7.
 * 验证码服务
 */
public interface VerificationCodeService {

    /**
     * 发送验证码
     * @param phoneNumber 手机号码
     * @param action 执行动作
     */
    void sendVerifactionCode(String phoneNumber, VerificationAction action);

    /**
     * 验证验证码
     * @param phoneNumber 手机号码
     * @param action 验证动作
     * @param code 验证码
     * @return 验证通过返回true，否则返回false
     */
    boolean validateCode(String phoneNumber, VerificationAction action, String code);
}
