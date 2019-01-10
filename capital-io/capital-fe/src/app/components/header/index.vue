<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "style.scss";
</style>

<!-- Template -->
<template>
  <div class="component-container header-component">
    <b-navbar toggleable="md" type="light" variant="light">
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
      <b-navbar-brand href="javascript:void(0);" @click="headerBarIndexHandler()">
        <i class="fa fa-github" aria-hidden="true" style="font-size:1.5rem"></i>
      </b-navbar-brand>
      <b-collapse is-nav id="nav_collapse">
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">

          <b-navbar-nav>
            <router-link to="/guide">
              <b-nav-item href="javascript:void(0);">
                GUIDE
              </b-nav-item>
            </router-link>
            <b-nav-item href="javascript:void(0);" @click="headerBarSignInHandler()">
              登陆
            </b-nav-item>
            <b-nav-item href="javascript:void(0);" @click="headerBarSignUpHandler()">
              企业注册
            </b-nav-item>
          </b-navbar-nav>

          <b-nav-item-dropdown right>
            <template slot="button-content">
              <i class="fa fa-address-card" aria-hidden="true"></i><em>用户名</em>
            </template>
            <b-dropdown-item href="javascript:void(0);" @click="profileBarMainHandler()">
              <i class="fa fa-user" aria-hidden="true"></i><span>个人中心</span>
            </b-dropdown-item>
            <b-dropdown-item href="javascript:void(0);" @click="profileBarSettingsHandler()">
              <i class="fa fa-cog" aria-hidden="true"></i><span>系统设置</span>
            </b-dropdown-item>
            <b-dropdown-item href="javascript:void(0);" @click="profileBarSignOutHandler()">
              <i class="fa fa-sign-out" aria-hidden="true"></i><span>退出</span>
            </b-dropdown-item>
          </b-nav-item-dropdown>

        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Header = {
    name: 'Header',
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
    methods: {
      headerBarIndexHandler() {
        console.log('Header methods > headerBarIndexHandler');
        this.$router.push({path: '/'});
      },
      headerBarSignInHandler() {
        console.log('Header methods > headerBarSignInHandler');
      },
      headerBarSignUpHandler() {
        console.log('Header methods > headerBarSignUpHandler');
      },
      profileBarMainHandler() {
        console.log('Header methods > profileBarMainHandler');
      },
      profileBarSettingsHandler() {
        console.log('Header methods > profileBarSettingsHandler');
      },
      profileBarSignOutHandler() {
        console.log('Header methods > profileBarSignOutHandler');
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _Header;
</script>
