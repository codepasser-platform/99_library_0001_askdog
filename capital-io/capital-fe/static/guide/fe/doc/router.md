# Stock Capital IO - FE

## 开发篇 (4)

### 路由

- router.js

>
    ./src/router.js

#### 路由声明

> 代码片段

    {
      // 功能模块-相对路径，需要合理规划
      path: 'sample',
      name: 'sample',
      // 选择布局模版
      component: _DefaultLayout,
      children: [
        // 功能模块视图，注意：name 全部数据中必须保证唯一，必须通过_import函数声明组件，否则加载模式动态切换。
        {path: 'dashboard', name: 'dashboard', component: _import('sample/index')},
        // 功能模块视图，注意：name 全部数据中必须保证唯一，必须通过_import函数声明组件，否则加载模式动态切换。
        {path: 'hello', name: 'hello', component: _import('sample/hello')},
        // 功能模块视图，注意：name 全部数据中必须保证唯一，必须通过_import函数声明组件，否则加载模式动态切换。重要的事情说三遍！！！
        {path: 'hello', name: 'hello2', component: _import('sample/hello')}
      ]
    }
    
#### 路由参数

##### 隐式query (query string)

- router无需定义参数

- 参数传递

> router-link

    <router-link :to="{path:'/sample/dashboard',query:{id:1}}">...</router-link>
    # 或
    <router-link :to="{name:'sample',query:{id:1}}">...</router-link>


> vue

    this.$router.push({  name:'sample', query: { id:  _id }});


##### 显式params（path variable）

- 定义参数

>
    {
      // 功能模块-相对路径，需要合理规划
      path: 'sample',
      name: 'sample',
      // 选择布局模版
      component: _DefaultLayout,
      children: [
        // 参数ID
        {path: 'dashboard/:id', name: 'dashboard', component: _import('sample/index')}
      ]
    }

- 参数传递

> router-link

    # 注意必须使用name
    <router-link :to="{name:'sample',params:{id:1}}">...</router-link>

> vue

    this.$router.push({  name:'sample', params: { id: _id }});

##### 参数获取

    # query
    this.$route.query
    # params
    this.$route.params

#### 路由加载模式

- 加载模式

> Require模式

    构建时全部依赖构建为一个JS文件，访问全部加载。

> Lazeload 模式

    构建时按需分模块构建，访问按需加载。

- 加载模式设置

> dev.env.js

    LAZE_MODE : true (懒加载默认)

> prod.env.js

    LAZE_MODE : true (懒加载默认)

> test.env.js

    LAZE_MODE : true (懒加载默认)
    
#### 系统默认视图

- [401](/error/401)

- [404](/error/404)

- [500](/error/500)

> 代码片段

    {
      path: '/error',
      name: 'error',
      component: _ScreenLayout,
      meta: {title: 'ERROR', icon: 'error', hidden: true},
      children: [
        {path: '401', name: '404', component: _ERROR_401},
        {path: '404', name: '401', component: _ERROR_404},
        {path: '500', name: '500', component: _ERROR_500}
      ]
    },
    {path: '*', redirect: '/error/404', hidden: true}
