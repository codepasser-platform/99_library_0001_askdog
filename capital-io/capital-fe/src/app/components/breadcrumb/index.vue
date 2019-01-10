<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "style.scss";
</style>

<!-- Template -->
<template>
  <div class="component-container breadcrumb-component">
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb py-0">
        <li class="breadcrumb-item" v-for="(item,index) in breadcrumb.items">

          <div class="d-inline-block" v-if="index < (breadcrumb.items.length-1) && !item.meta.hidden">
            <div class="d-inline-block" v-if="item.path">
              <router-link :to="item.path">{{item.meta.title}}</router-link>
            </div>
            <div class="d-inline-block" v-else>
              <router-link to="/">{{item.meta.title}}</router-link>
            </div>
          </div>
          <div class="d-inline-block" v-if="index < (breadcrumb.items.length-1) && item.meta.hidden">
            {{item.meta.title}}
          </div>

          <div class="d-inline-block" v-if="index === (breadcrumb.items.length-1)">
            {{item.meta.title}}
          </div>

        </li>
      </ol>
    </nav>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Breadcrumb = {
    name: 'Breadcrumb',
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
        items: [{
          text: 'Admin',
          href: '#'
        }, {
          text: 'Manage',
          href: '#'
        }, {
          text: 'Library',
          active: true
        }]
      }
    },
    methods: {},
    components: {},
    computed: {
      ...mapGetters([
        'breadcrumb'
      ])
    }
  };
  export default _Breadcrumb;
</script>
