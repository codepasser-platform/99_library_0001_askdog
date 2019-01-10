"use strict";
/** import font-awesome **/
import 'font-awesome/css/font-awesome.css'
/** import bootstrap-vue **/
import 'bootstrap/scss/bootstrap.scss'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import './style/extends.scss'
import './style/app.scss'
import BootstrapVue from 'bootstrap-vue'
import Vue from 'vue'
import axios from 'axios';
/* Javascript extends **/
import './basement/utils/extends'
/* Directive **/
import './basement/directives';
/* Components **/
import './basement/components'
/* Store **/
import store from './basement/service/store'
/** Application **/
import App from './app'
import router from './router'
/* Permission **/
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
