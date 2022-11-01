package com.lc.car.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/16
 * @description     用于封装车辆状态信息(四表联查的结果)的 Vo对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCarListVo {

    //车辆id
    private Long carId;
    //车辆型号
    private String carName;
    //车牌号
    private String carNumber;
    //车辆示例图
    private String carPhoto;
    //车辆状态
    private String carStatus;
    //车主昵称
    private String userNickname;
    //车主手机号
    private String userPhone;
    //车位编号
    private Long parkinglotSort;
    //停车场名
    private String carparkName;

}
