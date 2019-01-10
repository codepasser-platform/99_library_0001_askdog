import Vue from 'vue'
import VueRouter from 'vue-router'
/** Default layout **/
import _DefaultLayout from 'capital-fe/src/basement/layout/default'
/** Screen layout **/
import _ScreenLayout from 'capital-fe/src/basement/layout/screen'
/** Guide layout **/
import _GuideLayout from 'capital-fe/src/basement/layout/guide'
/** Error Components **/
import _ERROR_401 from 'capital-fe/src/basement/error/401'
import _ERROR_404 from 'capital-fe/src/basement/error/404'
import _ERROR_500 from 'capital-fe/src/basement/error/500'
/** Sample Components**/
import _Dashboard from 'capital-fe/src/app/views/sample/index'
import _Hello from 'capital-fe/src/app/views/sample/hello'

/** Laze loading mode **/
console.log('Main process.env > ', process.env);
const _import = require('capital-fe/src/basement/router/_import_' + (process.env.LAZE_MODE ? 'lazeload' : 'require'));

Vue.use(VueRouter);

export const _ConstantRouterMap = [


  // Sample
  {
    path: '/sample',
    name: 'sample',
    component: _ScreenLayout,
    meta: {title: 'Sample', hidden: true},
    children: [
      {
        path: 'index',
        name: 'sample_index',
        component: _import('sample/index'),
        meta: {title: 'Sample', hidden: false}
      },
      {
        path: 'form',
        name: 'sample_form',
        component: _import('sample/form-inline'),
        meta: {title: 'Form', hidden: false}
      },
      {
        path: 'tinymce',
        name: 'sample_tinymce',
        component: _import('sample/tinymce'),
        meta: {title: 'Tinymce', hidden: false}
      }
    ]
  },
  {
    path: '/guide',
    name: 'guide',
    component: _GuideLayout,
    meta: {title: 'Guide', hidden: false},
    children: []
  },

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
    component: _ScreenLayout,
    meta: {title: '首页', hidden: false},
    children: [
      {
        path: 'home',
        name: 'home',
        component: _import('index/home'),
        meta: {title: '首页', hidden: false}
      },
      {
        path: 'policy',
        name: 'policy',
        component: _import('index/policy'),
        meta: {title: '政策', hidden: false}
      },
      {
        path: 'policy-content',
        name: 'policy-content',
        component: _import('index/policy-content'),
        meta: {title: '政策解读', hidden: false}
      },
      {
        path: 'work-dynamic',
        name: 'work-dynamic',
        component: _import('index/work-dynamic'),
        meta: {title: '工作动态', hidden: false}
      },
      {
        path: 'dashboard',
        name: 'dashboard',
        component: _Dashboard,
        meta: {title: 'Dashboard', hidden: false}
      },
      {
        path: 'hello',
        name: 'hello',
        component: _Hello,
        meta: {title: 'Hello-World', hidden: false}
      }
    ]
  },

  {path: '*', redirect: '/error/404', hidden: false}

];
