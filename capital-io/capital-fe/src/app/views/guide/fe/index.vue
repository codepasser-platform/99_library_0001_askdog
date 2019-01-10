<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "./style.scss";
</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">
    <mavon-editor :value="content" :subfield="true" fontSize="14px" :editable="false"></mavon-editor>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {mavonEditor} from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import {_guideContent} from '../../../service/api/guide-api'

  const _GuideFE = {
    name: 'GuideFE',
    created: function () {
    },
    updated: function () {
    },
    mounted: function () {
      this.initialize();
    },
    activated: function () {
    },
    data: function () {
      return {
        content: undefined,
        pageTitle: undefined
      };
    },
    watch: {
      '$route'(to, from) {
        this.initialize();
      }
    },
    methods: {
      initialize() {
        const _self = this;
        this.pageTitle = this.$route.params.chapter + '-' + this.$route.params.view;
        _guideContent(this.$route.params.chapter, this.$route.params.view).then(
            function (response) {
              console.log('_GuideFE > initialize > guide-api > _guideContent ', response);
              _self.content = response.data;
            },
            function (error) {
              console.error('_GuideFE > initialize > guide-api > _guideContent', error);
            }
        );
      }
    },
    components: {
      mavonEditor
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _GuideFE;
</script>