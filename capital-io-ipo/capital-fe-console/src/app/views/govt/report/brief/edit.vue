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
          <b-form-group horizontal class="col-md-6" label="标题：" label-for="title">
            <b-form-input id="title" name="title" type="text" v-model="form_vue.title"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="简报内容：" label-for="briefContent">
            <b-form-textarea id="briefContent" v-model="form_vue.briefContent" rows="2" maxlength="140">
            </b-form-textarea>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="简报附件：" label-for="briefAttachment">
            <b-form-file id="briefAttachment" v-model="form_vue.briefAttachment" placeholder="Choose a file...">
            </b-form-file>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6">
            <div>请选择doc/docx/xls/xlsx 格式文件</div>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group class="col-md-12 text-center">
            <b-button type="reset" variant="outline-secondary" size="sm">取消</b-button>
            <b-button type="submit" variant="outline-primary" size="sm">上报</b-button>
          </b-form-group>
        </b-row>

      </b-form>
    </div>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {REGEX_FILE_OFFICE} from "capital-fe/src/basement/utils/regex-pattern";
  import {_getDetail, _importBrief, _updateSave} from "../../../../service/api/report/report-api";

  const _BriefEdit = {
    name: 'BriefEdit',
    created: function () {
      //调用查询接口
      if (this.$route.query.id) {
        this.requireRecord(this.$route.query.id);
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
        validator_vue: {
          rules: {
            title: {
              required: true
            },
            briefContent: {
              required: true
            },
            briefAttachment: {
              required: true,
              regex: REGEX_FILE_OFFICE
            }
          },
          message: {
            title: {
              required: '标题为必填项！'
            },
            briefContent: {
              required: '简报内容为必填项！'
            },
            briefAttachment: {
              required: '简报附件为必填项！',
              regex: '简报附件格式不正确！'
            }
          },
          submitHandler: function () {
            console.log('form vue validator submit handler 2');
            _self.onSubmitVue();
          }
        },

        form_vue: {
          title: undefined,
          briefContent: undefined,
          briefAttachment: undefined
        },
        showVue: true
      };
    },
    methods: {
      onSubmitVue() {
        const _self = this;
        if (_self.$route.query.id) {
          _updateSave(_self.form_vue).then(
            function (response) {
              console.log(response);
            }
          ).catch(
            function (error) {
            }
          )
        } else {
          _importBrief(_self.form_vue).then(
            function (response) {
              console.log(response);
            }
          ).catch(
            function (error) {
            }
          )
        }
      },
      onResetVue(evt) {
        evt.preventDefault();
        /* Reset our form values */
        this.form_vue.title = undefined;
        this.form_vue.briefContent = undefined;
        this.form_vue.briefAttachment = undefined;
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
  export default _BriefEdit;
</script>
