"use strict";

/* Console */
if (!window.console || process.env.NODE_ENV === 'production') {
  var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml", "group", "groupEnd", "time",
    "timeEnd", "count", "trace", "profile", "profileEnd"];
  window.console = {};
  for (var i = 0; i < names.length; ++i) {
    window.console[names[i]] = function () {
    };
  }
}

/* Array */
if (!Array.prototype.remove) {
  Array.prototype.remove = function (predicate) {
    if (this == null) {
      throw new TypeError('Array.prototype.remove called on null or undefined');
    }
    if (typeof predicate !== 'function') {
      throw new TypeError('predicate must be a function');
    }
    var list = Object(this);
    var length = list.length >>> 0;
    var thisArg = arguments[1];
    var value;

    for (var i = 0; i < length; i++) {
      value = list[i];
      if (predicate.call(thisArg, value, i, list)) {
        list.splice(i, 1);
        return;
      }
    }
    return undefined;
  };
}
if (!Array.prototype.find) {
  Array.prototype.find = function (predicate) {
    if (this == null) {
      throw new TypeError('Array.prototype.find called on null or undefined');
    }
    if (typeof predicate !== 'function') {
      throw new TypeError('predicate must be a function');
    }
    var list = Object(this);
    var length = list.length >>> 0;
    var thisArg = arguments[1];
    var value;

    for (var i = 0; i < length; i++) {
      value = list[i];
      if (predicate.call(thisArg, value, i, list)) {
        return value;
      }
    }
    return undefined;
  };
}

/* Date */
if (!Date.prototype.format) {
  /**
   *  对Date的扩展，将 Date 转化为指定格式的String
   * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
   * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
   * 例子：
   * (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
   * (new Date()).format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
   *  */

  Date.prototype.format = function (fmt) { // author: mattdamon
    var o = {
      "M+": this.getMonth() + 1, // 月份
      "d+": this.getDate(), // 日
      "h+": this.getHours(), // 小时
      "m+": this.getMinutes(), // 分
      "s+": this.getSeconds(), // 秒
      "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
      "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt))
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
  };
}

if (!Date.prototype.dateAdd) {
  /* 得到日期年月日等加数字后的日期 */
  Date.prototype.dateAdd = function (interval, number) {
    var d = this;
    var k = {
      'y': 'FullYear',
      'q': 'Month',
      'm': 'Month',
      'w': 'Date',
      'd': 'Date',
      'h': 'Hours',
      'n': 'Minutes',
      's': 'Seconds',
      'ms': 'MilliSeconds'
    };
    var n = {
      'q': 3,
      'w': 7
    };
    eval('d.set' + k[interval] + '(d.get' + k[interval] + '()+' + ((n[interval] || 1) * number) + ')');
    return d;
  };
}

if (!Date.prototype.dateDiff) {
  /* 计算两日期相差的日期年月日等 */
  Date.prototype.dateDiff = function (interval, objDate2) {
    var d = this, i = {}, t = d.getTime(), t2 = objDate2.getTime();
    i['y'] = objDate2.getFullYear() - d.getFullYear();
    i['q'] = i['y'] * 4 + Math.floor(objDate2.getMonth() / 4) - Math.floor(d.getMonth() / 4);
    i['m'] = i['y'] * 12 + objDate2.getMonth() - d.getMonth();
    i['ms'] = objDate2.getTime() - d.getTime();
    i['w'] = Math.floor((t2 + 345600000) / (604800000)) - Math.floor((t + 345600000) / (604800000));
    i['d'] = Math.floor(t2 / 86400000) - Math.floor(t / 86400000);
    i['h'] = Math.floor(t2 / 3600000) - Math.floor(t / 3600000);
    i['n'] = Math.floor(t2 / 60000) - Math.floor(t / 60000);
    i['s'] = Math.floor(t2 / 1000) - Math.floor(t / 1000);
    return i[interval];
  };
}


// Shielding ESC
// window.document.addEventListener('keydown', function (event) {
//   if (event.keyCode === 27) {
//     event.stopPropagation();
//     return;
//   }
// });
//
// function screenKey(e) {
//   var ev = e || window.event; //获取event对象
//   var obj = ev.target || ev.srcElement; //获取事件源
//   var t = obj.type || obj.getAttribute('type'); //获取事件源类型
//   var readonly = obj.readonly || obj.getAttribute("readonly");
//   var disabled = obj.disabled || obj.getAttribute("disabled");
//   console.log(t, readonly, disabled);
//   if (ev.keyCode === 8) {
//     if (t === "password" || t === "text" || t === "textarea") {
//       if (readonly || disabled) {
//         return false;
//       }
//     } else {
//       return false;
//     }
//   }
// }
//禁止后退键 作用于Firefox、Opera
// window.document.onkeypress = screenKey;
//禁止后退键 作用于IE、Chrome
// window.document.onkeydown = screenKey;