import request from '@/utils/request'

// 给手机号发送短信返回验证码
export function sendCode(phone) {
  return request({
    url: '/front/register/send/' + phone,
    method: 'get'
  })
}

//注册
export function register(registerVo) {
  return request({
    url: '/front/register',
    method: 'post',
    data: registerVo
  })
}
