<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="component-container issue-container">

    <div class="form-container container">
      <b-form class="needs-validation" v-validator="validator_vue" novalidate @reset="onResetVue" v-if="showVue" ref="form_vue">

        <b-row>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="企业名称:" label-for="companyName" valid-feedback="valid-feedback">
            <b-form-input id="companyName" name="companyName" type="text" v-model="report_form.companyName" placeholder="企业名称"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="注册地:" label-for="passwordVue" valid-feedback="valid-feedback">
            <b-form-input id="registryArea" name="registryArea" type="text" v-model="report_form.registryArea" placeholder="注册地"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="责任领导:" label-for="dutyPerson" valid-feedback="valid-feedback">
            <b-form-input id="dutyPerson" name="dutyPerson" type="text" v-model="report_form.dutyPerson" placeholder="责任领导"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="电话:" label-for="phone" valid-feedback="valid-feedback">
            <b-form-input id="phone" name="phone" type="text" v-model="report_form.phone" placeholder="电话"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="事项分类:" label-for="issueType" valid-feedback="valid-feedback">
            <b-form-input id="issueType" name="issueType" type="text" v-model="report_form.issueType" placeholder=""></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-12" :label-cols="1" label="事项内容:" label-for="content" valid-feedback="valid-feedback">
            <b-form-textarea id="content" v-model="report_form.content" placeholder="事项内容" rows="6" maxlength="512">
            </b-form-textarea>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-12" :label-cols="1" label="处置措施:" label-for="measures" valid-feedback="valid-feedback">
            <b-form-textarea id="measures" v-model="report_form.measures" placeholder="处置措施" rows="6" maxlength="512">
            </b-form-textarea>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group class="col-md-12 text-center">
            <b-button type="button" variant="outline-secondary" size="sm">取消</b-button>
            <b-button type="submit" variant="outline-primary" size="sm">提交</b-button>
          </b-form-group>
        </b-row>

      </b-form>
    </div>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {
    REGEX_GENERAL_NAME,
    REGEX_PHONE
  } from "capital-fe/src/basement/utils/regex-pattern";

  const _IssueEditComp = {
    name: 'IssueEditComp',
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

        validator_vue: {
          rules: {
            companyName: {
              required: true,
              regex: REGEX_GENERAL_NAME
            },
            registryArea: {
              required: true,
              regex: REGEX_GENERAL_NAME
            },
            dutyPerson: {
              required: true,
              regex: REGEX_GENERAL_NAME
            },
            phone: {
              required: true,
              regex: REGEX_PHONE
            },
            issueType: {
              required: true
            },
            content: {
              required: true
            },
            measures: {
              required: true,
            }
          },
          message: {
            companyName: {
              required: '公司名称是必填项',
              regex: '最大长度30个字符'
            },
            registryArea: {
              required: '注册地是必填项',
              regex: '最大长度30个字符'
            },
            dutyPerson: {
              required: '责任人是必填项'
            },
            phone: {
              required: '电话是必填项',
              regex: '电话格式错误'
            },
            issueType: {
              required: '事项分类是必填项'
            },
            content: {
              required: '事项内容是必填项'
            },
            measures: {
              required: '处置措施是必填项'
            }
          },
          submitHandler: function () {
            console.log('form vue validator submit handler 2');
            _self.onSubmitVue();
          }
        },

        form: {
          email: undefined,
          password: undefined,
          selector: undefined,
          file: undefined,
          textarea: undefined,
          custom: undefined,
          radio: 'ADMIN',
          checker: false
        },

        form_vue: {
          email: undefined,
          password: undefined,
          selector: undefined,
          file: undefined,
          textarea: undefined,
          custom: undefined,
          radio: 'NORMAL',
          checker: false
        },

        report_form: {
          companyName: undefined,
          registryArea: undefined,
          dutyPerson: undefined,
          phone: undefined,
          issueType: undefined,
          content: undefined,
          measures: undefined
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

        show: true,
        showVue: true
      };
    },
    methods: {
      onSubmit() {
        let _file = this.$refs.form_file.files[0];
        this.form.file = _file;
        console.log(this.$refs.form);
        console.log(this.form);
      },
      onReset(evt) {
        evt.preventDefault();
        /* Reset our form values */
        this.form.email = undefined;
        this.form.password = undefined;
        this.form.selector = undefined;
        this.form.file = undefined;
        this.form.textarea = undefined;
        this.form.custom = undefined;
        this.form.radio = 'ADMIN';
        this.form.checker = false;
        /* Trick to reset/clear native browser form validation state */
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        });
      },

      onSubmitVue() {
        console.log(this.$refs.form_vue);
        console.log(this.form_vue);
      },
      onResetVue(evt) {
        evt.preventDefault();
        /* Reset our form values */
        this.form_vue.email = undefined;
        this.form_vue.password = undefined;
        this.form_vue.selector = undefined;
        this.form_vue.file = undefined;
        this.form_vue.textarea = undefined;
        this.form_vue.custom = undefined;
        this.form_vue.radio = 'NORMAL';
        this.form_vue.checker = false;
        /* Trick to reset/clear native browser form validation state */
        this.showVue = false;
        this.$nextTick(() => {
          this.showVue = true
        });
      }

    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _IssueEditComp;
</script>
