package com.lc.car.domain;

import com.lc.common.annotation.Excel;
import com.lc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 停车场对象 car_carpark
 *
 * @author lc
 * @date 2022-10-14
 */
public class CarCarpark extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Excel(name = "id")
    private Long carparkId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String carparkName;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 示例图
     */
    @Excel(name = "示例图")
    private String photo;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String isDisabled;

    /**
     * 逻辑删除 (1已删除， 0未删除)
     */
    private String isDeleted;

    public void setCarparkId(Long carparkId) {
        this.carparkId = carparkId;
    }

    public Long getCarparkId() {
        return carparkId;
    }

    public void setCarparkName(String carparkName) {
        this.carparkName = carparkName;
    }

    public String getCarparkName() {
        return carparkName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getIsDisabled() {
        return isDisabled;
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
                .append("carparkId", getCarparkId())
                .append("carparkName", getCarparkName())
                .append("address", getAddress())
                .append("photo", getPhoto())
                .append("description", getDescription())
                .append("isDisabled", getIsDisabled())
                .append("createTime", getCreateTime())
                .append("isDeleted", getIsDeleted())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
