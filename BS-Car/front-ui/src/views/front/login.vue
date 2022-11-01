<template>
  <div class="register">
    <el-form ref="registerForm" :model="loginForm" :rules="loginRules" class="register-form">
      <div class="title">
        <a href="/frontLogin"><font :class="active">登录</font></a>
        <span>&nbsp;&nbsp;&nbsp;·&nbsp;&nbsp;&nbsp;</span>
        <a href="/frontRegister">注册</a>
      </div>
      <el-form-item prop="phone">
        <el-input v-model="loginForm.phone" type="text" auto-complete="off" placeholder="手机号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="success"
          style="width:100%;"
          @click.native.prevent="handleLogin"
          round
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: left;">
          <router-link class="link-type" :to="'/frontResetPwd'">忘记密码了？找回密码</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-register-footer" style="color: #121315">
      <span><font size="1px">Shared Parking Space © 2022-2023  All Rights Reserved.</font></span>
    </div>
  </div>
</template>
验证码

<script>
//引入Api
import { login, getUserInfo } from "@/api/car/front/login";
//引入cookie
import Cookies from 'js-cookie'

export default {
  name: "Login",
  data() {
    return {
      active: '',
      loginForm: {   //登录表单
        phone: "",
        password: "",
      },
      token: "",   //用来接收登陆成功后返回的token
      userInfo: {},  //用户登陆成功后，用来封装用户的信息

      loginRules: {
        phone: [
          { required: true, trigger: "blur", message: "请输入您的手机号" },
          { min: 11, max: 11, message: '手机号长度为11位', trigger: 'blur' }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ]
      },
      loading: false,
    };
  },
  created() {
    this.active="active"
  },
  methods: {
    //登录
    handleLogin(){
      login(this.loginForm).then(repsonse => {
        //将后端响应的token赋给this.token
        this.token = repsonse.token
        /*  将token放进cookie中(存在cookie中就能在其他页面中取出了)
                第一个参数：cookie的名称
                第二个参数：cookie的值
                第三个参数: cookie传递的作用范围(localhost表示只有请求url中是localhost的才能传递，其他的ip不能传递)    */
        Cookies.set('front_token',this.token,{domain: 'localhost'})

        //调用接口，根据token获取用户信息(在请求拦截其中进行判断了，符合要求就在请求头中携带token)
        getUserInfo().then(response => {
          //将获取到的用户信息赋给this.loginInfo
          this.userInfo = response.userInfo
          //将用户信息存进cookie(存在cookie中就能在其他页面中取出了),用于首页的用户数据显示(注意cookie中只能存字符串，所以先将后端响应的对象转为字符串)
          Cookies.set('front_userInfo',JSON.stringify(this.userInfo),{domain: 'localhost'})
        })
        //跳转到首页(默认是pages/index.vue)
        // this.$router.push({path: '/homePage'})
        this.$router.replace({path: '/homePage'})
      })
    },

  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../../assets/images/login.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  border-radius: 6px;
  background: #e8f4ff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.register-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.register-code-img {
  height: 38px;
}
.active {
  color: #00bb29;
}

</style>
