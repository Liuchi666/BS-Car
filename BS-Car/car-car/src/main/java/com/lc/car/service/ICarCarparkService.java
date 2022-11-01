package com.lc.car.service;

import com.lc.car.domain.CarCarpark;

import java.util.List;

/**
 * 停车场Service接口
 *
 * @author lc
 * @date 2022-10-14
 */
public interface ICarCarparkService
{
    /**
     * 查询停车场
     *
     * @param carparkId 停车场主键
     * @return 停车场
     */
    public CarCarpark selectCarCarparkByCarparkId(Long carparkId);

    /**
     * 查询停车场列表
     *
     * @param carCarpark 停车场
     * @return 停车场集合
     */
    public List<CarCarpark> selectCarCarparkList(CarCarpark carCarpark);

    /**
     * 新增停车场
     *
     * @param carCarpark 停车场
     * @return 结果
     */
    public int insertCarCarpark(CarCarpark carCarpark);

    /**
     * 修改停车场
     *
     * @param carCarpark 停车场
     * @return 结果
     */
    public int updateCarCarpark(CarCarpark carCarpark);

    /**
     * 批量删除停车场
     *
     * @param carparkIds 需要删除的停车场主键集合
     * @return 结果
     */
    public int deleteCarCarparkByCarparkIds(Long[] carparkIds);

    /**
     * 删除停车场信息
     *
     * @param carparkId 停车场主键
     * @return 结果
     */
    public int deleteCarCarparkByCarparkId(Long carparkId);

    /**
     *  查询所有
     */
    List<CarCarpark> getAll();
}
