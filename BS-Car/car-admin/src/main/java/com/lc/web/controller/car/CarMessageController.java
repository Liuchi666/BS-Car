package com.lc.web.controller.car;

import com.lc.car.domain.CarMessage;
import com.lc.car.service.ICarMessageService;
import com.lc.common.annotation.Log;
import com.lc.common.core.controller.BaseController;
import com.lc.common.core.domain.AjaxResult;
import com.lc.common.core.page.TableDataInfo;
import com.lc.common.enums.BusinessType;
import com.lc.common.utils.poi.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 留言Controller
 *
 * @author lc
 * @date 2022-10-17
 */
@RestController
@RequestMapping("/car/message")
public class CarMessageController extends BaseController {
    @Autowired
    private ICarMessageService carMessageService;

    /**
     * 查询留言列表
     */
    @PreAuthorize("@ss.hasPermi('car:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(CarMessage carMessage) {
        startPage();
        List<CarMessage> list = carMessageService.selectCarMessageList(carMessage);
        return getDataTable(list);
    }

    /**
     * 导出留言列表
     */
    @ApiOperation("导出留言列表")
    @PreAuthorize("@ss.hasPermi('car:message:export')")
    @Log(title = "留言", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CarMessage carMessage) {
        List<CarMessage> list = carMessageService.selectCarMessageList(carMessage);
        ExcelUtil<CarMessage> util = new ExcelUtil<CarMessage>(CarMessage.class);
        util.exportExcel(response, list, "留言数据");
    }

    /**
     * 获取留言详细信息
     */
    @PreAuthorize("@ss.hasPermi('car:message:query')")
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") Long messageId) {
        return AjaxResult.success(carMessageService.selectCarMessageByMessageId(messageId));
    }

    /**
     * 新增留言
     */
    @PreAuthorize("@ss.hasPermi('car:message:add')")
    @Log(title = "留言", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CarMessage carMessage) {
        return toAjax(carMessageService.insertCarMessage(carMessage));
    }

    /**
     * 修改留言
     */
    @PreAuthorize("@ss.hasPermi('car:message:edit')")
    @Log(title = "留言", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CarMessage carMessage) {
        return toAjax(carMessageService.updateCarMessage(carMessage));
    }

    /**
     * 删除留言
     */
    @PreAuthorize("@ss.hasPermi('car:message:remove')")
    @Log(title = "留言", businessType = BusinessType.DELETE)
    @DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable Long[] messageIds) {
        return toAjax(carMessageService.deleteCarMessageByMessageIds(messageIds));
    }
}
