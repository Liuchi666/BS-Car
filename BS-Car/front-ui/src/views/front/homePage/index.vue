<template>
  <div class="carpark">

    <!--    头部显示区域-->
    <div>
<!--      <First/>-->
    </div>
<!--    轮播图-->
    <div>
      <el-carousel type="card" height="420px">
        <el-carousel-item>
          <img src="@/assets/images/bs1.jpg" alt="" style="width: 100%;height: 100%">
        </el-carousel-item>
        <el-carousel-item>
          <img src="@/assets/images/8396bbcc505171983e6c690158fea6f7.jpeg" alt="" style="width: 100%;">
        </el-carousel-item>
        <el-carousel-item>
          <img src="@/assets/images/9752708030d57ea3b4be4f661f7272b2.jpeg" alt="" style="width: 100%;">
        </el-carousel-item>
      </el-carousel>
    </div>

<!--    路由跳转到停车场-->
    <div class="btn">
      <el-button type="primary" plain style="width: 1000px;height: 100px;">
        <router-link :to="{ path: '/carparkInfo' }">
          <h1>查看附近停车场</h1>
        </router-link>
      </el-button>
    </div>
  </div>
</template>
<script>
//导入轮播图组件
import Banner from '@/front-layout/Banner'
import First from '@/front-layout/header'

export default {
  name: "Header",
  components:{Banner,First},
  data() {
    return {
    };
  },
  methods: {
  }
}
</script>

<style>
.el-menu-item{
  padding: 0 100px;
}
.btn{
  /*margin: 0 auto;*/
  padding-left:18%;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
