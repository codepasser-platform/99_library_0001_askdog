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

          <b-nav-item-dropdown right>
            <template slot="button-content">
              <i class="fa fa-book" aria-hidden="true"></i><em>GUIDE</em>
            </template>
            <router-link to="/dashboard">
              <b-dropdown-item href="javascript:void(0);">
                <i class="fa fa-tachometer" aria-hidden="true"></i><span>DASHBOARD</span>
              </b-dropdown-item>
            </router-link>
            <router-link to="/hello">
              <b-dropdown-item href="javascript:void(0);">
                <i class="fa fa-thumbs-up" aria-hidden="true"></i><span>HELLO</span>
              </b-dropdown-item>
            </router-link>
            <div class="dropdown-divider"></div>
            <router-link to="/portal">
              <b-dropdown-item href="javascript:void(0);">
                <i class="fa fa-thumbs-up" aria-hidden="true"></i><span>Portal Layout</span>
              </b-dropdown-item>
            </router-link>
            <router-link to="/console">
              <b-dropdown-item href="javascript:void(0);">
                <i class="fa fa-thumbs-up" aria-hidden="true"></i><span>Console Layout</span>
              </b-dropdown-item>
            </router-link>
            <router-link to="/govt">
              <b-dropdown-item href="javascript:void(0);">
                <i class="fa fa-thumbs-up" aria-hidden="true"></i><span>Govt Layout</span>
              </b-dropdown-item>
            </router-link>
            <div class="dropdown-divider"></div>
            <router-link to="/guide">
              <b-dropdown-item href="javascript:void(0);">
                <i class="fa fa-book" aria-hidden="true"></i><span>GUIDE</span>
              </b-dropdown-item>
            </router-link>
            <router-link to="/error/401">
              <b-dropdown-item href="javascript:void(0);">
                <i class="fa fa-exclamation-triangle" aria-hidden="true"></i><span>401</span>
              </b-dropdown-item>
            </router-link>
            <router-link to="/error/404">
              <b-dropdown-item href="javascript:void(0);">
                <i class="fa fa-exclamation-triangle" aria-hidden="true"></i><span>404</span>
              </b-dropdown-item>
            </router-link>
            <router-link to="/error/500">
              <b-dropdown-item href="javascript:void(0);">
                <i class="fa fa-exclamation-triangle" aria-hidden="true"></i><span>500</span>
              </b-dropdown-item>
            </router-link>
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
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _Header;
</script>
