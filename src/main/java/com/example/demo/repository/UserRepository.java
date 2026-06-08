package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据访问层
 * - JpaRepository: 提供基础CRUD
 * - 继承JpaRepository<User, Long>：主键类型为Long
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 按姓名查询 (方法名自动生成SQL)
    List<User> findByName(String name);

    // 按邮箱查询
    User findByEmail(String email);

    // 使用@Query自定义SQL，查询年龄大于指定值的用户
    @Query("SELECT u FROM User u WHERE u.age > :age")
    List<User> findUsersByAgeGreaterThan(@Param("age") Integer age);
}