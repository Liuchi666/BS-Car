package com.lc.web.controller.car;

import com.lc.car.domain.CarParkinglot;
import com.lc.car.domain.vo.front.UseParkinglotVo;
import com.lc.car.service.ICarParkinglotService;
import com.lc.common.annotation.Log;
import com.lc.common.constant.Constants;
import com.lc.common.core.controller.BaseController;
import com.lc.common.core.domain.AjaxResult;
import com.lc.common.core.page.TableDataInfo;
import com.lc.common.enums.BusinessType;
import com.lc.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 车位Controller
 *
 * @author lc
 * @date 2022-10-14
 */
@RestController
@RequestMapping("/car/parkinglot")
public class CarParkinglotController extends BaseController {
    @Autowired
    private ICarParkinglotService carParkinglotService;

    /**
     * 查询车位列表
     */
    @PreAuthorize("@ss.hasPermi('car:parkinglot:list')")
    @GetMapping("/list")
    public TableDataInfo list(CarParkinglot carParkinglot) {
        startPage();
        List<CarParkinglot> list = carParkinglotService.selectCarParkinglotList(carParkinglot);
        return getDataTable(list);
    }

    /**
     * 导出车位列表
     */
    @PreAuthorize("@ss.hasPermi('car:parkinglot:export')")
    @Log(title = "车位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CarParkinglot carParkinglot) {
        List<CarParkinglot> list = carParkinglotService.selectCarParkinglotList(carParkinglot);
        ExcelUtil<CarParkinglot> util = new ExcelUtil<CarParkinglot>(CarParkinglot.class);
        util.exportExcel(response, list, "车位数据");
    }

    /**
     * 获取车位详细信息
     */
    @PreAuthorize("@ss.hasPermi('car:parkinglot:query')")
    @GetMapping(value = "/{parkinglotId}")
    public AjaxResult getInfo(@PathVariable("parkinglotId") Long parkinglotId) {
        return AjaxResult.success(carParkinglotService.selectCarParkinglotByParkinglotId(parkinglotId));
    }

    /**
     * 新增车位
     */
    @PreAuthorize("@ss.hasPermi('car:parkinglot:add')")
    @Log(title = "车位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CarParkinglot carParkinglot) {
        return toAjax(carParkinglotService.insertCarParkinglot(carParkinglot));
    }

    /**
     * 修改车位
     */
    @PreAuthorize("@ss.hasPermi('car:parkinglot:edit')")
    @Log(title = "车位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CarParkinglot carParkinglot) {
        return toAjax(carParkinglotService.updateCarParkinglot(carParkinglot));
    }

    /**
     * 删除车位
     */
    @PreAuthorize("@ss.hasPermi('car:parkinglot:remove')")
    @Log(title = "车位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{parkinglotIds}")
    public AjaxResult remove(@PathVariable Long[] parkinglotIds) {
        return toAjax(carParkinglotService.deleteCarParkinglotByParkinglotIds(parkinglotIds));
    }

    /**
     * 前台-(带分页)根据停车场id查询该停车场中所有的车位(可以选择只查其中的空闲状态的车位)
     */
    @GetMapping("/getListByCarParkId")
    public TableDataInfo getListByCarParkId(CarParkinglot carParkinglot) {
        startPage();
        List<CarParkinglot> list = carParkinglotService.getListByCarParkId(carParkinglot);
        for (CarParkinglot parkinglot : list) {
            String photo = parkinglot.getPhoto();
            photo = Constants.PHOTO_PREFIX + photo;
            parkinglot.setPhoto(photo);
        }
        return getDataTable(list);
    }

    /**
     * 使用车位(向车辆表插入记录，并修改车位表中该车位的状态为已用)
     */
    @PostMapping("/useParkingLot")
    public AjaxResult useParkingLot(@RequestBody UseParkinglotVo useParkinglotVo) {
        boolean flag = carParkinglotService.useParkingLot(useParkinglotVo);
        if (flag) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

}
