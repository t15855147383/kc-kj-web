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
     * @param fromId 外键Id
     * @param fromType 外键类型
     * @return 产品图片
     */
    public List<TImages> selectTImagesList(@Param("fromId") Long fromId, @Param("fromType") String fromType);
    public TImages selectTImagesMap(@Param("fromId") Long fromId, @Param("fromType") String fromType);
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
     * @param fromId 外键Id
     * @param fromType 外键类型
     * @return 结果
     */
    public int deleteTImagesById(@Param("fromId") Long fromId,@Param("fromType") String fromType);
}
