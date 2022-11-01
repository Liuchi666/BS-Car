package com.lc.car.service.impl;

import com.lc.car.domain.CarMessage;
import com.lc.car.domain.CarUser;
import com.lc.car.domain.vo.front.MsgFormVo;
import com.lc.car.mapper.CarMessageMapper;
import com.lc.car.mapper.CarUserMapper;
import com.lc.car.service.FrontMessageService;
import com.lc.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/22
 * @description
 */
@Service
public class FrontMessageServiceImpl implements FrontMessageService {
    @Autowired
    private CarMessageMapper carMessageMapper;
    @Autowired
    private CarUserMapper carUserMapper;

    /**
     *  前台用户发表留言(返回true为留言成功，否则失败)
     */
    @Override
    public boolean publish(MsgFormVo msgFormVo) {
        //获取用户提交的数据
        String nickname = msgFormVo.getNickname();
        String phone = msgFormVo.getPhone();
        String type = msgFormVo.getType();
        String content = msgFormVo.getContent();

        //非空判断
        if(!StringUtils.hasText(nickname) || !StringUtils.hasText(phone)
                || !StringUtils.hasText(type) || !StringUtils.hasText(content)){
            throw new ServiceException("请填写完整的表单数据");
        }

        //判断用户是否登录
        CarUser userInfo = carUserMapper.getUserInfoByName(nickname);
        if(userInfo == null){
            throw new ServiceException("请填写当前登录用户的昵称");
        }

        //以上校验都通过，向留言表插入数据
        //先定义一个carMessage对象封装插入数据
        CarMessage carMessage = new CarMessage();
        carMessage.setUserId(userInfo.getUserId());
        carMessage.setType(type);
        carMessage.setContent(content);
        carMessage.setStatus("0"); //新增的而留言默认未处理状态
        carMessage.setCreateTime(new Date());
        carMessage.setUpdateTime(new Date());
        //插入数据
        int count = carMessageMapper.insertCarMessage(carMessage);
        if(count == 0){
            return false;
        }else {
            return true;
        }

    }
}
