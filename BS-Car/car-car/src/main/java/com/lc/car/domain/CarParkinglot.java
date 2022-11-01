package com.lc.car.domain;

import com.lc.common.annotation.Excel;
import com.lc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 车位对象 car_parkinglot
 *
 * @author lc
 * @date 2022-10-14
 */
public class CarParkinglot extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 封装了停车场信息(可以根据下面的停车场id查出详细信息)
     */
    private CarCarpark carCarpark;

    /**
     * 停车场车位id
     */
    @Excel(name = "停车场车位id")
    private Long parkinglotId;

    /**
     * 车位编号
     */
    @Excel(name = "车位编号")
    private Long sort;

    /**
     * 所属停车场
     */
    @Excel(name = "所属停车场")
    private Long carparkId;

    /**
     * 示例图
     */
    @Excel(name = "示例图")
    private String photo;

    /**
     * 车位状态 (0 未用、1 已用、2 损坏)
     */
    @Excel(name = "车位状态 (0 未用、1 已用、2 损坏)")
    private String status;

    /**
     * 逻辑删除 (1已删除， 0未删除)
     */
    private String isDeleted;

    public CarCarpark getCarCarpark() {
        return carCarpark;
    }

    public void setCarCarpark(CarCarpark carCarpark) {
        this.carCarpark = carCarpark;
    }

    public void setParkinglotId(Long parkinglotId) {
        this.parkinglotId = parkinglotId;
    }

    public Long getParkinglotId() {
        return parkinglotId;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    public Long getCarparkId() {
        return carparkId;
    }

    public void setCarparkId(Long carparkId) {
        this.carparkId = carparkId;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("parkinglotId", getParkinglotId())
                .append("sort", getSort())
                .append("carparkId", getCarparkId())
                .append("photo", getPhoto())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("isDeleted", getIsDeleted())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("carCarpark", getCarCarpark())
                .toString();
    }
}
