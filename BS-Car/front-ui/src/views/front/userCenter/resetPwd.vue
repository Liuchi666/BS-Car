<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="旧密码" prop="oldPassword">
      <el-input v-model="user.oldPassword" placeholder="请输入旧密码" type="password" show-password/>
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword">
      <el-input v-model="user.newPassword" placeholder="请输入新密码" type="password" show-password/>
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" placeholder="请确认新密码" type="password" show-password/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">保存</el-button>
      <el-button type="danger" size="mini" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/car/front/userCenter.js";
import Cookies from 'js-cookie'

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      userInfo: {},  //含有当前登录用户id
      // 表单校验
      rules: {
        oldPassword: [
          { required: true, message: "旧密码不能为空", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  created() {
    //执行检验是否登录的方法
    this.isLogin()
  },
  methods: {
    //修改密码
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword,this.userInfo.userId).then(response => {
            this.$modal.msgSuccess("修改成功");
          });
        }
      });
    },
    //关闭页面
    close() {
      //跳转到首页
      this.$router.push({path: '/homePage'})
    },
    //检验是否登录的方法
    isLogin(){
      //从cookie中查看是否有用户信息
      var userStr = Cookies.get("front_userInfo");
      if (userStr) {   //判断cookie是否存在
        //因为cookie中存放的都是字符串,所以要先将字符串转换为js对象
        // debugger
        this.userInfo = JSON.parse(userStr)
      }else {
        //提示信息
        this.$confirm('您当前尚未登录，请您登录后再进行操作', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //跳转到登陆页面
          this.$router.push({path: '/frontLogin'})
        }).catch(() => {
          this.$router.push({path: '/frontLogin'})
        })
      }
    }
  }
};
</script>
