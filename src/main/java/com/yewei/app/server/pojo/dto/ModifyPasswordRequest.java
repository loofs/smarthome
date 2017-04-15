package com.yewei.app.server.pojo.dto;


/**
 * Created by lenovo on 2017/3/30.
 * 修改密码请求参数
 */
public class ModifyPasswordRequest extends RequestParam {

    /**
     * 用户当前密码
     */
    public String password;

    /**
     * 用户新密码
     */
    public String newPassword;


    @Override
    public String toString() {
        return "ModifyPasswordRequest{" +
                "password='***'" +
                ", newPassword='***'" +
                '}';
    }
}
