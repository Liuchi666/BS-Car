<template>
  <div>
<!--    头部logo显示-->
    <div class="top">
      <div class="content">
        <div class="left">
          <span class="logo">
            <a href="/homePage"><img src="@/assets/logo/logo.png" class="img-fluid"
                                     width="180" height="100" alt="共享车位平台logo"></a>
          </span>
          <span class="log-right">
            <span style="float: right">
              <font size="50px">共享车位平台</font><br/>
              <p style="color: black">解决您的停车难题，让您放心出行，是我们的宗旨</p>
            </span>
          </span>
        </div>
        <div class="right">
          <br/>
          <div style="float: right;color: black">服务热线:<span style="color: #C03639">0571-28281111</span></div>
        </div>
      </div>
    </div>

<!--    头部导航栏区域-->
    <Header/>

<!--    路由显示区域-->
    <div>
      <router-view>
      </router-view>
    </div>

    <hr/>
<!--    尾部信息-->
    <Footer/>
  </div>
</template>

<script>
import Header from '@/views/front/header'
import Footer from '@/front-layout/Footer'

export default {
  name: "front-layout",
  components: { Header,Footer },  //注册组件
}
</script>

<style>
.top{
}
.content {
  height: 100px;
  max-width: 1200PX;
  width: 100%;
  margin: 0 auto;
}
.left {
  height: 100px;
  margin: 0 auto;
  font-size: 14px;
  width: 50%;
  float: left;
}
.right {
   height: 100px;
   margin: 0 auto;
   width: 50%;
   float: right;
   font-size: 24px;
 }


</style>
