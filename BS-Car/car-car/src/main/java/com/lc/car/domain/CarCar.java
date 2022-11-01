package com.lc.car.domain;

import com.lc.common.annotation.Excel;
import com.lc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 车辆对象 car_car
 *
 * @author lc
 * @date 2022-10-15
 */
public class CarCar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户信息 */
    private CarUser carUser;

    /** 车位信息(车位信息里面又封装了对应的停车场信息) */
    private CarParkinglot carParkinglot;


    /** ID */
    private Long carId;

    /** 车主id */
    @Excel(name = "车主id")
    private Long userId;

    /** 车位id */
    @Excel(name = "车位id")
    private Long parkinglotId;

    /** 车辆型号 */
    @Excel(name = "车辆型号")
    private String carName;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carNumber;

    /** 车辆照片 */
    @Excel(name = "车辆照片")
    private String photo;

    /** 车辆状态 */
    @Excel(name = "车辆状态")
    private String status;

    /** 逻辑删除 */
    private String isDeleted;


    public CarUser getCarUser() {
        return carUser;
    }

    public void setCarUser(CarUser carUser) {
        this.carUser = carUser;
    }

    public CarParkinglot getCarParkinglot() {
        return carParkinglot;
    }

    public void setCarParkinglot(CarParkinglot carParkinglot) {
        this.carParkinglot = carParkinglot;
    }
    public void setCarId(Long carId)
    {
        this.carId = carId;
    }

    public Long getCarId()
    {
        return carId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setParkinglotId(Long parkinglotId)
    {
        this.parkinglotId = parkinglotId;
    }

    public Long getParkinglotId()
    {
        return parkinglotId;
    }
    public void setCarName(String carName)
    {
        this.carName = carName;
    }

    public String getCarName()
    {
        return carName;
    }
    public void setCarNumber(String carNumber)
    {
        this.carNumber = carNumber;
    }

    public String getCarNumber()
    {
        return carNumber;
    }
    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String getPhoto()
    {
        return photo;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setIsDeleted(String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public String getIsDeleted()
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("carId", getCarId())
            .append("userId", getUserId())
            .append("parkinglotId", getParkinglotId())
            .append("carName", getCarName())
            .append("carNumber", getCarNumber())
            .append("photo", getPhoto())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("isDeleted", getIsDeleted())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
