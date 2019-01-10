<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "layout.scss";
</style>

<!-- Template -->
<template>
  <div class="layout-container layout-default">
    <div class="layout-header">
      <Header></Header>
    </div>
    <div class="layout-main clearfix">
      <div class="layout-sidebar sidebar-container">
        <Sidebar></Sidebar>
      </div>
      <div class="layout-body body-container" ref="bodyContainer">
        <Breadcrumb></Breadcrumb>
        <router-view/>
      </div>
    </div>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import PerfectScrollbar from 'perfect-scrollbar';

  import Header from '../../app/components/header'
  import Sidebar from '../../app/components/sidebar/console'
  import Breadcrumb from '../../app/components/breadcrumb'

  const _ConsoleLayout = {
    name: 'ConsoleLayout',
    created: function () {
    },
    updated: function () {
    },
    mounted: function () {
      const bodyContainer = this.$refs.bodyContainer;
      this.bodyPS = new PerfectScrollbar(bodyContainer);
    },
    activated: function () {
    },
    destroyed: function () {
      if (this.bodyPS) {
        this.bodyPS.destroy();
        this.bodyPS = null;
      }
    },
    data: function () {
      return {
        bodyPS: undefined
      };
    },
    watch: {
      '$route'(to, from) {
        if (this.bodyPS) {
          this.bodyPS.update();
          this.$refs.bodyContainer.scrollTop = 0;
        }
      }
    },
    methods: {},
    components: {
      Header,
      Sidebar,
      Breadcrumb
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _ConsoleLayout;
</script>
