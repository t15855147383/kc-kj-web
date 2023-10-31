package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRoleType;
import com.ruoyi.system.service.ISysRoleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色类型信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/roleType")
public class SysRoleTypeController extends BaseController {
    @Autowired
    private ISysRoleTypeService sysRoleTypeService;
    @GetMapping(value = "/tree")
    public AjaxResult tree()
    {
        return AjaxResult.success(sysRoleTypeService.selectRoleTypeTreeList(new SysRoleType()));
    }
    @PostMapping
    public AjaxResult add(@RequestBody SysRoleType roleType)
    {
        return toAjax(sysRoleTypeService.insertRoleType(roleType));
    }
    @PutMapping
    public AjaxResult edit(@RequestBody SysRoleType roleType)
    {
        return toAjax(sysRoleTypeService.updateRoleType(roleType));
    }
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(sysRoleTypeService.deleteRoleTypeById(id));
    }
}
