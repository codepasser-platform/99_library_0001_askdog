# Stock Capital IO - FE

## 开发篇 (3)

### 布局模版

#### 默认布局

- default.vue

> 默认布局

    /capital-fe/src/basement/layout/default.vue

> 结构

    Header      ： 应用头部组件（默认）
    Sidebar     ： 应用侧边栏（默认）
    Breadcrumb  ： 面包屑
    Body        ： 路由视图渲染区域
    

> 代码片段

    <div class="layout-container layout-default">
      <div class="layout-header">
        <Header></Header>
      </div>
      <div class="layout-main clearfix">
        <div class="layout-sidebar sidebar-container">
          <Sidebar></Sidebar>
        </div>
        <div class="layout-body body-container" ref="bodyContainer">
          <Breadcrumb></Breadcrumb>
          <router-view/>
        </div>
      </div>
    </div>

#### 单页布局

- screen.vue

> 单页布局

    /capital-fe/src/basement/layout/screen.vue

> 结构

    Header ： 应用头部组件（默认）
    Body   ： 路由视图渲染区域
    Footer ： 应用底部（默认）

> 代码片段

    <div class="layout-container layout-screen">
      <div class="layout-header">
        <Header></Header>
      </div>
      <div class="layout-main clearfix">
        <div class="layout-body body-container" ref="bodyContainer">
          <router-view/>
        </div>
      </div>
      <div class="layout-footer">
        <Footer></Footer>
      </div>
    </div>

#### 特别说明

- 扩展

>
    如需要继续实现相应布局，提供项目开发使用

- Body

>   默认body 为动态计算屏幕自适应高度，取消滚动条，依赖perfect-scrollbar触发滚动。