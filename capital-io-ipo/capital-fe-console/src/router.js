import Vue from 'vue'
import VueRouter from 'vue-router'
/** Default layout **/
import _DefaultLayout from 'capital-fe/src/basement/layout/default'
/** Screen layout **/
import _ScreenLayout from 'capital-fe/src/basement/layout/screen'
/** Guide layout **/
import _GuideLayout from 'capital-fe/src/basement/layout/guide'

/** Guide layout **/
import _ConsoleLayout from 'capital-fe/src/basement/layout/console'
/** Guide layout **/
import _GovtLayout from 'capital-fe/src/basement/layout/govt'

/** Error Components **/
import _ERROR_401 from 'capital-fe/src/basement/error/401'
import _ERROR_404 from 'capital-fe/src/basement/error/404'
import _ERROR_500 from 'capital-fe/src/basement/error/500'

/** Laze loading mode **/
console.log('Main process.env > ', process.env);
const _import = require('capital-fe/src/basement/router/_import_' + (process.env.LAZE_MODE ? 'lazeload' : 'require'));

Vue.use(VueRouter);

export const _ConstantRouterMap = [
  // Sample
  {
    path: '/sample',
    name: 'sample',
    component: _DefaultLayout,
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

export const _AsyncRouterMap4Console = [
  //  Index
  {
    path: '',
    name: 'index',
    component: _ConsoleLayout,
    meta: {title: '首页', hidden: true},
    children: [
      {
        path: 'home',
        name: 'enterprise_home',
        component: _import('enterprise/index/home'),
        meta: {title: '企业端首页', hidden: false}
      }
    ]
  },
  // TODO add view router configuration for enterprise
  // 企业信息
  {
    path: '/company',
    name: 'company',
    component: _ConsoleLayout,
    meta: {title: '企业信息', hidden: true},
    children: [
      {
        path: 'profile',
        name: 'company_profile',
        component: _import('enterprise/company/companyProfile'),
        meta: {title: '我的企业信息', hidden: false}
      },
      {
        path: 'progress',
        name: 'company_audit_progress',
        component: _import('enterprise/company/companyAuditProgress'),
        meta: {title: '审核进度', hidden: false}
      }

    ]
  },
  // 企业台账
  {
    path: '/report',
    name: 'report',
    component: _ConsoleLayout,
    meta: {title: '企业台账', hidden: true},
    children: [
      {
        path: 'list',
        name: 'report_list',
        component: _import('enterprise/report/list'),
        meta: {title: '企业台账列表', hidden: false}
      },
      {
        path: 'csrc/add',
        name: 'csrc_add',
        component: _import('enterprise/report/csrc/add'),
        meta: {title: '在会企业周报新增', hidden: false}
      },
      {
        path: 'csrc/edit',
        name: 'csrc_edit',
        component: _import('enterprise/report/csrc/edit'),
        meta: {title: '在会企业周报编辑', hidden: false}
      },
      {
        path: 'tutorial/add',
        name: 'tutorial_add',
        component: _import('enterprise/report/tutorial/add'),
        meta: {title: '在辅企业周报新增', hidden: false}
      },
      {
        path: 'tutorial/edit',
        name: 'tutorial_edit',
        component: _import('enterprise/report/tutorial/edit'),
        meta: {title: '在辅企业周报编辑', hidden: false}
      },
      {
        path: 'overseas/add',
        name: 'overseas_add',
        component: _import('enterprise/report/overseas/add'),
        meta: {title: '境外上市后备企业月报新增', hidden: false}
      },
      {
        path: 'overseas/edit',
        name: 'overseas_edit',
        component: _import('enterprise/report/overseas/edit'),
        meta: {title: '境外上市后备企业月报编辑', hidden: false}
      },
      {
        path: 'golden/add',
        name: 'golden_add',
        component: _import('enterprise/report/golden/add'),
        meta: {title: '金种子企业月报新增', hidden: false}
      },
      {
        path: 'golden/edit',
        name: 'golden_edit',
        component: _import('enterprise/report/golden/edit'),
        meta: {title: '金种子企业月报编辑', hidden: false}
      },
      {
        path: 'silver/add',
        name: 'silver_add',
        component: _import('enterprise/report/silver/add'),
        meta: {title: '银种子企业季报新增', hidden: false}
      },
      {
        path: 'silver/edit',
        name: 'silver_edit',
        component: _import('enterprise/report/silver/edit'),
        meta: {title: '银种子企业季报编辑', hidden: false}
      },
      {
        path: 'issue',
        name: 'issue_list',
        component: _import('enterprise/report/issue/list'),
        meta: {title: '重大事项', hidden: true},
      },
      {
        path: 'issue/edit',
        name: 'issue_edit',
        component: _import('enterprise/report/issue/edit'),
        meta: {title: '新增', hidden: true}
      }
    ]
  },
  // 绿色通道
  {
    path: '/problem',
    name: 'problem',
    component: _ConsoleLayout,
    meta: {title: '绿色通道', hidden: true},
    children: [
      {
        path: 'list',
        name: 'problem_list',
        component: _import('enterprise/problem/list'),
        meta: {title: '问题列表', hidden: true}
      },
      {
        path: 'edit',
        name: 'problem_edit',
        component: _import('enterprise/problem/edit'),
        meta: {title: '问题申报', hidden: true}
      }
    ]
  },
  // 企业调研
  {
    path: '/research',
    name: 'research',
    component: _ConsoleLayout,
    meta: {title: '企业调研', hidden: true},
    children: [
      {
        path: 'list',
        name: 'research_list',
        component: _import('enterprise/todo'),
        meta: {title: '企业调研涵', hidden: false}
      }
    ]
  },
  // 企业培训
  {
    path: '/training',
    name: 'training',
    component: _ConsoleLayout,
    meta: {title: '企业培训服务', hidden: true},
    children: [
      {
        path: 'list',
        name: 'training_list',
        component: _import('enterprise/todo'),
        meta: {title: '企业培训活动', hidden: false}
      },
      {
        path: 'enroll',
        name: 'training_enroll',
        component: _import('enterprise/todo'),
        meta: {title: '我的培训报名', hidden: false}
      }
    ]
  },

  {path: '*', redirect: '/error/404', hidden: false}
];


export const _AsyncRouterMap4Govt = [

  //  Index
  {
    path: '',
    name: 'index',
    component: _GovtLayout,
    meta: {title: '首页', hidden: true},
    children: [
      {
        path: '/home',
        name: 'govt_home',
        component: _import('govt/index/home'),
        meta: {title: '政府端首页', hidden: false}
      }
    ]
  },
  // TODO add view router configuration for govt
  // 企业信息
  {
    path: '/company',
    name: 'company',
    component: _GovtLayout,
    meta: {title: '企业信息管理', hidden: true},
    children: [
      {
        path: 'list',
        name: 'company_list',
        component: _import('govt/company/companyList'),
        meta: {title: '企业信息列表', hidden: false}
      }
    ]
  },
  // 企业台账
  {
    path: '/report',
    name: 'report',
    component: _GovtLayout,
    meta: {title: '企业台账管理', hidden: true},
    children: [
      {
        path: 'csrc/list',
        name: 'csrc_list',
        component: _import('govt/report/csrc/list'),
        meta: {title: '在会企业周报列表', hidden: false}
      },
      {
        path: 'csrc/edit',
        name: 'csrc_edit',
        component: _import('govt/report/csrc/edit'),
        meta: {title: '在会企业周报编辑', hidden: false}
      },
      {
        path: 'csrc/briefs',
        name: 'csrc_briefs',
        component: _import('govt/report/csrc/briefs'),
        meta: {title: '在会企业周报简报列表', hidden: false}
      },
      {
        path: 'csrc/brief',
        name: 'csrc_brief',
        component: _import('govt/report/csrc/brief'),
        meta: {title: '在会企业周报简报', hidden: false}
      },
      {
        path: 'tutorial/list',
        name: 'tutorial_list',
        component: _import('govt/report/tutorial/list'),
        meta: {title: '在辅企业周报列表', hidden: false}
      },
      {
        path: 'tutorial/edit',
        name: 'tutorial_edit',
        component: _import('govt/report/tutorial/edit'),
        meta: {title: '在辅企业周报编辑', hidden: false}
      },
      {
        path: 'tutorial/briefs',
        name: 'tutorial_briefs',
        component: _import('govt/report/tutorial/briefs'),
        meta: {title: '在辅企业周报简报列表', hidden: false}
      },
      {
        path: 'tutorial/brief',
        name: 'tutorial_brief',
        component: _import('govt/report/tutorial/brief'),
        meta: {title: '在辅企业周报简报', hidden: false}
      },
      {
        path: 'overseas/list',
        name: 'overseas_list',
        component: _import('govt/report/overseas/list'),
        meta: {title: '境外上市后备企业月报列表', hidden: false}
      },
      {
        path: 'overseas/edit',
        name: 'overseas_edit',
        component: _import('govt/report/overseas/edit'),
        meta: {title: '境外上市后备企业月报编辑', hidden: false}
      },
      {
        path: 'overseas/briefs',
        name: 'overseas_briefs',
        component: _import('govt/report/overseas/briefs'),
        meta: {title: '境外上市后备企业月报简报列表', hidden: false}
      },
      {
        path: 'overseas/brief',
        name: 'overseas_brief',
        component: _import('govt/report/overseas/brief'),
        meta: {title: '境外上市后备企业月报简报', hidden: false}
      },
      {
        path: 'golden/list',
        name: 'golden_list',
        component: _import('govt/report/golden/list'),
        meta: {title: '金种子企业月报列表', hidden: false}
      },
      {
        path: 'golden/edit',
        name: 'golden_edit',
        component: _import('govt/report/golden/edit'),
        meta: {title: '金种子企业月报编辑', hidden: false}
      },
      {
        path: 'golden/briefs',
        name: 'golden_briefs',
        component: _import('govt/report/golden/briefs'),
        meta: {title: '金种子企业月报简报列表', hidden: false}
      },
      {
        path: 'golden/brief',
        name: 'golden_brief',
        component: _import('govt/report/golden/brief'),
        meta: {title: '金种子企业月报简报', hidden: false}
      },
      {
        path: 'silver/list',
        name: 'silver_list',
        component: _import('govt/report/silver/list'),
        meta: {title: '银种子企业季报列表', hidden: false}
      },
      {
        path: 'silver/edit',
        name: 'silver_edit',
        component: _import('govt/report/silver/edit'),
        meta: {title: '银种子企业季报编辑', hidden: false}
      },
      {
        path: 'silver/briefs',
        name: 'silver_briefs',
        component: _import('govt/report/silver/briefs'),
        meta: {title: '银种子企业季报简报列表', hidden: false}
      },
      {
        path: 'silver/brief',
        name: 'silver_brief',
        component: _import('govt/report/silver/brief'),
        meta: {title: '银种子企业季报简报', hidden: false}
      },
      {
        path: 'brief/list',
        name: 'brief_list',
        component: _import('govt/report/brief/list'),
        meta: {title: '市、州推进企业上市工作情况月度简报列表', hidden: false}
      },
      {
        path: 'brief/edit',
        name: 'brief_edit',
        component: _import('govt/report/brief/edit'),
        meta: {title: '市、州推进企业上市工作情况月度简报', hidden: false}
      },

      {
        path: 'issue',
        name: 'issue_list',
        component: _import('govt/todo'),
        meta: {title: '重大事项', hidden: false}
      },
    ]
  },
  // 绿色通道
  {
    path: '/problem',
    name: 'problem',
    component: _GovtLayout,
    meta: {title: '绿色通道', hidden: true},
    children: [
      {
        path: 'todoList',
        name: 'problem_todoList',
        component: _import('govt/problem/tabs'),
        meta: {title: '问题列表', hidden: true}
      }
    ]
  },
  // 企业调研
  {
    path: '/research',
    name: 'research',
    component: _GovtLayout,
    meta: {title: '企业调研管理', hidden: true},
    children: [
      {
        path: 'list',
        name: 'research_list',
        component: _import('govt/todo'),
        meta: {title: '企业调研涵', hidden: false}
      },
      {
        path: 'record',
        name: 'research_record',
        component: _import('govt/todo'),
        meta: {title: '调研记录', hidden: false}
      }
    ]
  },
  // 企业培训
  {
    path: '/training',
    name: 'training',
    component: _GovtLayout,
    meta: {title: '企业培训管理', hidden: true},
    children: [
      {
        path: 'list',
        name: 'training_list',
        component: _import('govt/todo'),
        meta: {title: '企业培训活动', hidden: false}
      },
      {
        path: 'enroll',
        name: 'training_enroll',
        component: _import('govt/todo'),
        meta: {title: '企业培训报名', hidden: false}
      }
    ]
  },

  {path: '*', redirect: '/error/404', hidden: false}
];
