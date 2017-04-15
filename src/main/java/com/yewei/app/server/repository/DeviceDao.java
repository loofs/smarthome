package com.yewei.app.server.repository;

import com.yewei.app.server.pojo.domain.Device;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lenovo on 2017/4/6.
 * 设备信息表访问层
 */
public interface DeviceDao extends CrudRepository<Device, Long> {
}
