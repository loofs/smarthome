package com.yewei.app.server.repository;

import com.yewei.app.server.pojo.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lenovo on 2017/3/30.
 * 用户信息表访问层
 */
public interface UserDao extends CrudRepository<User, Long> {

    /**
     * 根据手机号码查询用户信息
     * @param phoneNumber
     * @return
     */
    User findByPhoneNumber(String phoneNumber);

    /**
     * 根据ID修改用户密码
     * @param password
     * @param id
     * @return
     */
    @Modifying
    @Query("update #{#entityName} u set u.password = ?1, u.updateTime = now() where u.id = ?2")
    int updatePassword(String password, Long id);


}
