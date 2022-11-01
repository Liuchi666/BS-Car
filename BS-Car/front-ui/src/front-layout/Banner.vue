<template>
<div>
  <MyContainer>
    <img src="https://static.zhipin.com/v2/web/geek/images/fast-reg.png" alt="">
    <img src="https://static.zhipin.com/v2/web/geek/images/fast-reg-text.png" alt="">
  </MyContainer>
</div>
</template>

<script>
import MyContainer from '@/components/Me/MyContainer'
export default {
  name: 'Banner',
  components: { MyContainer }
}
</script>

<style scoped lang="scss">
div {
  height: 120px;
  background: #5dd5c8;
  img:first-child{
    height: 100%;
    margin-left: 60px;
  }
  img:last-child{
    float: right;
    margin-right: 60px;
    margin-top: 30px;
  }
}
</style>
