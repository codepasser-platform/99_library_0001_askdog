"use strict";
import Vue from 'vue';

import Toast from './message/toast'
import Confirm from './message/confirm'

const _Toast = {
  install: function (Vue) {
    console.log('Components Toast -> install');
    Vue.component('Toast', Toast)
  }
};

const _Confirm = {
  install: function (Vue) {
    console.log('Components Confirm -> install');
    Vue.component('Confirm', Confirm)
  }
};

Vue.use(_Toast);
Vue.use(_Confirm);
