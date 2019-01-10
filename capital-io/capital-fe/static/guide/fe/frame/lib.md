# Stock Capital IO - FE

## 框架篇 (2)

### Library

-  perfect-scrollbar

> npm install --save

    # https://www.npmjs.com/package/perfect-scrollbar
    npm install --save perfect-scrollbar
    
> import

    # 引入
    import PerfectScrollbar from 'perfect-scrollbar';
    # 使用
    const sidebarContainer = this.$refs.sidebarContainer;
    const ps = new PerfectScrollbar(sidebarContainer);

- mavon-editor

> npm install --save

    npm install --save mavon-editor

> import

    import mavonEditor from 'mavon-editor'
    import 'mavon-editor/dist/css/index.css'
    Vue.use(mavonEditor)

    ...
    <mavon-editor v-model="value"/>
    
- bootstrap datetimepicker

- [Bootstrap DateTimePicker](http://www.bootcss.com/p/bootstrap-datetimepicker/index.htm)


> 依赖 jquery

> import

    # 下载后导入到静态目录
    import '../../style/lib/datetime-picker/css/bootstrap-datetimepicker.min.css'
    import '../../style/lib/datetime-picker/js/bootstrap-datetimepicker.min'
    import '../../style/lib/datetime-picker/js/locales/bootstrap-datetimepicker.zh-CN'
    
    
    $(el).datetimepicker(_options).on('changeDate', function (ev) {
      el.focus();
      // datePickerChangeEvent
      binding.value($(el).val());
      el.blur();
    });
    
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
