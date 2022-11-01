<template>
  <div class="call">

    <!--    轮播图-->
    <div>
      <el-carousel height="400px">
        <el-carousel-item>
          <img src="@/assets/images/bs1.jpg" alt="" style="width: 100%;height: 100%">
        </el-carousel-item>
      </el-carousel>
    </div>


    <!--联系我们-->
    <div class="linked-1">
      <div class="location">
        <div class="location-1">
          <b>你的位置：</b>
          <a>首页</a><span> > </span>
          <a>联系我们</a>
        </div>
      </div>
      <div class="products-title">
        联系我们
      </div>
      <div class="linked">
        <div class="linked-left">
          <h1>XXXXXXXX科技有限公司</h1>
          <p>业务负责人：X先生 15012722143<br></p>
          <p>网址：https://www.XXXXXX.com<br></p>
          <p>地址：广东省中山市火炬开发区XXX路AAA号A208<br></p>
          <div class="wxh">
            <img src="@/assets/images/wx3.png" alt="微信号">
            <span>微信扫一扫</span>
          </div>
        </div>
<!--        地图显示区域-->
        <div class="linked-right" style="border: 1px solid #0d4b4c">
          <div id="map">
          <!--  集成高德地图1-2 定义一个容器，即地图显示位置-->
            <div id="container" style="width: 100%; height: 563px"/>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<script>
// 集成高德地图1-6 信息窗体的内容
var content = [
    "<div style = 'font-size: 14px; color: black;width:320px;height: 90px'><h3>火炬开发区德创智谷</h3>\n" +
    "            <hr>\n" +
    "            <h5>地址：广东省中山市火炬开发区XXX路AAA号A208</h5></div>"
];

export default {
  name: "index",
  data() {
    return {

    };
  },
  mounted() {  //页面渲染之后执行
    //集成高德地图1-3 创建地图实例
    var map = new AMap.Map("container", {
      zoom: 18,  //地图缩放级别
      center: [113.44417144272612,22.56423647011236],  //默认地图中心点
      resizeEnable: true,
      mapStyle: 'amap://styles/whitesmoke'
    });

    // 集成高德地图1-7 创建 infoWindow 实例
    var infoWindow = new AMap.InfoWindow({
      anchor: 'top-left',  //窗口出现的位置
      content: content.join("<br>")  //传入 dom 对象，或者 html 字符串
    });

    // 集成高德地图1-8 定义点击事件方法，打开信息窗口
    var clickHandler = function(e){
      infoWindow.open(map,[113.44417144272612,22.56423647011236]);
    }

    //集成高德地图1-4 创建一个 Marker 实例
    var marker = new AMap.Marker({
      position: new AMap.LngLat(113.44417144272612,22.56423647011236),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
      title: '中山市火炬开发区德创智谷'
    });
    // 集成高德地图1-9 绑定点击事件
    marker.on('click',clickHandler)

    //集成高德地图1-5 并将创建的点标记添加到已有的地图实例
    map.add(marker);

  },
  methods: {

  }
}
</script>

<style scoped>
.linked-1 {
  width: 1350px;
  margin: 0 auto;
  height: 700px;
}
.location {
  margin-bottom: 20px;
  border-bottom: 1px dashed #dcdcdc;
  width: 1200px;
  height: 40px;
  float: left;
  line-height: 40px;
}
.location-1 b {
  border-left: 10px solid #333333;
  font-weight: normal;
  padding-left: 8px;
}
.location-1 a {
  font-size: 15px;
}
.products-title {
  display: none;
}
.linked {
  padding-top: 65px;
}
.linked-left {
  float: left;
  width: 400px;
  color: #606266;
}
.linked-left h2 {
  margin: 0;
  font-size: 30px;
  font-weight: normal;
}
.linked-left p {
  margin-top: 15px;
}
.wxh {
  width: 300px;
  text-align: center;
  margin: 40px auto;
}
.wxh img {
  width: 100%;
  padding-bottom: 15px;
}
.linked-right {
  float: right;
  width: 900px;
}



.BMap_mask {
  background: transparent url(https://api.map.baidu.com/images/blank.gif);
}
.pano_close {
  position: absolute;
  right: 10px;
  top: 10px;
  width: 40px;
  cursor: pointer;
  height: 40px;
  line-height: 40px;
  border-radius: 3px;
  background-color: rgba(37,37,37,.9);
  background-image: url(https://api.map.baidu.com/images/panorama/close.png);
  background-repeat: no-repeat;
  background-position: center center;
  background-size: 90%;
}
.pano_pc_indoor_exit {
  position: absolute;
  right: 60px;
  top: 10px;
  width: 89px;
  cursor: pointer;
  height: 40px;
  line-height: 40px;
  color: #ebedf0;
  border-radius: 3px;
  background-color: #252525;
  background-color: rgba(37,37,37,.9);
  background-image: url(https://api.map.baidu.com/images/panorama/indoor_exit.png);
  background-repeat: no-repeat;
  background-position: 15px 12px;
}

element.style {
  cursor: default;
  white-space: nowrap;
  color: black;
  background: none;
  font: 11px / 15px arial, sans-serif;
  inset: auto auto 2px 2px;
  position: absolute;
  z-index: 10;
  text-size-adjust: none;
}




</style>
