package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.TImages;
import com.ruoyi.business.mapper.TImagesMapper;
import com.ruoyi.business.service.ITImagesService;
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
public class TImagesServiceImpl implements ITImagesService {

    @Autowired
    private TImagesMapper tImagesMapper;

    @Override
    public List<TImages> selectTImagesList(Long folderId) {
        return tImagesMapper.selectTImagesList(folderId);
    }

    @Override
    public TImages selectTImagesById(Long id) {
        return tImagesMapper.selectTImagesById(id);
    }

    @Override
    public int insertTImages(TImages tImages) {
        return tImagesMapper.insertTImages(tImages);
    }

    @Override
    public int deleteTImagesByIds(Long[] ids) {
        return tImagesMapper.deleteTImagesByIds(ids);
    }
}
