package com.lc.web.controller.car.front;

import com.lc.car.domain.CarUser;
import com.lc.car.service.ICarUserService;
import com.lc.car.tools.MD5;
import com.lc.common.annotation.Log;
import com.lc.common.config.CarConfig;
import com.lc.common.core.domain.AjaxResult;
import com.lc.common.core.domain.model.LoginUser;
import com.lc.common.exception.ServiceException;
import com.lc.common.utils.file.FileUploadUtils;
import com.lc.common.utils.file.MimeTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.server.ServerCloneException;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/25
 * @description     前台用户个人中心
 */
@RestController
@RequestMapping("/front/userCenter")
public class UserCenterController {
    @Autowired
    private ICarUserService iCarUserService;

    /** 根据用户id获取用户详细信息  */
    @GetMapping("/getUserInfo/{id}")
    public AjaxResult getUserInfo(@PathVariable Long id){
        CarUser carUser = iCarUserService.selectCarUserByUserId(id);
        return AjaxResult.success(carUser);
    }

    /** 修改用户信息  */
    @PutMapping("/update")
    public AjaxResult update(@RequestBody CarUser carUser){
        iCarUserService.updateCarUser(carUser);
        return AjaxResult.success();
    }

    /** 修改用户密码  */
    @PutMapping("/updateUserPwd")
    public AjaxResult updateUserPwd(String oldPassword,String newPassword,Long userId){
        //因为数据库中的密码是加密后的,所以这里先对用户输入的密码进行加密
        String oldPwdMD5 = MD5.encrypt(oldPassword);
        String newPwdMD5 = MD5.encrypt(newPassword);
        //获取用户信息
        CarUser carUser = iCarUserService.selectCarUserByUserId(userId);
        String dbPassword = carUser.getPassword();
        //判断输入的旧密码是否正确
        if(!oldPwdMD5.equals(dbPassword)){
           throw new ServiceException("修改密码失败，旧密码错误");
        }
        //判断新密码是否和旧密码相同
        if(newPwdMD5.equals(dbPassword)){
            throw new ServiceException("新密码不能与旧密码相同");
        }
        //验证通过，修改密码
        carUser.setPassword(newPwdMD5);
        iCarUserService.updateCarUser(carUser);
        return AjaxResult.success();
    }

    /**
     * 前台用户中心头像上传
     */
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file,@RequestParam("userId")Long userId) throws Exception {
        if (!file.isEmpty()) {
            String avatar = FileUploadUtils.upload(CarConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            //System.out.println("==============" + avatar);
            if (iCarUserService.updateUserAvatar(avatar,userId)) {
                //返回图片路径
                return AjaxResult.success().put("imgUrl", avatar);
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }


}
