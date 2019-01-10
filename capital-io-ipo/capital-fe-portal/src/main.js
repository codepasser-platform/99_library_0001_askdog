"use strict";
/** import font-awesome **/
import 'font-awesome/css/font-awesome.css'
/** import bootstrap-vue **/
import 'bootstrap/scss/bootstrap.scss'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'capital-fe/src/style/extends.scss'
import 'capital-fe/src/style/app.scss'
import BootstrapVue from 'bootstrap-vue'
import Vue from 'vue'
import axios from 'axios';
// Javascript extends
import 'capital-fe/src/basement/utils/extends'
/* Directive **/
import 'capital-fe/src/basement/directives';
/* Components **/
import 'capital-fe/src/basement/components'
/* Store **/
import store from 'capital-fe/src/basement/service/store'
/** Application **/
import App from './app'
import router from './router'
// Permission handler
import './app/security/permission';

if (process.env.MOCK_MODE) {
  /* Mock api **/
  require('./app/service/mock')
}

Vue.config.productionTip = false;
Vue.use(BootstrapVue);
Vue.prototype.axios = axios;

/* eslint-disable no-new */
new Vue({
  el: '#application',
  router,
  store,
  components: {App}
});
