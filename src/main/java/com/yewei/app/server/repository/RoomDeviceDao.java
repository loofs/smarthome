package com.yewei.app.server.repository;

import com.yewei.app.server.pojo.domain.RoomDevice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lenovo on 2017/4/6.
 * 房间包含设备关联表访问层
 */
public interface RoomDeviceDao extends CrudRepository<RoomDevice, Long> {

    /**
     * 根据房间ID、设备ID查询
     * @param roomId
     * @param deviceId
     * @return
     */
    RoomDevice findByRoomIdAndDeviceId(Long roomId, Long deviceId);

    /**
     * 删除房间所有关联设备信息
     * @param roomId
     * @return
     */
    @Modifying
    @Query("delete from #{#entityName} u where u.roomId = ?1")
    int deleteRoomDevices(Long roomId);

}
