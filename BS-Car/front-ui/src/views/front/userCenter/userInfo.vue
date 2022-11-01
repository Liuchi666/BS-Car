<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="用户昵称" prop="nickname">
      <el-input v-model="user.nickname" maxlength="30" />
    </el-form-item>
    <el-form-item label="年龄" prop="age">
      <el-input v-model="user.age" maxlength="4" />
    </el-form-item>
    <el-form-item label="手机号码" prop="phone">
      <el-input v-model="user.phone" maxlength="11" />
    </el-form-item>
    <el-form-item label="签名" prop="sign">
      <el-input v-model="user.sign" maxlength="50" />
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="user.sex">
        <el-radio label="0">女</el-radio>
        <el-radio label="1">男</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">保存</el-button>
      <el-button type="danger" size="mini" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserInfo } from "@/api/car/front/userCenter";

export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // 表单校验
      rules: {
        nickname: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" }
        ],
        age: [
          { required: true, message: "年龄不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        sign: [
          { required: true, message: "签名不能为空", trigger: "blur" }
        ],
      }
    };
  },
  methods: {
    //点击保存修改信息
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserInfo(this.user).then(response => {
            this.$modal.msgSuccess("用户信息修改成功");
          });
        }
      });
    },
    //点击关闭
    close() {
      //跳转到首页
      this.$router.push({path: '/homePage'})
    }
  }
};
</script>
