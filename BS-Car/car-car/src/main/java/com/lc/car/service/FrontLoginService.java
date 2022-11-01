package com.lc.car.service;

import com.lc.car.domain.CarUser;
import com.lc.car.domain.vo.front.RegisterVo;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/20
 * @description
 */
public interface FrontLoginService{

    String login(CarUser carUser);

    CarUser getById(String userId);

    void resetpwd(RegisterVo registerVo);
}
