package com.ruoyi.business.service;

import com.ruoyi.business.domain.TProduct;

import java.util.List;

/**
 * 产品Service接口
 *
 * @author ruoyi
 * @date 2023-10-11
 */
public interface ITProductService {
    /**
     * 查询产品
     *
     * @param id 产品主键
     * @return 产品
     */
    public TProduct selectTProductById(Long id);

    /**
     * 查询产品列表
     *
     * @param tProduct 产品
     * @return 产品集合
     */
    public List<TProduct> selectTProductList(TProduct tProduct);

    /**
     * 新增产品
     *
     * @param tProduct 产品
     * @return 结果
     */
    public int insertTProduct(TProduct tProduct);

    /**
     * 修改产品
     *
     * @param tProduct 产品
     * @return 结果
     */
    public int updateTProduct(TProduct tProduct);

    /**
     * 批量删除产品
     *
     * @param ids 需要删除的产品主键集合
     * @return 结果
     */
    public int deleteTProductByIds(Long[] ids);

    /**
     * 删除产品信息
     *
     * @param id 产品主键
     * @return 结果
     */
    public int deleteTProductById(Long id);
}
