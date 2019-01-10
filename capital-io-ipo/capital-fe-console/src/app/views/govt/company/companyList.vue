<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <!-- Condition container -->
    <div class="condition-container">
      <b-form class="needs-validation" novalidate @submit="conditionSubmitHandler" @reset="conditionResetHandler">
        <b-row>
          <b-form-group horizontal label-size="sm" :label-cols="4" class="col-md-3" label="企业名称:"
                        label-for="conditionId">
            <b-form-input id="conditionId" name="conditionId" type="text" size="sm" v-model="condition_form.id"
                          placeholder="企业名称"></b-form-input>
          </b-form-group>
          <b-form-group horizontal label-size="sm" :label-cols="4" class="col-md-3" label="注册地:"
                        label-for="conditionName">
            <b-form-input id="conditionName" name="conditionName" type="text" size="sm" v-model="condition_form.name"
                          placeholder="注册地"></b-form-input>
          </b-form-group>
          <b-form-group horizontal label-size="sm" :label-cols="4" class="col-md-3" label="审核状态:"
                        label-for="conditionName">
            <b-form-select id="conditionAudit" :options="auditOptions" size="sm"
                           v-model="condition_form.conditionAudit"></b-form-select>
          </b-form-group>
        </b-row>
        <b-row>
          <b-form-group horizontal class="col-md-3" label-size="sm" :label-cols="4" label="申请时间:"
                        label-for="dateStartPicker">
            <b-form-input id="dateStartPicker" name="dateStartPicker" type="text" size="sm" placeholder="年/月/日"
                          data-date-format="yyyy-mm-dd"
                          data-view="date"
                          data-position="left"
                          v-date-picker="dateStartPickerChangeEvent"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-3" label-size="sm" :label-cols="4" label="-" label-for="dateEndPicker">
            <b-form-input id="dateEndPicker" name="dateEndPicker" type="text" size="sm" placeholder="年/月/日"
                          data-date-format="yyyy-mm-dd"
                          data-view="date"
                          data-position="left"
                          v-date-picker="dateEndPickerChangeEvent"></b-form-input>
          </b-form-group>

          <b-form-group horizontal class="col-md-6 text-right">
            <b-button type="reset" size="sm" variant="outline-secondary">清空</b-button>
            <b-button type="submit" size="sm" variant="outline-primary">查询</b-button>
            <b-button type="export" size="sm" variant="outline-primary">导出</b-button>
          </b-form-group>
        </b-row>
      </b-form>
    </div>

    <!-- Grid container -->
    <div class="grid-container">

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
          <b-button variant="outline-secondary" size="sm"
                    @click.stop="actionDetailHandler(row.item, row.index, $event.target)" class="mr-1">
            详细
          </b-button>
          <b-button variant="outline-primary" size="sm"
                    @click.stop="actionAuditHandler(row.item, row.index, $event.target)" class="mr-1">
            审核
          </b-button>
          <CompanyAudit ref="editor"></CompanyAudit>
        </template>
      </b-table>

      <b-row>
        <b-col>
          <b-pagination :total-rows="totalRows" :per-page="perPage" v-model="currentPage" size="sm" align="left"
                        class="my-0"/>
        </b-col>
      </b-row>
    </div>

    <!--<CompanyAudit ref="audit" @cancelEvent="cancelEventHandler" @confirmEvent="confirmEventHandler"></CompanyAudit>-->


  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import CompanyAudit from './companyAudit';

  const _CompanyList = {
    name: 'CompanyList',
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
        dateStartPickerChangeEvent(_value) {
          _self.condition_form.startDate = _value;
        },
        dateEndPickerChangeEvent(_value) {
          _self.condition_form.endDate = _value;
        },
        auditOptions: [
          {text: "已审核", value: "1"},
          {text: "待审核", value: "0"}
        ],
        // condition
        condition_form: {
          companyName: undefined,
          startDate: undefined,
          endDate: undefined,
          conditionAudit: undefined
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
            label: '企业名称',
            sortable: false
          },
          {
            key: 'register_area',
            label: '注册地',
            sortable: true
          },
          {
            key: 'report_time',
            label: '申请时间',
            sortable: false
          },
          {
            key: 'report_type',
            label: '来源',
            sortable: true
          },
          {
            key: 'audit_node',
            label: '当前审核结点',
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
      actionDetailHandler(item, index, event) {
        console.log(item, index, event);
      },
      actionAuditHandler(item, index, event) {
        console.log(item, index, event);
        debugger;
        this.$refs.editor.open();
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
            company_name: "武汉xxx软件开发有限公司",
            register_area: "湖北省武汉市武昌区",
            report_time: "2018-07-30",
            report_type: "省环保局",
            audit_node: "武昌区金融办"
          },
          {
            company_name: "武汉斯多克软件开发有限公司",
            register_area: "湖北省武汉市武昌区",
            report_time: "2018-07-30",
            report_type: "洪山区金融办",
            audit_node: "武昌区金融办"
          }]
      },
      cancelEventHandler(){
        console.log('cancelEventHandler');
      },
      confirmEventHandler(){
        console.log('confirmEventHandler');
      }
    },
    components: {
      CompanyAudit
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _CompanyList;
</script>
