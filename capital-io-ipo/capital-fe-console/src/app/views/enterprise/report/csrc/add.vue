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
          <b-form-group horizontal class="col-md-6" label="审核状态：" label-for="approvalStatus">
            <b-form-input id="approvalStatus" name="approvalStatus" type="text"
                          v-model="form_vue.approvalStatus"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" label="上会时间：" label-for="meetingTime">
            <b-form-input id="meetingTime" name="meetingTime" type="text" placeholder="年/月/日"
                          data-date-format="yyyy-mm-dd"
                          data-view="date"
                          data-position="left"
                          v-date-picker="meetingTimeChangeEvent"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="解决事项：" label-for="solution">
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
          <b-form-group horizontal class="col-md-6" label="协调结果：" label-for="coordinationResult">
            <b-form-textarea id="coordinationResult" v-model="form_vue.coordinationResult" rows="2" maxlength="140">
            </b-form-textarea>
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
  import {_addSave} from "../../../../service/api/report/report-api";

  const _InCompanyWeekAdd = {
    name: 'InCompanyWeekAdd',
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
        meetingTimeChangeEvent(_value) {
          _self.form_vue.meetingTime = _value;
        },

        validator_vue: {
          rules: {
            companyName: {
              required: true
            },
            registration: {
              required: true
            },
            approvalStatus: {
              required: true
            },
            meetingTime: {
              required: true,
              regex: REGEX_DATE
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
            coordinationResult: {
              required: false
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
            approvalStatus: {
              required: '审核状态为必填项！'
            },
            meetingTime: {
              required: '上会时间为必填项！',
              regex: '上会时间格式不正确！'
            },
            solution: {
              required: '解决事项为必填项！'
            },
            companyContact: {
              required: '公司联系人为必填项！'
            },
            companyTel: {
              required: '公司联系人电话为必填项！',
              regex: '电话格式不正确！'
            },
            sponsorsTel: {
              regex: '电话格式不正确！'
            }
          },
          submitHandler: function () {
            _self.onSubmitVue();
          }
        },

        form_vue: {
          companyName: undefined,
          registration: undefined,
          approvalStatus: undefined,
          meetingTime: undefined,
          solution: undefined,
          companyContact: undefined,
          companyTel: undefined,
          sponsorsContact: undefined,
          sponsorsTel: undefined,
          coordinationResult: undefined,
          remarks: undefined
        },
        showVue: true
      };
    },
    methods: {
      onSubmitVue() {
        const _self = this;
        _addSave(_self.form_vue).then(
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
        this.form_vue.approvalStatus = undefined;
        this.form_vue.meetingTime = undefined;
        this.form_vue.solution = undefined;
        this.form_vue.companyContact = undefined;
        this.form_vue.companyTel = undefined;
        this.form_vue.sponsorsContact = undefined;
        this.form_vue.sponsorsTel = undefined;
        this.form_vue.coordinationResult = undefined;
        this.form_vue.remarks = undefined;
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
  export default _InCompanyWeekAdd;
</script>
