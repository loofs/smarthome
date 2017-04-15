package com.yewei.app.server.framework.service;

/**
 * Created by lenovo on 2017/4/6.
 * 数据缓存服务
 */
public interface CacheService {

    /**
     * 缓存数据
     * @param key 数据索引
     * @param value 数值值
     * @param expires 数据过期时间
     */
    void cacheData(String key, String value, long expires);

    /**
     * 获取缓存数据
     * @param key 数据索引
     * @return
     */
    String getCacheData(String key);

    /**
     * 清除缓存数据
     * @param key
     */
    void deleteCacheData(String key);

}
