package com.example.demo.service.impl;

import com.example.demo.service.MQService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RocketMQ消息生产者实现
 * - RocketMQTemplate: Spring Boot提供的消息发送模板
 * - 负责将消息发送到RocketMQ Broker
 */
@Service
public class MQServiceImpl implements MQService {

    private static final Logger log = LoggerFactory.getLogger(MQServiceImpl.class);

    private final RocketMQTemplate rocketMQTemplate;

    @Autowired
    public MQServiceImpl(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @Override
    public void sendMessage(String topic, String message) {
        log.info("[RocketMQ生产者] 发送消息 -> topic={}, message={}", topic, message);
        rocketMQTemplate.convertAndSend(topic, message);
    }

    /**
     * 发送带Tag的消息: topic:tag 的格式
     * Tag可用于消费者过滤消息，只消费指定tag的消息
     */
    @Override
    public void sendMessageWithTag(String topic, String tag, String message) {
        String destination = topic + ":" + tag;
        log.info("[RocketMQ生产者] 发送消息 -> destination={}, message={}", destination, message);
        rocketMQTemplate.convertAndSend(destination, message);
    }
}