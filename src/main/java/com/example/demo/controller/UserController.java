package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 用户控制器
// - 提供用户相关的HTTP接口
@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // - 返回所有用户列表
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // - 返回根据ID查询用户
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // - 返回按姓名查询用户
    @GetMapping("/search")
    public ResponseEntity<List<User>> getUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }

    // - 返回按年龄大于查询用户
    @GetMapping("/age")
    public ResponseEntity<List<User>> getUsersByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(userService.findByAgeGreaterThan(age));
    }

    // - 新增用户
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    // - 批量新增用户 
    @PostMapping("/batch")
    public ResponseEntity<String> batchCreate(@RequestBody List<User> users) {
        userService.batchSave(users);
        return ResponseEntity.ok("Batch save success, count: " + users.size());
    }

    // - 更新用户
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    // - 删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}