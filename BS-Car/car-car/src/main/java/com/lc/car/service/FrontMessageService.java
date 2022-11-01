package com.lc.car.service;

import com.lc.car.domain.vo.front.MsgFormVo;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/22
 * @description
 */
public interface FrontMessageService {

    boolean publish(MsgFormVo msgFormVo);

}
