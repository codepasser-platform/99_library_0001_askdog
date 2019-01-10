<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="components-container issue-container">

    <!-- Condition container -->
    <div class="condition-container">
      <b-form class="needs-validation" novalidate @submit="conditionSubmitHandler" @reset="conditionResetHandler">
        <b-row>
          <b-form-group horizontal label-size="sm" :label-cols="3" class="col-md-3" label="公司名称:"
                        label-for="companyName">
            <b-form-input id="companyName" name="companyName" type="text" size="sm" v-model="condition_form.companyName"
                          placeholder="公司名称"></b-form-input>
          </b-form-group>

          <b-form-group horizontal label-size="sm" :label-cols="2" class="col-md-4" label="起始时间:"
                        label-for="startDate">
            <b-row>
              <b-col>
                <b-form-input id="startDate" name="startDate" type="text" placeholder="年-月-日"
                              data-date-format="yyyy-mm-dd"
                              data-view="date"
                              data-position="left"
                              data-start="2018-01-01"
                              data-end="2018-12-31"
                              size="sm"
                              v-date-picker="datePickerStartDateChangeEvent"></b-form-input>
              </b-col>
              <b-col cols="1">-</b-col>
              <b-col>
                <b-form-input id="endDate" name="endDate" type="text" placeholder="年-月-日"
                              data-date-format="yyyy-mm-dd"
                              data-view="date"
                              data-position="left"
                              data-start="2018-01-01"
                              data-end="2018-12-31"
                              size="sm"
                              v-date-picker="datePickerEndDateChangeEvent"></b-form-input>
              </b-col>
            </b-row>
          </b-form-group>


          <b-form-group horizontal class="col-md-3">
            <b-button type="reset" size="sm" variant="outline-secondary">清空</b-button>
            <b-button type="submit" size="sm" variant="outline-primary">查询</b-button>
            <b-button type="submit" size="sm" variant="outline-primary">新建</b-button>
          </b-form-group>
        </b-row>
      </b-form>
    </div>

    <!-- Grid container -->
    <div class="grid-container">

      <b-row>
        <b-col>
          <b-pagination :total-rows="totalRows" :per-page="perPage" v-model="currentPage" size="sm" align="right"/>
        </b-col>
      </b-row>

      <b-table
          :items="grid_items"
          :fields="grid_fields"
          head-variant="light"
          thead-tr-class="text-center"
          tbody-tr-class="text-center"
          outlined bordered small fixed hover>
        <template slot="number" slot-scope="row">
          {{row.index + 1}}
        </template>
        <template slot="actions" slot-scope="row">
          <!-- We use @click.stop here to prevent a 'row-clicked' event from also happening -->
          <b-button variant="outline-primary" size="sm"
                    @click.stop="actionCreateHandler(row.item, row.index, $event.target)" class="mr-1">
            create
          </b-button>
          <b-button variant="outline-secondary" size="sm"
                    @click.stop="actionSettingsHandler(row.item, row.index, $event.target)" v-if="row.item.is_admin">
            settings
          </b-button>
        </template>
      </b-table>

      <b-row>
        <b-col>
          <b-pagination :total-rows="totalRows" :per-page="perPage" v-model="currentPage" size="sm" align="left"
                        class="my-0"/>
        </b-col>
      </b-row>
    </div>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _IssueListComp = {
    name: 'IssueListComp',
    created: function () {
      this._searchData();
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
        datePickerStartDateChangeEvent(_value) {
          _self.condition_form.startDate = _value;
        },
        datePickerEndDateChangeEvent(_value) {
          _self.condition_form.endDate = _value;
        },
        // condition
        condition_form: {
          companyName: undefined,
          startDate: undefined,
          endDate: undefined
        },

        // pagination
        currentPage: 1,
        perPage: 10, // limit
        totalRows: 0,

        grid_fields: [
          {
            key: 'number',
            sortable: false,
            label: '序号'
            // ,variant: 'warning' 七种颜色
          },
          {
            key: 'company_name',
            label: '公司名称',
            sortable: false
          },
          {
            key: 'register_area',
            label: '注册地',
            sortable: true
          },
          {
            key: 'report_time',
            label: '上报时间',
            sortable: false
          },
          {
            key: 'report_type',
            label: '分类',
            sortable: true
          },
          {key: 'actions', label: '操作'}
        ],
        grid_items: []
      };
    },
    watch: {
      'currentPage'(_currentPage) {
        this.paginationHandler(_currentPage);
      }
    },
    methods: {
      conditionSubmitHandler(evt) {
        evt.preventDefault();
        this._searchData();
      },
      conditionResetHandler(evt) {
        evt.preventDefault();
        /* Reset our form values */
        this.condition_form.id = undefined;
        this.condition_form.name = undefined;
        this._searchData();
      },
      paginationHandler(_currentPage) {
        this._searchData();
      },
      actionCreateHandler(item, index, event) {
        console.log(item, index, event);
      },
      actionSettingsHandler(item, index, event) {
        console.log(item, index, event);
      },

      _searchData() {
        console.log('Grid view > _searchData >', this.condition_form, this.currentPage, this.perPage);
        const _self = this;
        // _sampleSearch (this.condition_form, this.currentPage, this.perPage).then(
        //   function (response) {
        //     console.log('Grid view > _searchData > response > ', response.data.result, response.data.total, response.data.is_last);
        //     _self.grid_items = response.data.result;
        //     _self.totalRows = response.data.total;
        //   }
        // ).catch(
        //   function (error) {
        //     console.log('Grid view > _searchData > error > ', error);
        //   }
        // )
        _self.grid_items = [
          {
            company_name: "测试公司1",
            register_area: "湖北省武汉市武昌区",
            report_time: "2018-07-30 10:00:00",
            report_type: "企业改制"
          },
          {
            company_name: "测试公司2",
            register_area: "湖北省武汉市武昌区",
            report_time: "2018-07-30 10:00:00",
            report_type: "企业改制"
          }]
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _IssueListComp;
</script>
