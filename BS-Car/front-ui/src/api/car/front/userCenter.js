import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询用户个人信息
export function getUserInfo(userId) {
  return request({
    url: '/front/userCenter/getUserInfo/' + userId,
    method: 'get'
  })
}

// 修改用户个人信息
export function updateUserInfo(data) {
  return request({
    url: '/front/userCenter/update',
    method: 'put',
    data: data
  })
}

// 用户密码重置
export function updateUserPwd(oldPassword, newPassword,userId) {
  const data = {
    oldPassword,
    newPassword,
    userId
  }
  return request({
    url: '/front/userCenter/updateUserPwd',
    method: 'put',
    params: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/front/userCenter/avatar',
    method: 'post',
    data: data
  })
}
