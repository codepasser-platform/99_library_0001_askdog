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
          <div class="col-md-1 pt-2">上报时间：</div>
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
          <b-form-group horizontal class="col-md-4 text-right">
            <b-button size="sm" type="reset" variant="outline-secondary">清空</b-button>
            <b-button size="sm" type="submit" variant="outline-primary">查询</b-button>
            <b-button size="sm" variant="outline-primary" @click.stop="accountAddHandler()">新建</b-button>
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
  import {_listSearch} from "../../../service/api/report/report-api";

  const _List = {
    name: 'List',
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
          dateStartPicker: undefined,
          dateEndPicker: undefined
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
          {
            key: 'report_time',
            label: '上报时间',
            sortable: false
          },
          {key: 'current_node', label: '当前节点'},
          {key: 'status', label: '状态'},
          {key: 'actions', label: '操作'}
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
        this.condition_form.dateStartPicker = undefined;
        this.condition_form.dateEndPicker = undefined;
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
        //TODO
        //判断企业类型和id去跳转到不同页面
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
      accountAddHandler: function () {
        const _self = this;
        //TODO
        //判断企业类型去跳转到不同页面
        _self.$router.push({name: 'tutorial_add'});
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _List;
</script>
