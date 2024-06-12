package com.Capstone.Project.domain.user.repository.impl;


import com.Capstone.Project.global.config.redis.AbstractKeyValueCacheRepository;
import com.Capstone.Project.global.config.redis.RedisKeys;
import com.Capstone.Project.domain.user.repository.AutoLoginRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;



@Repository
public class AutoLoginRedisRepository extends AbstractKeyValueCacheRepository implements AutoLoginRepository {

    private final Duration cacheDuration;

    protected AutoLoginRedisRepository(StringRedisTemplate redisTemplate,
                                       ObjectMapper objectMapper,
                                       @Value("P15D") Duration cacheDuration){
        super(redisTemplate, objectMapper, RedisKeys.AUTO_LOGIN_KEY);
        this.cacheDuration = cacheDuration;
    }

    public void setAutoLoginPayload(String refreshToken, String autoLoginName, Object data, Instant now) {
        String key = makeEntryKey(refreshToken, autoLoginName);
        set(key, data, now, cacheDuration);
    }

    public String makeEntryKey(String refreshToken, String autoLoginName) {
        return refreshToken + RedisKeys.KEY_DELIMITER + autoLoginName;
    }

    public <T> Optional<T> getAutoLoginPayload(String refreshToken, String autoLoginName, Class<T> clazz, Instant now) {
        String key = makeEntryKey(refreshToken, autoLoginName);
        return get(key, clazz, now);
    }
}
