<template>
  <div class="msg">

    <!--    轮播图-->
    <div>
      <el-carousel height="400px">
        <el-carousel-item>
          <img src="@/assets/images/bs1.jpg" alt="" style="width: 100%;height: 100%">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="location">
      <div class="location-1">
        <b>您的位置：</b>
        <a href="/homePage">首页</a>
        <span> > </span>
        <a>在线留言</a>
      </div>
    </div>

<!--    留言区域大盒子-->
    <div class="msg-box">
      <hr color="#acc1fd">
<!--      时间线盒子-->
      <!--  填写表单状态  -->
      <span v-if="!isShow" class="timeline">
        留言流程如下：
        <br>
        <br>
        <el-timeline :reverse="reverse">
          <el-timeline-item icon="el-icon-s-order" type="" color="#0bbd87" size="large" timestamp="...">
            填写留言内容
          </el-timeline-item>
          <el-timeline-item timestamp="...">
            留言内容待审核
          </el-timeline-item>
          <el-timeline-item timestamp="...">
            发表留言成功
          </el-timeline-item>
        </el-timeline>
      </span>
      <!--  留言成功状态  -->
      <span v-else class="timeline">
        留言流程如下：
        <br>
        <br>
        <el-timeline :reverse="reverse">
          <el-timeline-item icon="el-icon-check" color="grey" timestamp="2022-10-13 20:58:57">
            已填写留言内容
          </el-timeline-item>
          <el-timeline-item icon="el-icon-check" color="grey" timestamp="...">
            留言内容审核通过
          </el-timeline-item>
          <el-timeline-item icon="el-icon-check" color="" type="success" color="" size="large" timestamp="2022-10-13 20:58:57">
            发表留言成功
          </el-timeline-item>
        </el-timeline>
      </span>
<!--      留言表单盒子-->
      <span class="msg-form-box">
        <!--  标题 -->
        <div  class="msg-title">
          <h1 style="text-align: center;color: #b2b2b2">在线留言</h1>
        </div>
        <br>
        <hr color="#b2b2b2">
        <br>
        <!--  表单 -->
        <div class="msg-form">
          <el-form :model="msgForm" :rules="rules" ref="msgForm" label-width="78px" label-position="left" size="medium" class="demo-ruleForm">
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="msgForm.nickname" placeholder="请输入您的昵称"></el-input>
            </el-form-item>
            <br>
            <br>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="msgForm.phone" placeholder="请输入您的手机号"></el-input>
            </el-form-item>
            <br>
            <br>
            <el-form-item label="留言类型" prop="type">
              <el-select v-model="msgForm.type" placeholder="请选择留言的类型">
                <el-option style="text-align: center" label="报修信息" value="0"></el-option>
                <el-option style="text-align: center" label="付费异常" value="1"></el-option>
                <el-option style="text-align: center" label="车辆开锁异常" value="2"></el-option>
              </el-select>
            </el-form-item>
            <br>

            <br>
            <el-form-item label="留言内容" prop="content">
              <el-input type="textarea" :autosize="{ minRows: 8, maxRows: 10}" v-model="msgForm.content" placeholder="请输入留言内容" aria-valuemax="3"></el-input>
            </el-form-item>
            <br>
            <br>
            <el-form-item>
              <el-button style="font-size: 20px;border: 10px solid dodgerblue;" size="medium" type="primary" @click="submitForm('msgForm')">发表留言</el-button>
              <el-button style="font-size: 20px;border: 10px solid #ffffff;" @click="resetForm('msgForm')">重置内容</el-button>
            </el-form-item>
          </el-form>
        </div>
      </span>
    </div>

  </div>
</template>

<script>
//导入Api
import { publish } from "@/api/car/front/message";
import Cookies from 'js-cookie'

export default {
  name: "message",
  data(){
    return{
      reverse: false,  //控制时间线组件内容是否反转
      isShow: false,  //控制开启时间线组件的显示状态(约定true为显示留言成功的样式，false为还未留言的样式)
      msgForm: {  //表单数据
        nickname: '',  //姓名
        phone: '', //手机号
        type: '',  //留言类型
        content: '', //留言内容
      },
      rules: {  //表单校验规则
        nickname: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { min: 11, max: 11, message: '手机号长度为11位', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择留言内容', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请填写留言内容', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    //执行检验是否登录的方法
    this.isLogin()
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
    //发表留言按钮
    submitForm() {
      // alert("发表成功")
      if(this.msgForm == ''){
        //提示消息
        this.$message({
          type: 'error',
          message: '请填写完整的信息!'
        })
      }else {
        publish(this.msgForm).then(response => {
          //提示消息
          this.$message({
            type: 'success',
            message: '留言发表成功!'
          })
          //切换时间线组件样式
          this.isShow = true
          //清空表单内容
          this.msgForm.nickname = ''
          this.msgForm.phone = ''
          this.msgForm.type = ''
          this.msgForm.content = ''
        })
      }
    },
    //清空表单按钮
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
/*初始化默认样式*/
* {
  margin: 0;
  padding: 0;
}

.location-1 b {
  border-left: 10px solid #333333;
  font-weight: normal;
  padding-left: 8px;
}
.location-1 a {
  font-size: 15px;
}

.msg-box {
  border: 1px solid #b2b2b2;  /*边框*/
  width: 1200px;
  height: 650px;
  margin-top: 20px;   /*距离顶部*/
  margin-left: 150px;  /*距离左边*/
}

.timeline{
  /*border: 1px solid beige;*/
  border-right: 1px solid #5fc7ed;
  width: 200px;
  height: 570px;
  margin-left: 70px;
  margin-top: 50px;
  float: left;
  font-size: 20px;
}
.msg-form-box{
  border: 2px solid rgba(245, 245, 220, 0.42);
  margin-right: 50px;
  margin-top: 50px;
  width: 793px;
  height: 570px;
  float: right;
}

.msg-title{
  /*border: 2px solid beige;*/
  height: 40px;
}

.msg-form{
  /*border: 2px solid beige;*/
  margin-top: 10px;
  height: 470px;
}

</style>
