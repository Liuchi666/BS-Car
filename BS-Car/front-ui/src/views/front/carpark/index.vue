<template>
  <div class="carpark">
<!--    <link type="text/css" rel="stylesheet" href="@/assets/css/lc.css" />-->
<!--   ======================停车场布局========================== -->
<!--    大盒子-->
    <div>
      <div class="location">
        <div class="location-1">
          <b>您的位置：</b>
          <a href="/homePage">首页</a>
          <span> > </span>
          <a>附近停车场</a>
        </div>
      </div>
  <!--      停车场列表盒子-->
      <div v-for="carpark in carparkList" :key="carpark.carparkId">
        <!--   单个的停车场数据   -->
        <a :href="'/parkingLotInfo/' + carpark.carparkId">
          <div class="carpark-list">
            <!--  图片盒子  -->
            <span class="img-span">
              <img :src="carpark.photo">
            </span>
            <!--  停车场描述信息盒子  -->
            <span class="carpark_info">
              <h1>{{carpark.carparkName}}</h1>
              <h2>地址：{{carpark.address}}</h2>
              <h2 style="margin-top: 20px">简介：{{carpark.description}}</h2>
            </span>
          </div>
        </a>
      </div>

      <!--分页条-->
      <div class="page">
        <pagination
          v-show="1"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getAllList"
        />
        <br>
        <br>
        <br>
        <br>
      </div>

    </div>

    <!--   ================================================ -->
  </div>
</template>
<script>
//引入Api
import {getAll,getPageList} from '@/api/car/front/carpark'
// import {listCarpark} from '@/api/car/carpark'

//导入子组件
import Link from "@/layout/components/Sidebar/Link";

export default {
  name: "Header",
  components: {Link},
  data() {
    return {
      carparkList: [],  //用来封装后端响应的停车场列表
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
      },
      // 总条数
      total: 0,
    };
  },
  created() {
    this.getAllList()
  },
  methods: {
    //查询所有停车场数据
    getAllList(){
      getPageList(this.queryParams).then(response => {
        //将数据赋给this.carparkList用于数据展示
        this.carparkList = response.rows
        this.total = response.total
      })
    },
  }
}
</script>

<style scoped>
/*初始化默认样式*/
*{
  margin: 0;
  padding: 0;
}

/*大盒子*/
.carpark-list {
  border: 2px solid #bebfc3;  /*边框*/
  width: 1300px;
  height: 200px;
  margin-top: 20px;   /*距离顶部*/
  margin-left: 150px;  /*距离左边*/
  background-color: rgba(222, 238, 245, 0.47);
}
.img-span img{
  /*border: 3px solid green;  !*边框*!*/
  width: 300px;
  height: 195px;
}
.carpark_info{
  /*border: 3px solid blueviolet;  !*边框*!*/
  width: 980px;
  height: 190px;
  float: right;
}
.carpark_info h1{
  /*border: 1px solid blue;  !*边框*!*/
  width: 878px;
  height: 70px;

}
.carpark_info h2{
  /*border: 1px solid lawngreen;  !*边框*!*/
  width: 878px;
  height: 59px;
}

.location {
  /*margin-bottom: 20px;*/
  /*border-bottom: 1px dashed #dcdcdc;*/
  /*width: 1200px;*/
  /*height: 40px;*/
  /*float: left;*/
  /*line-height: 40px;*/
}
.location-1 b {
  border-left: 10px solid #333333;
  font-weight: normal;
  padding-left: 8px;
}
.location-1 a {
  font-size: 15px;
}

.page{
  /*border: 10px solid red;*/
  width: 878px;
  height: 50px;
}


</style>
