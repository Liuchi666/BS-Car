package com.lc.car.service.impl;

import com.lc.car.mapper.CarStatusMapper;
import com.lc.car.service.ICarCarStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/15
 * @description
 */
@Service
public class ICarCarStatusServiceImpl implements ICarCarStatusService {

    @Autowired
    private CarStatusMapper carStatusMapper;

    /**
     *  根据停车场id统计该停车场中车位的状态数据，返回一个List格式的数据，用于前端图表展示
     */
    @Override
    public List<Integer> getCarlotChartList(String carparkId) {
        //定义一个list集合用来封装查询结果
        List<Integer> list = new ArrayList<>();
        //查询未用状态的车位数量
        Integer good = carStatusMapper.getGood(carparkId);
        list.add(good);
        //查询已用状态的
        Integer use = carStatusMapper.getUse(carparkId);
        list.add(use);
        //查询损害状态的
        Integer bad = carStatusMapper.getBad(carparkId);
        list.add(bad);
        return list;
    }

    /**
     *  统计所有的车辆状态，返回list集合格式的数据，用于前端Chart图表展示
     */
    @Override
    public List<Integer> getCarStaChartList() {
        //定义一个list集合用来封装查询结果
        List<Integer> list = new ArrayList<>();

        //分别查询六种车辆状态的车位数量
        Integer unused = carStatusMapper.getUnused();
        list.add(unused);
        Integer used = carStatusMapper.getUsed();
        list.add(used);
        Integer damaged = carStatusMapper.getDamaged();
        list.add(damaged);
        Integer repair = carStatusMapper.getRepair();
        list.add(repair);
        Integer locationBad = carStatusMapper.getLocationBad();
        list.add(locationBad);
        Integer paymentBad = carStatusMapper.getPaymentBad();
        list.add(paymentBad);
        return list;
    }
}
