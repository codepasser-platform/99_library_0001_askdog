<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container text-center">
    <img src="@/assets/logo.png">
    <b-alert variant="success" show>{{ msg }}</b-alert>
    <h2>Essential Links</h2>
    <ul class="list-group">
      <li class="list-group-item">
        <a href="https://vuejs.org" target="_blank">Core Docs</a>
      </li>
      <li class="list-group-item">
        <a href="https://forum.vuejs.org" target="_blank">Forum</a>
      </li>
      <li class="list-group-item">
        <a href="https://chat.vuejs.org" target="_blank">Community Chat </a>
      </li>
      <li class="list-group-item">
        <a href="https://twitter.com/vuejs" target="_blank"> Twitter </a>
      </li>
      <li class="list-group-item">
        <a href="http://vuejs-templates.github.io/webpack/" target="_blank">
          Docs for This Template
        </a>
      </li>
    </ul>
    <h2>Ecosystem</h2>
    <ul class="list-group">
      <li class="list-group-item">
        <a href="http://router.vuejs.org/" target="_blank">vue-router</a>
      </li>
      <li class="list-group-item">
        <a href="http://vuex.vuejs.org/" target="_blank">vuex</a>
      </li>
      <li class="list-group-item">
        <a href="http://vue-loader.vuejs.org/" target="_blank">vue-loader</a>
      </li>
      <li class="list-group-item">
        <a href="https://github.com/vuejs/awesome-vue" target="_blank">awesome-vue</a>
      </li>
    </ul>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Dashboard = {
    name: 'Dashboard',
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
        msg: 'Welcome to Your Vue.js App'
      };
    },
    methods: {},
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _Dashboard;
</script>