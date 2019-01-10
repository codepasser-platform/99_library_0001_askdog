<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <div class="title-container container">
      <h5 class="my-0">
        <span class="badge badge-secondary">Sample 1</span>
        <span>Form validator(bootstrap)</span>
      </h5>
    </div>

    <div class="form-container container">
      <form class="needs-validation" v-validator="validator" novalidate @reset="onReset" v-if="show" ref="form">

        <div class="form-group input-append date">
          <label for="datePicker">Date Picker:</label>
          <input id="datePicker" name="datePicker" type="text" class="form-control " aria-describedby="datePickerTip" placeholder="datePicker"
                 data-date-format="yyyy-mm-dd"
                 data-view="date"
                 data-position="left"
                 data-start="2018-01-01"
                 data-end="2018-12-31"
                 v-date-picker="datePickerChangeEvent">
          <div class="valid-feedback">valid-feedback</div>
          <small id="datePickerTip" class="form-text text-muted">datePicker input.</small>
          <div class="invalid-feedback">invalid-feedback</div>
        </div>

        <div class="form-group input-append date">
          <label for="dateTimePicker">Date Time Picker:</label>
          <input id="dateTimePicker" name="dateTimePicker" type="text" class="form-control " aria-describedby="dateTimePickerTip" placeholder="dateTimePicker"
                 data-date-format="yyyy-mm-dd hh:ii:ss"
                 data-view="datetime"
                 data-position="left"
                 data-start="2018-07-01 10:00:00"
                 data-end="2018-07-02 00:00:00"
                 v-date-picker="dateTimePickerChangeEvent">
          <div class="valid-feedback">valid-feedback</div>
          <small id="dateTimePickerTip" class="form-text text-muted">dateTimePicker input.</small>
          <div class="invalid-feedback">invalid-feedback</div>
        </div>

        <div class="form-group">
          <label for="email">Email address:</label>
          <input id="email" name="email" type="text" class="form-control" aria-describedby="emailTip" placeholder="Enter email"
                 v-model="form.email">
          <div class="valid-feedback">valid-feedback</div>
          <small id="emailTip" class="form-text text-muted">email input.</small>
          <div class="invalid-feedback">invalid-feedback</div>
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input id="password" name="password" type="password" class="form-control" aria-describedby="passwordTip" placeholder="Password"
                 v-model="form.password">
          <div class="valid-feedback">valid-feedback</div>
          <small id="passwordTip" class="form-text text-muted">password input.</small>
          <div class="invalid-feedback">invalid-feedback</div>
        </div>
        <div class="form-group">
          <label for="selector">Selector:</label>
          <select id="selector" name="selector" class="form-control" aria-describedby="selectorTip"
                  v-model="form.selector">
            <option :value="option.value" v-for="option in options">{{option.text}}</option>
          </select>
          <div class="valid-feedback">valid-feedback</div>
          <small id="selectorTip" class="form-text text-muted">selector chose.</small>
          <div class="invalid-feedback">invalid-feedback</div>
        </div>

        <div class="form-group">
          <label for="file">File:</label>
          <input id="file" name="file" type="file" class="form-control-file" ref="form_file">
          <div class="valid-feedback">valid-feedback</div>
          <small id="fileTip" class="form-text text-muted">file chose.</small>
          <div class="invalid-feedback">invalid-feedback</div>
        </div>

        <div class="form-group">
          <label for="textarea">Textarea:</label>
          <textarea id="textarea" name="textarea" class="form-control" aria-describedby="textareaTip" placeholder="textarea"
                    v-model="form.textarea" rows="2" maxlength="140"></textarea>
          <div class="valid-feedback">valid-feedback</div>
          <small id="textareaTip" class="form-text text-muted">textarea input.</small>
          <div class="invalid-feedback">invalid-feedback</div>
        </div>

        <div class="form-group">
          <label for="custom">Custom:</label>
          <input id="custom" name="custom" type="text" class="form-control" aria-describedby="customTip" placeholder="custom"
                 v-model="form.custom">
          <div class="valid-feedback">valid-feedback</div>
          <small id="customTip" class="form-text text-muted">custom input.</small>
          <div class="invalid-feedback">invalid-feedback</div>
        </div>

        <div class="form-group">
          <label>Types: </label>
          <div class="d-flex justify-content-start">
            <div class="form-check mr-2" v-for="(item,index) in types" :key="index">
              <input :id="'radio'+index" name="radioGroup" class="form-check-input" type="radio" :value="item.value" v-model="form.radio">
              <label class="form-check-label" :for="'radio'+index">{{item.text}}</label>
            </div>
          </div>
        </div>

        <div class="form-group">
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

        <div class="form-group text-center">
          <button type="submit" class="btn btn-outline-primary btn-sm">Submit</button>
          <button type="reset" class="btn btn-outline-secondary btn-sm">Reset</button>
        </div>
      </form>
    </div>

    <div class="title-container container">
      <h5 class="my-0">
        <span class="badge badge-secondary">Sample 2</span>
        <span>Form validator(bootstrap-vue)</span>
      </h5>
    </div>

    <div class="form-container container">
      <b-form class="needs-validation" v-validator="validator_vue" novalidate @reset="onResetVue" v-if="showVue" ref="form_vue">
        <b-form-group label="Email address:" label-for="emailVue" description="email vue input." valid-feedback="valid-feedback">
          <b-form-input id="emailVue" name="emailVue" type="email" v-model="form_vue.email" placeholder="Enter email"></b-form-input>
        </b-form-group>

        <b-form-group label="Password:" label-for="passwordVue" description="password vue input." valid-feedback="valid-feedback">
          <b-form-input id="passwordVue" name="passwordVue" type="password" v-model="form_vue.password" placeholder="Enter password"></b-form-input>
        </b-form-group>

        <b-form-group label="Selector:" label-for="selectorVue" description="selector vue." valid-feedback="valid-feedback">
          <b-form-select id="selectorVue"
                         :options="options"
                         v-model="form_vue.selector">
          </b-form-select>
        </b-form-group>

        <b-form-group label="File:" label-for="fileVue" description="file vue." valid-feedback="valid-feedback">
          <b-form-file id="fileVue"
                       v-model="form_vue.file"
                       placeholder="Choose a file...">
          </b-form-file>
        </b-form-group>

        <b-form-group label="Textarea:" label-for="textareaVue" description="textarea vue." valid-feedback="valid-feedback">
          <b-form-textarea id="textareaVue"
                           v-model="form_vue.textarea"
                           placeholder="Enter something" rows="2" maxlength="140">
          </b-form-textarea>
        </b-form-group>

        <b-form-group label="Custom:" label-for="customVue" description="custom vue input." valid-feedback="valid-feedback">
          <b-form-input id="customVue" name="customVue" type="text" v-model="form_vue.custom" placeholder="Enter custom"></b-form-input>
        </b-form-group>

        <b-form-group label="Types">
          <b-form-radio-group id="radiosVue" v-model="form_vue.radio" :options="types" name="radioGroupsVue">
          </b-form-radio-group>
        </b-form-group>

        <b-form-group description="checker vue input." valid-feedback="valid-feedback">
          <b-form-checkbox id="checkerVue"
                           v-model="form_vue.checker">
            I accept the terms and use
          </b-form-checkbox>
        </b-form-group>

        <b-form-group class="text-center">
          <b-button type="submit" size="sm" variant="outline-primary">Submit</b-button>
          <b-button type="reset" size="sm" variant="outline-secondary">Reset</b-button>
        </b-form-group>
      </b-form>
    </div>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {
    REGEX_DATE,
    REGEX_DATETIME,
    REGEX_FILE_OFFICE,
    REGEX_GENERAL_NAME,
    REGEX_MAIL,
    REGEX_USER_PASSWORD
  } from "../../../../basement/utils/regex-pattern";

  const _GuideSampleForm = {
    name: 'GuideSampleForm',
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
        datePickerChangeEvent(_value) {
          _self.form.datePicker = _value;
        },
        dateTimePickerChangeEvent(_value) {
          _self.form.dateTimePicker = _value;
        },
        validator: {
          rules: {
            datePicker: {
              required: true,
              regex: REGEX_DATE
            },
            dateTimePicker: {
              required: true,
              regex: REGEX_DATETIME
            },
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
            datePicker: {
              required: 'date picker required',
              regex: 'regex : date invalid'
            },
            dateTimePicker: {
              required: 'date time picker required',
              regex: 'regex : date time invalid'
            },
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
          datePicker: undefined,
          dateTimePicker: undefined,
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
        this.form.datePicker = undefined;
        this.form.dateTimePicker = undefined;
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
  export default _GuideSampleForm;
</script>