package com.lc.web.controller.system;

import com.lc.common.config.CarConfig;
import com.lc.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author lc
 */
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Autowired
    private CarConfig carConfig;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用共享车位平台后台管理框架，当前版本：1.0.SNAPSHOT，请通过前端地址访问。",
                carConfig.getName(), carConfig.getVersion());
    }
}
