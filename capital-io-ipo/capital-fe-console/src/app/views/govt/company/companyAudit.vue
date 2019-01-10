<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <b-modal centered hide-footer no-close-on-backdrop no-close-on-esc size="lg" :title="modalTitle" ref="modalRef">
      <div class="form-container container">
        <b-form class="needs-validation" novalidate @reset="onReset" v-if="show" ref="form">
          <b-row>
            <b-col md="4">
              县（市、区）金融办（上市办）
            </b-col>
            <b-col md="4">
              市州金融办（上市办）
            </b-col>
            省政府金融办（上市办）
            <b-col md="4">
            </b-col>
          </b-row>
          <b-row>
            <b-form-group class="col-md-12 text-center">

              <b-form-group label="审核意见:" label-for="textareaVue" valid-feedback="valid-feedback">
                <b-form-textarea id="textareaVue" v-model="company.textarea" placeholder="" rows="2" maxlength="140">
                </b-form-textarea>
              </b-form-group>
              <!--<b-button type="cancel" variant="outline-primary" size="sm">驳回</b-button>-->
              <!--<b-button type="submit" variant="primary" size="sm">通过</b-button>-->

              <b-button variant="outline-primary" size="sm">驳回</b-button>
              <b-button variant="primary" size="sm">通过</b-button>
            </b-form-group>
          </b-row>
        </b-form>
      </div>
    </b-modal>


  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _CompanyAudit = {
    name: 'CompanyAudit',
    created: function () {
    },
    updated: function () {
    },
    mounted: function () {
    },
    activated: function () {
    },
    data: function () {
      const _self = this;
      return {
        form: {
          id: '',
          email: undefined,
          password: undefined,
          selector: undefined,
          file: undefined,
          textarea: undefined,
          custom: undefined,
          radio: 'ADMIN',
          checker: false
        },

        options: [
          {text: 'Select One ...', value: undefined},
          {text: 'Carrots', value: 'CARROTS'},
          {text: 'Beans', value: 'BEANS'},
          {text: 'Tomatoes', value: 'TOMATOES'},
          {text: 'Corn', value: 'CORN'}
        ],

        types: [
          {text: 'admin', value: 'ADMIN'},
          {text: 'normal', value: 'NORMAL'}
        ],

        modalTitle: '审核',
        show: true
      };
    },
    methods: {
      open(_id) {
        console.log('GuideSampleModalFormInline > open', _id);
        this.form.id = _id;
        this.$refs.modalRef.show();
        if (_id) {
          this.requireRecord(_id);
        }
      },
      close() {
        console.log('GuideSampleModalFormInline > close');
        this.clearForm();
        this.$refs.modalRef.hide();
        this.$emit('confirmEvent');
      },
      cancel() {
        console.log('GuideSampleModalFormInline > cancel');
        this.clearForm();
        this.$refs.modalRef.hide();
        this.$emit('cancelEvent');
      },
      onSubmit() {
        console.log('GuideSampleModalFormInline > onSubmit');
        console.log(this.$refs.form);
        console.log(this.form);
        this.close();
      },
      onReset(evt) {
        console.log('GuideSampleModalFormInline > onReset');
        evt.preventDefault();
        this.clearForm();
      },
      clearForm() {
        console.log('GuideSampleModalFormInline > clearForm');
        /* Reset our form values */
        this.form.email = undefined;
        this.form.password = undefined;
        this.form.selector = undefined;
        this.form.file = undefined;
        this.form.textarea = undefined;
        this.form.custom = undefined;
        this.form.checker = false;
        /* Trick to reset/clear native browser form validation state */
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        });
      },
      requireRecord(_id) {
        console.log('GuideSampleModalFormInline > requireRecord', _id);
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _CompanyAudit;
</script>
