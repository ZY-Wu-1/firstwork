package com.example.demo.controller;

import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Redis操作控制器
 * 提供各种Redis数据结构的RESTful操作接口
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    // ============== String 操作 ==============

    @PostMapping("/string")
    public ResponseEntity<String> setString(@RequestParam String key, @RequestParam String value) {
        redisService.set(key, value);
        return ResponseEntity.ok("Set OK: " + key + " = " + value);
    }

    @GetMapping("/string")
    public ResponseEntity<String> getString(@RequestParam String key) {
        String value = redisService.get(key);
        return ResponseEntity.ok(value == null ? "(nil)" : value);
    }

    @PostMapping("/string/expire")
    public ResponseEntity<String> setStringWithExpire(@RequestParam String key,
                                                       @RequestParam String value,
                                                       @RequestParam long seconds) {
        redisService.setWithExpire(key, value, seconds);
        return ResponseEntity.ok("Set OK, expire: " + seconds + "s");
    }

    @DeleteMapping("/string")
    public ResponseEntity<String> deleteKey(@RequestParam String key) {
        redisService.delete(key);
        return ResponseEntity.ok("Deleted: " + key);
    }

    @PostMapping("/incr")
    public ResponseEntity<String> increment(@RequestParam String key) {
        redisService.increment(key);
        return ResponseEntity.ok("Increment OK: " + key);
    }

    // ============== Hash 操作 ==============

    @PostMapping("/hash")
    public ResponseEntity<String> setHash(@RequestParam String key,
                                           @RequestParam String field,
                                           @RequestParam String value) {
        redisService.hset(key, field, value);
        return ResponseEntity.ok("HSET OK: " + key + "." + field + " = " + value);
    }

    @GetMapping("/hash")
    public ResponseEntity<Map<String, String>> getHash(@RequestParam String key) {
        return ResponseEntity.ok(redisService.hgetAll(key));
    }

    // ============== List 操作 ==============

    @PostMapping("/list")
    public ResponseEntity<String> pushList(@RequestParam String key, @RequestParam String value) {
        redisService.lpush(key, value);
        return ResponseEntity.ok("LPUSH OK: " + key);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> getList(@RequestParam String key) {
        return ResponseEntity.ok(redisService.lrange(key, 0, -1));
    }

    // ============== Set 操作 ==============

    @PostMapping("/set")
    public ResponseEntity<String> addSet(@RequestParam String key, @RequestParam String value) {
        redisService.sadd(key, value);
        return ResponseEntity.ok("SADD OK: " + key);
    }

    @GetMapping("/set")
    public ResponseEntity<Set<String>> getSet(@RequestParam String key) {
        return ResponseEntity.ok(redisService.smembers(key));
    }

    // ============== 分布式锁 ==============

    /**
     * 尝试获取分布式锁
     * 每个请求都会生成一个唯一的requestId作为锁标识
     */
    @PostMapping("/lock")
    public ResponseEntity<Map<String, Object>> tryLock(@RequestParam String lockKey,
                                                       @RequestParam(defaultValue = "30") long seconds) {
        String requestId = UUID.randomUUID().toString();
        boolean locked = redisService.tryLock(lockKey, requestId, seconds);

        Map<String, Object> result = new HashMap<>();
        result.put("locked", locked);
        result.put("requestId", requestId);
        result.put("lockKey", lockKey);
        result.put("msg", locked ? "获取锁成功" : "获取锁失败，已被占用");
        return ResponseEntity.ok(result);
    }

    /**
     * 释放分布式锁（需要获取锁时返回的requestId）
     */
    @PostMapping("/unlock")
    public ResponseEntity<String> releaseLock(@RequestParam String lockKey,
                                               @RequestParam String requestId) {
        redisService.releaseLock(lockKey, requestId);
        return ResponseEntity.ok("释放锁请求完成: " + lockKey);
    }
}