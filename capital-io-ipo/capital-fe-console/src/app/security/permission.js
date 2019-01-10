"use strict";
import _Router from '../../router'

import {
  _ConstantRouterMap,
  _AsyncRouterMap4Console,
  _AsyncRouterMap4Govt
} from '../../router'

import store from 'capital-fe/src/basement/service/store'

_Router.beforeEach((to, from, next) => {
  console.log('Permission router > ', to, from);
  _routerInterceptor(to, from, next);
});

_Router.afterEach((to, from) => {
  // console.log('permission afterEach > ', to, from);
});

const _routerInterceptor = function (to, from, next) {
  if (!_Router.initialized) {
    _initializeRouter(to, from, next);
  } else {
    // settings breadcrumb
    _breadcrumbHandler(to, from);
    next();
  }
};

const _initializeRouter = function (to, from, next) {

  if (to.query && to.query.identity && to.query.identity === 'ENTERPRISE') {
    _Router.addRoutes(_AsyncRouterMap4Console);
  } else if (to.query && to.query.identity && to.query.identity === 'GOVT') {
    _Router.addRoutes(_AsyncRouterMap4Govt);
  }

  console.log('Permission router > initialize > ', _ConstantRouterMap, _AsyncRouterMap4Console, _AsyncRouterMap4Govt);
  _Router.initialized = true;
  // TODO LOGIN
  if (to.path) {
    next({path: to.path});
  } else {
    next({path: '/'});
  }
};

const _breadcrumbHandler = function (to, from) {
  if (to.matched && to.matched.length > 0) {
    store.dispatch('setBreadcrumb', {items: to.matched}).then(
        (_response) => {
          // ignore
        }, (_reason) => {
          // ignore
        }
    );
  } else {
    store.dispatch('setBreadcrumb', {items: []}).then(
        (_response) => {
          // ignore
        }, (_reason) => {
          // ignore
        }
    );
  }
};