package com.ruoyi.framework.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

public class CanalEntryBo {
    private String tableName;
    private CanalEntry.EventType eventType;
    private List<CanalEntry.RowData> rowDatas;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public CanalEntry.EventType getEventType() {
        return eventType;
    }

    public void setEventType(CanalEntry.EventType eventType) {
        this.eventType = eventType;
    }

    public List<CanalEntry.RowData> getRowDatas() {
        return rowDatas;
    }

    public void setRowDatas(List<CanalEntry.RowData> rowDatas) {
        this.rowDatas = rowDatas;
    }
}
