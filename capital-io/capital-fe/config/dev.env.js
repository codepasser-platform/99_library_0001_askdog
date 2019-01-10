'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  APP_BASE: '"http://localhost:7000"',
  API_BASE: '"http://localhost:9001"',
  LAZE_MODE: true,
  MOCK_MODE: true
});
