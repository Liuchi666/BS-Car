<template>
  <div id="head">
    <!--头部导航栏-->
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#ffffff"
      active-text-color="#f7fe2e" align="center" router="true">
      <el-menu-item index="/homePage">首页</el-menu-item>
      <el-menu-item index="/carparkInfo">附近停车场</el-menu-item>
<!--      <el-menu-item  index="/parkingLotInfo">查找车位</el-menu-item>-->
      <el-menu-item index="/msg">在线留言</el-menu-item>
      <el-menu-item index="/call">联系我们</el-menu-item>

      <!--==============未登录则显示空白头像================-->
      <div v-if="userInfo.avatar == '' || userInfo.avatar == undefined">
        <a href="/frontLogin" title="登录">
          <el-avatar icon="el-icon-user-solid" class="el-avatar" style="margin-top: 12px"></el-avatar>
          <span><font size="5px" color="#f0fff0">点击登录</font></span>
        </a>
      </div>
      <!--==============登陆成功则显示头像和下拉列表================-->
      <div v-if="userInfo.avatar != '' && userInfo.avatar != undefined" class="nav-right">
        <el-dropdown trigger="click">
          <!--        头像-->
          <div>
<!--            https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201411%2F09%2F20141109001942_Qczrs.thumb.700_0.png&refer=http%3A%2F%2Fimg5.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1668851305&t=dc85ed8c69b781a3f5347ed85f937a37-->
            <el-avatar style="width: 60px ;height: 60px"
                       :src="userInfo.avatar"></el-avatar>
          </div>
          <!--        下拉菜单-->
          <el-dropdown-menu slot="dropdown">
            <router-link to="userCenter">
              <el-dropdown-item>个人中心</el-dropdown-item>
            </router-link>

            <el-dropdown-item divided @click.native="logout">
              <span>退出登录</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

    </el-menu>
  </div>
</template>

<script>
//导入cookie
import Cookies from "js-cookie";

export default {
  name: "Header",
  data() {
    return {
      activeIndex: '/homePage',  //路由路径,控制上面elementUI中的导航菜单的默认访问的页面
      userInfo: '',  //用来封装已登录的用户信息(从cookie中取)
    };
  },
  created() { //页面渲染之前执行
    // debugger
    //登陆成功后，获取cookie中存的登录用户信息
    this.getLoginUserInfo()
  },
  mounted() {
    // debugger
    // console.log("==============" + this.userInfo)
  },
  methods: {
    // 菜单激活回调(饿了么菜单导航栏组件的)
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    //登陆成功后，获取cookie中存的登录用户信息
    getLoginUserInfo(){
      // debugger
      var userStr = Cookies.get("front_userInfo");
      if (userStr) {   //判断cookie是否存在
        //因为cookie中存放的都是字符串,所以要先将字符串转换为js对象
        console.log(userStr)
        // debugger
        this.userInfo = JSON.parse(userStr)
      }
    },
    //退出登录
    logout(){
      //js中cookie没有提供清除的方法，所以可以通过设置一个同名的但是值为''的覆盖掉旧的，就相当于清除了
      Cookies.set('front_token','',{domain: 'localhost'})
      Cookies.set('front_userInfo','',{domain: 'localhost'})
      //重新跳转到首页面
      window.location.href = "/homePage";
    },

  }
}
</script>

<style lang="scss" scoped>
/** 控制导航栏宽度*/
.el-menu-item {
  padding: 0 100px;
}
//
//.el-avatar{
//  position: center;
//}
</style>
