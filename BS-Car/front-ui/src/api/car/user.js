import request from '@/utils/request'

// 查询用户表列表
export function listUser(query) {
  return request({
    url: '/car/user/list',
    method: 'get',
    params: query
  })
}

// 查询用户表详细
export function getUser(userId) {
  return request({
    url: '/car/user/' + userId,
    method: 'get'
  })
}

// 新增用户表
export function addUser(data) {
  return request({
    url: '/car/user',
    method: 'post',
    data: data
  })
}

// 修改用户表
export function updateUser(data) {
  return request({
    url: '/car/user',
    method: 'put',
    data: data
  })
}

// 删除用户表
export function delUser(userId) {
  return request({
    url: '/car/user/' + userId,
    method: 'delete'
  })
}
