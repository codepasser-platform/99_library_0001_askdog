import request from 'capital-fe/src/basement/service/request'

export function _listSearch(_conditions, _page, _size) {
  const pagination = {
    page: _page,
    size: _size
  };
  return request({
    url: '/api/report/search',
    method: 'post',
    params: pagination,
    data: _conditions
  })
}

export function _addSave(_data) {
  return request({
    url: '/api/report/add-save',
    method: 'post',
    data: _data
  })
}

export function _getDetail(_id) {
  const param = {
    id: _id
  };
  return request({
    url: '/api/report/detail',
    method: 'get',
    params: param
  })
}

export function _updateSave(_data) {
  return request({
    url: '/api/report/update-save',
    method: 'post',
    data: _data
  })
}

export function _importBrief(_data) {
  return request({
    url: `/api/report/import`,
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    timeout: 1000 * 60 * 30,
    data: _data
  })
}
