<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">
    <IssueListComp></IssueListComp>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  import IssueListComp from '../../../../components/issue/list'

  const _IssueList = {
    name: 'IssueList',
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
      IssueListComp
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _IssueList;
</script>