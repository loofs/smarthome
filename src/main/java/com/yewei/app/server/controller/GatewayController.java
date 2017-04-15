package com.yewei.app.server.controller;

import com.yewei.app.server.pojo.dto.AddGatewayRequest;
import com.yewei.app.server.pojo.dto.ResponseParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by lenovo on 2017/4/15.
 * 网关相关接口
 */
@RestController
@RequestMapping(value = "/api/gateway", method = RequestMethod.POST)
public class GatewayController {

    @RequestMapping("add")
    public ResponseParam add(@RequestBody AddGatewayRequest request) {

        return new ResponseParam();
    }


}
