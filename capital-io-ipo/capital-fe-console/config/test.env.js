'use strict';
const merge = require('webpack-merge');
const devEnv = require('./dev.env');

module.exports = merge(devEnv, {
  NODE_ENV: '"testing"',
  APP_BASE: '"http://localhost:7002"',
  API_BASE: '"http://localhost:9002"',
  LAZE_MODE: true,
  MOCK_MODE: false
});
