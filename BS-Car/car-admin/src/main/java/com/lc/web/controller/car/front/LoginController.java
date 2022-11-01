package com.lc.web.controller.car.front;

import com.lc.car.domain.CarUser;
import com.lc.car.domain.vo.front.RegisterVo;
import com.lc.car.service.FrontLoginService;
import com.lc.car.tools.JwtUtils;
import com.lc.common.constant.Constants;
import com.lc.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/20
 * @description
 */
@Api(tags = "前台登录")
@RestController
@RequestMapping("/front/login")
public class LoginController {
    @Autowired
    private FrontLoginService frontLoginService;

    /**
     *  用户登录
     *  登录成功就返回一个token
     */
    @ApiOperation("登录成功返回token")
    @PostMapping
    public AjaxResult userLogin(@RequestBody CarUser carUser){
        //调用service方法实现登录，返回一个token(使用jwt生成)
        String token = frontLoginService.login(carUser);
        return AjaxResult.success().put("token",token);
    }

    /**
     *  根据请求头中的token获取用户信息
     */
    @GetMapping("/getMemberInfo")
    public AjaxResult getMemberInfo(HttpServletRequest request){
        //调用jwt工具类中的方法，根据request对象获取头信息，解析token,返回用户id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id查询用户信息
        CarUser userInfo = frontLoginService.getById(userId);
        if(StringUtils.hasText(userInfo.getAvatar())){
            String avatar = userInfo.getAvatar();
            avatar = Constants.PHOTO_PREFIX + avatar;
            userInfo.setAvatar(avatar);
        }
        return AjaxResult.success().put("userInfo",userInfo);
    }

    /**
     *  找回密码
     */
    @ApiOperation("找回密码")
    @PostMapping("/resetpwd")
    public AjaxResult resetpwd(@RequestBody RegisterVo registerVo){
        frontLoginService.resetpwd(registerVo);
        return AjaxResult.success();
    }

}
