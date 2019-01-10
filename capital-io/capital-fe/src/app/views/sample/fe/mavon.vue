<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <div class="title-container container">
      <h5 class="my-0">
        <span class="badge badge-secondary">Sample 1</span>
        <span>Mavon</span>
      </h5>
    </div>

    <div class="form-container container">
      <mavon-editor :value="content" :subfield="true" fontSize="14px" :editable="true"></mavon-editor>
    </div>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {mavonEditor} from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'

  const _GuideSampleMavon = {
    name: 'GuideSampleMavon',
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
        content: '<p>Mavon Editor</p>'
      };
    },
    methods: {},
    components: {
      mavonEditor
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _GuideSampleMavon;
</script>