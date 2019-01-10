<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid">

    <!-- Condition container -->
    <div class="condition-container">
      <b-form class="needs-validation" novalidate v-if="show" @submit="conditionSubmitHandler"
              @reset="conditionResetHandler">
        <b-row>
          <b-form-group horizontal class="col-md-4" label="企业名称：" label-for="companyName">
            <b-form-input id="companyName" name="companyName" type="text" v-model="condition_form.companyName"></b-form-input>
          </b-form-group>
          <div class="col-md-1 pt-2">上会时间：</div>
          <b-form-group class="col-md-2" label-for="dateStartPicker">
            <b-form-input id="dateStartPicker" name="dateStartPicker" type="text" placeholder="年/月/日"
                          data-date-format="yyyy-mm-dd"
                          data-view="date"
                          data-position="left"
                          v-date-picker="dateStartPickerChangeEvent"></b-form-input>
          </b-form-group>
          <span>-</span>
          <b-form-group class="col-md-2" label-for="dateEndPicker">
            <b-form-input id="dateEndPicker" name="dateEndPicker" type="text" placeholder="年/月/日"
                          data-date-format="yyyy-mm-dd"
                          data-view="date"
                          data-position="left"
                          v-date-picker="dateEndPickerChangeEvent"></b-form-input>
          </b-form-group>
        </b-row>
        <b-row>
          <b-form-group horizontal :label-cols="3" class="col-md-4" label="周报状态：" label-for="status">
            <b-form-select id="status"
                           :options="options"
                           v-model="condition_form.status">
            </b-form-select>
          </b-form-group>
          <b-form-group horizontal class="col-md-8 text-right">
            <b-button size="sm" type="reset" variant="outline-secondary">清空</b-button>
            <b-button size="sm" type="submit" variant="outline-primary">查询</b-button>
            <b-button size="sm" variant="outline-primary" @click.stop="exportHandler()">导出</b-button>
            <b-button size="sm" variant="outline-primary" @click.stop="actionPrintHandler()">打印</b-button>
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
        :items="account_items"
        :fields="account_fields"
        head-variant="light"
        outlined bordered small fixed hover>
        <template slot="actions" slot-scope="row">
          <!-- We use @click.stop here to prevent a 'row-clicked' event from also happening -->
          <b-button variant="outline-primary" size="sm"
                    @click.stop="actionEditHandler(row.item, row.index, $event.target)" class="mr-1">
            编辑
          </b-button>
          <b-button variant="outline-primary" size="sm"
                    @click.stop="actionFinishHandler(row.item, row.index, $event.target)" class="mr-1" v-if="row.item.is_finish">
            完成
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
  import {_listSearch} from "../../../../service/api/report/report-api";

  const _TutorialWeekList = {
    name: 'TutorialWeekList',
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
          _self.condition_form.dateStartPicker = _value;
        },
        dateEndPickerChangeEvent(_value) {
          _self.condition_form.dateEndPicker = _value;
        },
        // condition
        condition_form: {
          companyName: undefined,
          dateStartPicker: undefined,
          dateEndPicker: undefined,
          status: '1'
        },

        // pagination
        currentPage: 1,
        perPage: 10, // limit
        totalRows: 0,

        account_fields: [
          {
            key: 'index',
            sortable: false,
            label: '序号'
          },
          {
            key: 'company_name',
            label: '企业名称',
            sortable: false
          },
          {
            key: 'registration',
            label: '注册地',
            sortable: false
          },
          {key: 'tutorial_status', label: '辅导状态'},
          {key: 'solution', label: '存在的困难和问题'},
          {
            key: 'meeting_time',
            label: '何时报会',
            sortable: false
          },
          {key: 'company_contact', label: '公司联系人、联系方式'},
          {key: 'sponsors_contact', label: '保荐机构及联系人、联系方式'},
          {key: 'remarks', label: '备注'},
          {key: 'actions', label: '操作'}
        ],
        options: [
          {text: '已报', value: '1'},
          {text: '未报', value: '0'}
        ],
        account_items: [],
        show: true
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
        this.condition_form.companyName = undefined;
        this.condition_form.dateStartPicker = undefined;
        this.condition_form.dateEndPicker = undefined;
        this.condition_form.status = '1';
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        });
        this._searchData();
      },
      paginationHandler(_currentPage) {
        this._searchData();
      },
      actionEditHandler(item, index, event) {
        this.$router.push({name: 'tutorial_edit', query: {id: '1'}});
      },

      _searchData() {
        const _self = this;
        _listSearch(this.condition_form, this.currentPage, this.perPage).then(
          function (response) {
            _self.account_items = response.data.result;
            _self.totalRows = response.data.total;
          }
        ).catch(
          function (error) {
          }
        )
      },
      exportHandler(){
        //导出
      },
      actionPrintHandler(){
        //打印
      },
      actionFinishHandler(){
        //调用完成接口，结束流程
      }
    },
    components: {
    },
    computed: {
      ...mapGetters([])
    }
  };
  export default _TutorialWeekList;
</script>
