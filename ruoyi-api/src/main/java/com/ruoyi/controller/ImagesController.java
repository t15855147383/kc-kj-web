package com.ruoyi.controller;

import com.ruoyi.business.domain.TFolder;
import com.ruoyi.business.domain.TImages;
import com.ruoyi.business.service.ITImagesService;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
public class ImagesController {
    @Autowired
    private ITImagesService itImagesService;
    @PostMapping("/add")
    public AjaxResult add(TImages tImages) {
        return AjaxResult.success(itImagesService.insertTImages(tImages));
    }
    @DeleteMapping("/{ids}")
    public AjaxResult del(@PathVariable Long[] ids) {
        return AjaxResult.success(itImagesService.deleteTImagesByIds(ids));
    }
    @RequestMapping("/list")
    public AjaxResult list(@RequestParam("folderId") Long folderId) {
        return AjaxResult.success(itImagesService.selectTImagesList(folderId));
    }
    @RequestMapping("/detail")
    public AjaxResult detail(@RequestParam("id") Long id) {
        return AjaxResult.success(itImagesService.selectTImagesById(id));
    }
}
