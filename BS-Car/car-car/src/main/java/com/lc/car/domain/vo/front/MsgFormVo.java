package com.lc.car.domain.vo.front;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/22
 * @description      封装了前台用户发表留言的表单数据的 vo对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgFormVo {

    //用户昵称
    private String nickname;

    //用户手机号
    private String phone;

    //留言类型
    private String type;

    //留言内容
    private String content;

}
