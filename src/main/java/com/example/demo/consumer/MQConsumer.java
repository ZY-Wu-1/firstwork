package com.example.demo.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

// RocketMQ消息消费者
@Component
@ConditionalOnProperty(prefix = "rocketmq", name = "consumer.enabled", havingValue = "true", matchIfMissing = false)
@RocketMQMessageListener(
        topic = "demo-topic",
        consumerGroup = "demo-consumer-group"
)
public class MQConsumer implements RocketMQListener<String> {

    private static final Logger log = LoggerFactory.getLogger(MQConsumer.class);

    @Override
    public void onMessage(String message) {
        log.info("[RocketMQ消费者] 收到消息: {}", message);
        System.out.println("[RocketMQ消费者] 消息内容: " + message);
    }
}