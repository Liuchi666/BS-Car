package com.lc.web.controller.car.front;

import com.lc.car.domain.vo.front.RegisterVo;
import com.lc.car.service.FrontRegisterService;
import com.lc.car.tools.RandomUtil;
import com.lc.common.core.domain.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/20
 * @description
 */
@RestController
@RequestMapping("/front/register")
public class RegisterController {

    @Autowired
    private FrontRegisterService frontRegisterService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  通过腾讯云SMS服务发送短信
     */
    @ApiOperation("通过腾讯云SMS服务发送短信")
    @GetMapping("/send/{phone}")
    public AjaxResult send(@PathVariable String phone){
        //1.先从redis获取验证码，如果取到了直接返回(这是为了防止用户上一个验证码还有效就再次发送验证码，防止重复发短信)
        String code = (String) redisTemplate.opsForValue().get(phone);
        if(StringUtils.hasText(code)){
            return AjaxResult.success();
        }

        //2.如果redis中没有验证码，则说明过期了，用户可以重新发送短信验证码了
        //生成6位验证码,传给腾讯云进行发送
        code = RandomUtil.getSixBitRandom();
        //调用service发送短信
        boolean isSend = frontRegisterService.send(code,phone);
        if(isSend){
            //如果发送成功，就把发送成功的验证码存进redis中,并设置2分钟有效期
            redisTemplate.opsForValue().set(phone,code,2, TimeUnit.MINUTES);
            return AjaxResult.success();
        }else {
            return AjaxResult.error("短信发送失败");
        }
    }

    /**
     *  用户注册
     */
    @ApiOperation("用户注册")
    @PostMapping
    public AjaxResult userRegister(@RequestBody RegisterVo registerVo){
        frontRegisterService.register(registerVo);
        return AjaxResult.success();
    }

}
