<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">
    <Toast ref="toastRef"></Toast>

    <Confirm ref="confirmRef"></Confirm>

    <div class="condition-container">
      <b-form class="needs-validation" novalidate>
        <b-row>
          <b-form-group horizontal class="col-md-6">
            <b-button type="button" size="sm" variant="outline-default" @click="sampleClickHandler()">
              Api Mock
            </b-button>
            <b-button type="button" size="sm" variant="outline-default" @click="showToast()">
              Show Toast
            </b-button>
            <b-button type="button" size="sm" variant="outline-default" @click="showConfirm()">
              Show Confirm
            </b-button>
            <b-button type="button" size="sm" variant="outline-default" @click="showConfirmOnly()">
              Show Confirm Only
            </b-button>
          </b-form-group>
        </b-row>
      </b-form>
    </div>

    <mavon-editor :value="content" :subfield="true"></mavon-editor>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {mavonEditor} from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import {_indexContent, _sample} from '../../service/api/sample-api';

  const _Index = {
    name: 'Index',
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
        content: undefined
      };
    },
    methods: {
      initialize() {
        const _self = this;
        _indexContent('index').then(
            function (response) {
              console.log('Index > initialize > sample-api > _indexContent ', response);
              _self.content = response.data;
            },
            function (error) {
              console.error('Index > initialize > sample-api > _indexContent', error);
            }
        );
      },
      sampleClickHandler() {
        _sample('sample', '123123').then(
            function (response) {
              console.log('Index > initialize > sample-api > _sample ', response);
            },
            function (error) {
              console.error('Index > initialize > sample-api > _sample', error);
            }
        );
      },
      showToast() {
        // show(_content, _variant)
        // content : message
        // variant : primary secondary success danger warning info light dark | default is info
        this.$refs.toastRef.show('操作成功！', 'success');
      },
      showConfirm() {
        this.$refs.confirmRef.show('确认删除？',
            function (_confirm) {
              console.log('confirmRef Confirm > cancelHandler');
            },
            function (_cancel) {
              console.log('confirmRef Confirm > cancelHandler');
            });
      },
      showConfirmOnly() {
        this.$refs.confirmRef.show('该操作成功提醒',
            function (_confirm) {
              console.log('confirmRef Confirm > cancelHandler');
            },
            false);
      }
    },
    components: {
      mavonEditor
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _Index;
</script>