package com.burak.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addValue(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    public int getKeysCount() {
        return this.redisTemplate.keys("*").size();
    }
}
