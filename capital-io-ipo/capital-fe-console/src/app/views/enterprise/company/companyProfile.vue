<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
</style>

<template>
  <div class="view-container container-fluid">
    <div class="title-container container">
      <h5 class="my-0">
        <span class="badge badge-secondary">企业基本信息</span>
      </h5>
    </div>

    <div class="form-container container">
        <b-form class="needs-validation" novalidate @reset="onResetVue" v-if="showVue" ref="form_vue">
          <b-row>
            <b-form-group horizontal class="col-md-6" label="企业名称" label-for="name" valid-feedback="valid-feedback">
              <b-form-input id="name" name="name" type="text" v-model="company.name" placeholder="输入企业名称"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-6" label="注册地" label-for="passwordVue" valid-feedback="valid-feedback">
              <b-form-select class="col-md-2" id="selectorSheng" :options="options" v-model="company.selector"></b-form-select>
              <b-form-select class="col-md-2" id="selectorShi" :options="options" v-model="company.selector"></b-form-select>
              <b-form-select class="col-md-2" id="selectorQu" :options="options" v-model="company.selector"></b-form-select>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-6" label="成立时间" label-for="datePicker" valid-feedback="valid-feedback">
              <b-form-input id="datePicker" name="datePicker" type="text" placeholder="请选择时间" data-date-format="yyyy-mm-dd" data-view="date" data-position="left" data-start="2018-01-01" data-end="2018-12-31" v-date-picker="datePickerChangeEvent"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-6" label="股改时间" label-for="datePicker" valid-feedback="valid-feedback">
              <b-form-input id="datePicker" name="datePicker" type="text" placeholder="请选择时间" data-date-format="yyyy-mm-dd" data-view="date" data-position="left" data-start="2018-01-01" data-end="2018-12-31" v-date-picker="datePickerChangeEvent"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="行业大类" label-for="selectorVue" valid-feedback="valid-feedback">
              <b-form-select id="selectorL" :options="options" v-model="company.selector"></b-form-select>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="行业小类" label-for="selectorVue" valid-feedback="valid-feedback">
              <b-form-select id="selectorS" :options="options" v-model="company.selector">
              </b-form-select>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="战略新兴产业" label-for="selectorVue" valid-feedback="valid-feedback">
              <b-form-select id="selectorN" :options="options" v-model="company.selector">
              </b-form-select>
            </b-form-group>
            <b-col md="3"></b-col>

            <b-form-group horizontal class="col-md-2" label-cols="0" valid-feedback="valid-feedback">
              <b-form-checkbox id="checkerXinSanBan" v-model="company.checker">是否新三板企业
              </b-form-checkbox>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="0" valid-feedback="valid-feedback">
              <b-form-checkbox id="checkerGaoXin" v-model="company.checker">是否高新技术企业
              </b-form-checkbox>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-6" label="通讯地址" label-for="name" valid-feedback="valid-feedback">
              <b-form-input id="address" name="address" type="text" v-model="company.address" placeholder="输入通讯地址"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="法人代表" label-for="name" valid-feedback="valid-feedback">
              <b-form-input id="address" name="address" type="text" v-model="company.address" placeholder="输入姓名"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="职务" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="输入职务"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="座机" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="座机号码"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="手机" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="手机号码"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="联系人" label-for="name" valid-feedback="valid-feedback">
              <b-form-input id="address" name="address" type="text" v-model="company.address" placeholder="输入姓名"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="职务" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="输入职务"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="手机" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="tel" v-model="company.address" placeholder="输入手机号码"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="邮箱" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="mail" v-model="company.address" placeholder="输入邮箱地址"></b-form-input>
            </b-form-group>
          </b-row>

          <span class="badge badge-secondary">企业股本</span>
          <b-row>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="股东性质" label-for="selectorVue" valid-feedback="valid-feedback">
              <b-form-select id="selectorG" :options="options" v-model="company.selector">
              </b-form-select>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="股东名称" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="输入股东名称"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="持股比例" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="股东持股比例"></b-form-input>
            </b-form-group>
            <b-col>
              <p>添加图标??
                <span class="glyphicon glyphicon-plus-sign"></span>
              </p>
            </b-col>
          </b-row>

          <h5 class="my-0">
            <span class="badge badge-secondary">上市筹备情况</span>
          </h5>
          <b-row>
            <b-form-group horizontal class="col-md-12" label-cols="2" label="上市进展" label-for="selectorVue">
              <b-form-checkbox-group id="checkerXinSanBan" plain v-model="company.checker" :options="checkboxGroupOptions1" />
            </b-form-group>
          </b-row>
          <b-row>
            <b-form-group horizontal class="col-md-6" label="拟上市时间" label-for="datePicker" valid-feedback="valid-feedback">
              <b-form-input id="datePicker" name="datePicker" type="text" placeholder="请选择时间" data-date-format="yyyy-mm" data-view="date" data-position="left" data-start="2018-01" data-end="2018-12" v-date-picker="datePickerChangeEvent"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-6" label="拟上市地点" label-for="selectorVue" valid-feedback="valid-feedback">
              <b-form-select id="selectorN" :options="ipoAreaOptions" v-model="company.selector">
              </b-form-select>
            </b-form-group>
          </b-row>

          <h5 class="my-0">
            <span class="badge badge-secondary">中介机构</span>
          </h5>

          <b-row>
            <b-form-group horizontal class="col-md-6" label="券商" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder=""></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="签字保代" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="签字保代"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="手机" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="手机"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="项目负责人" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="项目负责人"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label-cols="6" label="手机" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="手机"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-6" label="会计事务所" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="会计事务所"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label="会计师" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="会计师"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label="手机" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="手机"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-6" label="律师事务所" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="律师事务所"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label="律师" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="律师"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-3" label="手机" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="手机"></b-form-input>
            </b-form-group>
          </b-row>

          <h5 class="my-0">
            <span class="badge badge-secondary">企业经营情况</span>
          </h5>

          <b-row>
            <b-form-group horizontal class="col-md-6" label="主营业务" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="主营业务"></b-form-input>
            </b-form-group>
          </b-row>
          <span>主要财务指标(万元)</span>

          <b-row>
            <b-form-group horizontal class="col-md-2 text-center" label-cols="6" label="2015年" label-for="name" valid-feedback="valid-feedback">
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="总资产" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="总资产"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="净资产" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="净资产"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="销售收入" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="销售收入"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="净利润" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="净利润"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-2 text-center" label-cols="6" label="2016年" label-for="name" valid-feedback="valid-feedback">
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="总资产" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="总资产"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="净资产" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="净资产"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="销售收入" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="销售收入"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="净利润" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="净利润"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-2 text-center" label-cols="6" label="2017年" label-for="name" valid-feedback="valid-feedback">
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="总资产" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="总资产"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="净资产" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="净资产"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="销售收入" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="销售收入"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="净利润" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="净利润"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-2 text-center" label-cols="6" label="2018年<br/>预测" label-for="name" valid-feedback="valid-feedback">
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="总资产" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="总资产"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="净资产" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="净资产"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="销售收入" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="销售收入"></b-form-input>
            </b-form-group>
            <b-form-group horizontal class="col-md-2 text-right" label-cols="6" label="净利润" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="净利润"></b-form-input>
            </b-form-group>
          </b-row>

          <h5 class="my-0">
            <span class="badge badge-secondary">金融支持情况</span>
          </h5>
          <b-row>
            <b-form-group horizontal class="col-md-6" label-cols="4" label="主要融资途径" label-for="selectorVue">
              <b-form-checkbox-group id="checkerRongZi" plain v-model="company.checker" :options="checkboxGroupOptions2" />
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-6" label-cols="4" label="政策性资金支持" label-for="selectorVue">
              <b-form-checkbox-group id="checkerRongZi" plain v-model="company.checker" :options="yesOrNoOptions" />
            </b-form-group>
            <b-form-group horizontal class="col-md-6" label-cols="2" label="资金名称" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="资金名称"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-6" label-cols="4" label="引导基金支持" label-for="selectorVue">
              <b-form-checkbox-group id="checkerRongZi" plain v-model="company.checker" :options="yesOrNoOptions" />
            </b-form-group>
            <b-form-group horizontal class="col-md-6" label-cols="2" label="基金名称" label-for="name" valid-feedback="valid-feedback">
              <b-form-input name="address" type="text" v-model="company.address" placeholder="基金名称"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-12" label-cols="2" label="拟融资用途" label-for="name" valid-feedback="valid-feedback">
              <b-form-input id="name" name="name" type="text" v-model="company.name" placeholder="输入拟融资用途"></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-6" label-cols="4" label="拟融资方式" label-for="selectorVue">
              <b-form-checkbox-group id="checkerRongZi" plain v-model="company.checker" :options="checkboxGroupOptions2" />
            </b-form-group>
            <b-form-group horizontal class="col-md-6" label-cols="2" label="其他" label-for="name" valid-feedback="valid-feedback">
              <b-form-input id="name" name="name" type="text" v-model="company.name" placeholder=""></b-form-input>
            </b-form-group>
          </b-row>

          <b-row>
            <b-form-group horizontal class="col-md-6" label-cols="4" label="并购重组需求" label-for="selectorVue">
              <b-form-checkbox-group id="checkerRongZi" plain v-model="company.checker" :options="checkboxGroupOptions3" />
            </b-form-group>
            <b-form-group horizontal class="col-md-6" label-cols="2" label="其他" label-for="name" valid-feedback="valid-feedback">
              <b-form-input id="name" name="name" type="text" v-model="company.name" placeholder=""></b-form-input>
            </b-form-group>
          </b-row>

          <h5 class="my-0">
            <span class="badge badge-secondary">企业基本情况介绍</span>
          </h5>
		<small class="form-text text-muted">历史沿革、生产及产品、主要管理团队、行业地位、市场发展与空间、知识产权、公司优势、企业荣誉等</small>
          <b-form-group label="" label-for="textareaVue" valid-feedback="valid-feedback">
            <b-form-textarea id="textareaVue" v-model="company.textarea" placeholder="" rows="2" maxlength="140">
            </b-form-textarea>
          </b-form-group>

          <h5 class="my-0">
            <span class="badge badge-secondary">企业在改制上市中面临的主要问题和困难</span>
          </h5>
          <b-form-group label="" label-for="textareaVue" valid-feedback="valid-feedback">
            <b-form-textarea id="textareaVue" v-model="company.textarea" placeholder="" rows="2" maxlength="140">
            </b-form-textarea>
          </b-form-group>

          <b-form-group class="text-center">
            <b-button type="submit" variant="outline-primary">Submit</b-button>
            <b-button type="reset" variant="outline-secondary">Reset</b-button>
          </b-form-group>
        </b-form>
      </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";

const _CompanyProfile = {
    name: "CompanyProfile",
    created: function() {},
    updated: function() {},
    mounted: function() {},
    activated: function() {},
    data: function() {
        return {
            company: {
                email: undefined,
                password: undefined,
                selector: undefined,
                file: undefined,
                textarea: undefined,
                custom: undefined,
                radio: "NORMAL",
                checker: false,
                address: undefined
            },
            options: [
                { text: "请选择", value: undefined },
                { text: "Carrots", value: "CARROTS" },
                { text: "Beans", value: "BEANS" },
                { text: "Tomatoes", value: "TOMATOES" },
                { text: "Corn", value: "CORN" }
            ],
            checkboxGroupOptions1: [
                { text: "未启动", value: "0" },
                { text: "开始筹备", value: "1" },
                { text: "尽职调查", value: "2" },
                { text: "股改", value: "3" },
                { text: "报辅导", value: "4" },
                { text: "撤辅导", value: "5" },
                { text: "报会", value: "6" },
                { text: "报会撤材料", value: "7" },
                { text: "过会待发", value: "8" },
                { text: "上会被否", value: "9" }
            ],
            checkboxGroupOptions2: [
                { text: "银行贷款", value: "0" },
                { text: "债务融资", value: "1" },
                { text: "股权融资", value: "2" }
            ],
            checkboxGroupOptions3: [
                { text: "现金收购资产", value: "0" },
                { text: "现金收购股权", value: "1" },
                { text: "股权收购资产", value: "2" },
                { text: "股权置换", value: "3" },
                { text: "资产置换", value: "4" },
            ],

            yesOrNoOptions:[
                { text: "是", value: "1" },
                { text: "否", value: "0" }
            ],
            ipoAreaOptions: [
                { text: "主板", value: "0" },
                { text: "中小板", value: "1" },
                { text: "创业板", value: "2" },
                { text: "境外上会", value: "3" }
            ],

            showVue: true
        };
    },
    methods: {},
    components: {},
    computed: {
        ...mapGetters([])
    }
};
export default _CompanyProfile;
</script>
