package com.lc.web.controller.car.front;

import com.lc.car.domain.vo.front.MsgFormVo;
import com.lc.car.service.FrontMessageService;
import com.lc.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/10/22
 * @description
 */
@RestController
@RequestMapping("/front/msg")
public class MessageController {

    @Autowired
    private FrontMessageService frontMessageService;

    /**
     *  前台用户发表留言
     */
    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody MsgFormVo msgFormVo){
        boolean flag = frontMessageService.publish(msgFormVo);
        if(flag){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("留言失败");
        }
    }

}
