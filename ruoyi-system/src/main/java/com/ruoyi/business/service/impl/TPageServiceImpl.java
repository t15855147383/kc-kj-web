package com.ruoyi.business.service.impl;

import java.util.List;

import com.ruoyi.business.domain.TPage;
import com.ruoyi.business.mapper.TPageMapper;
import com.ruoyi.business.service.ITPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 页面配置Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-11
 */
@Service
public class TPageServiceImpl implements ITPageService {
    @Autowired
    private TPageMapper tPageMapper;


    @Override
    public TPage selectTPageById(Long id) {
        return tPageMapper.selectTPageById(id);
    }

    @Override
    public List<TPage> selectTPageList(TPage tPage) {
        return tPageMapper.selectTPageList(tPage);
    }

    @Override
    public int insertTPage(TPage tPage) {
        return tPageMapper.insertTPage(tPage);
    }

    @Override
    public int updateTPage(TPage tPage) {
        return tPageMapper.updateTPage(tPage);
    }

    @Override
    public int deleteTPageByIds(Long[] ids) {
        return tPageMapper.deleteTPageByIds(ids);
    }

    @Override
    public int deleteTPageById(Long id) {
        return tPageMapper.deleteTPageById(id);
    }
}
