<style scoped lang="scss">
.content-container{
  border: 1px solid #eaeaea;
  padding: 20px;
  background: $white;
}
.page h2 {
  font-size: 20px;
  line-height: 30px;
  text-align: center;
  color: #414141;
  font-weight: bold;
}
.page .note {
  font-size: 12px;
  min-height: 36px;
  background: #f9f9f9;
  line-height: 36px;
  text-align: center;
  color: #666;
  padding: 0 1%;
  margin: 24px 0;
}
.page .art{
  color: #414141;
  padding: 20px;
}
.page .art p {
  font-size: 18px;
  line-height: 30px;
  color: #414141;
}
.indent{
  text-indent:2em;
}
.nav-bar{
  border-bottom: 1px solid #d6d6d6;
  font-size: 14px;
  padding: 15px 0 15px 26px;
}
</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid content-container page">
    <h4 class="nav-bar">
      <a href="#">首页</a>&nbsp;>&nbsp;
      <a href="#">最新政策</a>&nbsp;>&nbsp;
      <a href="#">政策解读</a>&nbsp;>&nbsp;
      <a href="#">内容</a>
    </h4>
    <h2>
      关于修改《期货交易所、期货公司缴纳期货投资者保障基金有关事项的规定》的决定
    </h2>
    <p class="note">
      <span>
       来源：中国证监会 www.csrc.gov.cn
      </span>
      <span>
        作者：
      </span>
      <span>
        2018-07-04 16:39:38
      </span>
    </p>
    <div class="art">
      <p class="text-center">中国证券监督管理委员会</p>
      <p class="text-center">〔2016〕27号</p>
      <p class="indent">
          现公布《关于修改〈期货交易所、期货公司缴纳期货投资者保障基金有关事项的规定〉的决定》，自公布之日起30日后施行。现公布《关于修改〈期货交易所、期货公司缴纳期货投资者保障基金有关事项的规定〉的决定》，自公布之日起30日后施行。现公布《关于修改〈期货交易所、期货公司缴纳期货投资者保障基金有关事项的规定〉的决定》，自公布之日起30日后施行。
      </p>
      <p class="text-right">中国证监会</p>
      <p class="text-right">2016年11月8日</p>
    </div>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Home = {
    name: 'Home',
    created: function () {
    },
    updated: function () {
    },
    mounted: function () {
    },
    activated: function () {
    },
    data: function () {
      return {};
    },
    methods: {},
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _Home;
</script>
