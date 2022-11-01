import request from '@/utils/request'

//查询所有停车场数据
export function getAll() {
  return request({
    url: '/car/carpark/getAll',
    method: 'get'
  })
}

//分页查询停车场列表数据
export function getPageList(params) {
  return request({
    url: '/car/carpark/pageList',
    method: 'get',
    params: params
  })
}
