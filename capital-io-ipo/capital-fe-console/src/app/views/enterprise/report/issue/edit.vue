<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">
    <IssueEditComp></IssueEditComp>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  import IssueEditComp from '../../../../components/issue/edit';

  const _IssueEdit = {
    name: 'IssueEdit',
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
      IssueEditComp
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _IssueEdit;
</script>