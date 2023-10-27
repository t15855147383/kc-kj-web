package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.TImages;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品图片Mapper接口
 *
 * @author ruoyi
 * @date 2023-10-11
 */
public interface TImagesMapper
{
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
