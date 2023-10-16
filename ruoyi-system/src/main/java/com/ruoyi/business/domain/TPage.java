package com.ruoyi.business.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 页面配置对象 t_page
 *
 * @author ruoyi
 * @date 2023-10-11
 */
public class TPage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 名称 */
    private String name;

    /** 页面地址 */
    private String url;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
}
