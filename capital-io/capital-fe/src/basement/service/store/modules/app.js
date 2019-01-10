"use strict";
const app = {
  state: {
    breadcrumb: {
      items: []
    }
  },
  mutations: {
    SET_BREADCRUMB: (state, breadcrumb) => {
      state.breadcrumb = breadcrumb;
    }
  },
  actions: {
    setBreadcrumb({commit}, breadcrumb) {
      return new Promise(function (resolve, reject) {
        commit('SET_BREADCRUMB', breadcrumb);
        resolve(breadcrumb);
      });
    }
  }
};

export default app
