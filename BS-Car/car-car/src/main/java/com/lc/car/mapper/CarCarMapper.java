package com.lc.car.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.car.domain.CarCar;
import com.lc.car.domain.vo.QueryCarListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 车辆Mapper接口
 *
 * @author lc
 * @date 2022-10-15
 */
@Mapper
public interface CarCarMapper extends BaseMapper<CarCar> {
    /**
     * 查询车辆
     *
     * @param carId 车辆主键
     * @return 车辆
     */
    public QueryCarListVo selectCarCarByCarId(Long carId);

    /**
     * 查询车辆列表
     *
     * @param carCar 车辆
     * @return 车辆集合
     */
//    public List<CarCar> selectCarCarList(CarCar carCar);
    public List<QueryCarListVo> selectCarCarList(CarCar carCar);

    /**
     * 新增车辆
     *
     * @param carCar 车辆
     * @return 结果
     */
    public int insertCarCar(CarCar carCar);

    /**
     * 修改车辆
     *
     * @param carCar 车辆
     * @return 结果
     */
    public int updateCarCar(CarCar carCar);

    /**
     * 删除车辆
     *
     * @param carId 车辆主键
     * @return 结果
     */
    public int deleteCarCarByCarId(Long carId);

    /**
     * 批量删除车辆
     *
     * @param carIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCarCarByCarIds(Long[] carIds);
}
