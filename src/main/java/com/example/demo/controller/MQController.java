package com.example.demo.controller;

import com.example.demo.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// RocketMQ消息发送控制器
@RestController
@RequestMapping("/mq")
public class MQController {

    private final MQService mqService;

    @Autowired
    public MQController(MQService mqService) {
        this.mqService = mqService;
    }

    // 发送普通消息
       @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam(defaultValue = "demo-topic") String topic,
                                               @RequestParam String message) {
        mqService.sendMessage(topic, message);
        return ResponseEntity.ok("消息已发送 -> topic: " + topic + ", content: " + message);
    }

    // 发送带tag的消息
       @PostMapping("/send/tag")
    public ResponseEntity<String> sendMessageWithTag(@RequestParam(defaultValue = "demo-topic") String topic,
                                                      @RequestParam String tag,
                                                      @RequestParam String message) {
        mqService.sendMessageWithTag(topic, tag, message);
        return ResponseEntity.ok("消息已发送 -> topic: " + topic + ":" + tag + ", content: " + message);
    }
}