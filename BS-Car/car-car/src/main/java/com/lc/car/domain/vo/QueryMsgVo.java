package com.lc.car.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/17
 * @description       用于封装查看用户留言相关信息的 Vo对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryMsgVo {
    //留言ID
    private Long msgId;
    //留言人昵称
    private String userNickname;
    //留言人手机号
    private String userPhone;
    //留言人头像
    private String userAvatar;
    //留言类型(0报修信息 1付费异常 2车辆开锁异常)
    private String msgType;
    //留言内容
    private String msgContent;
    //处理进度(0待处理 1处理中 2已处理)
    private String msgStatus;
}
