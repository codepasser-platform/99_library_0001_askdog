import request from 'capital-fe/src/basement/service/request'

export function _todoListSearch(_conditions, _page, _size) {
  const pagination = {
    page: _page,
    size: _size
  };
  return request({
    url: '/api/mock/problem/govt/todoList',
    method: 'post',
    params: pagination,
    data: _conditions
  })
}

export function _handledListSearch(_conditions, _page, _size) {
  const pagination = {
    page: _page,
    size: _size
  };
  return request({
    url: '/api/mock/problem/govt/handledList',
    method: 'post',
    params: pagination,
    data: _conditions
  })
}

export function _transferredListSearch(_conditions, _page, _size) {
  const pagination = {
    page: _page,
    size: _size
  };
  return request({
    url: '/api/mock/problem/govt/transferList',
    method: 'post',
    params: pagination,
    data: _conditions
  })
}
