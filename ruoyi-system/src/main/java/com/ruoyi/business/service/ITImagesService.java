package com.ruoyi.business.service;

import com.ruoyi.business.domain.TImages;
import java.util.List;

/**
 * 图片Service接口
 *
 * @author ruoyi
 * @date 2023-10-10
 */
public interface ITImagesService {
    /**
     * 查询产品图片
     *
     * @param folderId 外键Id
     * @return 产品图片
     */
    public List<TImages> selectTImagesList(Long folderId);
    public TImages selectTImagesById(Long id);
    /**
     * 新增产品图片
     *
     * @param tImages 产品图片
     * @return 结果
     */
    public int insertTImages(TImages tImages);

    /**
     * 删除产品图片
     *
     * @param ids id
     * @return 结果
     */
    public int deleteTImagesByIds(Long[] ids);
}
