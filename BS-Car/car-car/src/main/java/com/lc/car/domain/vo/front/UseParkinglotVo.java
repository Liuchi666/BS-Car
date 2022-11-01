package com.lc.car.domain.vo.front;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/23
 * @description       封装了点击使用车位之后的弹窗里面数据的vo对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UseParkinglotVo {

    //车主id
    private Long userId;

    //停车场id
    private Long carparkId;

    //车位id
    private Long parkinglotId;

    //车辆型号
    private String carName;

    //车牌号
    private String carNumber;

    //车辆照片
    private String photo;

}
