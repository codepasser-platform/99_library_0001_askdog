"use strict";

import axios from 'axios';

// create an axios instance
const service = axios.create({
  baseURL: process.env.API_BASE,
  // request timeout
  timeout: 150000
});

// request interceptor
service.interceptors.request.use(
    function (config) {
      // TODO set accessToken with request header
      // config.headers['accessToken'] = getToken();
      // config.headers['refreshToken'] = getRefreshToken();
      // fixed GET request method caching problem
      config.headers['Cache-Control'] = 'no-cache';
      config.headers['Pragma'] = 'no-cache';
      return config
    },
    function (error) {
      //request error
      console.log(error);
      Promise.reject(error)
    }
);

// respone interceptor
service.interceptors.response.use(
    function (response) {
      return response
    },
    function (error) {
      let tipError = false;
      // TODO Reservation processing error response
      if (error && error.response) {
        switch (error.response.status) {
          case 400:
            tipError = false;
            break;
          case 401:
            if (error.response.data && (error.response.data.code === 'WEB_ACCESS_INVALID_TOKEN' || error.response.data.code === 'WEB_ACCESS_AUTH_REQUIRED')) {
              // TODO authorization require
              // clearAllCookie();
              // location.reload();
              tipError = false;
            }
            break;
          case 403:
            tipError = false;
            break;
          case 404:
            tipError = false;
            break;
          case 405:
            tipError = false;
            break;
          case 409:
            tipError = false;
            break;
          case 422:
            tipError = false;
            break;
          case 500:
            error.message = '服务器内部错误';
            tipError = true;
            break;
          default:
            error.message = '服务器内部错误';
            tipError = true;
            break;
        }
      }
      if (tipError) {
        console.error('request tip errors > ', {
          message: error.message,
          type: 'error',
          duration: 5 * 1000
        })
      }
      return Promise.reject(error)
    });

export default service
