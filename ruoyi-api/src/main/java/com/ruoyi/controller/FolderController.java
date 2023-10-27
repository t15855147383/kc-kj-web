package com.ruoyi.controller;

import com.ruoyi.business.domain.TFolder;
import com.ruoyi.business.service.ITFolderService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/folder")
public class FolderController extends BaseController {
    @Autowired
    private ITFolderService tFolderService;
    @PostMapping("/add")
    public AjaxResult add(TFolder tFolder) {
        return AjaxResult.success(tFolderService.insertTFolder(tFolder));
    }
    @PostMapping("/edit")
    public AjaxResult edit(TFolder tFolder) {
        return AjaxResult.success(tFolderService.updateTFolder(tFolder));
    }
    @PostMapping("/del")
    public AjaxResult del(TFolder tFolder) {
        return AjaxResult.success(tFolderService.deleteTFolderById(tFolder.getId()));
    }
    @RequestMapping("/list")
    public AjaxResult list(@RequestParam("userId") Long userId) {
        return AjaxResult.success(tFolderService.selectTFolderList(userId));
    }
    @RequestMapping("/detail")
    public AjaxResult detail(@RequestParam("id") Long id) {
        return AjaxResult.success(tFolderService.selectTFolderById(id));
    }
}
