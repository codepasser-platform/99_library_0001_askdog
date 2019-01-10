import Mock from 'mockjs'

const _problemTodoList = function () {
  return {
    total: 7,
    is_last: false,
    result: [
      {id:1, company_name:"测试公司1", register_area:"湖北省武汉市武昌区",report_person:"人员1", report_time:"2018-07-30 10:00:00",current_node:"武昌区金融办", status:"DISTRICT_WAITING"},
      {id:3, company_name:"测试公司3", register_area:"湖北省武汉市武昌区",report_person:"人员3", report_time:"2018-07-30 10:00:00",current_node:"武昌区金融办", status:"DISTRICT_WAITING"},
      {id:6, company_name:"测试公司6", register_area:"湖北省武汉市洪山区",report_person:"人员6", report_time:"2018-07-30 10:00:00",current_node:"洪山区金融办", status:"DISTRICT_WAITING"},
      {id:7, company_name:"测试公司6", register_area:"湖北省武汉市洪山区",report_person:"人员6", report_time:"2018-07-16 16:00:00",current_node:"洪山区金融办", status:"DISTRICT_DOING"},
      {id:8, company_name:"测试公司6", register_area:"湖北省武汉市洪山区",report_person:"人员6", report_time:"2018-07-16 16:00:00",current_node:"洪山区金融办", status:"DISTRICT_DOING"},
      {id:9, company_name:"测试公司7", register_area:"湖北省武汉市武昌区",report_person:"人员7", report_time:"2018-07-16 16:00:00",current_node:"武昌区金融办", status:"DISTRICT_WAITING"},
      {id:10, company_name:"测试公司8", register_area:"湖北省武汉市武昌区",report_person:"人员8", report_time:"2018-07-08 08:00:00",current_node:"武昌区金融办", status:"DISTRICT_WAITING"}
    ]
  }
};

const _problemHandledList = function () {
  return {
    total: 3,
    is_last: false,
    result: [
      {id:2, company_name:"测试公司2", register_area:"湖北省武汉市武昌区",report_person:"人员2", report_time:"2018-07-30 10:00:00",handled_node:"武昌区金融办", handled_time:"2018-08-1 10:00:00", status:"FINISH"},
      {id:4, company_name:"测试公司4", register_area:"湖北省武汉市武昌区",report_person:"人员4", report_time:"2018-07-30 10:00:00",handled_node:"武昌区金融办", handled_time:"2018-08-1 10:00:00", status:"FINISH"},
      {id:5, company_name:"测试公司5", register_area:"湖北省武汉市武昌区",report_person:"人员5", report_time:"2018-07-30 10:00:00",handled_node:"武昌区金融办", handled_time:"2018-08-1 10:00:00", status:"FINISH"}
    ]
  }
};

const _transferList = function () {
  return {
    total: 2,
    is_last: false,
    result: [
      {id:2, company_name:"测试公司9", register_area:"湖北省武汉市武昌区",report_person:"人员2", report_time:"2018-07-30 10:00:00",current_node:"武汉市金融办", trans_time:"2018-08-1 10:00:00", status:"CITY_WAITING"},
      {id:4, company_name:"测试公司10", register_area:"湖北省武汉市武昌区",report_person:"人员4", report_time:"2018-07-30 10:00:00",current_node:"武汉市金融办", trans_time:"2018-08-1 10:00:00", status:"CITY_DOING"},
    ]
  }
};

// Mock.mock( url, post/get , 返回的数据)；
Mock.mock(process.env.API_BASE + '/api/mock/problem/govt/todoList?page=1&size=10', 'post', _problemTodoList);
Mock.mock(process.env.API_BASE + '/api/mock/problem/govt/handledList?page=1&size=10', 'post', _problemHandledList);
Mock.mock(process.env.API_BASE + '/api/mock/problem/govt/transferList?page=1&size=10', 'post', _transferList);
