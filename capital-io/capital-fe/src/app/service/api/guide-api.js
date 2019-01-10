"use strict";
import request from '../../../basement/service/request'

export function _guideContent(chapter, view) {
  return request({
    url: process.env.APP_BASE + '/static/guide/fe/' + chapter + '/' + view + '.md',
    method: 'get'
  })
}