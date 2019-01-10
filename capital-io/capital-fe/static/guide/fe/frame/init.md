# Stock Capital IO - FE

## 框架篇 (1)

### capital-fe publish


- NPM publish

>
    # 切换官方镜像地址
    npm config set registry https://registry.npmjs.org/
    npm login
      > codepasser

>
    # 撤销版本
    npm deprecate capital-fe@x.x.x "Discarded"
    # 发布
    npm publish

### 创建项目

- init

> 
    # 切换npm taobao镜像地址
    npm config set registry http://registry.npm.taobao.org/
    vue init webpack capital-fe-xxx
    # install dependencies and go!
    # chrome driver 被墙
    npm install --chromedriver_cdnurl=https://npm.taobao.org/mirrors/chromedriver


- vuex

> npm install --save

    npm install --save vuex


- scss load

> npm install --save

    npm install --save sass-loader node-sass

> import

    import {mapGetters} from 'vuex';

- bootstrap 4 & bootstrap scss

> npm install --save

    # https://bootstrap-vue.js.org/docs/
    npm install --save jquery@1.12.4
    npm install --save bootstrap@4.1.3
    npm install --save bootstrap-vue@2.0.0-rc.11
    npm install --save sass-resources-loader

> import 

    # build/utils.js
    scss: generateLoaders('sass').concat(
        {
          loader: 'sass-resources-loader',
          options: {
            resources: path.resolve(__dirname, '../src/style/variables.scss')
          }
        }
    ),
    
    # main.js
    import 'bootstrap/scss/bootstrap.scss'
    import 'bootstrap-vue/dist/bootstrap-vue.css'
    import './style/extends.scss'
    import BootstrapVue from 'bootstrap-vue'

    Vue.use(BootstrapVue);
    
    # webpack.base.conf.js
    const webpack = require('webpack');
    ...
    resolve: {
      extensions: ['.js', '.vue', '.json'],
      alias: {
        'vue$': 'vue/dist/vue.esm.js',
        '@': resolve('src'),
        // import jquery
        'jquery': 'jquery'
      }
    },
    // import jquery
    plugins: [
      new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery"
      })
    ],
    ...

- font-awesome

> npm install --save

    npm install --save font-awesome

> import

    import 'font-awesome/css/font-awesome.css'

- mockjs

> npm install --save

    npm install --save axios
    npm install --save mockjs

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

- tinymce

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

- capital-fe

>
    # https://www.npmjs.com/package/capital-fe
    npm install --save capital-fe