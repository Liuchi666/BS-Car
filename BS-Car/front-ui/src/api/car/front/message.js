import request from '@/utils/request'

//前台用户发表留言
export function publish(data) {
  return request({
    url: '/front/msg/publish',
    method: 'post',
    data: data
  })
}
