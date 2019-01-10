<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "./style.scss";
</style>
<!-- Template -->
<template>
  <div class="view-container container text-center">
    <div class="error-container">
      <span>4</span>
      <span><span class="screen-reader-text">0</span></span>
      <span>1</span>
    </div>
    <div class="error-message">
      抱歉，您没有权限访问该资源。
    </div>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _ERROR_401 = {
    name: 'ERROR_401',
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
  export default _ERROR_401;
</script>