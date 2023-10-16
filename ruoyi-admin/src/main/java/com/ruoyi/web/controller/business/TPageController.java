package com.ruoyi.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.business.domain.TPage;
import com.ruoyi.business.service.ITPageService;
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
 * 页面配置Controller
 *
 * @author ruoyi
 * @date 2023-10-11
 */
@RestController
@RequestMapping("/business/page")
public class TPageController extends BaseController
{
    @Autowired
    private ITPageService tPageService;

    /**
     * 查询页面配置列表
     */
    @PreAuthorize("@ss.hasPermi('business:page:list')")
    @GetMapping("/list")
    public TableDataInfo list(TPage tPage)
    {
        startPage();
        List<TPage> list = tPageService.selectTPageList(tPage);
        return getDataTable(list);
    }

    @GetMapping("/all")
    public AjaxResult all(TPage tPage)
    {
        List<TPage> list = tPageService.selectTPageList(tPage);
        return AjaxResult.success(list);
    }

    /**
     * 获取页面配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:page:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tPageService.selectTPageById(id));
    }

    /**
     * 新增页面配置
     */
    @PreAuthorize("@ss.hasPermi('business:page:add')")
    @Log(title = "页面配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TPage tPage)
    {
        return toAjax(tPageService.insertTPage(tPage));
    }

    /**
     * 修改页面配置
     */
    @PreAuthorize("@ss.hasPermi('business:page:edit')")
    @Log(title = "页面配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TPage tPage)
    {
        return toAjax(tPageService.updateTPage(tPage));
    }

    /**
     * 删除页面配置
     */
    @PreAuthorize("@ss.hasPermi('business:page:remove')")
    @Log(title = "页面配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tPageService.deleteTPageByIds(ids));
    }
}
