package com.yewei.app.server.repository;

import com.yewei.app.server.pojo.domain.Gateway;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lenovo on 2017/4/6.
 * 网关信息表访问层
 */
public interface GatewayDao extends CrudRepository<Gateway, Long> {
}
