package com.yewei.app.server.pojo.dto;

import com.yewei.app.server.framework.exception.ExcepFactor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by lenovo on 2017/3/27.
 * API 应答参数
 */
public class ResponseParam<T> {

    @ApiModelProperty(value = "结果编码", notes = "0表示成功，其他为异常编码", required = true)
    public int code = 0;

    @ApiModelProperty(value = "结果描述", example = "成功", required = true)
    public String desc = "成功";

    @ApiModelProperty(value = "结果内容")
    public T result;

    public ResponseParam() {

    }

    public ResponseParam(T result) {
        this.result = result;
    }

    public ResponseParam(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public ResponseParam(ExcepFactor factor) {
        this.code = factor.getErrorCode();
        this.desc = factor.getMessage();
    }
}
