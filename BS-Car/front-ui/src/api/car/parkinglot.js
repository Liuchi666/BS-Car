import request from '@/utils/request'

// 查询车位列表
export function listParkinglot(query) {
  return request({
    url: '/car/parkinglot/list',
    method: 'get',
    params: query
  })
}

// 查询车位详细
export function getParkinglot(parkinglotId) {
  return request({
    url: '/car/parkinglot/' + parkinglotId,
    method: 'get'
  })
}

// 新增车位
export function addParkinglot(data) {
  return request({
    url: '/car/parkinglot',
    method: 'post',
    data: data
  })
}

// 修改车位
export function updateParkinglot(data) {
  return request({
    url: '/car/parkinglot',
    method: 'put',
    data: data
  })
}

// 删除车位
export function delParkinglot(parkinglotId) {
  return request({
    url: '/car/parkinglot/' + parkinglotId,
    method: 'delete'
  })
}
