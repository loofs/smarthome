package com.yewei.app.server.framework.service.impl;


import com.yewei.app.server.framework.*;
import com.yewei.app.server.framework.exception.*;
import com.yewei.app.server.framework.type.*;
import com.yewei.app.server.framework.service.RequestAuthService;
import com.yewei.app.server.util.JwtHelper;
import com.yewei.app.server.util.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Authors: sofn
 * Version: 1.0  Created at 15-8-30 00:22.
 */
@Service
public class AuthServiceImpl implements RequestAuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final String RATE_KEY_PREFIX = "rate_";

    @Resource
    private StringRedisTemplate redis;

    @Override
    public void authAccessible(HttpServletRequest request, Optional<BaseInfo> baseInfo) {
        AuthType type = AuthType.REQUIRED;
        if (baseInfo.isPresent()) {
            type = baseInfo.get().needAuth();
        }
        RequestContext requestContext = ThreadLocalContext.getRequestContext();
        long uid = 0;
        if (type.authFailThrowException()) {
            LOGGER.info(String.format("auth accessible: " + request.getRequestURI()));
            try {
                uid = NumberUtils.toLong(JwtHelper.parseJWT(requestContext.getAccessToken()).getAudience());
                requestContext.setUserId(uid);
                LOGGER.info("auth accessible sucesss, uid=" + uid);
            } catch (Exception e) {
                LOGGER.error("auth accessible failed:" + e.getMessage(), e);
                throw new EngineException(ExcepFactor.E_AUTH_ACCESS_TOKEN_ERROR, e.getMessage());
            }
        }

    }

    @Override
    public void authAccessRateLimit(RateLimit rateLimit) {
        RateLimitTypeConfig[] configs = rateLimit.value();

        Calendar calendar = Calendar.getInstance();
        for (RateLimitTypeConfig config : configs) {
            for (RateLimitRateConfig rateConfig : config.rates()) {
                Long count = incrCount(rateConfig.time(), calendar, config.value().getParamString(ThreadLocalContext.getRequestContext()));
                if (count > rateConfig.value()) {
                    throw new EngineException(config.value().getExcepFactor());
                }
            }
        }
    }

    private long incrCount(TimeUnit time, Calendar calendar, String params) {
        String key = RATE_KEY_PREFIX + StringUtils.substring(time.name(), 0, 1).toLowerCase() + "_" + TimeUtils.getTimeNum(calendar, time) + "_" + params;
        Long result = redis.execute((RedisConnection redisConnection) -> {
            return redisConnection.incr(key.getBytes());
        });
        if (result != null && result <= 2) {
            redis.expire(key, time.toSeconds(1) + 10, TimeUnit.SECONDS);
        }
        return result != null ? result : 0;
    }


}
