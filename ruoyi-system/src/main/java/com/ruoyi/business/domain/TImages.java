package com.ruoyi.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

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
    private Long folderId;
    private String name;

    /** 地址 */
    private String url;
    /** 源图地址 */
    private String sourceUrl;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date originalTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getOriginalTime() {
        return originalTime;
    }

    public void setOriginalTime(Date originalTime) {
        this.originalTime = originalTime;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
