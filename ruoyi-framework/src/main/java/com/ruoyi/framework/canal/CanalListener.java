package com.ruoyi.framework.canal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CanalListener {
    private static final Logger logger = LoggerFactory.getLogger(CanalListener.class);
    @Autowired
    private CanalMessage canalMessage;

    /**
     * 启动服务
     */
    @PostConstruct
    public void start() {
        //canalMessage.start();
    }
}
