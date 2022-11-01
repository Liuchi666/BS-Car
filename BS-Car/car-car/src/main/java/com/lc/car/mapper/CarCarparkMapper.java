package com.lc.car.mapper;

import com.lc.car.domain.CarCarpark;

import java.util.List;

/**
 * 停车场Mapper接口
 *
 * @author lc
 * @date 2022-10-14
 */
public interface CarCarparkMapper
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
     * 删除停车场
     *
     * @param carparkId 停车场主键
     * @return 结果
     */
    public int deleteCarCarparkByCarparkId(Long carparkId);

    /**
     * 批量删除停车场
     *
     * @param carparkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCarCarparkByCarparkIds(Long[] carparkIds);

    List<CarCarpark> selectAll();

}
