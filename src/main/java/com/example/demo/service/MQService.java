package com.example.demo.service;

// RocketMQ消息服务接口
// - 生产者发送消息的抽象
public interface MQService {

    // 发送普通消息
    // @param topic 主题
    // @param message 消息内容
    void sendMessage(String topic, String message);

    // 发送带Tag的消息
    void sendMessageWithTag(String topic, String tag, String message);
}