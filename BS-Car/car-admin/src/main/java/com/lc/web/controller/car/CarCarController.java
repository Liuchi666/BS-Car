package com.lc.web.controller.car;

import com.lc.car.domain.CarCar;
import com.lc.car.domain.vo.QueryCarListVo;
import com.lc.car.service.ICarCarService;
import com.lc.common.annotation.Log;
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
 * 车辆Controller
 *
 * @author lc
 * @date 2022-10-15
 */
@Api(tags = "车辆状态")
@RestController
@RequestMapping("/car/car")
public class CarCarController extends BaseController {
    @Autowired
    private ICarCarService carCarService;

    /**
     * 查询车辆列表
     */
    @ApiOperation("查询车辆列表")
    @PreAuthorize("@ss.hasPermi('car:carSta:list')")
    @GetMapping("/list")
    public TableDataInfo list(CarCar carCar) {
        startPage();
        List<QueryCarListVo> list = carCarService.selectCarCarList(carCar);
        return getDataTable(list);
    }

    /**
     * 导出车辆列表
     */
    @PreAuthorize("@ss.hasPermi('car:carSta:export')")
    @Log(title = "车辆", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CarCar carCar) {
        List<QueryCarListVo> list = carCarService.selectCarCarList(carCar);
        ExcelUtil<QueryCarListVo> util = new ExcelUtil<QueryCarListVo>(QueryCarListVo.class);
        util.exportExcel(response, list, "车辆数据");
    }

    /**
     * 获取车辆详细信息
     */
    @PreAuthorize("@ss.hasPermi('car:carSta:query')")
    @GetMapping(value = "/{carId}")
    public AjaxResult getInfo(@PathVariable("carId") Long carId) {
        return AjaxResult.success(carCarService.selectCarCarByCarId(carId));
    }

    /**
     * 新增车辆
     */
    @PreAuthorize("@ss.hasPermi('car:carSta:add')")
    @Log(title = "车辆", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CarCar carCar) {
        return toAjax(carCarService.insertCarCar(carCar));
    }

    /**
     * 修改车辆状态
     */
    @PreAuthorize("@ss.hasPermi('car:carSta:edit')")
    @Log(title = "车辆", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestParam("carId") Long carId,@RequestParam("carStatus")String carStatus) {
        CarCar carCar = new CarCar();
        carCar.setCarId(carId);
        carCar.setStatus(carStatus);
        return toAjax(carCarService.updateCarCar(carCar));
    }

    /**
     * 删除车辆
     */
    @PreAuthorize("@ss.hasPermi('car:carSta:remove')")
    @Log(title = "车辆", businessType = BusinessType.DELETE)
    @DeleteMapping("/{carIds}")
    public AjaxResult remove(@PathVariable Long[] carIds) {
        return toAjax(carCarService.deleteCarCarByCarIds(carIds));
    }
}
