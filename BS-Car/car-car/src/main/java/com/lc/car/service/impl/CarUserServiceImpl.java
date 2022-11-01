package com.lc.car.service.impl;

import com.lc.car.domain.CarUser;
import com.lc.car.mapper.CarUserMapper;
import com.lc.car.service.ICarUserService;
import com.lc.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户表Service业务层处理
 *
 * @author lc
 * @date 2022-10-14
 */
@Service
public class CarUserServiceImpl implements ICarUserService {
    @Autowired
    private CarUserMapper carUserMapper;

    /**
     * 查询用户表
     *
     * @param userId 用户表主键
     * @return 用户表
     */
    @Override
    public CarUser selectCarUserByUserId(Long userId) {
        return carUserMapper.selectCarUserByUserId(userId);
    }

    /**
     * 查询用户表列表
     *
     * @param carUser 用户表
     * @return 用户表
     */
    @Override
    public List<CarUser> selectCarUserList(CarUser carUser) {
        return carUserMapper.selectCarUserList(carUser);
    }

    /**
     * 新增用户表
     *
     * @param carUser 用户表
     * @return 结果
     */
    @Override
    public int insertCarUser(CarUser carUser) {
        carUser.setCreateTime(DateUtils.getNowDate());
        return carUserMapper.insertCarUser(carUser);
    }

    /**
     * 修改用户表
     *
     * @param carUser 用户表
     * @return 结果
     */
    @Override
    public int updateCarUser(CarUser carUser) {
        carUser.setUpdateTime(DateUtils.getNowDate());
        return carUserMapper.updateCarUser(carUser);
    }

    /**
     * 批量删除用户表
     *
     * @param userIds 需要删除的用户表主键
     * @return 结果
     */
    @Override
    public int deleteCarUserByUserIds(Long[] userIds) {
        return carUserMapper.deleteCarUserByUserIds(userIds);
    }

    /**
     * 删除用户表信息
     *
     * @param userId 用户表主键
     * @return 结果
     */
    @Override
    public int deleteCarUserByUserId(Long userId) {
        return carUserMapper.deleteCarUserByUserId(userId);
    }

    /**
     * 前台用户中心头像上传
     */
    @Override
    public boolean updateUserAvatar(String avatar, Long userId) {
        return carUserMapper.updateAvatarByUserId(avatar,userId);
    }
}
