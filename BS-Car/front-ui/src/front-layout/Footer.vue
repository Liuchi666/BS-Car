<template>
  <div id="footer" class="">
<!--    页面尾部-->
    <MyContainer>
      <el-row>
        <el-col :span="2">
          <h4>社会服务</h4>
          <a>平台协议</a>
          <a>防骗指南</a>
          <a>使用帮助</a>
        </el-col>
        <el-col :span="2">
          <h4>企业服务</h4>
          <a>平台协议</a>
          <a>防骗指南</a>
          <a>使用帮助</a>
        </el-col>
        <el-col :span="10">
          <h4>联系我们</h4>
          <a>XXXXXXXXXXX技术有限公司</a>
          <a>公司地址 中山市火炬开发区建业路22号德创智谷6栋301号</a>
          <a>联系电话 010-84150633</a>
          <a>联系电话 010-84150633</a>
        </el-col>
        <el-col :span="5">
          <h4>共享车位平台</h4>
          <div class="footer-social">
            <a>服务热线和举报投诉 <span>400 065 5799</span></a>
            <a>工作日 <span>8:00 - 22:00</span></a>
            <a>休息日 <span>9:30 - 18:30</span></a>
          </div>
        </el-col>
        <el-col :span="5">
          <h4>微信公众号</h4>
<!--          <img src="https://static.zhipin.com/v2/web/geek/images/we_chat_qr.jpg">-->
          <img src="@/assets/images/wx.png">
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span style="float:left;margin-right: 30px;">Copyright © 2022 共享车位平台</span>
          <a href="https://beian.miit.gov.cn/" target="_blank">京ICP备14013441号-5</a>
        </el-col>
        <el-col :span="2">
          <a href="https://www.zhipin.com/activity/cc/businesslicense.html" rel="nofollow" ka="link-businesslicense" target="_blank">
            <img src="https://static.zhipin.com/v2/web/geek/images/icon-badge-1.png" alt="">
            电子营业执照
          </a>
        </el-col>
        <el-col :span="6">
          <a><img src="https://static.zhipin.com/v2/web/geek/images/icon-beian.png" alt=""> 京公网安备11010502032801</a>
        </el-col>
      </el-row>
    </MyContainer>
  </div>
</template>

<script>
import MyContainer from '@/components/Me/MyContainer'
export default {
  name: 'Footer',
  components: { MyContainer },
  data() {
    return {}
  },
  methods: {},
  created() {
  }
}
</script>

<style scoped>
a {
  display: block;
  font-size: 12px;
  color: #8d92a1;
  height: 24px;
  line-height: 24px;
}
a img {
  width: 16px;
  height: 16px;
  vertical-align: middle;
}
</style>
