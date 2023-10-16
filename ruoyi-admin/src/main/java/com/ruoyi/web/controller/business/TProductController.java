package com.ruoyi.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.business.domain.TProduct;
import com.ruoyi.business.service.ITProductService;
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
 * 产品Controller
 *
 * @author ruoyi
 * @date 2023-10-11
 */
@RestController
@RequestMapping("/business/product")
public class TProductController extends BaseController
{
    @Autowired
    private ITProductService tProductService;

    /**
     * 查询产品列表
     */
    @PreAuthorize("@ss.hasPermi('business:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(TProduct tProduct)
    {
        startPage();
        List<TProduct> list = tProductService.selectTProductList(tProduct);
        return getDataTable(list);
    }

    /**
     * 获取产品详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:product:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tProductService.selectTProductById(id));
    }

    /**
     * 新增产品
     */
    @PreAuthorize("@ss.hasPermi('business:product:add')")
    @Log(title = "产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TProduct tProduct)
    {
        tProduct.setCreateBy(this.getUsername());
        tProduct.setCreateTime(DateUtils.getNowDate());
        return toAjax(tProductService.insertTProduct(tProduct));
    }

    /**
     * 修改产品
     */
    @PreAuthorize("@ss.hasPermi('business:product:edit')")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProduct tProduct)
    {
        tProduct.setUpdateBy(this.getUsername());
        tProduct.setUpdateTime(DateUtils.getNowDate());
        return toAjax(tProductService.updateTProduct(tProduct));
    }

    /**
     * 删除产品
     */
    @PreAuthorize("@ss.hasPermi('business:product:remove')")
    @Log(title = "产品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tProductService.deleteTProductByIds(ids));
    }
}
