package com.lc.car.service;

import com.lc.car.domain.CarMessage;

import java.util.List;

/**
 * 留言Service接口
 *
 * @author lc
 * @date 2022-10-17
 */
public interface ICarMessageService
{
    /**
     * 查询留言
     *
     * @param messageId 留言主键
     * @return 留言
     */
    public CarMessage selectCarMessageByMessageId(Long messageId);

    /**
     * 查询留言列表
     *
     * @param carMessage 留言
     * @return 留言集合
     */
    public List<CarMessage> selectCarMessageList(CarMessage carMessage);

    /**
     * 新增留言
     *
     * @param carMessage 留言
     * @return 结果
     */
    public int insertCarMessage(CarMessage carMessage);

    /**
     * 修改留言
     *
     * @param carMessage 留言
     * @return 结果
     */
    public int updateCarMessage(CarMessage carMessage);

    /**
     * 批量删除留言
     *
     * @param messageIds 需要删除的留言主键集合
     * @return 结果
     */
    public int deleteCarMessageByMessageIds(Long[] messageIds);

    /**
     * 删除留言信息
     *
     * @param messageId 留言主键
     * @return 结果
     */
    public int deleteCarMessageByMessageId(Long messageId);
}
