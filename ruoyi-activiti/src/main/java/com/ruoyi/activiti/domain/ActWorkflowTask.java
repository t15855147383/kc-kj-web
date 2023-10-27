package com.ruoyi.activiti.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class ActWorkflowTask extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String id;
    private String processId;
    private String elementId;
    private String elementName;
    private String sort;
    private int assigneeCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getAssigneeCount() {
        return assigneeCount;
    }

    public void setAssigneeCount(int assigneeCount) {
        this.assigneeCount = assigneeCount;
    }
}
