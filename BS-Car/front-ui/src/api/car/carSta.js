import request from '@/utils/request'

// 统计所有车辆的状态数据
export function getCarStaChart() {
  return request({
    url: '/car/status/car',
    method: 'get'
  })
}
