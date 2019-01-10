import request from 'capital-fe/src/basement/service/request'

export function _indexContent(view) {
  return request({
    url: process.env.APP_BASE + '/static/sample/' + view + '.md',
    method: 'get'
  })
}

export function _sample(username, password) {
  const data = {
    username: username,
    password: password
  };
  return request({
    url: '/api/mock/sample',
    method: 'post',
    data: data
  })
}