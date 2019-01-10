<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <div class="title-container container">
      <h5 class="my-0">
        <span class="badge badge-secondary">Sample 1</span>
        <span>Date Picker</span>
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
            <b-form-group class="col-md-6" label="Date Picker:" label-for="datePicker" description="date picker." valid-feedback="valid-feedback">
              <b-form-input id="datePicker" name="datePicker" type="text" placeholder="date picker"
                            data-date-format="yyyy-mm-dd"
                            data-view="date"
                            data-position="left"
                            data-start="2018-01-01"
                            data-end="2018-12-31"
                            v-date-picker="datePickerChangeEvent"></b-form-input>
            </b-form-group>

            <b-form-group class="col-md-6" label="Date Time Picker:" label-for="dateTimePicker" description="date time Picker." valid-feedback="valid-feedback">
              <b-form-input id="dateTimePicker" name="dateTimePicker" type="text" placeholder="date time Picker"
                            data-date-format="yyyy-mm-dd hh:ii:ss"
                            data-view="datetime"
                            data-position="right"
                            data-start="2018-07-01 00:00:00"
                            data-end="2018-12-31 00:00:0"
                            v-date-picker="dateTimePickerChangeEvent"></b-form-input>
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
  import {
    REGEX_DATE,
    REGEX_DATETIME,
    REGEX_MAIL,
    REGEX_USER_PASSWORD
  } from "../../../../basement/utils/regex-pattern";

  const _GuideSampleDatePicker = {
    name: 'GuideSampleDatePicker',
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
          datePicker: undefined,
          dateTimePicker: undefined,
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
        this.form.datePicker = undefined;
        this.form.dateTimePicker = undefined;
        this.form.email = undefined;
        this.form.password = undefined;
        /* Trick to reset/clear native browser form validation state */
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        });
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _GuideSampleDatePicker;
</script>