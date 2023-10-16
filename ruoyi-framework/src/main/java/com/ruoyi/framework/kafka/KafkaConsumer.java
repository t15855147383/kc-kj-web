package com.ruoyi.framework.kafka;

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

    @KafkaListener(topics = "${kafka.topics}",groupId = "${kafka.groupId}")
    public void listenerMessage(ConsumerRecord<String, String> record) {
        logger.info("接收到kafka消息键为:{},消息值为:{},消息头为:{},消息分区为:{},消息主题为:{}", record.key(), record.value(), record.headers(), record.partition(), record.topic());
    }
}
