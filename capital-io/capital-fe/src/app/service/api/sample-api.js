"use strict";
import request from '../../../basement/service/request'

export function _sampleSearch(_conditions, _page, _size) {
  const pagination = {
    page: _page,
    size: _size
  };
  return request({
    url: '/api/sample/search',
    method: 'post',
    params: pagination,
    data: _conditions
  })
}