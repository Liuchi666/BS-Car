package com.lc.web.controller.car;

import com.lc.car.domain.CarCarpark;
import com.lc.car.service.ICarCarparkService;
import com.lc.common.annotation.Log;
import com.lc.common.constant.Constants;
import com.lc.common.core.controller.BaseController;
import com.lc.common.core.domain.AjaxResult;
import com.lc.common.core.page.TableDataInfo;
import com.lc.common.enums.BusinessType;
import com.lc.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 停车场Controller
 *
 * @author lc
 * @date 2022-10-14
 */
@Api(tags = "停车场表")
@RestController
@RequestMapping("/car/carpark")
public class CarCarparkController extends BaseController {
    @Autowired
    private ICarCarparkService carCarparkService;

    /**
     * 查询所有数据
     */
    @ApiOperation("查询所有停车场数据")
    @GetMapping("/getAll")
    public AjaxResult getAll() {
        List<CarCarpark> carParkList = carCarparkService.getAll();
        for (CarCarpark carCarpark : carParkList) {
            String photo = carCarpark.getPhoto();
            photo = Constants.PHOTO_PREFIX + photo;
            carCarpark.setPhoto(photo);
        }
        return AjaxResult.success().put("carParkList", carParkList);
    }

    /**
     * 查询停车场列表(带分页-后台)
     */
    @PreAuthorize("@ss.hasPermi('car:carpark:list')")
    @GetMapping("/list")
    public TableDataInfo list(CarCarpark carCarpark) {
        startPage();
        List<CarCarpark> list = carCarparkService.selectCarCarparkList(carCarpark);
        return getDataTable(list);
    }

    /**
     * 导出停车场列表
     */
    @PreAuthorize("@ss.hasPermi('car:carpark:export')")
    @Log(title = "停车场", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CarCarpark carCarpark) {
        List<CarCarpark> list = carCarparkService.selectCarCarparkList(carCarpark);
        ExcelUtil<CarCarpark> util = new ExcelUtil<CarCarpark>(CarCarpark.class);
        util.exportExcel(response, list, "停车场数据");
    }

    /**
     * 获取停车场详细信息
     */
    @PreAuthorize("@ss.hasPermi('car:carpark:query')")
    @GetMapping(value = "/{carparkId}")
    public AjaxResult getInfo(@PathVariable("carparkId") Long carparkId) {
        return AjaxResult.success(carCarparkService.selectCarCarparkByCarparkId(carparkId));
    }

    /**
     * 新增停车场
     */
    @PreAuthorize("@ss.hasPermi('car:carpark:add')")
    @Log(title = "停车场", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CarCarpark carCarpark) {
        return toAjax(carCarparkService.insertCarCarpark(carCarpark));
    }

    /**
     * 修改停车场
     */
    @PreAuthorize("@ss.hasPermi('car:carpark:edit')")
    @Log(title = "停车场", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CarCarpark carCarpark) {
        return toAjax(carCarparkService.updateCarCarpark(carCarpark));
    }

    /**
     * 删除停车场
     */
    @PreAuthorize("@ss.hasPermi('car:carpark:remove')")
    @Log(title = "停车场", businessType = BusinessType.DELETE)
    @DeleteMapping("/{carparkIds}")
    public AjaxResult remove(@PathVariable Long[] carparkIds) {
        return toAjax(carCarparkService.deleteCarCarparkByCarparkIds(carparkIds));
    }

    /**
     *  分页查询停车场数据(前台)
     */
    @ApiOperation("前台-分页查询停车场列表")
    @GetMapping("/pageList")
    public TableDataInfo pageList(CarCarpark carCarpark) {
        startPage();
        List<CarCarpark> list = carCarparkService.selectCarCarparkList(carCarpark);
        for (CarCarpark carpark : list) {
            String photo = carpark.getPhoto();
            photo = Constants.PHOTO_PREFIX + photo;
            carpark.setPhoto(photo);
        }
        return getDataTable(list);
    }

}
