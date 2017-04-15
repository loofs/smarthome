package com.yewei.app.server.service.impl;

import com.yewei.app.server.framework.exception.EngineException;
import com.yewei.app.server.framework.exception.ExcepFactor;
import com.yewei.app.server.pojo.domain.User;
import com.yewei.app.server.repository.UserDao;
import com.yewei.app.server.service.UserService;
import com.yewei.app.server.util.Digests;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by lenovo on 2017/3/31.
 * 用户服务接口实现
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User addUser(String phoneNumber, String password, String registerIp) {
        if (userDao.findByPhoneNumber(phoneNumber) != null) {
            throw new EngineException(ExcepFactor.ACCOUNT_EXISTS);
        }
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setPassword(Hex.encodeHexString(Digests.sha1(password.getBytes())));
        user.setRegisterIp(registerIp);
        user.setCreateTime(now);
        user.setUpdateTime(now);
        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getAuthenticatedUser(String phoneNumber, String password) {
        User user = userDao.findByPhoneNumber(phoneNumber);
        if (user == null || !user.getPassword().equals(Hex.encodeHexString(Digests.sha1(password.getBytes())))) {
            throw new EngineException(ExcepFactor.USERPASS_ERROR);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getAuthenticatedUser(Long id, String password) {
        User user = this.getUser(id);
        if (!user.getPassword().equals(Hex.encodeHexString(Digests.sha1(password.getBytes())))) {
            throw  new EngineException(ExcepFactor.PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        User user = userDao.findOne(id);
        if (user == null) {
            throw  new EngineException(ExcepFactor.ACCOUNT_NOT_EXISTS);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String phoneNumber) {
        User user = userDao.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw  new EngineException(ExcepFactor.ACCOUNT_NOT_EXISTS);
        }
        return user;
    }

    @Override
    public boolean modifyPassword(Long userId, String newPassword) {
        return userDao.updatePassword(Hex.encodeHexString(Digests.sha1(newPassword.getBytes())), userId) == 1;
    }
}
