package com.lc.web.controller.car;

import com.lc.car.domain.CarUser;
import com.lc.car.service.ICarUserService;
import com.lc.common.annotation.Log;
import com.lc.common.core.controller.BaseController;
import com.lc.common.core.domain.AjaxResult;
import com.lc.common.core.page.TableDataInfo;
import com.lc.common.enums.BusinessType;
import com.lc.common.utils.poi.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户表Controller
 *
 * @author lc
 * @date 2022-10-14
 */
@RestController
@RequestMapping("/car/user")
public class CarUserController extends BaseController {
    @Autowired
    private ICarUserService carUserService;

    /**
     * 多条件+分页查询用户信息列表
     *
     * @param carUser
     * @return
     */
    @ApiOperation("查询用户表列表")
    @PreAuthorize("@ss.hasPermi('car:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(CarUser carUser) {
        startPage();
        List<CarUser> list = carUserService.selectCarUserList(carUser);
        return getDataTable(list);
    }

    /**
     * 导出用户表列表
     */
    @PreAuthorize("@ss.hasPermi('car:user:export')")
    @Log(title = "用户表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CarUser carUser) {
        List<CarUser> list = carUserService.selectCarUserList(carUser);
        ExcelUtil<CarUser> util = new ExcelUtil<CarUser>(CarUser.class);
        util.exportExcel(response, list, "用户表数据");
    }

    /**
     * 获取用户表详细信息
     */
    @PreAuthorize("@ss.hasPermi('car:user:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId) {
        return AjaxResult.success(carUserService.selectCarUserByUserId(userId));
    }

    /**
     * 新增用户表
     */
    @PreAuthorize("@ss.hasPermi('car:user:add')")
    @Log(title = "用户表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CarUser carUser) {
        return toAjax(carUserService.insertCarUser(carUser));
    }

    /**
     * 修改用户表
     */
    @ApiOperation("修改用户表")
    @PreAuthorize("@ss.hasPermi('car:user:edit')")
    @Log(title = "用户表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CarUser carUser) {
        return toAjax(carUserService.updateCarUser(carUser));
    }

    /**
     * 删除用户表
     */
    @PreAuthorize("@ss.hasPermi('car:user:remove')")
    @Log(title = "用户表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(carUserService.deleteCarUserByUserIds(userIds));
    }
}
