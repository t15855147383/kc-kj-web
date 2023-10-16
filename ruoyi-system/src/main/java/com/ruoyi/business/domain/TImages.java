package com.ruoyi.business.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 产品图片对象 t_images
 *
 * @author ruoyi
 * @date 2023-10-11
 */
public class TImages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 外键ID */
    private Long fromId;

    /** 外键类型 */
    private String fromType;
    private String name;

    /** 地址 */
    private String url;

    /** 宽度（px） */
    private BigDecimal width;

    /** 高度（px） */
    private BigDecimal height;

    /** 大小（kb） */
    private BigDecimal size;

    /** 类型 */
    private String mime;

    /** 后缀 */
    private String suffix;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setFromId(Long fromId)
    {
        this.fromId = fromId;
    }

    public Long getFromId()
    {
        return fromId;
    }
    public void setFromType(String fromType)
    {
        this.fromType = fromType;
    }

    public String getFromType()
    {
        return fromType;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setWidth(BigDecimal width)
    {
        this.width = width;
    }

    public BigDecimal getWidth()
    {
        return width;
    }
    public void setHeight(BigDecimal height)
    {
        this.height = height;
    }

    public BigDecimal getHeight()
    {
        return height;
    }
    public void setSize(BigDecimal size)
    {
        this.size = size;
    }

    public BigDecimal getSize()
    {
        return size;
    }
    public void setMime(String mime)
    {
        this.mime = mime;
    }

    public String getMime()
    {
        return mime;
    }
    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
