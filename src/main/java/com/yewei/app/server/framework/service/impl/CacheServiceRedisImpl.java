package com.yewei.app.server.framework.service.impl;

import com.yewei.app.server.framework.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lenovo on 2017/4/7.
 * 数据缓存服务实现类
 */
@Service
public class CacheServiceRedisImpl implements CacheService{

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public void cacheData(String key, String value, long expires) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expireAt(key, new Date(expires));
    }

    @Override
    public String getCacheData(String key) {
       return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteCacheData(String key) {
        redisTemplate.delete(key);
    }


}
