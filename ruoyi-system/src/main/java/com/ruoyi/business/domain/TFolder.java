package com.ruoyi.business.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文件夹对象 t_folder
 *
 * @author ruoyi
 * @date 2023-10-11
 */
public class TFolder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    private Long userId;

    private String name;
    private Integer num;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
