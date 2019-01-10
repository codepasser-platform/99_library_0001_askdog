"use strict";
import Mock from 'mockjs'

const _signIn = function (options) {
  return {
    username: 'tom',
    age: 15,
    is_admin: false
  };
};

const _me = function (options) {
  return {
    username: 'tom',
    age: 15,
    is_admin: false
  };
};
// Mock.mock( url, post/get , 返回的数据)；
Mock.mock(process.env.API_BASE + '/api/sign/in', 'post', _signIn);
Mock.mock(process.env.API_BASE + '/api/auth/me?token=123123', 'get', _me);