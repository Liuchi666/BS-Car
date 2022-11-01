<template>
  <div>
    <!--抬头-->
    <div class="top box">
      <div class="logo fl" aos="fade-right"><img varid="logo" src="@/assets/logo/logo.png" alt="广州川安交通设施工程有限公司" /></div>
      <div class="name fl" aos="fade-left"><p>共享车位平台</p>
        <span varid="txt1">主营：道路交通划线，交通标志牌、停车场设施及各种交通安全设施等</span></div>

      <div class="tel fr" aos="fade-left">
        <i class="dicon di-dianhua5"></i>
        <span>全国咨询热线</span>
        <p>13928856658</p>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "header"
}
</script>

<style type="text/css" media="all">
.mod1032 a:hover{ color:#178f47;}
/*抬头*/

.logo{ padding:25px 10px 40px 0; }
.logo img{ height: 100px;}
.name{ font-size:18px; color:#333; padding-top: 48px; line-height:34px;}
.name p{ font-size:34px; color:#178f47; font-weight:bold;}
.tel{margin-top:50px; padding-left: 50px;}
.tel p{font-size: 26px; line-height: 26px; font-weight: bold; color: #178f47;}
.tel span{ font-size: 14px;}
.tel .dicon{ float: left; width: 50px; line-height: 46px; padding-top:5px; font-size: 46px; margin-left: -50px;}

</style>
