# Stock Capital IO - FE

## 开发篇 (1)

### 工程结构

#### 框架工程 capital-fe

>
    .
    ├── app
    │   ├── components                        # 共同组件
    │   │   ├── footer
    │   │   │   ├── index.vue
    │   │   │   └── style.scss
    │   │   ├── header
    │   │   │   ├── guide.vue
    │   │   │   ├── index.vue
    │   │   │   └── style.scss
    │   │   ├── navigator
    │   │   │   ├── index.vue
    │   │   │   └── style.scss
    │   │   └── sidebar
    │   │       ├── guide.vue
    │   │       ├── index.vue
    │   │       └── style.scss
    │   ├── security                          # 权限控制
    │   │   └── permission.js
    │   ├── service                           # 数据服务
    │   │   ├── api                           # 后端API
    │   │   │   ├── auth-api.js
    │   │   │   └── guide-api.js
    │   │   ├── mock                          # API MOCK
    │   │   │   └── auth-api.js
    │   │   ├── mock.js                       # API MOCK声明
    │   │   └── store                         # 数据存储
    │   │       └── index.js
    │   └── views                             # 视图
    │       ├── sample                        # SAMPLE
    │       │   ├── hello.scss
    │       │   ├── hello.vue
    │       │   └── index.vue
    │       └── guide                         # GUIDE
    │           ├── sample
    │           │   ├── sample.vue
    │           │   └── style.scss
    │           └── todo.vue
    ├── app.vue                               # VUE 引导
    ├── assets                                # 前端资源
    │   └── logo.png
    ├── basement                              # 前端基础包
    │   ├── directives                        # 共同指令
    │   ├── error                             # 系统级别错误视图
    │   │   ├── 401.vue
    │   │   ├── 404.vue
    │   │   ├── 500.vue
    │   │   └── style.scss
    │   ├── filters                           # 共同过滤器
    │   ├── layout                            # 共同布局
    │   │   ├── default.vue
    │   │   ├── guide.vue
    │   │   ├── layout.scss
    │   │   └── screen.vue
    │   ├── router                            # 路由加载模式
    │   │   ├── _import_lazeload.js
    │   │   └── _import_require.js
    │   ├── service                           # 数据服务
    │   │   ├── store
    │   │   │       ├── modules
    │   │   │       ├── getters.js
    │   │   │       └── index.js              # 系统级数据存储器
    │   │   └── request.js
    │   └── utils                             # 工具包
    │       ├── extends.js
    │       └── validation.js
    ├── main.js                               # VUE 主程序
    ├── router.js                             # 应用路由
    └── style                                 # 样式库
        ├── app.scss
        ├── extends.scss
        ├── lib
        │   └── bootstrap
        └── variables.scss

#### 应用工程 capital-fe-xxx

>
        .
        ├── app
        │   ├── components                     # 组件
        │   │   └── todo
        │   ├── security                       # 权限控制
        │   │   └── permission.js
        │   ├── service                        # 数据服务
        │   │   ├── api
        │   │   │   └── todo
        │   │   ├── mock
        │   │   │   └── todo
        │   │   ├── mock.js
        │   │   └── store
        │   │       └── todo
        │   └── views                          # 视图
        │       └── sample
        │       └── todo
        ├── app.vue                            # VUE 引导
        ├── assets                             # 前端资源
        ├── main.js                            # VUE 主程序
        ├── router.js                          # 应用路由
        └── style                              # 样式库
            └── variables.scss                 # SCSS变量设置