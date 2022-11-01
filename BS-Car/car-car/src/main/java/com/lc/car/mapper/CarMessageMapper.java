package com.lc.car.mapper;

import com.lc.car.domain.CarMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 留言Mapper接口
 *
 * @author lc
 * @date 2022-10-17
 */
@Mapper
public interface CarMessageMapper
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
     * 删除留言
     *
     * @param messageId 留言主键
     * @return 结果
     */
    public int deleteCarMessageByMessageId(Long messageId);

    /**
     * 批量删除留言
     *
     * @param messageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCarMessageByMessageIds(Long[] messageIds);
}
