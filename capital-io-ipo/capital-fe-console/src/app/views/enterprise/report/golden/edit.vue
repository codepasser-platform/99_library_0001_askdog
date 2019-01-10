<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <div class="form-container container">
      <b-form class="needs-validation" v-validator="validator_vue" novalidate @reset="onResetVue" v-if="showVue"
              ref="form_vue">

        <b-row>
          <b-form-group horizontal class="col-md-6" label="企业名称：" label-for="companyName">
            <b-form-input id="companyName" name="companyName" type="text" v-model="form_vue.companyName"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" label="注册地：" label-for="registration">
            <b-form-input id="registration" name="registration" type="text"
                          v-model="form_vue.registration"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="申请辅导备案时间：" label-for="filingTime">
            <b-form-input id="filingTime" name="filingTime" type="text" placeholder="年/月/日"
                          data-date-format="yyyy-mm-dd"
                          data-view="date"
                          data-position="left"
                          v-date-picker="filingTimeChangeEvent"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" label="上市地：" label-for="listPlace">
            <b-form-input id="listPlace" name="listPlace" type="text" v-model="form_vue.listPlace"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="解决事项与进展：" label-for="solution">
            <b-form-textarea id="solution" v-model="form_vue.solution" rows="2" maxlength="140">
            </b-form-textarea>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="公司联系人：" label-for="companyContact">
            <b-form-input id="companyContact" name="companyContact" type="text"
                          v-model="form_vue.companyContact"></b-form-input>
          </b-form-group>

          <b-form-group horizontal class="col-md-6" label="电话：" label-for="companyTel">
            <b-form-input id="companyTel" name="companyTel" type="number" v-model="form_vue.companyTel"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="保荐机构及联系人：" label-for="sponsorsContact">
            <b-form-input id="sponsorsContact" name="sponsorsContact" type="text"
                          v-model="form_vue.sponsorsContact"></b-form-input>
          </b-form-group>

          <b-form-group horizontal class="col-md-6" label="电话：" label-for="sponsorsTel">
            <b-form-input id="sponsorsTel" name="sponsorsTel" type="number"
                          v-model="form_vue.sponsorsTel"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="审计事务所及联系人：" label-for="auditContact">
            <b-form-input id="auditContact" name="auditContact" type="text"
                          v-model="form_vue.auditContact"></b-form-input>
          </b-form-group>

          <b-form-group horizontal class="col-md-6" label="电话：" label-for="auditTel">
            <b-form-input id="auditTel" name="auditTel" type="number" v-model="form_vue.auditTel"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="律师事务所及联系人：" label-for="lawContact">
            <b-form-input id="lawContact" name="lawContact" type="text" v-model="form_vue.lawContact"></b-form-input>
          </b-form-group>

          <b-form-group horizontal class="col-md-6" label="电话：" label-for="lawTel">
            <b-form-input id="lawTel" name="lawTel" type="number" v-model="form_vue.lawTel"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="备注：" label-for="remarks">
            <b-form-textarea id="remarks" v-model="form_vue.remarks" rows="2" maxlength="140">
            </b-form-textarea>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group class="col-md-12 text-center">
            <b-button size="sm" type="reset" variant="outline-secondary">取消</b-button>
            <b-button size="sm" type="submit" variant="outline-primary">确定</b-button>
          </b-form-group>
        </b-row>

      </b-form>
    </div>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {REGEX_DATE, REGEX_PHONE} from "capital-fe/src/basement/utils/regex-pattern";
  import {_getDetail, _updateSave} from "../../../../service/api/report/report-api";

  const _GoldenMonthEdit = {
    name: 'GoldenMonthEdit',
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
        filingTimeChangeEvent(_value) {
          _self.form_vue.filingTime = _value;
        },
        validator_vue: {
          rules: {
            companyName: {
              required: true
            },
            registration: {
              required: true
            },
            filingTime: {
              required: true,
              regex: REGEX_DATE
            },
            listPlace: {
              required: true
            },
            solution: {
              required: true
            },
            companyContact: {
              required: true
            },
            companyTel: {
              required: true,
              regex: REGEX_PHONE
            },
            sponsorsContact: {
              required: false
            },
            sponsorsTel: {
              required: false,
              regex: REGEX_PHONE
            },
            auditContact: {
              required: false
            },
            auditTel: {
              required: false,
              regex: REGEX_PHONE
            },
            lawContact: {
              required: false
            },
            lawTel: {
              required: false,
              regex: REGEX_PHONE
            },
            remarks: {
              required: false
            }
          },
          message: {
            companyName: {
              required: '企业名称为必填项！'
            },
            registration: {
              required: '注册地为必填项！'
            },
            filingTime: {
              required: '申请辅导备案时间为必填项！',
              regex: '申请辅导备案时间格式不正确！'
            },
            listPlace: {
              required: '上市地为必填项！'
            },
            solution: {
              required: '解决事项与进展为必填项！'
            },
            companyContact: {
              required: '公司联系人为必填项！'
            },
            companyTel: {
              required: '公司联系人电话为必填项！',
              regex: '电话格式不正确！'
            },
            sponsorsTel: {
              regex: '保荐机构及联系人电话格式不正确！'
            },
            auditTel: {
              regex: '审计事务所及联系人电话格式不正确！'
            },
            lawTel: {
              regex: '律师事务所及联系人电话格式不正确！'
            }
          },
          submitHandler: function () {
            console.log('form vue validator submit handler 2');
            _self.onSubmitVue();
          }
        },

        form_vue: {
          companyName: undefined,
          registration: undefined,
          filingTime: undefined,
          listPlace: undefined,
          solution: undefined,
          companyContact: undefined,
          companyTel: undefined,
          sponsorsContact: undefined,
          sponsorsTel: undefined,
          auditContact: undefined,
          auditTel: undefined,
          lawContact: undefined,
          lawTel: undefined,
          remarks: undefined
        },
        showVue: true
      };
    },
    methods: {
      onSubmitVue() {
        const _self = this;
        _updateSave(_self.form_vue).then(
          function (response) {
            console.log(response);
          }
        ).catch(
          function (error) {
          }
        )
      },
      onResetVue(evt) {
        evt.preventDefault();
        /* Reset our form values */
        this.form_vue.companyName = undefined;
        this.form_vue.registration = undefined;
        this.form_vue.filingTime = undefined;
        this.form_vue.listPlace = undefined;
        this.form_vue.solution = undefined;
        this.form_vue.companyContact = undefined;
        this.form_vue.companyTel = undefined;
        this.form_vue.sponsorsContact = undefined;
        this.form_vue.sponsorsTel = undefined;
        this.form_vue.auditContact = undefined;
        this.form_vue.auditTel = undefined;
        this.form_vue.lawContact = undefined;
        this.form_vue.lawTel = undefined;
        this.form_vue.remarks = undefined;
        /* Trick to reset/clear native browser form validation state */
        this.showVue = false;
        this.$nextTick(() => {
          this.showVue = true
        });
      },
      requireRecord(id) {
        const _self = this;
        _getDetail(id).then(
          function (response) {
            _self.form_vue = response.data.result;
          }
        ).catch(
          function (error) {
          }
        )
      }

    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _GoldenMonthEdit;
</script>
