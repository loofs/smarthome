package com.yewei.app.server.pojo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by lenovo on 2017/4/6.
 * 房间包含设备关联表
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
@Entity
@Table(name = "room_device", uniqueConstraints = {@UniqueConstraint(name = "uk_room_device", columnNames={"room_id", "device_id"})})
public class RoomDevice extends BaseEntity {

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "device_id", nullable = false)
    private Long deviceId;
}
