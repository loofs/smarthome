package com.yewei.app.server.pojo.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lenovo on 2017/4/15.
 * 添加网关请求
 */
public class AddGatewayRequest extends RequestParam {

    @ApiModelProperty(value = "网关标识", required = true)
    public String gatewayNativeId;

    @ApiModelProperty(value = "网关名称", required = true)
    public String name;

    @ApiModelProperty(value = "品牌")
    public String brand;

    @ApiModelProperty(value = "型号")
    public String model;

    @ApiModelProperty(value = "厂商")
    public String manufacturer;

    @ApiModelProperty(value = "序列号", required = true)
    public String serialNumber;


}
