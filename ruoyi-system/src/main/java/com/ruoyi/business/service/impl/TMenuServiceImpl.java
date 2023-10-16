package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.TMenu;
import com.ruoyi.business.mapper.TMenuMapper;
import com.ruoyi.business.service.ITMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导航菜单Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-10
 */
@Service
public class TMenuServiceImpl implements ITMenuService {
    @Autowired
    private TMenuMapper tMenuMapper;

    @Override
    public TMenu selectTMenuById(Long id) {
        return tMenuMapper.selectTMenuById(id);
    }

    @Override
    public List<TMenu> selectTMenuList(TMenu tMenu) {
        tMenu.setParentId(0L);
        List<TMenu> list1 = tMenuMapper.selectTMenuList(tMenu);
        if(list1 != null && !list1.isEmpty()){
            for(int i=0;i<list1.size();i++){
                list1.get(i).setLevel(1);
                tMenu.setParentId(list1.get(i).getId());
                List<TMenu> list2 = tMenuMapper.selectTMenuList(tMenu);
                if(list2 != null && !list2.isEmpty()){
                    for(int j=0;j<list2.size();j++){
                        list2.get(j).setLevel(2);
                        tMenu.setParentId(list2.get(j).getId());
                        List<TMenu> list3 = tMenuMapper.selectTMenuList(tMenu);
                        if(list3 != null && !list3.isEmpty()) {
                            for (int n = 0; n < list3.size(); n++) {
                                list3.get(n).setLevel(3);
                            }
                        }
                        list2.get(j).setChildren(list3);
                    }
                }
                list1.get(i).setChildren(list2);
            }
        }
        return list1;
    }

    @Override
    public List<TMenu> selectTMenuAll(TMenu tMenu) {
        return tMenuMapper.selectTMenuList(tMenu);
    }

    @Override
    public int insertTMenu(TMenu tMenu) {
        return tMenuMapper.insertTMenu(tMenu);
    }

    @Override
    public int updateTMenu(TMenu tMenu) {
        return tMenuMapper.updateTMenu(tMenu);
    }

    @Override
    public int deleteTMenuByIds(Long[] ids) {
        return tMenuMapper.deleteTMenuByIds(ids);
    }

    @Override
    public int deleteTMenuById(Long id) {
        return tMenuMapper.deleteTMenuById(id);
    }
}
