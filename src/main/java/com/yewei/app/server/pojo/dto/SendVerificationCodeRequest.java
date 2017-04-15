package com.yewei.app.server.pojo.dto;

import lombok.Data;

/**
 * Created by lenovo on 2017/3/30.
 * 获取验证码请求参数
 */
public class SendVerificationCodeRequest extends RequestParam {

    /**
     * 手机号码
     */
    public String phoneNumber;

    /**
     * 认证动作
     */
    public String verificationAction;
}
