package com.ruoyi.activiti.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工作流程配置 act_workflow_process
 * 
 * @author danny
 * @date 2020-11-02
 */
public class ActWorkflowProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识符 */
    private String id;
    private String procDefId;
    private String pass;
    private String reject;
    private String page;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
