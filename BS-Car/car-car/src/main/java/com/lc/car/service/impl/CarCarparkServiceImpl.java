package com.lc.car.service.impl;

import com.lc.car.domain.CarCarpark;
import com.lc.car.mapper.CarCarparkMapper;
import com.lc.car.service.ICarCarparkService;
import com.lc.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 停车场Service业务层处理
 *
 * @author lc
 * @date 2022-10-14
 */
@Service
public class CarCarparkServiceImpl implements ICarCarparkService
{
    @Autowired
    private CarCarparkMapper carCarparkMapper;

    /**
     * 查询停车场
     *
     * @param carparkId 停车场主键
     * @return 停车场
     */
    @Override
    public CarCarpark selectCarCarparkByCarparkId(Long carparkId)
    {
        return carCarparkMapper.selectCarCarparkByCarparkId(carparkId);
    }

    /**
     * 查询停车场列表
     *
     * @param carCarpark 停车场
     * @return 停车场
     */
    @Override
    public List<CarCarpark> selectCarCarparkList(CarCarpark carCarpark)
    {
        return carCarparkMapper.selectCarCarparkList(carCarpark);
    }

    /**
     * 新增停车场
     *
     * @param carCarpark 停车场
     * @return 结果
     */
    @Override
    public int insertCarCarpark(CarCarpark carCarpark)
    {
        carCarpark.setCreateTime(DateUtils.getNowDate());
        return carCarparkMapper.insertCarCarpark(carCarpark);
    }

    /**
     * 修改停车场
     *
     * @param carCarpark 停车场
     * @return 结果
     */
    @Override
    public int updateCarCarpark(CarCarpark carCarpark)
    {
        carCarpark.setUpdateTime(DateUtils.getNowDate());
        return carCarparkMapper.updateCarCarpark(carCarpark);
    }

    /**
     * 批量删除停车场
     *
     * @param carparkIds 需要删除的停车场主键
     * @return 结果
     */
    @Override
    public int deleteCarCarparkByCarparkIds(Long[] carparkIds)
    {
        return carCarparkMapper.deleteCarCarparkByCarparkIds(carparkIds);
    }

    /**
     * 删除停车场信息
     *
     * @param carparkId 停车场主键
     * @return 结果
     */
    @Override
    public int deleteCarCarparkByCarparkId(Long carparkId)
    {
        return carCarparkMapper.deleteCarCarparkByCarparkId(carparkId);
    }

    /**
     *      查询所有
     */
    @Override
    public List<CarCarpark> getAll() {
        return carCarparkMapper.selectAll();
    }
}
