<style scoped lang="scss">
.list-box{
  height: 346px;
  padding: 0px 18px 0px 18px;
  margin-top: 20px;
}
  .more{
    color: #cf0000;
    padding-right: 3px;
    float: right;
  }
  .policy-container{
    background: $light;
  }
</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid policy-container">
    <b-row>
      <b-form-group
                    horizontal
                    :label-cols="4"
                    class="col-sm-5"
                    label-class="text-sm-right"
                    breakpoint="sm"
                    label="关键词"
                    label-for="inputHorizontal">
        <b-form-input id="inputHorizontal"></b-form-input>
      </b-form-group>
    </b-row>

    <b-row>
      <b-col>
        <div class="list-box">
          <h2 class="text-center">证监会文件</h2>
          <ul>
            <li>关于修改《期货交易所、期货公司缴纳期货投资者保障基金有关事项的规定》的决定</li>
            <li>关于明确期货投资者保障基金缴纳比例有关事项的规定</li>
            <li>证券基金经营机构参与内地与香港股票市场交易互联互通指引</li>
            <li>资本市场交易结算系统核心技术指标</li>
            <li>第二批证券期货投资者教育基地申报工作指引</li>
          </ul>
          <a target="_blank" class="more" href="#">更多>></a>
        </div>
      </b-col>
      <b-col>
        <div class="list-box">
          <h2 class="text-center">省政府文件</h2>
          <ul>
            <li>资本市场交易结算系统核心技术指标</li>
            <li>第二批证券期货投资者教育基地申报工作指引</li>
            <li>第二批证券期货投资者教育基地申报工作指引</li>
          </ul>
          <a target="_blank" class="more" href="#">更多>></a>
        </div>
      </b-col>
      <b-col>
        <div class="list-box">
          <h2 class="text-center">地方政府文件</h2>
          <ul>
            <li>资本市场交易结算系统核心技术指标</li>
            <li>第二批证券期货投资者教育基地申报工作指引</li>
            <li>第二批证券期货投资者教育基地申报工作指引</li>
          </ul>
          <a target="_blank" class="more" href="#">更多>></a>
        </div>
      </b-col>
    </b-row>
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
