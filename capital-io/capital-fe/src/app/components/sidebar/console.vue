<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "style.scss";
</style>

<!-- Template -->
<template>
  <div class="component-container sidebar-component">

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>企业信息</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>我的企业信息</span></div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>企业台账</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>企业台账</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>重大事项</span></div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>绿色通道</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>问题申报</span></div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>企业调研</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>企业调研涵</span></div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>企业培训服务</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>企业培训活动</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>我的培训报名</span></div>
          </router-link>
        </div>
      </div>
    </div>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Sidebar = {
    name: 'Sidebar',
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
  export default _Sidebar;
</script>
