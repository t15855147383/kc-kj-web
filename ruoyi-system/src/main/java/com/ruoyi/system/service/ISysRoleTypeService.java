package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRoleType;

import java.util.List;

/**
 * 角色类型业务层
 *
 * @author ruoyi
 */
public interface ISysRoleTypeService {
    public List<TreeSelect> selectRoleTypeTreeList(SysRoleType roleType);

    /**
     * 查询角色类型
     *
     * @param roleType 角色类型信息
     * @return 数据集合信息
     */
    public List<SysRoleType> selectRoleTypeList(SysRoleType roleType);

    /**
     * 新增角色类型
     * @param roleType 角色类型信息
     * @return 受影响行数
     */
    public int insertRoleType(SysRoleType roleType);
    /**
     * 修改角色类型
     * @param roleType 角色类型信息
     * @return 受影响行数
     */
    public int updateRoleType(SysRoleType roleType);
    /**
     * 删除角色类型
     * @param id 角色类型ID
     * @return 受影响行数
     */
    public int deleteRoleTypeById(Long id);
}
