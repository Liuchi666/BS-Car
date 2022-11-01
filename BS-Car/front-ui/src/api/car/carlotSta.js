import request from '@/utils/request'

// 根据停车场id查询要统计的数据
export function getChart(carparkId) {
  return request({
    url: '/car/status/carlot/' + carparkId,
    method: 'get'
  })
}
