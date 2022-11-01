package com.lc.car.service.impl;

import com.lc.car.domain.CarUser;
import com.lc.car.domain.vo.front.RegisterVo;
import com.lc.car.mapper.CarUserMapper;
import com.lc.car.service.FrontLoginService;
import com.lc.car.tools.JwtUtils;
import com.lc.car.tools.MD5;
import com.lc.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/20
 * @description
 */
@Service
@SuppressWarnings("all")
public class FrontLoginServiceImpl implements FrontLoginService {

    @Autowired
    private CarUserMapper carUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  用户登录
     *  登录成功就返回一个token
     */
    @Override
    public String login(CarUser carUser) {
        //获取登录的手机号和密码
        String phone = carUser.getPhone();
        String password = carUser.getPassword();

        //进行非空判断
        if(!StringUtils.hasText(phone) || !StringUtils.hasText(password)){
            throw new ServiceException("手机号或密码不能为空");
        }

        //判断手机号是否正确（根据手机号查询用户信息条数）
        CarUser userInfo = carUserMapper.selectUserInfoByPhone(phone);

        if(userInfo == null){
            throw new ServiceException("您输入的手机号有误");
        }
        //判断密码是否正确，因为数据库中的密码是MD5加密的，所以要先把用户输入的密码进行加密，再进行比较
        String MD5Password = MD5.encrypt(password);
        if(!MD5Password.equals(userInfo.getPassword())){
            throw new ServiceException("您输入的密码有误");
        }
        //判断用户是否被禁用
        if(Integer.parseInt(userInfo.getIsDisabled()) == 1){  //1为禁用，0为未禁用
            throw new ServiceException("该账号已被封禁");
        }
        //手机号跟密码都正确，身份校验通过，通过jwt工具类生成一个token并返回
        String jwtToken = JwtUtils.getJwtToken(userInfo.getUserId().toString(), userInfo.getNickname());
        return jwtToken;
    }

    /**
     *      根据用户id查询用户信息
     */
    @Override
    public CarUser getById(String userId) {
        return carUserMapper.selectCarUserByUserId(Long.valueOf(userId));
    }

    /**
     *  找回密码
     */
    @Override
    public void resetpwd(RegisterVo registerVo) {
        //获取用户提交的信息
        String phone = registerVo.getPhone();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        String confirmPassword = registerVo.getConfirmPassword();

        //进行非空判断
        if(!StringUtils.hasText(phone) || !StringUtils.hasText(code)||
                !StringUtils.hasText(password)|| !StringUtils.hasText(confirmPassword)){
            throw new ServiceException("请填写完整的信息");
        }

        if(!password.equals(confirmPassword)){
            throw new ServiceException("两次填写的密码不一致");
        }

        //判断手机号是否正确（根据手机号查询用户信息）
        CarUser userInfo = carUserMapper.selectUserInfoByPhone(phone);

        if(userInfo == null){
            throw new ServiceException("您输入的手机号有误");
        }

        //判断验证码是否正确
        //获取redis中的验证码
        String redisCode = (String) redisTemplate.opsForValue().get(phone);
        if(redisCode == null){
            throw new ServiceException("验证码已过期");
        }
        if(!redisCode.equals(code)){
            throw new ServiceException("验证码错误");
        }

        //对用户输入的密码进行加密
        password = MD5.encrypt(registerVo.getPassword());

        //身份验证通过，修改用户密码
        carUserMapper.updatePwdById(userInfo.getUserId(),password);
    }
}
