package com.lc.car.service;

import com.lc.car.domain.CarParkinglot;
import com.lc.car.domain.vo.front.UseParkinglotVo;

import java.util.List;

/**
 * 车位Service接口
 *
 * @author lc
 * @date 2022-10-14
 */
public interface ICarParkinglotService {
    /**
     * 查询车位
     *
     * @param parkinglotId 车位主键
     * @return 车位
     */
    public CarParkinglot selectCarParkinglotByParkinglotId(Long parkinglotId);

    /**
     * 查询车位列表
     *
     * @param carParkinglot 车位
     * @return 车位集合
     */
    public List<CarParkinglot> selectCarParkinglotList(CarParkinglot carParkinglot);

    /**
     * 新增车位
     *
     * @param carParkinglot 车位
     * @return 结果
     */
    public int insertCarParkinglot(CarParkinglot carParkinglot);

    /**
     * 修改车位
     *
     * @param carParkinglot 车位
     * @return 结果
     */
    public int updateCarParkinglot(CarParkinglot carParkinglot);

    /**
     * 批量删除车位
     *
     * @param parkinglotIds 需要删除的车位主键集合
     * @return 结果
     */
    public int deleteCarParkinglotByParkinglotIds(Long[] parkinglotIds);

    /**
     * 删除车位信息
     *
     * @param parkinglotId 车位主键
     * @return 结果
     */
    public int deleteCarParkinglotByParkinglotId(Long parkinglotId);

    List<CarParkinglot> getListByCarParkId(CarParkinglot carParkinglot);

    boolean useParkingLot(UseParkinglotVo useParkinglotVo);

}
