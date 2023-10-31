package com.ruoyi.activiti.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class ActWorkflowAssignee extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String taskId;
    private String assigneeType;
    private String assigneeValue;
    private String sponsorType;
    private String sponsorValue;
    private String sponsorUserOut;
    private String sponsorUserIn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAssigneeType() {
        return assigneeType;
    }

    public void setAssigneeType(String assigneeType) {
        this.assigneeType = assigneeType;
    }

    public String getAssigneeValue() {
        return assigneeValue;
    }

    public void setAssigneeValue(String assigneeValue) {
        this.assigneeValue = assigneeValue;
    }

    public String getSponsorType() {
        return sponsorType;
    }

    public void setSponsorType(String sponsorType) {
        this.sponsorType = sponsorType;
    }

    public String getSponsorValue() {
        return sponsorValue;
    }

    public void setSponsorValue(String sponsorValue) {
        this.sponsorValue = sponsorValue;
    }

    public String getSponsorUserOut() {
        return sponsorUserOut;
    }

    public void setSponsorUserOut(String sponsorUserOut) {
        this.sponsorUserOut = sponsorUserOut;
    }

    public String getSponsorUserIn() {
        return sponsorUserIn;
    }

    public void setSponsorUserIn(String sponsorUserIn) {
        this.sponsorUserIn = sponsorUserIn;
    }
}
