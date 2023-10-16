package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.TPage;

import java.util.List;

/**
 * 页面配置Mapper接口
 *
 * @author ruoyi
 * @date 2023-10-11
 */
public interface TPageMapper {
    /**
     * 查询页面配置
     *
     * @param id 页面配置主键
     * @return 页面配置
     */
    public TPage selectTPageById(Long id);

    /**
     * 查询页面配置列表
     *
     * @param tPage 页面配置
     * @return 页面配置集合
     */
    public List<TPage> selectTPageList(TPage tPage);

    /**
     * 新增页面配置
     *
     * @param tPage 页面配置
     * @return 结果
     */
    public int insertTPage(TPage tPage);

    /**
     * 修改页面配置
     *
     * @param tPage 页面配置
     * @return 结果
     */
    public int updateTPage(TPage tPage);

    /**
     * 删除页面配置
     *
     * @param id 页面配置主键
     * @return 结果
     */
    public int deleteTPageById(Long id);

    /**
     * 批量删除页面配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTPageByIds(Long[] ids);
}
