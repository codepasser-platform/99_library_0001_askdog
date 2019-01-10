<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <Toast ref="toastRef"></Toast>

    <Confirm ref="confirmRef"></Confirm>

    <!-- Condition container -->
    <div class="condition-container">
      <b-form class="needs-validation" novalidate>
        <b-row>
          <b-form-group horizontal label-size="sm" :label-cols="4" class="col-md-3" label="数据ID:" label-for="conditionId">
            <b-form-input id="conditionId" name="conditionId" type="text" size="sm" v-model="condition_form.id" placeholder="Enter id"></b-form-input>
          </b-form-group>
          <b-form-group horizontal label-size="sm" :label-cols="4" class="col-md-3" label="数据名称:" label-for="conditionName">
            <b-form-input id="conditionName" name="conditionName" type="text" size="sm" v-model="condition_form.name" placeholder="Enter name"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-6">
            <b-button type="submit" size="sm" variant="outline-primary">Search</b-button>
            <b-button type="reset" size="sm" variant="outline-secondary">Reset</b-button>
            <b-button type="button" size="sm" variant="outline-primary" @click="createHandler()">
              Create
            </b-button>
            <b-button type="button" size="sm" variant="outline-primary" @click="editHandler(1)">
              Edit
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

    <!-- Form -->
    <!--<GuideSampleModalForm ref="editor" @cancelEvent="cancelEventHandler" @confirmEvent="confirmEventHandler"></GuideSampleModalForm>-->

    <!-- Form grid-->
    <!--<GuideSampleModalFormGrid ref="editor" @cancelEvent="cancelEventHandler" @confirmEvent="confirmEventHandler"></GuideSampleModalFormGrid>-->

    <!-- Form inline-->
    <GuideSampleModalFormInline ref="editor" @cancelEvent="cancelEventHandler" @confirmEvent="confirmEventHandler"></GuideSampleModalFormInline>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import GuideSampleModalForm from './modal-form';
  import GuideSampleModalFormGrid from './modal-form-grid';
  import GuideSampleModalFormInline from './modal-form-inline';

  const _GuideSampleModal = {
    name: 'GuideSampleModal',
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
        // condition
        condition_form: {
          id: undefined,
          name: undefined
        }
      };
    },
    methods: {
      createHandler() {
        this.$refs.editor.open();
      },
      editHandler(_id) {
        this.$refs.editor.open(_id);
      },
      confirmEventHandler() {
        console.log('GuideSampleModal -> confirmEventHandler');
      },
      cancelEventHandler() {
        console.log('GuideSampleModal -> cancelEventHandler');
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
      GuideSampleModalForm,
      GuideSampleModalFormGrid,
      GuideSampleModalFormInline
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _GuideSampleModal;
</script>