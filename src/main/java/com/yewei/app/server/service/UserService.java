package com.yewei.app.server.service;

import com.yewei.app.server.pojo.domain.User;

/**
 * Created by lenovo on 2017/3/31.
 */
public interface UserService {

    /**
     * 新增用户
     * @param phoneNumber 手机号码
     * @param password 用户密码
     * @param registerIp 注册IP地址
     * @return 用户信息
     */
    User addUser(String phoneNumber, String password, String registerIp);

    /**
     * 获取已认证的用户信息
     * @param phoneNumber 手机号码
     * @param password 用户密码
     * @return
     */
    User getAuthenticatedUser(String phoneNumber, String password);

    /**
     * 获取已认证的用户信息
     * @param id 用户信息
     * @param password 用户密码
     * @return
     */
    User getAuthenticatedUser(Long id, String password);

    /**
     * 获取用户信息
     * @param id 用户ID
     * @return
     */
    User getUser(Long id);

    /**
     * 根据手机号码获取用户信息
     * @param phoneNumber 手机号码
     * @return
     */
    User getUser(String phoneNumber);

    /**
     * 修改用户密码
     * @param userId 用户ID
     * @param newPassword 新密码
     * @return 修改成功与否
     */
    boolean modifyPassword(Long userId, String newPassword);

}
