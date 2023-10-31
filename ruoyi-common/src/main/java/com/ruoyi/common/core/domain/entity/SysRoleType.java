package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色类型表 sys_role_type
 *
 * @author ruoyi
 */
public class SysRoleType extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Long parentId;
    private String delFlag;
    private List<SysRoleType> children = new ArrayList<SysRoleType>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public List<SysRoleType> getChildren() {
        return children;
    }

    public void setChildren(List<SysRoleType> children) {
        this.children = children;
    }
}
