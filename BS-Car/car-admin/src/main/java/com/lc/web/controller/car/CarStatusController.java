package com.lc.web.controller.car;

import com.lc.car.service.ICarCarStatusService;
import com.lc.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/15
 * @description  获取状态监控展示的数据(包括车位状态和车辆状态)
 */
@RestController
@RequestMapping("/car/status")
public class CarStatusController {

    @Autowired
    private ICarCarStatusService iCarCarStatusService;

    /**
     *  根据停车场id统计该停车场中车位的状态数据，返回一个List格式的数据，用于前端图表展示
     */
    @PreAuthorize("@ss.hasPermi('car:carlotSta:list')")
    @GetMapping("/carlot/{carparkId}")
    public AjaxResult getCarLotChart(@PathVariable String carparkId){
//        System.out.println("===========" + carparkId);
        List<Integer> chartList = iCarCarStatusService.getCarlotChartList(carparkId);
        return AjaxResult.success(chartList);
    }

    /**
     *  统计所有的车辆状态，返回list集合格式的数据，用于前端Chart图表展示
     */
    @PreAuthorize("@ss.hasPermi('car:carSta:list')")
    @GetMapping("/car")
    public AjaxResult getCarStaChart(){
        List<Integer> chartList = iCarCarStatusService.getCarStaChartList();
        return AjaxResult.success(chartList);
    }



}
