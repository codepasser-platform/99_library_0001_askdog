# Stock Capital IO - FE

## 环境篇 (2)

### GIT

    # Stock Capital IO 框架
    ssh://git@git.valueonline.cn:8888/capital-io.git
    # Stock Capital - IPO 湖北金融办-上市之家
    ssh://git@git.valueonline.cn:8888/capital-io-ipo.git

### 应用工程环境

- 安装

> 进入到capital-fe目录

    # 切换淘宝镜像
    npm config set registry http://registry.npm.taobao.org/
    # chrome driver 被墙,使用CDN安装
    npm install --chromedriver_cdnurl=https://npm.taobao.org/mirrors/chromedriver

    注：本框架衍生项目可快速复制，环境配置、安装、构建、部署一致。

- 相关命令

> 
    # install dependencies
    npm install
    
    # serve with hot reload at localhost:8080
    npm run dev
    
    # build for production with minification
    npm run build
    
    # build for production and view the bundle analyzer report
    npm run build --report
    
    # run unit tests
    npm run unit
    
    # run e2e tests
    npm run e2e
    
    # run all tests
    npm test

### 应用全局配置参数

- env.js

>
    # 环境参数 development | production | testing
    NODE_ENV: '"development"'
    # 前端服务默认URL
    APP_BASE: '"http://localhost:7000"'
    # API服务默认URL
    API_BASE: '"http://localhost:9001"'
    # 懒加载模式
    LAZE_MODE: true
    # MOCK模式
    MOCK_MODE: true

### 脚本

> 
    # 本地前端服务启动
    app-run.sh
    # 生产环境构建
    app-build.sh
    # 清理构建文件
    app-clean.sh

### 部署

- Nginx

> 
    TODO
