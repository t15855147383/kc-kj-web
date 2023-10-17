package com.ruoyi.framework.kafka;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.framework.kafka.config.MysqlMessagePOJO;
import com.ruoyi.framework.kafka.service.KafkaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @KafkaListener(topics = "mysql01",groupId = "${kafka.groupId}")
    public void mysql01(ConsumerRecord<String, String> record) {
        logger.info("接收到kafka消息键为:{},消息值为:{},消息头为:{},消息分区为:{},消息主题为:{}", record.key(), record.value(), record.headers(), record.partition(), record.topic());
        JSONObject json = JSONObject.parseObject(record.value());
        MysqlMessagePOJO message = json.toJavaObject(MysqlMessagePOJO.class);
        System.out.println(message.toString());
    }
    @KafkaListener(topics = "kc-kj-base",groupId = "${kafka.groupId}")
    public void kckjbase(ConsumerRecord<String, String> record) {
        logger.info("接收到kafka消息键为:{},消息值为:{},消息头为:{},消息分区为:{},消息主题为:{}", record.key(), record.value(), record.headers(), record.partition(), record.topic());

    }
    @KafkaListener(topics = "kc-kj-activity",groupId = "${kafka.groupId}")
    public void kckjactivity(ConsumerRecord<String, String> record) {
        logger.info("接收到kafka消息键为:{},消息值为:{},消息头为:{},消息分区为:{},消息主题为:{}", record.key(), record.value(), record.headers(), record.partition(), record.topic());

    }
}
