package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 用户服务实现类
@Service
public class UserServiceImpl implements UserService {

    // 依赖注入: 通过@Autowired将UserRepository注入
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    // 批量保存用户,如果中途抛异常，所有已保存数据都会回滚
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<User> users) {
        for (User user : users) {
            userRepository.save(user);
        }
    }

    // 根据ID查询用户
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // 查询全部用户
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // 按姓名查询用户
    @Override
    @Transactional(readOnly = true)
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByAgeGreaterThan(Integer age) {
        return userRepository.findUsersByAgeGreaterThan(age);
    }

    @Override
    @Transactional
    public User update(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAge(user.getAge());
        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}