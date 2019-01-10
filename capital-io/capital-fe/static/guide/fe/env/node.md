# Stock Capital IO - FE

## 环境篇 (1)

### 前端开发环境配置

- 安装NodeJS

>  v8.11.2 or latter

    # 官网
    https://nodejs.org/en/

- 全局安装VUE

> 2.9.6 or latter

    # 安装命令
    npm install -g typescript
    npm install -g vue
    npm install -g vue-cli

    # 可指定国内镜像安装
    npm install -g typescript --registry=http://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist
    npm install -g vue --registry=http://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist
    npm install -g vue-cli --registry=http://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist

> npm 镜像地址切换

    # 切换npm taobao镜像地址
    npm config set registry http://registry.npm.taobao.org/
    # 切换npm官方地址
    npm config set registry https://registry.npmjs.org/

- 卸载

> 卸载安装

    npm uninstall -g typescript
    npm uninstall -g vue
    npm uninstall -g vue-cli
    npm cache clean

### CentOS

> Node环境配置

    # 查看
    update-alternatives --list
    
    # 切换npm镜像地址,NPM 用官方地址下载依赖包速度慢 这时候需要修改淘宝镜像 
    npm config set registry http://registry.npm.taobao.org/
    # 官方地址
    npm config set registry https://registry.npmjs.org/
    
    # 切换node版本
    update-alternatives --install /usr/bin/node node /usr/local/lib/node/node-v8.11.2-linux-x64/bin/node 60
    update-alternatives --config node
    
    # 切换npm版本
    update-alternatives --install /usr/bin/npm npm /usr/local/lib/node/node-v8.11.2-linux-x64/bin/npm 60
    update-alternatives --config npm
    
    # 切换npx版本
    update-alternatives --install /usr/bin/npx npx /usr/local/lib/node/node-v8.11.2-linux-x64/bin/npx 60
    update-alternatives --config npx
    
    # 切换vue版本
    update-alternatives --install /usr/bin/vue vue-cli /usr/local/lib/node/node-v8.11.2-linux-x64/bin/vue 60
    update-alternatives --config vue-cli
    
    # 切换vue-init版本
    update-alternatives --install /usr/bin/vue-init vue-init /usr/local/lib/node/node-v8.11.2-linux-x64/bin/vue-init 60
    update-alternatives --config vue-init
    
    # 切换vue-list版本
    update-alternatives --install /usr/bin/vue-list vue-list /usr/local/lib/node/node-v8.11.2-linux-x64/bin/vue-list 60
    update-alternatives --config vue-list