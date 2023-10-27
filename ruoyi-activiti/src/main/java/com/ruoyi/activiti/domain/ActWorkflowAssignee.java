package com.ruoyi.activiti.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class ActWorkflowAssignee extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String id;
    private String taskId;
    private String type;
    private String userId;
    private String postId;
    private String roleId;
    private String userOut;
    private String userIn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserOut() {
        return userOut;
    }

    public void setUserOut(String userOut) {
        this.userOut = userOut;
    }

    public String getUserIn() {
        return userIn;
    }

    public void setUserIn(String userIn) {
        this.userIn = userIn;
    }
}
