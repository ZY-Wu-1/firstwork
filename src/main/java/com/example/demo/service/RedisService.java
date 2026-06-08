package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis服务接口
 * 涵盖核心数据结构: String, Hash, List, Set
 * 以及分布式锁功能
 */
public interface RedisService {

    // ====== String 结构 ======
    void set(String key, String value);

    String get(String key);

    void setWithExpire(String key, String value, long seconds);

    boolean exists(String key);

    void delete(String key);

    void increment(String key);

    // ====== Hash 结构 ======
    void hset(String key, String field, String value);

    String hget(String key, String field);

    Map<String, String> hgetAll(String key);

    // ====== List 结构 ======
    void lpush(String key, String value);

    String lpop(String key);

    List<String> lrange(String key, long start, long end);

    // ====== Set 结构 ======
    void sadd(String key, String value);

    Set<String> smembers(String key);

    boolean sismember(String key, String value);

    // ====== 分布式锁 ======
    /**
     * 尝试获取分布式锁
     * @param lockKey 锁的key
     * @param requestId 请求标识(谁加的锁)
     * @param expireTime 过期时间(秒)
     * @return 是否获取成功
     */
    boolean tryLock(String lockKey, String requestId, long expireTime);

    /**
     * 释放分布式锁
     * @param lockKey 锁的key
     * @param requestId 请求标识(必须和加锁时一致)
     */
    void releaseLock(String lockKey, String requestId);
}