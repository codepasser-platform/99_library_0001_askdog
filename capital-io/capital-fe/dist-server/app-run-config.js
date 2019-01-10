/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/**
 * <Configuration>
 */
// ~ 端口
var PORT = 7000;
// ~ 上下文路径
var CONTEXT_PATH = '/';
// ~ 应用相对路径
var RELATIVE_PATH = '../dist';
var DEBUG = true;

/**
 * <Library>
 */
var _HTTP = require('http');
var _URL = require('url');
var _FS = require('fs');
var _MIME_TYPE = require('./app-run-mime').types;
var _PATH = require('path');

if (!String.prototype.startWith) {
  String.prototype.startWith = function (str) {
    if (str === null || str === '' || this.length === 0 || str.length > this.length) {
      return false;
    }
    return this.substr(0, str.length) === str;
  };
}

var server = _HTTP.createServer(function (request, response) {
  var _requestUri = _URL.parse(request.url).pathname;
  var _requestPath = _requestUri;

  if (_requestUri.startWith(CONTEXT_PATH)) {
    _requestUri = _requestUri.replace(CONTEXT_PATH, '');
  }
  if (_requestUri.length === 0 || _requestUri === '/') {
    _requestUri = 'index.html'
  }
  var _realPath = _PATH.join(RELATIVE_PATH, _requestUri);    //这里设置自己的文件名称;

  if (DEBUG) {
    console.log('DEBUG: <Request> [' + _requestPath + '] ---> [' + _realPath + ']');
  }

  var ext = _PATH.extname(_realPath);
  ext = ext ? ext.slice(1) : 'unknown';

  _FS.exists(_realPath, function (exists) {

    if (!exists) {
      response.writeHead(404, {
        'Content-Type': 'text/plain'
      });

      var _errorLog = 'ERROR : This request URL [' + _requestPath + '] ---> [' + _realPath + '] was not found on this server.';
      console.error(_errorLog);
      response.write(_errorLog);
      response.end();

    } else {

      _FS.readFile(_realPath, 'binary', function (err, file) {
        if (err) {
          response.writeHead(500, {
            'Content-Type': 'text/plain'
          });
          response.end(err);
        } else {
          var contentType = _MIME_TYPE[ext] || 'text/plain';
          response.writeHead(200, {
            'Content-Type': contentType
          });
          response.write(file, 'binary');
          response.end();
        }
      });

    }
  });

});
server.listen(PORT);
console.log('Server startup completed, Startup configuration', '<PORT> [', PORT, '] <CONTEXT_PATH> [', CONTEXT_PATH, '] <RELATIVE_PATH> [', RELATIVE_PATH, '] <DEBUG>[', DEBUG, '].');