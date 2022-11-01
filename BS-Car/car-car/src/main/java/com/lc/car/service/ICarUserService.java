package com.lc.car.service;

import com.lc.car.domain.CarUser;

import java.util.List;

/**
 * 用户表Service接口
 *
 * @author lc
 * @date 2022-10-14
 */
public interface ICarUserService
{
    /**
     * 查询用户表
     *
     * @param userId 用户表主键
     * @return 用户表
     */
    public CarUser selectCarUserByUserId(Long userId);

    /**
     * 查询用户表列表
     *
     * @param carUser 用户表
     * @return 用户表集合
     */
    public List<CarUser> selectCarUserList(CarUser carUser);

    /**
     * 新增用户表
     *
     * @param carUser 用户表
     * @return 结果
     */
    public int insertCarUser(CarUser carUser);

    /**
     * 修改用户表
     *
     * @param carUser 用户表
     * @return 结果
     */
    public int updateCarUser(CarUser carUser);

    /**
     * 批量删除用户表
     *
     * @param userIds 需要删除的用户表主键集合
     * @return 结果
     */
    public int deleteCarUserByUserIds(Long[] userIds);

    /**
     * 删除用户表信息
     *
     * @param userId 用户表主键
     * @return 结果
     */
    public int deleteCarUserByUserId(Long userId);

    boolean updateUserAvatar(String avatar, Long userId);
}
