<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <b-card no-body>
      <b-tabs card>
        <b-tab title="Form" active>
          <GuideSampleForm></GuideSampleForm>
        </b-tab>
        <b-tab title="Grid">
          <GuideSampleGrid></GuideSampleGrid>
        </b-tab>
        <b-tab title="Content">
          Tab Contents
        </b-tab>
      </b-tabs>
    </b-card>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  import GuideSampleForm from './form';
  import GuideSampleGrid from './grid';


  const _GuideSampleTabs = {
    name: 'GuideSampleTabs',
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
      GuideSampleForm,
      GuideSampleGrid
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _GuideSampleTabs;
</script>