# Stock Capital IO - FE

## 开发篇 (16)

### Tinymce

- tinymce 引入

> npm install --save

    # tinymce ^4.8.1
    npm install --save tinymce
    # tinymce-vue ^1.0.8
    npm install --save @tinymce/tinymce-vue

> import
    
    # 在 node_modules 中找到 tinymce/skins 目录，然后将 skins 目录拷贝到 static 目录下
    # 语言包 https://www.tiny.cloud/download/language-packages/
    
    import tinymce from 'tinymce/tinymce'
    import 'tinymce/themes/modern/theme'
    import Editor from '@tinymce/tinymce-vue'

    ...
    mounted: function () {
      tinymce.init({});
    },
    ...
    data(){
      return {
          editorInit: {
            language_url: '/static/tinymce/zh_CN.js',
            language: 'zh_CN',
            skin_url: '/static/tinymce/skins/lightgray',
            height: 300
          }
      }
    }
    ...
    <editor v-model="form.content" :init="editorInit"></editor>

> extends

    https://www.tiny.cloud/docs/plugins/
    
- 对Tinymce 支持了Validator 扩展