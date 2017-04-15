package com.yewei.app.server.pojo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by lenovo on 2017/4/1.
 * 基础实体类
 */
@MappedSuperclass
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity implements Serializable {

    /**
     * 对象主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

}
