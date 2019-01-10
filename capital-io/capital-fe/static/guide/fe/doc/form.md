# Stock Capital IO - FE

## 开发篇 (11)

### 表单

#### 表单布局

- [默认表单](/sample/fe/form)

- [栅格化表单1](/sample/fe/form/grid)

- [栅格化表单2](/sample/fe/form/inline)

#### 表单校验

- 表单校验支持的常用元素

> input type(包含h5新元素类型)

    text
    password
    email
    tel
    phone
    week
    month
    time
    date
    datetime
    datetime-local
    color
    number
    search
    range

    file
    checkbox

> select

> textarea

> radio 不予以支持校验， 如需用到radio提供默认选项。

- 表单校验尚未支持的表单元素可扩展

    暂时实现以上常用的，随遇随扩展

#### 表单校验详解

- 表单校验说明

>
    1 表单校验依赖bootstrap提供的默认校验样式，封装VUE指令实现。
    （原因：bootstrap-vue 中未提供解决方法，其他第三方校验插件在vue下使用不是很灵活。）

    2 表单校验支持原生bootstrap 和 bootstrap-vue组件两种实现方式。
    
    3 通过v-validator使用表单校验指令，框架已经默认依赖不必单独引入validator指令。
         
         <form class="needs-validation" v-validator="validator" novalidate @reset="onReset" v-if="show" ref="form">
    
    4 form @submit 事件不必再实现. validator指令通过自定义onSubmit事件调用内部submitHandler函数。

    5 @reset绑定onReset方法内实现表单清空,v-if="show" 用作清空校验
    
    6 novalidate 取消新一代浏览器默认校验触发效果
        
- 校验规则

> required : [true|false] 必须输入 或 选择 

> regex : 正则表达式校验, regex-pattern.js统一管理全部正则、相同复用、与后端RegexPattern.java一致。

> 备注

    1 自定义校验函数方式 - TODO
    
    2 输入子段长度问题可通过正则表达式约束， 推荐使用 input maxLength属性约束控制输入。

- 校验指令使用

> 1 <form v-validator="validator"> 

> 2 data中定义校验器

    data: function () {
        const _self = this;
        return {
          validator: {
            rules: {
              email: {  // 表单元素,与表单元素ID一致
                required: true,   // 必须输入
                regex: REGEX_MAIL // 正则表达式
              },
            },
            message: {
              email: {
                required: 'email required',     // 必须校验条件触发错误提醒
                regex: 'regex : email invalid'  // 正则校验条件触发错误提醒
              },
            },
            submitHandler: function () { // 校验通过事件回调函数
              _self.onSubmit(); // VUE form submit
            }
      }
    }


- 校验触发时机

> keyup
> change
> blur
> submit
