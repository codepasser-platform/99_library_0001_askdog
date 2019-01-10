"use strict";
// laze load
module.exports = file => () => import('@/app/views/' + file + '.vue');