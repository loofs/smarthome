package com.yewei.app.server.pojo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by lenovo on 2017/4/6.
 * 设备信息表
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
@Entity
@Table(name = "device", uniqueConstraints = {@UniqueConstraint(name = "uk_device_native_id", columnNames={"gateway_native_id", "device_native_id"})})
public class Device extends BaseEntity {

    /**
     * 网关ID
     */
    @Column(name="gateway_id", nullable = false)
    private Long gatewayId;

    /**
     * 网关本地标识
     */
    @Column(name = "gateway_native_id")
    private String gatewayNativeId;

    /**
     * 设备本地标识
     */
    @Column(name = "device_native_id")
    private String deviceNativeId;

    /**
     * 设备类型
     */
    @Column(name = "type", length = 20, nullable = false)
    private String type;

    /**
     * 设备名称
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
