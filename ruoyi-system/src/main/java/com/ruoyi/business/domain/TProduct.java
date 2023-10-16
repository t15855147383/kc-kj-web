package com.ruoyi.business.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 产品对象 t_product
 *
 * @author ruoyi
 * @date 2023-10-11
 */
public class TProduct extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 菜单ID */
    private Long manuId;

    /** 标题 */
    private String title;

    /** 标签 */
    private String lable;

    /** 描述 */
    private String desc;

    /** 功能特点 */
    private String detail1;

    /** 功能清单 */
    private String detail2;

    /** 在线预览 */
    private String detail3;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private List<TImages> images;
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setManuId(Long manuId)
    {
        this.manuId = manuId;
    }

    public Long getManuId()
    {
        return manuId;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setLable(String lable)
    {
        this.lable = lable;
    }

    public String getLable()
    {
        return lable;
    }
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }
    public void setDetail1(String detail1)
    {
        this.detail1 = detail1;
    }

    public String getDetail1()
    {
        return detail1;
    }
    public void setDetail2(String detail2)
    {
        this.detail2 = detail2;
    }

    public String getDetail2()
    {
        return detail2;
    }
    public void setDetail3(String detail3)
    {
        this.detail3 = detail3;
    }

    public String getDetail3()
    {
        return detail3;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public List<TImages> getImages() {
        return images;
    }

    public void setImages(List<TImages> images) {
        this.images = images;
    }
}
