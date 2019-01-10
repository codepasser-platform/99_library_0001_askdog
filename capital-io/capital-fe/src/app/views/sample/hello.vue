<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "./hello.scss";
</style>

<!-- Template -->
<template>
  <div class="view-container container text-center">
    Hello Stock Capital - IO PLATFORM (FE)
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Hello = {
    name: 'Hello',
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
  export default _Hello;
</script>