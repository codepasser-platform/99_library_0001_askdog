<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "style.scss";
</style>

<!-- Template -->
<template>
  <div class="component-container sidebar-component">

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i>
        <span>Sidebar Item 1</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 1</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 2</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 3</span>
          </div>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i>
        <span>Sidebar Item 2</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 1</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 2</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 3</span>
          </div>
        </div>
        <div class="sub-menu-group-col">
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 1</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 2</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 3</span>
          </div>
        </div>
      </div>
    </div>

    <div class="menu-group">
      <div class="menu-group-item">
        <i class="fa fa-home"></i>
        <span>Sidebar Item 3</span>
      </div>
      <div class="menu-selector"></div>
      <div class="sub-menu-group">
        <div class="sub-menu-group-col">
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 1</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 2</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 3</span>
          </div>
        </div>
        <div class="sub-menu-group-col">
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 1</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 2</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 3</span>
          </div>
        </div>
        <div class="sub-menu-group-col">
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 1</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 2</span>
          </div>
          <div class="sub-menu-group-item"><i class="fa fa-map"></i> <span>Sidebar Item 3</span>
          </div>
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
