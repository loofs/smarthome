package com.yewei.app.server.service;

import com.yewei.app.server.pojo.domain.Room;

import java.util.List;

/**
 * Created by lenovo on 2017/4/6.
 */
public interface RoomService {

    /**
     * 添加房间
     * @param userId 用户ID
     * @param type 房间类型
     * @param name 房间名称
     * @return
     */
    Room addRoom(Long userId, String type, String name);

    /**
     * 修改房间类型
     * @param userId
     * @param roomId 房间ID
     * @param newType 房间类型
     */
    void modifyRoomType(Long userId, Long roomId, String newType);

    /**
     * 修改房间名称
     * @param id 房间ID
     * @param name 房间名称
     */
    void modifyRoomName(Long id, String name);

    /**
     * 删除房间
     * @param roomIdList
     * @return
     */
    boolean deleteRooms(List<Long> roomIdList);

    /**
     * 房间添加设备
     * @param roomId
     * @param deviceIdList
     */
    void addDevices(Long roomId, List<Long> deviceIdList);

    /**
     * 房间删除设备
     * @param roomId
     * @param deviceIdList
     */
    void deleteDevices(Long roomId, List<Long> deviceIdList);
}
