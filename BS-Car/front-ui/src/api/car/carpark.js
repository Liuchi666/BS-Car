import request from '@/utils/request'

// 查询停车场列表
export function listCarpark(query) {
  return request({
    url: '/car/carpark/list',
    method: 'get',
    params: query
  })
}

// 查询停车场详细
export function getCarpark(carparkId) {
  return request({
    url: '/car/carpark/' + carparkId,
    method: 'get'
  })
}

// 新增停车场
export function addCarpark(data) {
  return request({
    url: '/car/carpark',
    method: 'post',
    data: data
  })
}

// 修改停车场
export function updateCarpark(data) {
  return request({
    url: '/car/carpark',
    method: 'put',
    data: data
  })
}

// 删除停车场
export function delCarpark(carparkId) {
  return request({
    url: '/car/carpark/' + carparkId,
    method: 'delete'
  })
}
