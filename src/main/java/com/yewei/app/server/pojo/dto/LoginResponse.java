package com.yewei.app.server.pojo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lenovo on 2017/3/30.
 * 登录应答报文
 */

public class LoginResponse {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", notes = "用户唯一标识")
    public Long userId;

    /**
     * 访问令牌
     */
    @ApiModelProperty(value = "接口访问令牌", notes = "用于验证用户身份，后续调用接口时HTTP请求Header中Access-Token需包含此令牌")
    public String accessToken;

    /**
     * 令牌失效时间
     */
    @ApiModelProperty(value = "令牌失效时间", notes = "时间戳，1970年1月1日0时起的毫秒数。令牌失效后需重新登录获取令牌")
    public Long expires;

}
