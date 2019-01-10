import Vue from 'vue'
import VueRouter from 'vue-router'
/** Default layout **/
import _DefaultLayout from './basement/layout/default'
/** Screen layout **/
import _ScreenLayout from './basement/layout/screen'
/** Console layout **/
import _ConsoleLayout from './basement/layout/console'
/** Govt layout **/
import _GovtLayout from './basement/layout/govt'
/** Guide layout **/
import _GuideLayout from './basement/layout/guide'
/** Error Components**/
import _ERROR_401 from './basement/error/401'
import _ERROR_404 from './basement/error/404'
import _ERROR_500 from './basement/error/500'

/** Laze loading mode **/
console.log('Main process.env > ', process.env);
const _import = require('./basement/router/_import_' + (process.env.LAZE_MODE ? 'lazeload' : 'require'));

Vue.use(VueRouter);

export const _ConstantRouterMap = [
  // Error
  {
    path: '/error',
    name: 'error',
    component: _ScreenLayout,
    meta: {title: 'ERROR', hidden: true},
    children: [
      {path: '401', name: '401', component: _ERROR_401, meta: {title: '401', hidden: true}},
      {path: '404', name: '404', component: _ERROR_404, meta: {title: '404', hidden: true}},
      {path: '500', name: '500', component: _ERROR_500, meta: {title: '500', hidden: true}}
    ]
  }
];

const _Router = new VueRouter({
  base: '/',
  mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: _ConstantRouterMap
});

export default _Router;

export const _AsyncRouterMap = [
  //  Index
  {
    path: '',
    name: 'index',
    component: _DefaultLayout,
    meta: {title: '首页', hidden: false},
    children: [
      {
        path: 'dashboard',
        name: 'dashboard',
        component: _import('sample/index'),
        meta: {title: 'Dashboard', hidden: false}
      },
      {
        path: 'hello',
        name: 'hello',
        component: _import('sample/hello'),
        meta: {title: 'Hello-World', hidden: false}
      }
    ]
  },
  //  Guide
  {
    path: '/guide',
    name: 'guide',
    component: _GuideLayout,
    meta: {title: 'Guide', hidden: true},
    children: [
      {
        path: 'todo',
        name: 'guide_todo',
        component: _import('guide/todo'),
        meta: {title: 'TODO', hidden: false}
      },
      {
        path: 'fe/:chapter/:view',
        name: 'guide_fe',
        component: _import('guide/fe/index'),
        meta: {title: 'Docs', hidden: false}
      }
    ]
  },
  //  Sample
  {
    path: '/sample',
    name: 'sample',
    component: _GuideLayout,
    meta: {title: 'Sample', hidden: true},
    children: [
      {
        path: 'fe/form',
        name: 'fe_sample_form',
        component: _import('sample/fe/form'),
        meta: {title: 'Form', hidden: false}
      },
      {
        path: 'fe/form/grid',
        name: 'fe_sample_form_grid',
        component: _import('sample/fe/form-grid'),
        meta: {title: 'Form-Grid', hidden: false}
      },
      {
        path: 'fe/form/inline',
        name: 'fe_sample_form_inline',
        component: _import('sample/fe/form-inline'),
        meta: {title: 'Form-Inline', hidden: false}
      },
      {
        path: 'fe/grid',
        name: 'fe_sample_grid',
        component: _import('sample/fe/grid'),
        meta: {title: 'Grid-View', hidden: false}
      },
      {
        path: 'fe/tabs',
        name: 'fe_sample_tabs',
        component: _import('sample/fe/tabs'),
        meta: {title: 'Tabs-View', hidden: false}
      },
      {
        path: 'fe/modal',
        name: 'fe_sample_modal',
        component: _import('sample/fe/modal'),
        meta: {title: 'Modal', hidden: false}
      },
      {
        path: 'fe/date-picker',
        name: 'fe_sample_date_pick',
        component: _import('sample/fe/date-picker'),
        meta: {title: 'DatePicker', hidden: false}
      },
      {
        path: 'fe/tinymce',
        name: 'fe_sample_tinymce',
        component: _import('sample/fe/tinymce'),
        meta: {title: 'Tinymce', hidden: false}
      },
      {
        path: 'fe/mavon',
        name: 'fe_sample_mavon',
        component: _import('sample/fe/mavon'),
        meta: {title: 'Mavon', hidden: false}
      }
    ]
  },
  //  Portal
  {
    path: '/portal',
    name: 'portal',
    component: _ScreenLayout,
    meta: {title: 'Portal', hidden: true},
    children: []
  },
  //  Console
  {
    path: '/console',
    name: 'console',
    component: _ConsoleLayout,
    meta: {title: 'Console', hidden: true},
    children: []
  },
  //  Govt
  {
    path: '/govt',
    name: 'govt',
    component: _GovtLayout,
    meta: {title: 'Console', hidden: true},
    children: []
  },
  {path: '*', redirect: '/error/404', hidden: true}
];