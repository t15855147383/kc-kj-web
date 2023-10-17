package com.ruoyi.framework.kafka.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MysqlMessagePOJO implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Map<String,Object>> data;
    private List<String> pkNames;
    private List<Map<String,Object>> old;
    private String type;
    private String es;
    private String sql;
    private String database;
    private Map<String,Object> sqlType;
    private Map<String,Object> mysqlType;
    private String gtid;
    private Long id;
    private Boolean isDdl;
    private String table;
    private Long ts;

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public List<String> getPkNames() {
        return pkNames;
    }

    public void setPkNames(List<String> pkNames) {
        this.pkNames = pkNames;
    }

    public List<Map<String, Object>> getOld() {
        return old;
    }

    public void setOld(List<Map<String, Object>> old) {
        this.old = old;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEs() {
        return es;
    }

    public void setEs(String es) {
        this.es = es;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Map<String, Object> getSqlType() {
        return sqlType;
    }

    public void setSqlType(Map<String, Object> sqlType) {
        this.sqlType = sqlType;
    }

    public Map<String, Object> getMysqlType() {
        return mysqlType;
    }

    public void setMysqlType(Map<String, Object> mysqlType) {
        this.mysqlType = mysqlType;
    }

    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDdl() {
        return isDdl;
    }

    public void setDdl(Boolean ddl) {
        isDdl = ddl;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "MysqlMessagePOJO{" +
                "data=" + data +
                ", pkNames=" + pkNames +
                ", old=" + old +
                ", type='" + type + '\'' +
                ", es='" + es + '\'' +
                ", sql='" + sql + '\'' +
                ", database='" + database + '\'' +
                ", sqlType=" + sqlType +
                ", mysqlType=" + mysqlType +
                ", gtid='" + gtid + '\'' +
                ", id=" + id +
                ", isDdl=" + isDdl +
                ", table='" + table + '\'' +
                ", ts=" + ts +
                '}';
    }
}
