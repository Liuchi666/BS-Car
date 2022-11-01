import request from '@/utils/request'

// 根据停车场id分页+条件查询(查找状态为空闲的车位)其所属的所有车位
export function getParkinglotList(query) {
  return request({
    url: '/car/parkinglot/getListByCarParkId',
    method: 'get',
    params: query
  })
}

//占用车辆
export function useParkingLot(params) {
  return request({
    url: '/car/parkinglot/useParkingLot',
    method: 'post',
    data: params
  })
}
