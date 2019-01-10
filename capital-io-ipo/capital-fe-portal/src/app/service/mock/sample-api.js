import Mock from 'mockjs'

const _sample = function (options) {
  return {
    username: 'tom',
    age: 15,
    is_admin: false
  };
};
// Mock.mock( url, post/get , 返回的数据)；
Mock.mock(process.env.API_BASE + '/api/mock/sample', 'post', _sample);