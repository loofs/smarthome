package com.yewei.app.server.repository;

import com.yewei.app.server.pojo.domain.Room;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lenovo on 2017/4/6.
 * 房间信息表访问层
 */
public interface RoomDao extends CrudRepository<Room, Long> {

    /**
     * 修改房间名称
     * @param name 房间名称
     * @param id 房间ID
     * @return
     */
    @Modifying
    @Query("update #{#entityName} u set u.name = ?1 where u.id = ?2")
    int updateName(String name, Long id);

    /**
     * 修改房间类型
     * @param type 房间类型
     * @param id 房间ID
     * @return
     */
    @Modifying
    @Query("update #{#entityName} u set u.type = ?1 where u.id = ?2")
    int updateType(String type, Long id);

}
