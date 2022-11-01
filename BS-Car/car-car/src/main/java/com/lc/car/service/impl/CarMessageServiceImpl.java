package com.lc.car.service.impl;

import com.lc.car.domain.CarMessage;
import com.lc.car.mapper.CarMessageMapper;
import com.lc.car.service.ICarMessageService;
import com.lc.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 留言Service业务层处理
 *
 * @author lc
 * @date 2022-10-17
 */
@Service
public class CarMessageServiceImpl implements ICarMessageService
{
    @Autowired
    private CarMessageMapper carMessageMapper;

    /**
     * 查询留言
     *
     * @param messageId 留言主键
     * @return 留言
     */
    @Override
    public CarMessage selectCarMessageByMessageId(Long messageId)
    {
        return carMessageMapper.selectCarMessageByMessageId(messageId);
    }

    /**
     * 查询留言列表
     *
     * @param carMessage 留言
     * @return 留言
     */
    @Override
    public List<CarMessage> selectCarMessageList(CarMessage carMessage)
    {
        return carMessageMapper.selectCarMessageList(carMessage);
    }

    /**
     * 新增留言
     *
     * @param carMessage 留言
     * @return 结果
     */
    @Override
    public int insertCarMessage(CarMessage carMessage)
    {
        carMessage.setCreateTime(DateUtils.getNowDate());
        return carMessageMapper.insertCarMessage(carMessage);
    }

    /**
     * 修改留言
     *
     * @param carMessage 留言
     * @return 结果
     */
    @Override
    public int updateCarMessage(CarMessage carMessage)
    {
        carMessage.setUpdateTime(DateUtils.getNowDate());
        return carMessageMapper.updateCarMessage(carMessage);
    }

    /**
     * 批量删除留言
     *
     * @param messageIds 需要删除的留言主键
     * @return 结果
     */
    @Override
    public int deleteCarMessageByMessageIds(Long[] messageIds)
    {
        return carMessageMapper.deleteCarMessageByMessageIds(messageIds);
    }

    /**
     * 删除留言信息
     *
     * @param messageId 留言主键
     * @return 结果
     */
    @Override
    public int deleteCarMessageByMessageId(Long messageId)
    {
        return carMessageMapper.deleteCarMessageByMessageId(messageId);
    }
}
