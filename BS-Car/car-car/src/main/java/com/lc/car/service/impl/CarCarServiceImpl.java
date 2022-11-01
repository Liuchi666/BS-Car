package com.lc.car.service.impl;

import com.lc.car.domain.CarCar;
import com.lc.car.domain.vo.QueryCarListVo;
import com.lc.car.mapper.CarCarMapper;
import com.lc.car.service.ICarCarService;
import com.lc.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车辆Service业务层处理
 *
 * @author lc
 * @date 2022-10-15
 */
@Service
public class CarCarServiceImpl implements ICarCarService
{
    @Autowired
    private CarCarMapper carCarMapper;

    /**
     * 查询车辆
     *
     * @param carId 车辆主键
     * @return 车辆
     */
    @Override
    public QueryCarListVo selectCarCarByCarId(Long carId)
    {
        return carCarMapper.selectCarCarByCarId(carId);
    }

    /**
     * 查询车辆列表
     *
     * @param carCar 车辆
     * @return 车辆
     */
//    @Override
//    public List<CarCar> selectCarCarList(CarCar carCar)
//    {
//        return carCarMapper.selectCarCarList(carCar);
//    }

    @Override
    public List<QueryCarListVo> selectCarCarList(CarCar carCar){
        return carCarMapper.selectCarCarList(carCar);
    }

    /**
     * 新增车辆
     *
     * @param carCar 车辆
     * @return 结果
     */
    @Override
    public int insertCarCar(CarCar carCar)
    {
        carCar.setCreateTime(DateUtils.getNowDate());
        return carCarMapper.insertCarCar(carCar);
    }

    /**
     * 修改车辆
     *
     * @param carCar 车辆
     * @return 结果
     */
    @Override
    public int updateCarCar(CarCar carCar)
    {
        carCar.setUpdateTime(DateUtils.getNowDate());
        return carCarMapper.updateCarCar(carCar);
    }

    /**
     * 批量删除车辆
     *
     * @param carIds 需要删除的车辆主键
     * @return 结果
     */
    @Override
    public int deleteCarCarByCarIds(Long[] carIds)
    {
        return carCarMapper.deleteCarCarByCarIds(carIds);
    }

    /**
     * 删除车辆信息
     *
     * @param carId 车辆主键
     * @return 结果
     */
    @Override
    public int deleteCarCarByCarId(Long carId)
    {
        return carCarMapper.deleteCarCarByCarId(carId);
    }
}
