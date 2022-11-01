package com.lc.car.mapper;

import com.lc.car.domain.CarParkinglot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 车位Mapper接口
 *
 * @author lc
 * @date 2022-10-14
 */
@Mapper
public interface CarParkinglotMapper
{
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
     * 删除车位
     *
     * @param parkinglotId 车位主键
     * @return 结果
     */
    public int deleteCarParkinglotByParkinglotId(Long parkinglotId);

    /**
     * 批量删除车位
     *
     * @param parkinglotIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCarParkinglotByParkinglotIds(Long[] parkinglotIds);

    List<CarParkinglot> getListByCarParkId(CarParkinglot carParkinglot);

}
