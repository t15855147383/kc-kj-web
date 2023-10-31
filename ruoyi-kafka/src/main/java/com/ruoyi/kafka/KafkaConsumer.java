package com.ruoyi.kafka;
import com.ruoyi.kafka.service.KafkaConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private KafkaConsumerService kafkaConsumerService;
    /*@KafkaListener(topics = "${kafka.topics01}",groupId = "${kafka.groupId}")
    public void kckjbase(ConsumerRecord<String, String> record) {
        logger.info("接收到kafka消息键为:{},消息值为:{},消息头为:{},消息分区为:{},消息主题为:{}", record.key(), record.value(), record.headers(), record.partition(), record.topic());

    }
    @KafkaListener(topics = "${kafka.topics02}",groupId = "${kafka.groupId}")
    public void kckjactivity(ConsumerRecord<String, String> record) {
        logger.info("接收到kafka消息键为:{},消息值为:{},消息头为:{},消息分区为:{},消息主题为:{}", record.key(), record.value(), record.headers(), record.partition(), record.topic());

    }
    @KafkaListener(topics = "${kafka.topics03}",groupId = "${kafka.groupId}")
    public void mysql01(ConsumerRecord<String, String> record) {
        logger.info("接收到kafka消息键为:{},消息值为:{},消息头为:{},消息分区为:{},消息主题为:{}", record.key(), record.value(), record.headers(), record.partition(), record.topic());
        JSONObject json = JSONObject.parseObject(record.value());
        MysqlMessagePOJO message = json.toJavaObject(MysqlMessagePOJO.class);
        System.out.println(message.toString());
    }*/
}
