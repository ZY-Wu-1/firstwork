package com.example.demo.service.impl;

import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

// Redis服务实现类
@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 设置带过期时间的键值对
    @Override
    public void setWithExpire(String key, String value, long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void increment(String key) {
        redisTemplate.opsForValue().increment(key);
    }


    @Override
    public void hset(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            result.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        return result;
    }

    // List 结构

    @Override
    public void lpush(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public String lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    // Set 结构

    @Override
    public void sadd(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public Set<String> smembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public boolean sismember(String key, String value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    //分布式锁
    @Override
    public boolean tryLock(String lockKey, String requestId, long expireTime) {
        Boolean result = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, requestId, expireTime, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(result);
    }

    // 释放分布式锁
    @Override
    public void releaseLock(String lockKey, String requestId) {
        String value = redisTemplate.opsForValue().get(lockKey);
        if (requestId.equals(value)) {
            redisTemplate.delete(lockKey);
        }
    }
}