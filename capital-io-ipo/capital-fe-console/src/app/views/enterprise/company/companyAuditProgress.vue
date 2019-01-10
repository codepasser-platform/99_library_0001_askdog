<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  .btn-circle {
    width: 30px;
    height: 30px;
    text-align: center;
    padding: 6px 0;
    font-size: 12px;
    line-height: 1.428571429;
    border-radius: 15px;
  }
  .progress{
    height: auto!important;
  }
  .progress-bar{
    height: 0.5rem!important;
    margin-top: 10px!important;
  }
  .auditNode{
    text-align: center;
  }
</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">
    <div class="title-container container">
      <h5 class="my-0">
        <span class="badge badge-secondary">审核进度</span>
      </h5>
    </div>
    <div class="form-container container">
      <!--<b-progress :max="max" class="mb-3">-->
      <b-progress :max="max">

        <div class="auditNode">
          <button type="button" class="btn btn-success btn-circle"></button>
          <br>
          <span>信息提交</span>
          <br>
          <span>2018-07-06 12:00:00</span>
        </div>
        <b-progress-bar variant="success" :value="values[0]"></b-progress-bar>


        <div class="auditNode">
          <button type="button" class="btn btn-danger btn-circle"></button>
          <br>
          <span>武昌区金融办审核中</span>
          <br>
          <span>2018-07-06 12:00:00</span>
        </div>
        <b-progress-bar variant="dark" :value="values[1]"></b-progress-bar>


        <div class="auditNode">
          <button type="button" class="btn btn-secondary btn-circle"></button>
          <br>
          <span>武汉市金融办</span>
          <br>
          <span></span>
        </div>
        <b-progress-bar variant="dark" :value="values[2]"></b-progress-bar>

        <div class="auditNode">
          <button type="button" class="btn btn-secondary btn-circle"></button>
          <br>
          <span>湖北省金融办</span>
          <br>
          <span></span>
        </div>
        <b-progress-bar variant="dark" :value="values[3]"></b-progress-bar>

        <div class="auditNode">
          <button type="button" class="btn btn-secondary btn-circle"></button>
          <br>
          <span>结束</span>
          <br>
          <span></span>
        </div>
      </b-progress>

      <b-form-group class="text-center">
        <b-button type="submit" variant="outline-primary">返回</b-button>
      </b-form-group>
    </div>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _CompanyAuditProgress = {
    name: 'CompanyAuditProgress',
    created: function () {
    },
    updated: function () {
    },
    mounted: function () {
    },
    activated: function () {
    },
    data: function () {
      return {
        max: 100,
        values: [20, 20, 20, 20]
      };
    },
    methods: {},
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _CompanyAuditProgress;
</script>
