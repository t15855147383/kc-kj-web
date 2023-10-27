package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.TFolder;
import com.ruoyi.business.mapper.TFolderMapper;
import com.ruoyi.business.service.ITFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件夹Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-10
 */
@Service
public class TFolderServiceImpl implements ITFolderService {
    @Autowired
    private TFolderMapper tFolderMapper;

    @Override
    public List<TFolder> selectTFolderList(Long userId) {
        return tFolderMapper.selectTFolderList(userId);
    }

    @Override
    public TFolder selectTFolderById(Long id) {
        return tFolderMapper.selectTFolderById(id);
    }

    @Override
    public int insertTFolder(TFolder tFolder) {
        return tFolderMapper.insertTFolder(tFolder);
    }

    @Override
    public int updateTFolder(TFolder tFolder) {
        return tFolderMapper.updateTFolder(tFolder);
    }

    @Override
    public int deleteTFolderById(Long id) {
        return tFolderMapper.deleteTFolderById(id);
    }
}
