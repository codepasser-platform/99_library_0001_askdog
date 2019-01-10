<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <div class="form-container container">
      <b-form class="needs-validation" v-validator="problem_validator" novalidate @reset="onResetVue" v-if="showVue" ref="form_vue">

        <b-row>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="企业名称:" label-for="companyName">
            <b-form-input id="companyName" name="companyName" type="text" v-model="report_form.companyName" placeholder="" :disabled="readOnly"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="注册地:" label-for="passwordVue">
            <b-form-input id="registryArea" name="registryArea" type="text" v-model="report_form.registryArea" placeholder="" :disabled="readOnly"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="申报人:" label-for="dutyPerson">
            <b-form-input id="dutyPerson" name="dutyPerson" type="text" v-model="report_form.reportPerson" placeholder="" :disabled="readOnly"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="手机号码:" label-for="phone">
            <b-form-input id="phone" name="phone" type="text" v-model="report_form.phone" placeholder="" :disabled="readOnly"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" :label-cols="2" label="上市进展:" label-for="issueType">
            <b-form-input id="issueType" name="issueType" type="text" v-model="report_form.ipoProcess" placeholder="" :disabled="readOnly"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-12" :label-cols="1" label="面临问题:" label-for="content">
            <b-form-textarea id="content" v-model="report_form.content" placeholder="" rows="6" maxlength="512" :disabled="readOnly">
            </b-form-textarea>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-12" :label-cols="1" label="备注:" label-for="measures" :disabled="readOnly">
            <b-form-textarea id="measures" v-model="report_form.remark" placeholder="" rows="6" maxlength="512">
            </b-form-textarea>
          </b-form-group>
        </b-row>

        <b-row v-if="false">
          <b-form-group horizontal class="col-md-12" :label-cols="1" label="受理意见:" label-for="measures" :disabled="readOnly">
            <b-form-textarea id="measures" v-model="report_form.opinion" placeholder="" rows="6" maxlength="512">
            </b-form-textarea>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group class="col-md-12 text-center">
            <b-button type="button" variant="outline-secondary" size="sm">取消</b-button>
            <b-button type="submit" variant="outline-primary" size="sm">确定</b-button>
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

  const _ProblemEdit = {
    name: 'ProblemEdit',
    created: function () {
      let problemId = this.$route.params.id;
      let type = this.$route.params.type;

      if (problemId != undefined) {
        if (type === 'detail') {
          this.readOnly = true;
        } else if (type === 'edit'){
          this.readOnly = false;
        }
        this.searchData(problemId)
      }
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

        problem_validator: {
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
              required: '申报人是必填项'
            },
            phone: {
              required: '电话是必填项',
              regex: '电话格式错误'
            },
            issueType: {
              required: '上市进展是必填项'
            },
            content: {
              required: '面临问题是必填项'
            }
          },
          submitHandler: function () {
            console.log('form vue validator submit handler 2');
            _self.onSubmitVue();
          }
        },

        report_form: {
          companyName: undefined,
          registryArea: undefined,
          reportPerson: undefined,
          phone: undefined,
          ipoProcess: undefined,
          content: undefined,
          remark: undefined,
          opinion: undefined
        },
        readOnly: false,
        show: true,
        showVue: true
      };
    },
    methods: {
      searchData(_id) {
        //假数据
        this.report_form.companyName = '测试公司1';
        this.report_form.registryArea = '湖北省/武汉市/武昌区';
        this.report_form.reportPerson = '测试人员1';
        this.report_form.phone = '13304119685';
        this.report_form.ipoProcess = '已报会';
        this.report_form.content = '绿色问题内容内容';
        this.report_form.remark = '备注备注';
      },

      onSubmit() {
        let _file = this.$refs.form_file.files[0];
        this.form.file = _file;
        console.log(this.$refs.form);
        console.log(this.form);
      },

      onSubmitVue() {
        console.log(this.$refs.form_vue);
        console.log(this.form_vue);
      },
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _ProblemEdit;
</script>
