package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器 (MVC中的Controller层)
 * - @RestController: 组合@Controller + @ResponseBody，返回JSON
 * - @RequestMapping: 定义基础路由前缀
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * 依赖注入: 将UserService注入到Controller
     */
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /users - 查询所有用户
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * GET /users/{id} - 根据ID查询用户
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /users/search?name=xxx - 按姓名查询
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> getUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }

    /**
     * GET /users/age?age=20 - 按年龄大于查询
     */
    @GetMapping("/age")
    public ResponseEntity<List<User>> getUsersByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(userService.findByAgeGreaterThan(age));
    }

    /**
     * POST /users - 新增用户
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * POST /users/batch - 批量新增 (演示事务)
     */
    @PostMapping("/batch")
    public ResponseEntity<String> batchCreate(@RequestBody List<User> users) {
        userService.batchSave(users);
        return ResponseEntity.ok("Batch save success, count: " + users.size());
    }

    /**
     * PUT /users/{id} - 更新用户
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    /**
     * DELETE /users/{id} - 删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}