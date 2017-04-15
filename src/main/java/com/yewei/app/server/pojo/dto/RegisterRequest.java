package com.yewei.app.server.pojo.dto;


/**
 * Created by lenovo on 2017/3/26.
 * 注册请求参数
 */
public class RegisterRequest extends RequestParam {

    /**
     * 手机号码
     */
    public String phoneNumber;

    /**
     * 密码
     */
    public String password;

    /**
     * 验证码
     */
    public String verificationCode;

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", password='***'" +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
