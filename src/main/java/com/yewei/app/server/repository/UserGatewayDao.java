package com.yewei.app.server.repository;

import com.yewei.app.server.pojo.domain.UserGateway;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lenovo on 2017/4/6.
 * 用户关联网关表访问层
 */
public interface UserGatewayDao extends CrudRepository<UserGateway, Long> {
}
