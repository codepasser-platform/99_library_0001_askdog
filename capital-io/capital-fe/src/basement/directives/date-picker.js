'use strict';
import Vue from 'vue';

import '../../style/lib/datetime-picker/css/bootstrap-datetimepicker.min.css'
import '../../style/lib/datetime-picker/js/bootstrap-datetimepicker.min'
import '../../style/lib/datetime-picker/js/locales/bootstrap-datetimepicker.zh-CN'

const _VIEWS = ['hour', 'day', 'month', 'year', 'decade'];
const _DEFAULT_VIEWS = 'month';
const _DEFAULT_FORMAT = 'yyyy-mm-dd HH:ii:ss';
const _DEFAULT_POSITION = 'bottom-right';

const datePicker = Vue.directive('date-picker', {
  bind: function (el, binding) {
  },
  inserted: function (el, binding) {
    console.log('Date Time Picker -> inserted', el, binding);
    bindDateTimePicker(el, binding);
  },
  unbind: function (el, binding) {
    console.log('Date Time Picker -> unbind', el, binding);
    unbindDateTimePicker(el, binding);
  }
});


const bindDateTimePicker = function (el, binding) {
  const _options = datePickerOptions(el, binding);
  $(el).datetimepicker(_options).on('changeDate', function (ev) {
    el.focus();
    // datePickerChangeEvent
    binding.value($(el).val());
    el.blur();
  });
};

const unbindDateTimePicker = function (el, binding) {
  $(el).datetimepicker('remove');
};

const datePickerOptions = function (el, binding) {
  const _format = $(el).data('date-format');
  const _view = $(el).data('view');
  const _position = $(el).data('position');
  const _start = $(el).data('start');
  const _end = $(el).data('end');
  let _options = {
    // 选择后自动关闭
    autoclose: true,
    // today 按钮
    todayBtn: 'linked',
    todayHighlight: true,
    clearBtn: true,
    // 起始： 0 周日 1 周一 2 周二 3 周三 3 周四 5 周五 6 周六
    weekStart: 1,
    // 当选择器关闭的时候，是否强制解析输入框中的值。
    forceParse: true,
    showMeridian: true,
    keyboardNavigation: true,
    language: 'zh-CN',
    minuteStep: 5,
    // 0 or 'hour' 为小时视图 | 1 or 'day' 为天视图 | 2 or 'month' 为月视图（为默认值）| 3 or 'year'  为年视图 | 4 or 'decade' 为十年视图
    startView: 'month',
    maxView: 'decade',
    minView: _view === 'date' ? 'month' : 'hour',
    format: _format,
    pickerPosition: (_position && _position === 'right') ? 'bottom-left' : 'bottom-right',
    startDate: _start,
    endDate: _end
  };
  return _options;
};

export default datePicker;