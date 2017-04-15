package com.yewei.app.server.pojo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lenovo on 2017/3/30.
 * 登录请求参数
 */
@ApiModel(value = "登录请求")
public class LoginRequest extends RequestParam {

    @ApiModelProperty(value = "手机号码",  position = 0, example = "18812341234", required = true)
    public String phoneNumber;

    @ApiModelProperty(value = "登录密码", position = 1, example = "123456", required = true)
    public String password;

}
