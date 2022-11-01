package com.lc.car.mapper;

import com.lc.car.domain.CarUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表Mapper接口
 *
 * @author lc
 * @date 2022-10-14
 */
@Mapper
public interface CarUserMapper{
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
     * 删除用户表
     *
     * @param userId 用户表主键
     * @return 结果
     */
    public int deleteCarUserByUserId(Long userId);

    /**
     * 批量删除用户表
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCarUserByUserIds(Long[] userIds);

    int selectCarUserByNickname(String nickname);

    int selectCarUserByPhone(String phone);

    CarUser selectUserInfoByPhone(String phone);

    void updatePwdById(@Param("userId") Long userId, @Param("password") String password);

    CarUser getUserInfoByName(String nickname);

    boolean updateAvatarByUserId(@Param("avatar") String avatar, @Param("userId") Long userId);
}
