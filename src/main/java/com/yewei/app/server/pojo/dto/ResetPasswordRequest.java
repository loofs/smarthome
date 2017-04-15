package com.yewei.app.server.pojo.dto;

import lombok.Data;

/**
 * Created by lenovo on 2017/3/30.
 * 通过短信验证码重置密码请求参数
 */
public class ResetPasswordRequest extends RequestParam {

    /**
     * 手机号码
     */
    public String phoneNumber;

    /**
     * 验证码
     */
    public String verificationCode;

    /**
     * 新密码
     */
    public String newPassword;

    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", newPassword='***'" +
                '}';
    }
}
