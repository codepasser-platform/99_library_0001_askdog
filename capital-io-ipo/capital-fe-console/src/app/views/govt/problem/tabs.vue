<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>

<template>
  <div class="view-container container-fluid">

    <b-card no-body>
      <b-tabs card>
        <b-tab title="我的待办任务" active>
          <TodoList></TodoList>
        </b-tab>
        <b-tab title="我处理过的任务">
          <HandledList></HandledList>
        </b-tab>
        <b-tab title="流转的任务">
          <TransferList></TransferList>
        </b-tab>
      </b-tabs>
    </b-card>

  </div>
</template>

<script>
  import {mapGetters} from 'vuex';

  import TodoList from './todoList';
  import HandledList from './handledList';
  import TransferList from './transferredList';

  const _ProblemTabs = {
    name: 'ProblemTabs',
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
    components: {
      TodoList,
      HandledList,
      TransferList
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _ProblemTabs;
</script>
