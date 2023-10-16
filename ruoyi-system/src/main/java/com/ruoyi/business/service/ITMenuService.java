package com.ruoyi.business.service;

import com.ruoyi.business.domain.TMenu;

import java.util.List;

/**
 * 导航菜单Service接口
 *
 * @author ruoyi
 * @date 2023-10-10
 */
public interface ITMenuService {
    /**
     * 查询导航菜单
     *
     * @param id 导航菜单主键
     * @return 导航菜单
     */
    public TMenu selectTMenuById(Long id);

    /**
     * 查询导航菜单列表
     *
     * @param tMenu 导航菜单
     * @return 导航菜单集合
     */
    public List<TMenu> selectTMenuList(TMenu tMenu);
    public List<TMenu> selectTMenuAll(TMenu tMenu);

    /**
     * 新增导航菜单
     *
     * @param tMenu 导航菜单
     * @return 结果
     */
    public int insertTMenu(TMenu tMenu);

    /**
     * 修改导航菜单
     *
     * @param tMenu 导航菜单
     * @return 结果
     */
    public int updateTMenu(TMenu tMenu);

    /**
     * 批量删除导航菜单
     *
     * @param ids 需要删除的导航菜单主键集合
     * @return 结果
     */
    public int deleteTMenuByIds(Long[] ids);

    /**
     * 删除导航菜单信息
     *
     * @param id 导航菜单主键
     * @return 结果
     */
    public int deleteTMenuById(Long id);
}
