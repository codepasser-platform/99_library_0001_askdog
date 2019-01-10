# Stock Capital IO - FE

## 开发篇 (14)

### 模态框

- [默认模态框](/sample/fe/modal)

>
    <b-modal centered 
             hide-footer
             hide-header-close
             no-close-on-backdrop
             no-close-on-esc
             size="lg"
             :title="modalTitle"
             ref="modalRef">
      ...
    </b-modal>

#### Toast

> toast为共同组建，默认依赖，引用方式

    <Toast ref="toastRef"></Toast>

> 调用，默认3秒钟关闭

    this.$refs.toastRef.show('操作成功！', 'success');

#### Confirm

> toast为共同组建，默认依赖，引用方式

    <Confirm ref="confirmRef"></Confirm>

> 确认取消提醒

     this.$refs.confirmRef.show('确认删除？',
                function (_confirm) {
                  console.log('confirmRef Confirm > cancelHandler');
                },
                function (_cancel) {
                  console.log('confirmRef Confirm > cancelHandler');
                });

> 只有确认提醒

    this.$refs.confirmRef.show('该操作成功提醒',
                function (_confirm) {
                  console.log('confirmRef Confirm > cancelHandler');
                },
                false);