"use strict";
import request from '../../../basement/service/request'

export function _signIn(username, password) {
  const data = {
    username: username,
    password: password
  };
  return request({
    url: '/api/sign/in',
    method: 'post',
    data: data
  })
}

export function _me(token) {
  const param = {
    token: token
  };
  return request({
    url: '/api/auth/me',
    method: 'get',
    params: param
  })
}