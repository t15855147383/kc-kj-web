package com.ruoyi.business.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 导航菜单对象 t_menu
 *
 * @author ruoyi
 * @date 2023-10-10
 */
public class TMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 上级ID */
    private Long parentId;

    /** 名称 */
    private String name;

    /** 页面ID */
    private Long pageId;

    /** 参数 */
    private String param;
    /** 序号 */
    private Integer sort;
    /** 级别 */
    private Integer level;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
    private List<TMenu> children;
    private TPage page;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public List<TMenu> getChildren() {
        return children;
    }

    public void setChildren(List<TMenu> children) {
        this.children = children;
    }

    public TPage getPage() {
        return page;
    }

    public void setPage(TPage page) {
        this.page = page;
    }
}
