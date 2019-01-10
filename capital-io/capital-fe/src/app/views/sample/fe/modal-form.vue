<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <b-modal centered hide-footer hide-header-close no-close-on-backdrop no-close-on-esc
             size="lg"
             :title="modalTitle"
             ref="modalRef">
      <div class="form-container container">
        <b-form class="needs-validation" v-validator="validator" novalidate @reset="onReset" v-if="show" ref="form">
          <input type="hidden" v-model="form.id">

          <b-form-group label="Email address:" label-for="email">
            <b-form-input id="email" name="email" type="email" v-model="form.email" placeholder="Enter email"></b-form-input>
          </b-form-group>

          <b-form-group label="Password:" label-for="password">
            <b-form-input id="password" name="password" type="password" v-model="form.password" placeholder="Enter password" v-if="!form.id"></b-form-input>
            <b-form-input id="password" name="password" type="password" v-model="form.password" placeholder="Enter password" v-if="form.id" readonly></b-form-input>
          </b-form-group>

          <b-form-group label="Selector:" label-for="selector">
            <b-form-select id="selector"
                           :options="options"
                           v-model="form.selector">
            </b-form-select>
          </b-form-group>

          <b-form-group label="File:" label-for="file">
            <b-form-file id="file"
                         v-model="form.file"
                         placeholder="Choose a file...">
            </b-form-file>
          </b-form-group>

          <b-form-group label="Textarea:" label-for="textarea">
            <b-form-textarea id="textarea"
                             v-model="form.textarea"
                             placeholder="Enter something" rows="2" maxlength="140">
            </b-form-textarea>
          </b-form-group>

          <b-form-group label="Custom:" label-for="custom">
            <b-form-input id="custom" name="custom" type="text" v-model="form.custom" placeholder="Enter custom"></b-form-input>
          </b-form-group>

          <b-form-group label="Types">
            <b-form-radio-group id="radios" v-model="form.radio" :options="types" name="radioGroups">
            </b-form-radio-group>
          </b-form-group>

          <b-form-group>
            <b-form-checkbox id="checker"
                             v-model="form.checker">
              I accept the terms and use
            </b-form-checkbox>
          </b-form-group>

          <b-form-group class="text-center">
            <b-button type="submit" size="sm" variant="outline-primary">Submit</b-button>
            <b-button type="reset" size="sm" variant="outline-secondary">Reset</b-button>
            <b-button type="button" size="sm" variant="outline-secondary" @click="cancel()">Cancel
            </b-button>
          </b-form-group>
        </b-form>
      </div>
    </b-modal>


  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {
    REGEX_FILE_OFFICE,
    REGEX_GENERAL_NAME,
    REGEX_MAIL,
    REGEX_USER_PASSWORD
  } from "../../../../basement/utils/regex-pattern";

  const _GuideSampleModalForm = {
    name: 'GuideSampleModalForm',
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
            selector: {
              required: true
            },
            file: {
              required: true,
              regex: REGEX_FILE_OFFICE
            },
            textarea: {
              required: true
            },
            custom: {
              required: false,
              regex: REGEX_GENERAL_NAME
            },
            checker: {
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
            selector: {
              required: 'selector required'
            },
            file: {
              required: 'file required',
              regex: 'regex : file invalid'
            },
            textarea: {
              required: 'textarea required'
            },
            custom: {
              required: 'custom required',
              regex: 'regex : custom invalid'
            },
            checker: {
              required: 'checker required',
            }
          },
          submitHandler: function () {
            console.log('form validator submit handler');
            _self.onSubmit();
          }
        },

        form: {
          id: undefined,
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

        modalTitle: 'Creation',
        show: true
      };
    },
    methods: {
      open(_id) {
        console.log('GuideSampleModalForm > open', _id);
        this.form.id = _id;
        this.modalTitle = 'Creation';
        this.$refs.modalRef.show();
        if (_id) {
          this.modalTitle = 'Edition';
          this.requireRecord(_id);
        }
      },
      close() {
        console.log('GuideSampleModalForm > close');
        this.clearForm();
        this.$refs.modalRef.hide();
        this.$emit('confirmEvent');
      },
      cancel() {
        console.log('GuideSampleModalForm > cancel');
        this.clearForm();
        this.$refs.modalRef.hide();
        this.$emit('cancelEvent');
      },
      onSubmit() {
        console.log('GuideSampleModalForm > onSubmit');
        console.log(this.$refs.form);
        console.log(this.form);
        this.close();
      },
      onReset(evt) {
        console.log('GuideSampleModalForm > onReset');
        evt.preventDefault();
        this.clearForm();
      },
      clearForm() {
        console.log('GuideSampleModalForm > clearForm');
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
        console.log('GuideSampleModalForm > requireRecord', _id);
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _GuideSampleModalForm;
</script>