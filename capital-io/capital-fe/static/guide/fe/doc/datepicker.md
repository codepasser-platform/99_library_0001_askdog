# Stock Capital IO - FE

## 开发篇 (15)

### DatePicker

- [日期选择控件](/sample/fe/date-picker)

#### 依赖v-date-picker指令

-  v-date-picker使用方法

>
    1 input text 节点中使用 v-date-picker="datePickerChangeEvent"
    2 与同时支持Validator

-  v-date-picker 参数说明

> datePickerChangeEvent 

    1 日期选择回调事件
    2 通过回调事件解决VUE input v-modal
    3 必须参数

> data-date-format

    1 日期格式： yyyy-mm-dd
    2 可选择： yyyy-mm-dd | yyyy-mm-dd hh:ii:ss
    3 与 data-view 结合使用
    3 必须参数

> data-view

    1 日期 : date
    2 日期时间 : datetime
    3 必须参数
    
> data-position

    1 日期控件定位
    2 可选择： right|left 默认 left
    3 可选参数
    
> data-start

    1 日期时间起始范围
    2 可选择： 2018-01-01 | 2018-01-01 00:00:00
    3 可选参数

> data-end="2018-12-31"

    1 日期时间起始范围
    2 可选择： 2018-01-01 | 2018-01-01 00:00:00
    3 可选参数


#### 日期选择

- 代码示例

>
    <div class="form-group input-append date">
      <label for="datePicker">Date Picker:</label>
      <input id="datePicker" name="datePicker" type="text" class="form-control " aria-describedby="datePickerTip" placeholder="datePicker"
             data-date-format="yyyy-mm-dd"
             data-view="date"
             data-position="left"
             data-start="2018-01-01"
             data-end="2018-12-31"
             v-date-picker="datePickerChangeEvent">
      <div class="valid-feedback">valid-feedback</div>
      <small id="datePickerTip" class="form-text text-muted">datePicker input.</small>
      <div class="invalid-feedback">invalid-feedback</div>
    </div>

#### 日期时间选择
    
- 代码示例

>
    <div class="form-group input-append date">
      <label for="dateTimePicker">Date Time Picker:</label>
      <input id="dateTimePicker" name="dateTimePicker" type="text" class="form-control " aria-describedby="dateTimePickerTip" placeholder="dateTimePicker"
             data-date-format="yyyy-mm-dd hh:ii:ss"
             data-view="datetime"
             data-position="left"
             data-start="2018-07-01 10:00:00"
             data-end="2018-07-02 00:00:00"
             v-date-picker="dateTimePickerChangeEvent">
      <div class="valid-feedback">valid-feedback</div>
      <small id="dateTimePickerTip" class="form-text text-muted">dateTimePicker input.</small>
      <div class="invalid-feedback">invalid-feedback</div>
    </div>