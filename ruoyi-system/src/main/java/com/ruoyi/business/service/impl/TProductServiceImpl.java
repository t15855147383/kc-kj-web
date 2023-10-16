package com.ruoyi.business.service.impl;

import java.util.List;

import com.ruoyi.business.domain.TImages;
import com.ruoyi.business.domain.TProduct;
import com.ruoyi.business.mapper.TImagesMapper;
import com.ruoyi.business.mapper.TProductMapper;
import com.ruoyi.business.service.ITProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-11
 */
@Service
public class TProductServiceImpl implements ITProductService
{
    @Autowired
    private TProductMapper tProductMapper;
    @Autowired
    private TImagesMapper tImagesMapper;

    @Override
    public TProduct selectTProductById(Long id) {
        TProduct product = tProductMapper.selectTProductById(id);
        product.setImages(tImagesMapper.selectTImagesList(id,"product_images"));
        return product;
    }

    @Override
    public List<TProduct> selectTProductList(TProduct tProduct) {
        return tProductMapper.selectTProductList(tProduct);
    }

    @Override
    public int insertTProduct(TProduct tProduct) {
        int row = tProductMapper.insertTProduct(tProduct);
        if(tProduct.getImages() != null && !tProduct.getImages().isEmpty()){
            for(TImages image:tProduct.getImages()){
                image.setFromId(tProduct.getId());
                image.setFromType("product_images");
                tImagesMapper.insertTImages(image);
            }
        }
        return row;
    }

    @Override
    public int updateTProduct(TProduct tProduct) {
        tImagesMapper.deleteTImagesById(tProduct.getId(),"product_images");
        if(tProduct.getImages() != null && !tProduct.getImages().isEmpty()){
            for(TImages image:tProduct.getImages()){
                image.setFromId(tProduct.getId());
                image.setFromType("product_images");
                tImagesMapper.insertTImages(image);
            }
        }
        return tProductMapper.updateTProduct(tProduct);
    }

    @Override
    public int deleteTProductByIds(Long[] ids) {
        return tProductMapper.deleteTProductByIds(ids);
    }

    @Override
    public int deleteTProductById(Long id) {
        return tProductMapper.deleteTProductById(id);
    }
}
