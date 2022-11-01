package com.lc.car.service.impl;

import com.lc.car.domain.CarCar;
import com.lc.car.domain.CarParkinglot;
import com.lc.car.domain.vo.front.UseParkinglotVo;
import com.lc.car.mapper.CarCarMapper;
import com.lc.car.mapper.CarParkinglotMapper;
import com.lc.car.service.ICarParkinglotService;
import com.lc.common.exception.ServiceException;
import com.lc.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 车位Service业务层处理
 *
 * @author lc
 * @date 2022-10-14
 */
@Service
public class CarParkinglotServiceImpl implements ICarParkinglotService
{
    @Autowired
    private CarParkinglotMapper carParkinglotMapper;

    @Autowired
    private CarCarMapper carCarMapper;

    /**
     * 查询车位
     *
     * @param parkinglotId 车位主键
     * @return 车位
     */
    @Override
    public CarParkinglot selectCarParkinglotByParkinglotId(Long parkinglotId)
    {
        return carParkinglotMapper.selectCarParkinglotByParkinglotId(parkinglotId);
    }

    /**
     * 查询车位列表
     *
     * @param carParkinglot 车位
     * @return 车位
     */
    @Override
    public List<CarParkinglot> selectCarParkinglotList(CarParkinglot carParkinglot)
    {
        return carParkinglotMapper.selectCarParkinglotList(carParkinglot);
    }

    /**
     * 新增车位
     *
     * @param carParkinglot 车位
     * @return 结果
     */
    @Override
    public int insertCarParkinglot(CarParkinglot carParkinglot)
    {
        carParkinglot.setCreateTime(DateUtils.getNowDate());
        return carParkinglotMapper.insertCarParkinglot(carParkinglot);
    }

    /**
     * 修改车位
     *
     * @param carParkinglot 车位
     * @return 结果
     */
    @Override
    public int updateCarParkinglot(CarParkinglot carParkinglot)
    {
        carParkinglot.setUpdateTime(DateUtils.getNowDate());
        return carParkinglotMapper.updateCarParkinglot(carParkinglot);
    }

    /**
     * 批量删除车位
     *
     * @param parkinglotIds 需要删除的车位主键
     * @return 结果
     */
    @Override
    public int deleteCarParkinglotByParkinglotIds(Long[] parkinglotIds)
    {
        return carParkinglotMapper.deleteCarParkinglotByParkinglotIds(parkinglotIds);
    }

    /**
     * 删除车位信息
     *
     * @param parkinglotId 车位主键
     * @return 结果
     */
    @Override
    public int deleteCarParkinglotByParkinglotId(Long parkinglotId)
    {
        return carParkinglotMapper.deleteCarParkinglotByParkinglotId(parkinglotId);
    }

    /**
     *  前台-(带分页)根据停车场id查询该停车场中所有的车位(可以选择只查其中的空闲状态的车位)
     */
    @Override
    public List<CarParkinglot> getListByCarParkId(CarParkinglot carParkinglot) {
        return carParkinglotMapper.getListByCarParkId(carParkinglot);
    }

    /**
     *  使用车位(向车辆表插入记录，并修改车位表中该车位的状态为已用)
     *  注意，涉及多表操作，要记得加事务
     */
    @Override
    @Transactional
    public boolean useParkingLot(UseParkinglotVo useParkinglotVo) {
        //获取用户提交的数据
        Long userId = useParkinglotVo.getUserId();
        Long carparkId = useParkinglotVo.getCarparkId();
        Long parkinglotId = useParkinglotVo.getParkinglotId();
        String carName = useParkinglotVo.getCarName();
        String carNumber = useParkinglotVo.getCarNumber();
        String photo = useParkinglotVo.getPhoto();

        //非空判断
        if(!StringUtils.hasText(carName) || !StringUtils.hasText(carNumber) || !StringUtils.hasText(photo)){
            throw new ServiceException("请填写完整的车辆信息");
        }

        //声明一个CarCar对象,将数据存进去
        CarCar carCar = new CarCar();
        carCar.setUserId(userId);
        carCar.setParkinglotId(parkinglotId);
        carCar.setCarName(carName);
        carCar.setCarNumber(carNumber);
        carCar.setPhoto(photo);
        carCar.setStatus("0");  //默认是未用状态，即车辆停在车位上
        carCar.setCreateTime(new Date());
        carCar.setUpdateTime(new Date());

        //1.向车辆表中插入一条记录
        carCarMapper.insertCarCar(carCar);

        CarParkinglot carParkinglot = new CarParkinglot();
        carParkinglot.setParkinglotId(parkinglotId);
        carParkinglot.setStatus("1");  //1代表已用
        carParkinglot.setUpdateTime(new Date()); //更新修改时间

        //2.修改当前车位的状态为已用
        int count = carParkinglotMapper.updateCarParkinglot(carParkinglot);
        if(count == 0){
           throw new ServiceException("操作失败，修改车位状态失败");
        }
        return true;
    }
}
