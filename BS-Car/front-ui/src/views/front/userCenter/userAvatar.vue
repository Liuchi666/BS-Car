<template>
  <div>
    <div class="user-info-head" @click="editCropper()">
      <img v-bind:src="options.img" title="点击上传头像" class="img-circle img-lg" />
    </div>
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body @opened="modalOpened"  @close="closeDialog">
      <el-row>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            @realTime="realTime"
            v-if="visible"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <div class="avatar-upload-preview">
            <img :src="previews.url" :style="previews.img" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :md="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{span: 2, offset: 6}" :md="2">
          <el-button type="primary" size="small" @click="uploadImg()">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import store from "@/store";
import { VueCropper } from "vue-cropper";
import { uploadAvatar } from "@/api/car/front/userCenter";
import { getUserInfo } from "@/api/car/front/login";
import Cookies from 'js-cookie'

export default {
  components: { VueCropper },
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 是否显示cropper裁剪
      visible: false,
      // 弹出层标题
      title: "修改头像",
      userInfo: {},  //封装已登录的用户信息(不完整的)
      options: {
        // img: store.getters.avatar, //裁剪图片的地址
        img: '',
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 200, // 默认生成截图框宽度
        autoCropHeight: 200, // 默认生成截图框高度
        fixedBox: true // 固定截图框大小 不允许改变
      },
      userInfo2: {},
      previews: {}
    };
  },
  created() {
    //从cookie中查看是否有用户信息
    var userStr = Cookies.get("front_userInfo");
    if (userStr) {   //判断cookie是否存在
      //因为cookie中存放的都是字符串,所以要先将字符串转换为js对象
      // debugger
      this.userInfo = JSON.parse(userStr)
      // console.log("--------" + this.userInfo.avatar)
      if(this.userInfo.avatar != this.options.img){
        this.options.img = this.userInfo.avatar.substring(19,200)
      }
    }
  },
  mounted() {
    console.log("PPPPPPPPPPPPPPP" + this.options.img)
  },
  methods: {
    // 上传预处理
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
        };
      }
    },
    // 上传图片
    uploadImg() {
      this.$refs.cropper.getCropBlob(data => {
        //封装传输数据
        let formData = new FormData();
        formData.append("avatarfile", data);
        formData.append("userId", this.userInfo.userId);
        //调用方法上传头像的方法
        uploadAvatar(formData).then(response => {
          //关闭弹窗
          this.open = false;
          //拼接全路径用于前端显示
          this.options.img = process.env.VUE_APP_BASE_API + response.imgUrl;
          store.commit('SET_AVATAR', this.options.img);
          this.$modal.msgSuccess("修改成功");
          //关闭图片裁剪
          this.visible = false;

          //将新修改的用户头像数据更新到cookie中
          // debugger
          this.getLoginUserInfo()
          //刷新页面

        });
      });
    },
    //获取当前登录用户的信息
   async getLoginUserInfo(){
    await getUserInfo().then(res => {
        //将获取到的用户信息赋给this.loginInfo
        // debugger
        this.userInfo = res.userInfo
        //将用户信息存进cookie(存在cookie中就能在其他页面中取出了),用于首页的用户数据显示(注意cookie中只能存字符串，所以先将后端响应的对象转为字符串)
        Cookies.set('front_userInfo',JSON.stringify(this.userInfo),{domain: 'localhost'})
      })
      window.location.reload()
    },
    // 编辑头像
    editCropper() {
      this.open = true;
    },
    // 打开弹出层结束时的回调
    modalOpened() {
      this.visible = true;
    },
    // 覆盖默认的上传行为
    requestUpload() {
    },
    // 向左旋转
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    // 向右旋转
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    // 图片缩放
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    // 实时预览
    realTime(data) {
      this.previews = data;
    },
    // 关闭窗口
    closeDialog() {
      this.options.img = store.getters.avatar
      this.visible = false;
    }
  }
};
</script>
<style scoped lang="scss">
.user-info-head {
  position: relative;
  display: inline-block;
  height: 120px;
}

.user-info-head:hover:after {
  content: '+';
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 24px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 110px;
  border-radius: 50%;
}
</style>
