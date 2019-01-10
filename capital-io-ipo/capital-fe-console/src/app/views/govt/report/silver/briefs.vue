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
          <b-form-group horizontal class="col-md-4" label="标题：" label-for="title">
            <b-form-input id="title" name="title" type="text" v-model="condition_form.title"></b-form-input>
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

  const _SilverBriefs = {
    name: 'SilverBriefs',
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
        // condition
        condition_form: {
          title: undefined,
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
            key: 'title',
            label: '标题',
            sortable: false
          },
          {
            key: 'time',
            label: '时间',
            sortable: false
          },
          {key: 'brief_content', label: '简报内容'},
          {key: 'brief_attachment', label: '简报附件'},
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
        this.condition_form.title = undefined;
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
        this.$router.push({name: 'brief_edit', query: {id: '1'}});
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
  export default _SilverBriefs;
</script>
