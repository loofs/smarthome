package com.yewei.app.server.pojo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by lenovo on 2017/4/6.
 * 网关信息表
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
@Entity
@Table(name = "gateway", uniqueConstraints = {@UniqueConstraint(name = "uk_gateway_native_id", columnNames={"gateway_native_id"})})
public class Gateway extends BaseEntity {

    /**
     * 网关本地标识
     */
    @Column(name = "gateway_native_id")
    private String gatewayNativeId;

    /**
     * 所属用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 网关名称
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 品牌
     */
    @Column(name = "brand", length = 50)
    private String brand;

    /**
     * 型号
     */
    @Column(name = "model", length = 50)
    private String model;

    /**
     * 生产厂商
     */
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    /**
     * 设备编号
     */
    @Column(name = "serial_number", length = 100)
    private String serialNumber;

}
