package com.yewei.app.server.service.impl;

import com.yewei.app.server.pojo.domain.Room;
import com.yewei.app.server.pojo.domain.RoomDevice;
import com.yewei.app.server.repository.RoomDao;
import com.yewei.app.server.repository.RoomDeviceDao;
import com.yewei.app.server.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2017/4/6.
 */
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomDeviceDao roomDeviceDao;

    @Override
    public Room addRoom(Long userId, String type, String name) {
        Room room = new Room();
        room.setUserId(userId);
        room.setType(type);
        room.setName(name);
        roomDao.save(room);
        return room;
    }

    @Override
    public void modifyRoomType(Long userId, Long roomId, String newType) {
        Room room = roomDao.findOne(roomId);

        roomDao.updateType(newType, roomId);
    }

    @Override
    public void modifyRoomName(Long id, String name) {
        roomDao.updateName(name, id);
    }

    @Override
    public boolean deleteRooms(List<Long> roomIdList) {
        if (roomIdList == null) {
            return false;
        }
        for (Long roomId : roomIdList) {
            roomDeviceDao.deleteRoomDevices(roomId);
            roomDao.delete(roomId);
        }
        return false;
    }

    @Override
    public void addDevices(Long roomId, List<Long> deviceIdList) {
        if (deviceIdList != null) {
            for (Long deviceId : deviceIdList) {
                RoomDevice roomDevice = new RoomDevice();
                roomDevice.setRoomId(roomId);
                roomDevice.setDeviceId(deviceId);
                roomDeviceDao.save(roomDevice);
            }
        }

    }

    @Override
    public void deleteDevices(Long roomId, List<Long> deviceIdList) {
        if (deviceIdList != null) {
            for (Long deviceId : deviceIdList) {
                RoomDevice roomDevice = roomDeviceDao.findByRoomIdAndDeviceId(roomId, deviceId);
                if (roomDevice != null) {
                    roomDeviceDao.delete(roomDevice.getId());
                }
            }
        }
    }

}
