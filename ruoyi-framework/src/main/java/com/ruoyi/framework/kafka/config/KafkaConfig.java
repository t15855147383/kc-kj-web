package com.ruoyi.framework.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {
    private String host;
    private String port;
    private String groupId;
    private String topics;
    private String offsetReset;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getOffsetReset() {
        return offsetReset;
    }

    public void setOffsetReset(String offsetReset) {
        this.offsetReset = offsetReset;
    }
}
