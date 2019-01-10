# Stock Capital IO - FE

## 开发篇 (12)

### 列表页面

#### 列表页面布局

- [默认列表页面](/sample/fe/grid)

- 检索区域

> 
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

- 列表区域

>

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

- 变量说明

> 条件表单

        condition_form

> 分页参数

        // pagination
        currentPage: 1, // 当前页面页码，初始默认统一为1
        perPage: 10, // 每一页limit， 统一为10
        totalRows: 0, // 总行数，依赖后端返回
        
> 表格展示约束（简单常用列举，可参考bootstrap-vue 文档）

         grid_fields: [
          {
            key: 'id',       // 数据KEY
            sortable: true,  // 是否排序
            label: 'ID',     // 表头Lable
            variant: 'info'  // 列背景色
          },
            ...
          {key: 'actions', label: 'Actions'}  // 操作栏，对应<template slot="actions" slot-scope="row">
        ]

> 特殊显示

        // 返回数据中嵌入，可控制数据行中每一列颜色
        _cellVariants: {id: 'primary', first_name: 'success', last_name: 'info', age: 'danger'}

> 过滤器

        // TODO
        可参考bootstrap-vue文档
        