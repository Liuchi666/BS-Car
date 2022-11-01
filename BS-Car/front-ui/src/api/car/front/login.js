import request from '@/utils/request'

//前台登录
export function login(user) {
  return request({
    url: '/front/login',
    method: 'post',
    data: user
  })
}

//获取登陆成功的用户信息(登陆成功会返回一个token,我们把他存在cookie中了，
    // 所以在前端请求拦截器中会将携带用户信息的token存进请求头里面)
export function getUserInfo() {
  return request({
    url: '/front/login/getMemberInfo',
    method: 'get'
  })
}

//找回密码
export function resetpwd(registerVo) {
  return request({
    url: '/front/login/resetpwd',
    method: 'post',
    data: registerVo
  })
}
