package com.lc.car.domain;

import com.lc.common.annotation.Excel;
import com.lc.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 留言对象 car_message
 *
 * @author lc
 * @date 2022-10-17
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**用来封装留言人的相关信息 (可以根据下面的用户id查出详细信息) */
    private CarUser carUser;

    /** id */
    @Excel(name = "id")
    private Long messageId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 留言类型 */
    @Excel(name = "留言类型")
    private String type;

    /** 留言内容 */
    @Excel(name = "留言内容")
    private String content;

    /** 处理进度 */
    @Excel(name = "处理进度")
    private String status;

    /** 车辆id */
    private Long carId;

    /** 停车场车位id */
    private Long parkinglotId;

    /** 逻辑删除 (1已删除  0未删除) */
    private String isDeleted;

    public CarUser getCarUser() {
        return carUser;
    }

    public void setCarUser(CarUser carUser) {
        this.carUser = carUser;
    }

    public void setMessageId(Long messageId)
    {
        this.messageId = messageId;
    }

    public Long getMessageId()
    {
        return messageId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCarId(Long carId)
    {
        this.carId = carId;
    }

    public Long getCarId()
    {
        return carId;
    }
    public void setParkinglotId(Long parkinglotId)
    {
        this.parkinglotId = parkinglotId;
    }

    public Long getParkinglotId()
    {
        return parkinglotId;
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
            .append("messageId", getMessageId())
            .append("userId", getUserId())
            .append("type", getType())
            .append("content", getContent())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("carId", getCarId())
            .append("parkinglotId", getParkinglotId())
            .append("isDeleted", getIsDeleted())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("carUser", getCarUser())
            .toString();
    }
}
