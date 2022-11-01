package com.lc.car.domain;

import com.lc.common.annotation.Excel;
import com.lc.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户表对象 car_user
 *
 * @author lc
 * @date 2022-10-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像")
    private String avatar;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickname;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private Integer age;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phone;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String isDisabled;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户签名
     */
    private String sign;

    /**
     * 逻辑删除 (1已删除， 0未删除)
     */
    private String isDeleted;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("avatar", getAvatar())
            .append("nickname", getNickname())
            .append("sex", getSex())
            .append("age", getAge())
            .append("phone", getPhone())
            .append("isDisabled", getIsDisabled())
            .append("createTime", getCreateTime())
            .append("openid", getOpenid())
            .append("password", getPassword())
            .append("sign", getSign())
            .append("isDeleted", getIsDeleted())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
