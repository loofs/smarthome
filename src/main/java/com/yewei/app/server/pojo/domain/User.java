package com.yewei.app.server.pojo.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by lenovo on 2017/3/26.
 * 用户信息表
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(name = "uk_user_phone_number", columnNames={"phone_number"})})
public class User extends BaseEntity {

    /**
     * 用户手机号码
     */
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    /**
     * 用户密码
     */
    @Column(length = 40, nullable = false)
    private String password;

    /**
     * 注册IP地址
     */
    @Column(name = "register_ip", length = 20)
    private String registerIp;


}
