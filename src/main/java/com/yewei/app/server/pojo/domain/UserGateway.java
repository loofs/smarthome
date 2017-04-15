package com.yewei.app.server.pojo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by lenovo on 2017/4/6.
 * 用户网关关联表
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
@Entity
@Table(name = "user_gateway", uniqueConstraints = {@UniqueConstraint(name = "uk_user_gateway", columnNames={"user_id", "gateway_id"})})
public class UserGateway extends BaseEntity{

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 网关ID
     */
    @Column(name = "gateway_id", nullable = false)
    private Long gatewayId;
}
