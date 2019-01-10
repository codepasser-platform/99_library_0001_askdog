<style scoped lang="scss">
.dynamic-container{
  background: $white;
}
.newslist li{
  padding: 26px 0;
  border-bottom: 1px solid #cccccc;
}
.newslist li span{
  float: right;
  font-family: Arial;
  color: #666666;
}
.newslist li a{
  display: block;
  width: 86%;
  padding-left: 33px;
}
.nav-bar{
  border-bottom: 1px solid #d6d6d6;
  font-size: 14px;
  padding: 15px 0 15px 26px;
}

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid dynamic-container">
    <h4 class="nav-bar">
      <a href="#">首页</a>&nbsp;>&nbsp;
      <a href="#">企业动态</a>&nbsp;>&nbsp;
      <a href="#">上市工作动态</a>&nbsp;>&nbsp;
    </h4>
    <ul class="newslist">
      <li class="clearfix">
        <span>2018-07-13</span>
        <a href="#">英国驻华大使馆公使衔参赞崔凯楠先生一行到访我办</a>
      </li>
      <li class="clearfix">
        <span>2018-07-13</span>
        <a href="#">英国驻华大使馆公使衔参赞崔凯楠先生一行到访我办</a>
      </li>
      <li class="clearfix">
        <span>2018-07-13</span>
        <a href="#">英国驻华大使馆公使衔参赞崔凯楠先生一行到访我办</a>
      </li>
      <li class="clearfix">
        <span>2018-07-13</span>
        <a href="#">英国驻华大使馆公使衔参赞崔凯楠先生一行到访我办</a>
      </li>
      <li class="clearfix">
        <span>2018-07-13</span>
        <a href="#">英国驻华大使馆公使衔参赞崔凯楠先生一行到访我办</a>
      </li>
      <li class="clearfix">
        <span>2018-07-13</span>
        <a href="#">英国驻华大使馆公使衔参赞崔凯楠先生一行到访我办</a>
      </li>
      <li class="clearfix">
        <span>2018-07-13</span>
        <a href="#">英国驻华大使馆公使衔参赞崔凯楠先生一行到访我办</a>
      </li>
      <li class="clearfix">
        <span>2018-07-13</span>
        <a href="#">英国驻华大使馆公使衔参赞崔凯楠先生一行到访我办</a>
      </li>
    </ul>
    <b-pagination size="sm" :total-rows="100" v-model="currentPage" :per-page="10">
    </b-pagination>
    <br>
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
