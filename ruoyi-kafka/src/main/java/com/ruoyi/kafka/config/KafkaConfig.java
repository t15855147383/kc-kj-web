package com.ruoyi.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {
    private String host;
    private String port;
    private String groupId;
    private String topics01;
    private String topics02;
    private String topics03;
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

    public String getTopics01() {
        return topics01;
    }

    public void setTopics01(String topics01) {
        this.topics01 = topics01;
    }

    public String getTopics02() {
        return topics02;
    }

    public void setTopics02(String topics02) {
        this.topics02 = topics02;
    }

    public String getTopics03() {
        return topics03;
    }

    public void setTopics03(String topics03) {
        this.topics03 = topics03;
    }

    public String getOffsetReset() {
        return offsetReset;
    }

    public void setOffsetReset(String offsetReset) {
        this.offsetReset = offsetReset;
    }
}
