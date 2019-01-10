<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <div class="title-container container">
      <h5 class="my-0">
        <span class="badge badge-secondary">Sample 1</span>
        <span>Tinymce</span>
      </h5>
    </div>

    <div class="form-container container">
      <div class="form-container container">
        <b-form class="needs-validation" v-validator="validator" novalidate @reset="onReset" v-if="show" ref="form">

          <b-row>
            <b-form-group class="col-md-6" label="Email address:" label-for="email" description="email  input." valid-feedback="valid-feedback">
              <b-form-input id="email" name="email" type="email" v-model="form.email" placeholder="Enter email"></b-form-input>
            </b-form-group>
            <b-form-group class="col-md-6" label="Password:" label-for="password" description="password  input." valid-feedback="valid-feedback">
              <b-form-input id="password" name="password" type="password" v-model="form.password" placeholder="Enter password"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group class="col-md-12" label="CK-Editor:" label-for="content" description="editor content." valid-feedback="valid-feedback">
              <editor v-model="form.content" :init="editorInit" id="content"></editor>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group class="col-md-12 text-center">
              <b-button type="submit" variant="outline-primary" size="sm">Submit</b-button>
              <b-button type="reset" variant="outline-secondary" size="sm">Reset</b-button>
            </b-form-group>
          </b-row>
        </b-form>
      </div>
    </div>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {REGEX_MAIL, REGEX_USER_PASSWORD} from "../../../../basement/utils/regex-pattern";

  import tinymce from 'tinymce/tinymce'
  import 'tinymce/themes/modern/theme'
  import Editor from '@tinymce/tinymce-vue'

  const _GuideSampleTinymce = {
    name: 'GuideSampleTinymce',
    created: function () {
    },
    updated: function () {
    },
    mounted: function () {
      tinymce.init({});
    },
    activated: function () {
    },
    data: function () {
      const _self = this;
      return {
        editorInit: {
          language_url: '/static/tinymce/langs/zh_CN.js',
          language: 'zh_CN',
          skin_url: '/static/tinymce/skins/lightgray',
          height: 300
        },
        datePickerChangeEvent(_value) {
          _self.form.datePicker = _value;
        },
        dateTimePickerChangeEvent(_value) {
          _self.form.dateTimePicker = _value;
        },
        validator: {
          rules: {
            email: {
              required: true,
              regex: REGEX_MAIL
            },
            password: {
              required: true,
              regex: REGEX_USER_PASSWORD
            },
            content: {
              required: true,
            }
          },
          message: {
            email: {
              required: 'email required',
              regex: 'regex : email invalid'
            },
            password: {
              required: 'password required',
              regex: 'regex : password invalid'
            },
            content: {
              required: 'content required',
            }
          },
          submitHandler: function () {
            console.log('form validator submit handler 1');
            _self.onSubmit();
          }
        },
        form: {
          email: undefined,
          password: undefined,
          content: undefined
        },
        show: true
      };
    },
    methods: {
      onSubmit() {
        console.log(this.$refs.form);
        console.log(this.form);
      },
      onReset(evt) {
        evt.preventDefault();
        /* Reset our form values */
        this.form.email = undefined;
        this.form.password = undefined;
        this.form.content = undefined;
        /* Trick to reset/clear native browser form validation state */
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        });
      }
    },
    components: {
      Editor
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _GuideSampleTinymce;
</script>