package com.ruoyi.web.controller.business;

import java.util.List;
import com.ruoyi.business.domain.TMenu;
import com.ruoyi.business.service.ITMenuService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 导航菜单Controller
 *
 * @author ruoyi
 * @date 2023-10-10
 */
@RestController
@RequestMapping("/business/menu")
public class TMenuController extends BaseController {
    @Autowired
    private ITMenuService tMenuService;

    /**
     * 查询导航菜单列表
     */
    @PreAuthorize("@ss.hasPermi('business:menu:list')")
    @GetMapping("/list")
    public TableDataInfo list(TMenu tMenu)
    {
        List<TMenu> list = tMenuService.selectTMenuList(tMenu);
        return getDataTable(list);
    }

    @GetMapping("/all")
    public AjaxResult all(TMenu tMenu)
    {
        List<TMenu> list = tMenuService.selectTMenuAll(tMenu);
        return AjaxResult.success(list);
    }

    /**
     * 获取导航菜单详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:menu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tMenuService.selectTMenuById(id));
    }

    /**
     * 新增导航菜单
     */
    @PreAuthorize("@ss.hasPermi('business:menu:add')")
    @Log(title = "导航菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TMenu tMenu)
    {
        tMenu.setCreateBy(this.getUsername());
        tMenu.setCreateTime(DateUtils.getNowDate());
        return toAjax(tMenuService.insertTMenu(tMenu));
    }

    /**
     * 修改导航菜单
     */
    @PreAuthorize("@ss.hasPermi('business:menu:edit')")
    @Log(title = "导航菜单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TMenu tMenu)
    {
        tMenu.setUpdateBy(this.getUsername());
        tMenu.setUpdateTime(DateUtils.getNowDate());
        return toAjax(tMenuService.updateTMenu(tMenu));
    }

    /**
     * 删除导航菜单
     */
    @PreAuthorize("@ss.hasPermi('business:menu:remove')")
    @Log(title = "导航菜单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tMenuService.deleteTMenuByIds(ids));
    }
}
