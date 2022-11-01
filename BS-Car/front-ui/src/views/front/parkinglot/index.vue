<template>
  <div class="parkinglot-big-box">
    <!--位置导航-->
    <div class="location">
      <div class="location-1">
        <b>您的位置：</b>
        <a href="/homePage">首页</a>
        <span> > </span>
        <a href="/carparkInfo">{{ carparkInfo.carparkName }}</a>
        <span> > </span>
        <a>所有车位</a>
      </div>
    </div>

<!--    车位列表大盒子-->
    <div class="parkinglot-box">

      <!-- 查询空闲状态的车位 -->
      <div class="query">
        <el-button type="primary" size="medium" round plain class="query-button" @click="query">一键快速查找空闲车位</el-button>
      </div>

      <!--  标题  -->
      <h1>{{carparkInfo.carparkName}}</h1>
      <hr color="#5fc7ed">
      <br>
      <br>

      <!--  车位列表  -->
      <el-row :gutter="15" style="margin-left: 3px">
        <div class="oneParkingLot">
        <el-col v-for="parkinglot in parkinglotList" :key="parkinglot.parkinglotId" :span="8" class="parkinglot">
          <!--  图片盒子  -->
          <span class="img-box">
            <img :src="parkinglot.photo">
          </span>
          <!--  信息盒子  -->
          <span class="info-box">
              <h1>{{parkinglot.sort}}</h1>
              <h2 v-if="parkinglot.status == 0" style="background-color: rgba(0,255,14,0.44);font-size: 60px;border-radius: 10px;margin-left: 20px;height: 70px">空闲</h2>
              <h2 v-if="parkinglot.status == 1" style="background-color: rgba(5,245,232,0.46);font-size: 60px;border-radius: 10px;margin-left: 20px;margin-top: 40px;height: 70px">已用</h2>
              <h2 v-if="parkinglot.status == 2" style="background-color: rgba(255,0,0,0.58);font-size: 60px;border-radius: 10px;margin-left: 20px;margin-top: 40px;height: 70px">损坏</h2>
              <a v-if="parkinglot.status == 0" @click="openWindow(parkinglot.parkinglotId)">
                <h2 style="border-radius: 10px;font-size: 30px">点击使用</h2>
              </a>
          </span>
        </el-col>
        </div>
      </el-row>
    </div>

    <!--分页条-->
    <div class="page">
      <pagination
        v-show="1"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getParkinglotListByCarParkId"
      />
      <br>
    </div>

<!--    点击使用按钮之后的弹出层-->
    <el-dialog title="填写您想要停放的车辆信息" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="submitInfo" :rules="rules">
        <el-form-item label="车辆型号" prop="carName">
          <el-input v-model="submitInfo.carName" placeholder="请输入车辆型号"></el-input>
        </el-form-item>
        <br>
        <el-form-item label="车牌号" prop="carNumber">
          <el-input v-model="submitInfo.carNumber" placeholder="请输入车牌号"></el-input>
        </el-form-item>
        <br>
        <el-form-item label="车辆照片">
            <image-upload v-model="submitInfo.photo"/>
        </el-form-item>
        <br>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" size="medium" style="font-size: 20px;border: 10px solid dodgerblue;">提 交 信 息</el-button>
        <el-button @click="cancel" style="font-size: 20px;border: 10px solid #ffffff;">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
//导入Api
import { getCarpark } from '@/api/car/carpark'
import { getParkinglotList,useParkingLot } from '@/api/car/front/parkinglot'
//导入cookie
import Cookies from "js-cookie";
import log from "@/views/monitor/job/log";

export default {
  name: "index",
  data(){
    return{
      carParkId: '',  //用来接收停车场跳转过来携带的路由参数
      carparkInfo: '',  //用来封装后端查询的当前停车场的详细信息
      parkinglotList: [],  //用来封装后端响应的车位列表数据
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 15,
        carparkId: '',
        status:'',  //用来控制是否查空闲车位
      },
      // 总条数
      total: 0,
      open: false,  //控制弹窗 true为打开 false为关闭
      submitInfo: {  //点击弹窗中的提交按钮(即占位)，封装用户向后端提交的信息
        userId: '',  //车主id
        carparkId: '',  //停车场id
        parkinglotId: '', //车位id
        carName: '',  //车辆型号
        carNumber: '', //车牌号
        photo: '',  //车辆照片
      },
      userInfo: '',  //用来封装已登录的用户信息(从cookie中取)
      // 表单校验
      rules: {
        carName: [
          { required: true, message: "车辆型号不能为空", trigger: "blur" }
        ],
        carNumber: [
          { required: true, message: "车牌号不能为空", trigger: "blur" }
        ],
      },
    }
  },
  created() {
    //执行检验是否登录的方法
    this.isLogin()
    //获取路由路径中的课程id(页面跳转的时候携带了)
    if(this.$route.params && this.$route.params.id){
      this.carParkId = this.$route.params.id
      this.queryParams.carparkId = this.carParkId
    }
    // console.log(this.carParkId)
    //页面渲染之前根据停车场id获取停车场详细信息
    this.getCarparkInfo()
    //调用方法获取车位列表
    this.getParkinglotListByCarParkId()
  },
  methods:{
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
    //根据停车场id获取停车场的详细信息
    getCarparkInfo(){
      getCarpark(this.carParkId).then(res => {
        this.carparkInfo = res.data
      })
    },
    //根据停车场id获取其所属的所有车位信息
    getParkinglotListByCarParkId(){
      getParkinglotList(this.queryParams).then(res => {
        //将响应的数据赋给this.parkinglotList用于数据展示
        this.parkinglotList = res.rows
        this.total = res.total
      })
    },
    //一键找空闲车位
    query(){
      //后端数据库中约定 0 代表空闲
      this.queryParams.status = '0'
      //调用查询
      this.getParkinglotListByCarParkId()
    },
    //点击使用按钮执行
    openWindow(parkinglotId){
      //打开弹窗
      this.open = true;
      //将停车场id、车位id、用户id赋给this.submitInfo
      this.submitInfo.carparkId = this.carParkId
      this.submitInfo.parkinglotId = parkinglotId
      this.submitInfo.userId = this.userInfo.userId
    },
    //关闭弹窗
    cancel(){
      this.open = false;
      //清空表单中的数据
      this.submitInfo.carName = '',
      this.submitInfo.carNumber = '',
      this.submitInfo.photo = ''
    },
    //点击弹窗中的提交信息
    submitForm(){
      //调用发请求方法
      useParkingLot(this.submitInfo).then(res => {
        //提示信息
        this.$message({
          type: 'success',
          message: '操作成功，您可以使用该车位了'
        })
        //将车辆信息清除
        this.submitInfo.carName = '',
        this.submitInfo.carNumber = '',
        this.submitInfo.photo = ''
        //关闭弹窗
        this.open = false
        //刷新页面
        this.getParkinglotListByCarParkId()
      })
    },
  },
}
</script>

<style scoped>
/*初始化默认样式*/
*{
  margin: 0;
  padding: 0;
}

.parkinglot-box{
  border: 1px solid #d5f1d8;  /*边框*/
  /*border: 1px solid red;  !*边框*!*/
  width: 1200px;
  height: 1050px;
  margin-top: 20px;   /*距离顶部*/
  margin-left: 150px;  /*距离左边*/
}
.page{
  /*border: 1px solid #54ed54;  !*边框*!*/
  width: 600px;
  margin-left: 370px;
}

.parkinglot {
  /*border: 3px solid lightgreen;*/
  height: 198px;
  background-color: rgba(173, 226, 243, 0.44);
}
.parkinglot-box h1{
  text-align: center;
  height: 60px;
  font-size: 50px;
  background-color: rgba(217, 243, 251, 0.47);
}
.oneParkingLot{
  /*border: 2px solid #00bb29;  !*边框*!*/
  width: 1192px;
  height: 200px;
}
.img-box{
  /*border: 1px solid blue;*/
  width: 202px;
  height: 192px
}
.img-box img{
  /*style="width: 200px;height: 190px"  每一个停车场小盒子大小 398*198      */
  width: 200px;
  height: 190px
}
.info-box{
  /*border: 1px solid blue;*/
  width: 170px;
  height: 190px;
  float: right;
}
.info-box h1{
  text-align: center;
  height: 50px;
  background-color: rgba(54, 98, 192, 0.41);
}
.info-box h2{
  /*border: 1px solid #f72cc7;*/
  width: 140px;
  height: 35px;
  font-size: 20px;
  text-align: center;
  background-color: #00a8e9;
  margin-left: 21px;
  margin-top: 10px;
}
.query{
  /*border: 2px solid red;*/
  width: 500px;
  height: 60px;
  margin-left: 430px;
  margin-top: 5px;
}
.query-button{
  font-size: 30px;
}



</style>
