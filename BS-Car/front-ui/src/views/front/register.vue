<template>
  <div class="register">
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
      <div class="title">
        <a href="/frontLogin">登录</a>
        <span>&nbsp;&nbsp;&nbsp;·&nbsp;&nbsp;&nbsp;</span>
        <a href="/frontRegister"><font :class="active">注册</font></a>
      </div>
      <!--      昵称(el-form-item中的prop属性控制上面的registerRules表单校验)-->
      <el-form-item prop="nickname">
        <el-input v-model="registerForm.nickname" type="text" auto-complete="off" placeholder="昵称">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <!--      手机号-->
      <el-form-item prop="phone">
        <el-input v-model="registerForm.phone" type="text" auto-complete="off" placeholder="手机号">
          <svg-icon slot="prefix" icon-class="phone" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>

      <!--      验证码-->
      <el-form-item
        class="input-prepend restyle no-radius"
        prop="code"
      >
        <div
          style="width: 100%; display: block; float: left; position: relative"
        >
          <el-input type="text" placeholder="手机验证码" v-model="registerForm.code">
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
          </el-input>
        </div>
        <div
          class="btn"
          style="position: absolute; right: 20px; top: 4px; width: 40%"
        >
          <a
            href="javascript:"
            type="button"
            @click="getCodeFun()"
            :value="codeTest"
            style="border: none; background-color: none;float: right"
          >{{ codeTest }}</a
          >
        </div>
      </el-form-item>

      <!--      密码-->
      <el-form-item prop="password">
        <el-input
          v-model="registerForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <!--      确认密码-->
      <el-form-item prop="confirmPassword">
        <el-input
          v-model="registerForm.confirmPassword"
          type="password"
          auto-complete="off"
          placeholder="确认密码"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>


      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="success"
          style="width:100%;"
          @click.native.prevent="handleRegister"
          round
        >
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <p align="center">
          点击 “注册” 即表示您同意并愿意遵守简书
          <br/>
          <font color="#3194d0">
            <a target="_blank" href="http://www.jianshu.com/p/c44d171298ce">用户协议</a>
          </font>
          和
          <font color="#3194d0">
            <a target="_blank" href="http://www.jianshu.com/p/2ov8x3">隐私政策</a>
          </font>
        </p>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-register-footer" style="color: #121315">
      <span><font size="1px">Shared Parking Space © 2022-2023  All Rights Reserved.</font></span>
    </div>
  </div>
</template>


<script>
import {sendCode, register} from "@/api/car/front/register.js";


export default {
  name: "Register",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      active: '',   //字体样式
      codeTest: "获取验证码", //用于插值表达式
      sending: false, //是否发送验证码
      second: 60, //倒计时间
      registerForm: {  //注册表单中的数据
        nickname: "",
        phone: "",
        code: "",
        password: "",
        confirmPassword: "",
      },
      //表单校验
      registerRules: {
        nickname: [
          {required: true, trigger: "blur", message: "请输入您的昵称"},
          {min: 2, max: 20, message: '用户账号长度必须介于 2 和 20 之间', trigger: 'blur'}
        ],
        password: [
          {required: true, trigger: "blur", message: "请输入您的密码"},
          {min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, trigger: "blur", message: "请再次输入您的密码"},
          {required: true, validator: equalToPassword, trigger: "blur"}
        ],
        code: [{required: true, trigger: "blur", message: "请输入手机验证码"}],
        phone: [
          { required: true, trigger: "blur", message: "请输入您的手机号" },
          { min: 11, max: 11, message: '手机号长度为11位', trigger: 'blur' }
        ],
      },
      loading: false,
      captchaEnabled: true
    };
  },
  created() {
    this.active = "active"  //让对立的字体变色

  },
  methods: {
    //通过输入的手机号发送验证码
    getCodeFun() {
      if(this.registerForm.phone == ''){
        //提示信息
        this.$message({
          type: "error",
          message: "请输入手机号再进行操作",
        });
      }else {
        sendCode(this.registerForm.phone).then(response => {
          //提示信息
          this.$message({
            type: "success",
            message: "验证码已发送，请注意查收",
          });
          //修改发送状态
          this.sending = true;
          this.codeTest = 60  //在倒计时前先直接赋初始值，可以解决前端倒计时的延迟出现问题
          //倒计时开始
          this.timeDown();
        })
      }
    },
    //验证码倒计时
    timeDown() {
      let result = setInterval(() => {
        //减少一秒
        --this.second;
        //将减少一秒后的数赋给this.codeTest用于前端显示
        this.codeTest = this.second;
        if (this.second < 1) {
          //停止周期函数
          clearInterval(result);
          //给变量重新赋值
          this.sending = true;
          this.second = 60;
          this.codeTest = "获取验证码";
        }
      }, 1000);
    },
    //注册
    handleRegister(){
      register(this.registerForm).then(response => {
        //提示信息
        this.$message({
          type: "success",
          message: "注册成功",
        })
        //跳转到登陆页面
        this.$router.push("/frontLogin");
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
  margin: 0px auto 40px auto;
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
