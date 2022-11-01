<template>
  <div class="resetpwd">
    <el-form ref="registerForm" :model="resetpwdForm" :rules="resetRules" class="register-form">
      <div class="title">
        <a href="#"><font class="active">找回密码</font></a>
      </div>
      <!--      手机号-->
      <el-form-item prop="phone">
        <el-input v-model="resetpwdForm.phone" type="text" auto-complete="off" placeholder="手机号">
          <svg-icon slot="prefix" icon-class="phone" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>

      <!--      验证码-->
      <el-form-item
        class="input-prepend restyle no-radius"
        prop="code">
        <div
          style="width: 100%; display: block; float: left; position: relative"
        >
          <el-input type="text" placeholder="手机验证码" v-model="resetpwdForm.code">
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
            @click="getCodeFun"
            :value="codeTest"
            style="border: none; background-color: none;float: right"
          >{{ codeTest }}</a
          >
        </div>
      </el-form-item>

      <!--      密码-->
      <el-form-item prop="password">
        <el-input
          v-model="resetpwdForm.password"
          type="password"
          auto-complete="off"
          placeholder="请输入新密码"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <!--      确认密码-->
      <el-form-item prop="confirmPassword">
        <el-input
          v-model="resetpwdForm.confirmPassword"
          type="password"
          auto-complete="off"
          placeholder="确认密码"
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
          @click.native.prevent="handleReSetPwd"
          round
        >
          <span>确 认</span>
        </el-button>

        <div style="float: left;">
          <router-link class="link-type" :to="'/frontLogin'">返回登录</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-register-footer" style="color: #121315">
      <span><font size="1px">Shared Parking Space © 2022-2023  All Rights Reserved.</font></span>
    </div>
  </div>
</template>


<script>
import {sendCode, register} from "@/api/car/front/register";
import {login, getUserInfo,resetpwd} from "@/api/car/front/login";


export default {
  name: "resetpwd",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.resetpwdForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      codeTest: "获取验证码", //用于插值表达式
      sending: false, //是否发送验证码
      second: 60, //倒计时间
      resetpwdForm: {  //找回密码表单中的数据
        phone: "",
        code: "",
        password: "",
        confirmPassword: ""
      },
      //表单校验
      resetRules: {
        phone: [
          { required: true, trigger: "blur", message: "请输入您的手机号" },
          { min: 11, max: 11, message: '手机号长度为11位', trigger: 'blur' }
        ],
        password: [
          {required: true, trigger: "blur", message: "请输入您的密码"},
          {min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, trigger: "blur", message: "请再次输入您的密码"},
          {required: true, validator: equalToPassword, trigger: "blur"}
        ],
        code: [{required: true, trigger: "blur", message: "请输入手机验证码"}]
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
      if(this.resetpwdForm.phone == ''){
        //提示信息
        this.$message({
          type: "error",
          message: "请输入手机号再进行操作",
        });
      }else {
        sendCode(this.resetpwdForm.phone).then(response => {
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
    //点击确认找回密码
    handleReSetPwd(){
      resetpwd(this.resetpwdForm).then(response => {
        //提示信息
        this.$message({
          type: "success",
          message: "密码修改成功",
        })
        //跳转到登陆页面
        this.$router.push("/frontLogin");
      })
    },

  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.resetpwd {
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
