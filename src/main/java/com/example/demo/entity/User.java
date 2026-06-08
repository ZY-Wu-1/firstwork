package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//用户实体类
@Entity
@Table(name = "t_user", indexes = {
        @Index(name = "idx_user_name", columnList = "name")
})
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}