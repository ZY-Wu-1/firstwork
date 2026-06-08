package com.example.demo.controller;

import com.example.demo.service.RedisService; // 请确保RedisService类存在于该包路径下，或检查包名是否正确
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;
    // 依赖注入: 通过@Autowired将RedisService注入
    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    // 设置字符串键值对
    @PostMapping("/string")
    public ResponseEntity<String> setString(@RequestParam String key, @RequestParam String value) {
        redisService.set(key, value);
        return ResponseEntity.ok("Set successfully");
    }

    // 获取字符串键值对
    @GetMapping("/string")
    public ResponseEntity<String> getString(@RequestParam String key) {
        String value = redisService.get(key);
        return ResponseEntity.ok(value);
    }

    // 设置哈希键值对
    @PostMapping("/hash")
    public ResponseEntity<String> setHash(@RequestParam String key, @RequestParam String field, @RequestParam String value) {
        redisService.hset(key, field, value);
        return ResponseEntity.ok("Hash set successfully");
    }

    // 获取哈希键值对
    @GetMapping("/hash")
    public ResponseEntity<Map<String, String>> getHash(@RequestParam String key) {
        Map<String, String> hash = redisService.hgetAll(key);
        return ResponseEntity.ok(hash);
    }

    // 设置列表键值对
    @PostMapping("/list")
    public ResponseEntity<String> pushList(@RequestParam String key, @RequestParam String value) {
        redisService.lpush(key, value);
        return ResponseEntity.ok("List push successfully");
    }

    // 获取列表键值对
    @GetMapping("/list")
    public ResponseEntity<List<String>> getList(@RequestParam String key) {
        List<String> list = redisService.lrange(key, 0, -1);
        return ResponseEntity.ok(list);
    }

    // 设置集合键值对
    @PostMapping("/set")
    public ResponseEntity<String> addSet(@RequestParam String key, @RequestParam String value) {
        redisService.sadd(key, value);
        return ResponseEntity.ok("Set add successfully");
    }

    // 获取集合键值对
    @GetMapping("/set")
    public ResponseEntity<Set<String>> getSet(@RequestParam String key) {
        Set<String> set = redisService.smembers(key);
        return ResponseEntity.ok(set);
    }

    // 尝试获取分布式锁
    @PostMapping("/lock")
    public ResponseEntity<Map<String, Object>> tryLock(@RequestParam String lockKey) {
        String requestId = UUID.randomUUID().toString();
        boolean locked = redisService.tryLock(lockKey, requestId, 30);
        
        Map<String, Object> result = new HashMap<>();
        result.put("locked", locked);
        result.put("requestId", requestId);
        return ResponseEntity.ok(result);
    }

    // 释放分布式锁
    @PostMapping("/lock/release")
    public ResponseEntity<String> releaseLock(@RequestParam String lockKey, @RequestParam String requestId) {
        redisService.releaseLock(lockKey, requestId);
        return ResponseEntity.ok("Lock released");
    }
}