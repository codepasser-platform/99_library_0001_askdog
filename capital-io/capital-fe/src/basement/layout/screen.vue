<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "layout.scss";
</style>

<!-- Template -->
<template>
  <div class="layout-container layout-screen">
    <div class="layout-header">
      <Header></Header>
    </div>
    <div class="layout-main clearfix">
      <div class="layout-body body-container" ref="bodyContainer">
        <div class="container">
          <router-view/>
        </div>
      </div>
    </div>
    <div class="layout-footer">
      <Footer></Footer>
    </div>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import Header from '../../app/components/header'
  import Footer from '../../app/components/footer'

  const _ScreenLayout = {
    name: 'ScreenLayout',
    created: function () {
    },
    updated: function () {
    },
    mounted: function () {
    },
    activated: function () {
    },
    destroyed: function () {
    },
    data: function () {
      return {};
    },
    methods: {},
    components: {
      Header,
      Footer
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _ScreenLayout;
</script>