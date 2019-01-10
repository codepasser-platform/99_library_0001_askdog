<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <div class="title-container container">
      <h5 class="my-0">
        <span class="badge badge-secondary">Sample 1</span>
        <span>Form inline validator(bootstrap)</span>
      </h5>
    </div>

    <div class="form-container container">
      <form class="needs-validation" v-validator="validator" novalidate @reset="onReset" v-if="show" ref="form">

        <div class="row">
          <div class="form-group col-md-6 row">
            <label class="col-form-label col-sm-3" for="email">Email address:</label>
            <div class="col-sm-9">
              <input id="email" name="email" type="text" class="form-control" aria-describedby="emailTip" placeholder="Enter email"
                     v-model="form.email">
              <div class="valid-feedback">valid-feedback</div>
              <small id="emailTip" class="form-text text-muted">email input.</small>
              <div class="invalid-feedback">invalid-feedback</div>
            </div>
          </div>
          <div class="form-group col-md-6 row">
            <label class="col-form-label col-sm-3" for="password">Password:</label>
            <div class="col-sm-9">
              <input id="password" name="password" type="password" class="form-control" aria-describedby="passwordTip" placeholder="Password"
                     v-model="form.password">
              <div class="valid-feedback">valid-feedback</div>
              <small id="passwordTip" class="form-text text-muted">password input.</small>
              <div class="invalid-feedback">invalid-feedback</div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="form-group col-md-6 row">
            <label class="col-form-label col-sm-3" for="selector">Selector:</label>
            <div class="col-sm-9">
              <select id="selector" name="selector" class="form-control" aria-describedby="selectorTip"
                      v-model="form.selector">
                <option :value="option.value" v-for="option in options">{{option.text}}</option>
              </select>
              <div class="valid-feedback">valid-feedback</div>
              <small id="selectorTip" class="form-text text-muted">selector chose.</small>
              <div class="invalid-feedback">invalid-feedback</div>
            </div>
          </div>
          <div class="form-group col-md-6 row">
            <label class="col-form-label col-sm-3" for="file">File:</label>
            <div class="col-sm-9">
              <input id="file" name="file" type="file" class="form-control-file" ref="form_file">
              <div class="valid-feedback">valid-feedback</div>
              <small id="fileTip" class="form-text text-muted">file chose.</small>
              <div class="invalid-feedback">invalid-feedback</div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="form-group col-md-6 row">
            <label class="col-form-label col-sm-3" for="textarea">Textarea:</label>
            <div class="col-sm-9">
          <textarea id="textarea" name="textarea" class="form-control" aria-describedby="textareaTip" placeholder="textarea"
                    v-model="form.textarea" rows="2" maxlength="140"></textarea>
              <div class="valid-feedback">valid-feedback</div>
              <small id="textareaTip" class="form-text text-muted">textarea input.</small>
              <div class="invalid-feedback">invalid-feedback</div>
            </div>
          </div>
          <div class="form-group col-md-6 row">
            <label class="col-form-label col-sm-3" for="custom">Custom:</label>
            <div class="col-sm-9">
              <input id="custom" name="custom" type="text" class="form-control" aria-describedby="customTip" placeholder="custom"
                     v-model="form.custom">
              <div class="valid-feedback">valid-feedback</div>
              <small id="customTip" class="form-text text-muted">custom input.</small>
              <div class="invalid-feedback">invalid-feedback</div>
            </div>
          </div>
        </div>

        <div class="row">

          <div class="form-group col-md-6 row">
            <label class="col-form-label col-sm-3">Types: </label>
            <div class="col-sm-9 d-flex align-items-center justify-content-start">
              <div class="form-check mr-2" v-for="(item,index) in types" :key="index">
                <input :id="'radio'+index" name="radioGroup" class="form-check-input" type="radio" :value="item.value" v-model="form.radio">
                <label class="form-check-label" :for="'radio'+index">{{item.text}}</label>
              </div>
            </div>
          </div>

          <div class="form-group col-md-6 text-right">
            <div class="form-check">
              <input id="checker" name="checker" class="form-check-input" type="checkbox" aria-describedby="checkerTip"
                     v-model="form.checker">
              <label class="form-check-label" for="checker">
                Agree to terms and conditions
              </label>
              <div class="valid-feedback">valid-feedback</div>
              <small id="checkerTip" class="form-text text-muted">checker input.</small>
              <div class="invalid-feedback">invalid-feedback</div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="form-group col-md-12 text-center">
            <button type="submit" class="btn btn-outline-primary btn-sm">Submit</button>
            <button type="reset" class="btn btn-outline-secondary btn-sm">Reset</button>
          </div>
        </div>
      </form>
    </div>

    <div class="title-container container">
      <h5 class="my-0">
        <span class="badge badge-secondary">Sample 2</span>
        <span>Form grid validator(bootstrap-vue)</span>
      </h5>
    </div>

    <div class="form-container container">
      <b-form class="needs-validation" v-validator="validator_vue" novalidate @reset="onResetVue" v-if="showVue" ref="form_vue">

        <b-row>
          <b-form-group horizontal class="col-md-6" label="Email address:" label-for="emailVue" description="email vue input." valid-feedback="valid-feedback">
            <b-form-input id="emailVue" name="emailVue" type="email" v-model="form_vue.email" placeholder="Enter email"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" label="Password:" label-for="passwordVue" description="password vue input." valid-feedback="valid-feedback">
            <b-form-input id="passwordVue" name="passwordVue" type="password" v-model="form_vue.password" placeholder="Enter password"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="Selector:" label-for="selectorVue" description="selector vue." valid-feedback="valid-feedback">
            <b-form-select id="selectorVue" :options="options" v-model="form_vue.selector">
            </b-form-select>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" label="File:" label-for="fileVue" description="file vue." valid-feedback="valid-feedback">
            <b-form-file id="fileVue" v-model="form_vue.file" placeholder="Choose a file...">
            </b-form-file>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="Textarea:" label-for="textareaVue" description="textarea vue." valid-feedback="valid-feedback">
            <b-form-textarea id="textareaVue" v-model="form_vue.textarea" placeholder="Enter something" rows="2" maxlength="140">
            </b-form-textarea>
          </b-form-group>
          <b-form-group horizontal class="col-md-6" label="Custom:" label-for="customVue" description="custom vue input." valid-feedback="valid-feedback">
            <b-form-input id="customVue" name="customVue" type="text" v-model="form_vue.custom" placeholder="Enter custom"></b-form-input>
          </b-form-group>
        </b-row>

        <b-row>
          <b-form-group horizontal class="col-md-6" label="Types">
            <b-form-radio-group id="radiosVue" v-model="form_vue.radio" :options="types" name="radioGroupsVue">
            </b-form-radio-group>
          </b-form-group>

          <b-form-group horizontal class="col-md-6 text-right" description="checker vue input." valid-feedback="valid-feedback">
            <b-form-checkbox id="checkerVue" v-model="form_vue.checker">I accept the terms and use
            </b-form-checkbox>
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

  const _GuideSampleFormInline = {
    name: 'GuideSampleFormInline',
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
            console.log('form validator submit handler 1');
            _self.onSubmit();
          }
        },

        validator_vue: {
          rules: {
            emailVue: {
              required: true,
              regex: REGEX_MAIL
            },
            passwordVue: {
              required: true,
              regex: REGEX_USER_PASSWORD
            },
            selectorVue: {
              required: true
            },
            fileVue: {
              required: true,
              regex: REGEX_FILE_OFFICE
            },
            textareaVue: {
              required: true
            },
            customVue: {
              required: false,
              regex: REGEX_GENERAL_NAME
            },
            checkerVue: {
              required: true
            }
          },
          message: {
            emailVue: {
              required: 'email vue required',
              regex: 'regex : email vue invalid'
            },
            passwordVue: {
              required: 'password vue required',
              regex: 'regex : password vue invalid'
            },
            selectorVue: {
              required: 'selector vue required'
            },
            fileVue: {
              required: 'file vue required',
              regex: 'regex : file vue invalid'
            },
            textareaVue: {
              required: 'textarea vue required'
            },
            customVue: {
              required: 'custom vue required',
              regex: 'regex : custom vue invalid'
            },
            checkerVue: {
              required: 'regex : checker vue invalid'
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
  export default _GuideSampleFormInline;
</script>