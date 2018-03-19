package com.burak.redis.controller;

import com.burak.redis.model.KeyValue;
import com.burak.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping(value = "/add")
    public String addToRedis(@RequestBody KeyValue keyValue) {
        redisService.addValue(keyValue.getKey(), keyValue.getValue());
        return "Success";
    }

    @GetMapping(value = "/get/{key}")
    public KeyValue getFromRedis(@PathVariable String key) throws Exception {
        String value = redisService.getValue(key);

        if (value != null) {return new KeyValue(key, value);}
        throw new Exception("Key Not Found!");
    }

    @GetMapping(value = "/keys")
    public Integer getKeysCount() {
        return redisService.getKeysCount();
    }
}
