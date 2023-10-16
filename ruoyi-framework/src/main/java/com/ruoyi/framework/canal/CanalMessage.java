package com.ruoyi.framework.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.ruoyi.framework.canal.service.CanalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CanalMessage {
    private static final Logger logger = LoggerFactory.getLogger(CanalMessage.class);
    @Autowired
    private CanalService canalService;
    /**
     * 解析binlog获得的实体类信息
     */
    public void handleDATAChange(List<CanalEntry.Entry> entrys) {
        List<CanalEntryBo> entryBoList = new ArrayList<CanalEntryBo>();
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            //RowChange对象，包含了一行数据变化的所有特征
            CanalEntry.RowChange rowChage;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),e);
            }
            CanalEntryBo entryBo = new CanalEntryBo();
            entryBo.setTableName(entry.getHeader().getTableName());
            entryBo.setEventType(rowChage.getEventType());
            entryBo.setRowDatas(rowChage.getRowDatasList());
            entryBoList.add(entryBo);
        }

    }

    private static void printColumn(List<CanalEntry.Column> columns){
        for (CanalEntry.Column column: columns) {
            //param : 6    update=true
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}
