package com.yewei.app.server.pojo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lenovo on 2017/4/6.
 * 房间信息表
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
@Entity
@Table(name = "room")
public class Room extends BaseEntity {

    /**
     * 用户ID
     */
    @Column(name="user_id", nullable = false)
    private Long userId;

    /**
     * 房间类型
     */
    @Column(name = "type", length = 20, nullable = false)
    private String type;

    /**
     * 房间名称
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

}
