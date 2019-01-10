<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "./style.scss";
</style>
<!-- Template -->
<template>
  <div class="view-container container text-center">
    <div class="error-container">
      <span>5</span>
      <span><span class="screen-reader-text">0</span></span>
      <span>0</span>
    </div>
    <div class="error-message">
      抱歉，服务异常！请联系管理员解决。
    </div>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _ERROR_500 = {
    name: 'ERROR_500',
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
      };
    },
    methods: {},
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _ERROR_500;
</script>