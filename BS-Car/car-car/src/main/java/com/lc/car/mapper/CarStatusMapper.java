package com.lc.car.mapper;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/15
 * @description
 */
public interface CarStatusMapper {

    /** 根据停车场id分别统计该停车场中车位状态为已用、未用、损坏的数量 */
    //统计已用状态的数量
    public Integer getUse(String carparkId);
    //统计未用状态的数量
    public Integer getGood(String carparkId);
    //统计损坏状态的数量
    public Integer getBad(String carparkId);

    /**     分别统计所有车辆中未用(unused)、已用(used)、损坏(damaged)、报修(repair)、
                位置异常(locationBad)、付费异常(paymentBad)状态的数量  */
    //统计未用状态的数量
    public Integer getUnused();
    //已用状态
    public Integer getUsed();
    //损坏状态
    public Integer getDamaged();
    //报修状态
    public Integer getRepair();
    //位置异常状态
    public Integer getLocationBad();
    //付费异常状态
    public Integer getPaymentBad();
}
