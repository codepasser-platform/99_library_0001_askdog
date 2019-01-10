<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "style.scss";
</style>

<!-- Template -->
<template>
  <div class="component-container sidebar-component">

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>企业信息管理</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>企业信息列表</span></div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>企业台账管理</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>在会企业台账</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>在辅企业台账</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>境外上市企业台账</span></div>
          </router-link>
        </div>
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>金种子企业台账</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>银种子企业台账</span></div>
          </router-link>
        </div>
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>市、洲推进企业上市月度简报</span>
            </div>
          </router-link>
        </div>
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>重大事项</span></div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>绿色通道管理</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>待办任务</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>已处理任务</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>已流转的任务</span></div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>企业调研管理</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>企业调研涵</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>调研记录</span></div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i> <span>企业培训管理</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>企业培训活动</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>企业培训报名</span></div>
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
