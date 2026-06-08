package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

// Redis 服务接口
public interface RedisService {

    void set(String key, String value);

    String get(String key);

    void setWithExpire(String key, String value, long seconds);

    boolean exists(String key);

    void delete(String key);

    void increment(String key);

    void hset(String key, String field, String value);

    String hget(String key, String field);

    Map<String, String> hgetAll(String key);

    void lpush(String key, String value);

    String lpop(String key);

    List<String> lrange(String key, long start, long end);

    void sadd(String key, String value);

    Set<String> smembers(String key);

    boolean sismember(String key, String value);

    // 尝试获取分布式锁
    boolean tryLock(String lockKey, String requestId, long expireTime);

    // 释放分布式锁
    void releaseLock(String lockKey, String requestId);
}