package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 保存用户 (事务: 写入操作)
     */
    User save(User user);

    /**
     * 批量保存用户 (事务: 演示事务一致性，任一条失败则全部回滚)
     */
    void batchSave(List<User> users);

    /**
     * 根据ID查询
     */
    Optional<User> findById(Long id);

    /**
     * 查询全部
     */
    List<User> findAll();

    /**
     * 按姓名查询
     */
    List<User> findByName(String name);

    /**
     * 按年龄大于查询
     */
    List<User> findByAgeGreaterThan(Integer age);

    /**
     * 更新用户
     */
    User update(Long id, User user);

    /**
     * 删除用户
     */
    void deleteById(Long id);
}