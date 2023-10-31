package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysRoleType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.SysRoleTypeMapper;
import com.ruoyi.system.service.ISysRoleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色类型 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysRoleTypeServiceImpl implements ISysRoleTypeService {

    @Autowired
    private SysRoleTypeMapper sysRoleTypeMapper;

    @Override
    public List<TreeSelect> selectRoleTypeTreeList(SysRoleType roleType) {
        List<SysRoleType> depts = selectRoleTypeList(roleType);
        return buildRoleTypeTreeSelect(depts);
    }

    @Override
    public List<SysRoleType> selectRoleTypeList(SysRoleType roleType) {
        return sysRoleTypeMapper.selectRoleTypeList(roleType);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param datas 列表
     * @return 树结构列表
     */
    public List<SysRoleType> buildRoleTypeTree(List<SysRoleType> datas)
    {
        List<SysRoleType> returnList = new ArrayList<SysRoleType>();
        List<Long> tempList = datas.stream().map(SysRoleType::getId).collect(Collectors.toList());
        for (SysRoleType data : datas)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(data.getParentId()))
            {
                recursionFn(datas, data);
                returnList.add(data);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = datas;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param datas 列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildRoleTypeTreeSelect(List<SysRoleType> datas)
    {
        List<SysRoleType> dataTrees = buildRoleTypeTree(datas);
        return dataTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    /**
     * 递归列表
     */
    private void recursionFn(List<SysRoleType> list, SysRoleType t)
    {
        // 得到子节点列表
        List<SysRoleType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysRoleType tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<SysRoleType> getChildList(List<SysRoleType> list, SysRoleType t)
    {
        List<SysRoleType> tlist = new ArrayList<SysRoleType>();
        Iterator<SysRoleType> it = list.iterator();
        while (it.hasNext())
        {
            SysRoleType n = (SysRoleType) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysRoleType> list, SysRoleType t)
    {
        return getChildList(list, t).size() > 0;
    }

    @Override
    public int insertRoleType(SysRoleType roleType) {
        return sysRoleTypeMapper.insertRoleType(roleType);
    }

    @Override
    public int updateRoleType(SysRoleType roleType) {
        return sysRoleTypeMapper.updateRoleType(roleType);
    }

    @Override
    public int deleteRoleTypeById(Long id) {
        return sysRoleTypeMapper.deleteRoleTypeById(id);
    }
}
