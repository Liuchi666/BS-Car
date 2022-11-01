<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar :user="user" />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="people" />用户昵称
                <div class="pull-right">{{ user.nickname }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="age" />年龄
                <div class="pull-right">{{ user.age }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="phone1" />手机号码
                <div class="pull-right">{{ user.phone }}</div>
              </li>
              <!--              <li class="list-group-item">-->
              <!--                <svg-icon icon-class="tree" />所属部门-->
              <!--                <div class="pull-right" v-if="user.dept">{{ user.dept.deptName }} / {{ postGroup }}</div>-->
              <!--              </li>-->
              <li class="list-group-item">
                <svg-icon icon-class="date" />创建日期
                <div class="pull-right">{{ user.createTime }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd />
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
//引入组件
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
//引入Api
import { getUserInfo } from "@/api/car/front/userCenter";
//引入cookie
import Cookies from 'js-cookie'

export default {
  name: "UserCenter",
  components: { userAvatar, userInfo, resetPwd },
  data() {
    return {
      user: {},  //完整的用户信息
      userInfo:{},  //存在cookie中的用户信息(但是不完整)
      activeTab: "userinfo"
    };
  },
  created() {
    //执行检验是否登录的方法
    this.isLogin()
    //调用获取用户信息的方法
    this.getUser();
  },
  methods: {
    //检验是否登录
    isLogin(){
      //从cookie中查看是否有用户信息
      var userStr = Cookies.get("front_userInfo");
      if (userStr) {   //判断cookie是否存在
        //因为cookie中存放的都是字符串,所以要先将字符串转换为js对象
        // debugger
        this.userInfo = JSON.parse(userStr)
      }else {  //cookie不存在
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
    },
    //获取用户详细信息
    getUser() {
      getUserInfo(this.userInfo.userId).then(response => {
        //将查询的数据赋给this.user用于数据展示
        this.user = response.data
      });
    }
  }
};
</script>
