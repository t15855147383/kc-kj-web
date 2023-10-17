package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.redis.RedisKey;
import com.ruoyi.framework.config.RedisConfig;
import com.ruoyi.framework.kafka.service.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kafka")
public class UserController extends BaseController {
    @Autowired
    private KafkaProducerService kafkaProducerService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private RedisCache redisCache;
    @RequestMapping("/setRedis")
    private void setRedis(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name","123");
        map.put("test","345");
        redisCache.setCacheMap(RedisKey.TEST_001,map);
    }
    @RequestMapping("/getRedis")
    private Map<String,Object> getRedis(){
        Map<String,Object> map = redisCache.getCacheMap(RedisKey.TEST_001);
        redisCache.deleteObject(RedisKey.TEST_001);
        return map;
    }

    private String topicName="kc-kj-web-test";

    @RequestMapping("/cotextLoads")
    public void cotextLoads() {
        kafkaProducerService.sendMessage(topicName, "测试");
    }

    @RequestMapping("/sendMessage1")
    public void sendMessage1() {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topicName, 0, System.currentTimeMillis(), "topic-key", "测试");
        producerRecord.headers().add("user", "zhangsan".getBytes());
        kafkaProducerService.sendMessage(producerRecord);
    }

    @RequestMapping("/sendMessage2")
    public void sendMessage2() {
        String event = "测试";
        Map<String, Object> map = new HashMap<>();
        map.put("user", "zhangsan");
        MessageHeaders headers = new MessageHeaders(map);
        Message<String> message = MessageBuilder.createMessage(event, headers);
        kafkaTemplate.setDefaultTopic(topicName);
        kafkaProducerService.sendMessage(message);
    }
}
