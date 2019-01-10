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
          <b-form-group horizontal label-size="sm" :label-cols="4" class="col-md-3" label="数据ID:" label-for="conditionId">
            <b-form-input id="conditionId" name="conditionId" type="text" size="sm" v-model="condition_form.id" placeholder="Enter id"></b-form-input>
          </b-form-group>
          <b-form-group horizontal label-size="sm" :label-cols="4" class="col-md-3" label="数据名称:" label-for="conditionName">
            <b-form-input id="conditionName" name="conditionName" type="text" size="sm" v-model="condition_form.name" placeholder="Enter name"></b-form-input>
          </b-form-group>
          <b-form-group horizontal class="col-md-3">
            <b-button type="submit" size="sm" variant="outline-primary">Search</b-button>
            <b-button type="reset" size="sm" variant="outline-secondary">Reset</b-button>
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
          outlined bordered small fixed hover>
        <template slot="actions" slot-scope="row">
          <!-- We use @click.stop here to prevent a 'row-clicked' event from also happening -->
          <b-button variant="outline-primary" size="sm" @click.stop="actionCreateHandler(row.item, row.index, $event.target)" class="mr-1">
            create
          </b-button>
          <b-button variant="outline-secondary" size="sm" @click.stop="actionSettingsHandler(row.item, row.index, $event.target)" v-if="row.item.is_admin">
            settings
          </b-button>
        </template>
      </b-table>

      <b-row>
        <b-col>
          <b-pagination :total-rows="totalRows" :per-page="perPage" v-model="currentPage" size="sm" align="left" class="my-0"/>
        </b-col>
      </b-row>
    </div>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';
  import {_sampleSearch} from "../../../service/api/sample-api";


  const _GuideSampleGrid = {
    name: 'GuideSampleGrid',
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
      return {

        // condition
        condition_form: {
          id: undefined,
          name: undefined
        },

        // pagination
        currentPage: 1,
        perPage: 10, // limit
        totalRows: 0,

        grid_fields: [
          {
            key: 'id',
            sortable: true,
            label: 'ID',
            variant: 'info'
          },
          {
            key: 'first_name',
            label: 'FIRST_NAME',
            sortable: false
          },
          {
            key: 'last_name',
            label: 'LAST_NAME',
            sortable: false
          },
          {
            key: 'age',
            label: 'PERSON_AGE',
            sortable: false
          },
          {key: 'actions', label: 'Actions'}
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
        _sampleSearch(this.condition_form, this.currentPage, this.perPage).then(
            function (response) {
              console.log('Grid view > _searchData > response > ', response.data.result, response.data.total, response.data.is_last);
              _self.grid_items = response.data.result;
              _self.totalRows = response.data.total;
            }
        ).catch(
            function (error) {
              console.log('Grid view > _searchData > error > ', error);
            }
        )
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _GuideSampleGrid;
</script>