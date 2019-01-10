<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "style.scss";
</style>

<!-- Template -->
<template>
  <div class="component-container sidebar-component">

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-book"></i> <span>前端-开发环境</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/fe/env/node">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Node</span></div>
          </router-link>
          <router-link to="/guide/fe/env/app">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>App</span></div>
          </router-link>
        </div>
        <div class="sub-menu-group-col">
          <router-link to="/guide/fe/env/depends">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Depends</span>
            </div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-book"></i> <span>前端-框架整合</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/fe/frame/init">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Initialize</span></div>
          </router-link>
          <router-link to="/guide/fe/frame/lib">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Library</span></div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-book"></i> <span>前端-开发文档</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/fe/doc/map">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Map</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/style">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Style</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/layout">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Layout</span></div>
          </router-link>
        </div>
        <div class="sub-menu-group-col">
          <router-link to="/guide/fe/doc/router">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Router</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/views">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Views</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/components">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Components</span></div>
          </router-link>
        </div>
        <div class="sub-menu-group-col">
          <router-link to="/guide/fe/doc/service">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Service</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/store">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Store</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/directive">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Directive</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/filter">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Filter</span></div>
          </router-link>
        </div>

        <div class="sub-menu-group-col">
          <router-link to="/guide/fe/doc/form">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Form</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/grid">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Grid</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/tabs">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Tabs</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/modal">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Modal</span></div>
          </router-link>
        </div>
        <div class="sub-menu-group-col">
          <router-link to="/guide/fe/doc/datepicker">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>DatePicker</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/tinymce">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Tinymce</span></div>
          </router-link>
          <router-link to="/guide/fe/doc/mavon">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Mavon</span></div>
          </router-link>
        </div>

      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-book"></i> <span>后端-开发环境</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Java TODO</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Maven TODO</span></div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>IDE TODO</span></div>
          </router-link>
        </div>
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Depends TODO</span>
            </div>
          </router-link>
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Docker TODO</span>
            </div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-book"></i> <span>后端-框架整合</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>TODO</span>
            </div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-book"></i> <span>后端-开发文档</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/guide/todo">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>TODO</span>
            </div>
          </router-link>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-book"></i> <span>示例</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <router-link to="/sample/fe/form">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Form</span></div>
          </router-link>
          <router-link to="/sample/fe/form/grid">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Form-grid</span>
            </div>
          </router-link>
          <router-link to="/sample/fe/form/inline">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Form-inline</span>
            </div>
          </router-link>
        </div>
        <div class="sub-menu-group-col">
          <router-link to="/sample/fe/grid">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Grid</span></div>
          </router-link>
          <router-link to="/sample/fe/tabs">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Tabs</span></div>
          </router-link>
          <router-link to="/sample/fe/modal">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Modal</span></div>
          </router-link>
        </div>

        <div class="sub-menu-group-col">
          <router-link to="/sample/fe/date-picker">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>DatePicker</span></div>
          </router-link>
          <router-link to="/sample/fe/tinymce">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Tinymce</span></div>
          </router-link>
          <router-link to="/sample/fe/mavon">
            <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Mavon</span></div>
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
