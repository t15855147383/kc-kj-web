package com.ruoyi.framework.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.ruoyi.framework.canal.config.CanalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

@Component
public class CanalListener {
    private static final Logger logger = LoggerFactory.getLogger(CanalListener.class);
    @Autowired
    private CanalConfig canalConfig;
    @Autowired
    private CanalMessage canalMessage;

    /**
     * 启动服务
     */
    @PostConstruct
    @Async("TaskPool")
    public void start() {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(canalConfig.getHost(), canalConfig.getPost()), "example", "", "");
        try {
            //打开连接
            connector.connect();
            logger.info("数据库检测连接成功!{}; 线程睡眠：{}秒。",canalConfig.getTableName(),canalConfig.getSleep());
            //订阅数据库表,全部表q
            connector.subscribe(canalConfig.getTableName() + "\\..*");
            //回滚到未进行ack的地方，下次fetch的时候，可以从最后一个没有ack的地方开始拿
            connector.rollback();
            while (true) {
                this.message(connector);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("成功断开监测连接!尝试重连");
        } finally {
            connector.disconnect();

        }
    }
    public void message(CanalConnector connector){
        try{
            // 获取指定数量的数据
            Message message = connector.getWithoutAck(canalConfig.getBatchSize());
            long batchId = message.getId();
            int size = message.getEntries().size();
            if (batchId == -1 || size == 0) {
            } else {
                canalMessage.handleDATAChange(message.getEntries());
            }
            // 提交确认
            connector.ack(batchId);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            //防止频繁访问数据库链接: 线程睡眠 ? 秒
            try {
                Thread.sleep(canalConfig.getSleep() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
