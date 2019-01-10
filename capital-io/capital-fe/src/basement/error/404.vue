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
      <span>4</span>
    </div>
    <div class="error-message">
      糟糕，您要找的网页不存在。
    </div>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _ERROR_404 = {
    name: 'ERROR_404',
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
  export default _ERROR_404;
</script>