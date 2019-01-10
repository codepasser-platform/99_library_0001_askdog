# Stock Capital IO - IPO

# 环境篇

## 前端开发配置

- 安装NodeJS

>  v8.11.2 or latter

    https://nodejs.org/en/

- 全局安装VUE

> 2.9.6 or latter

    npm install -g typescript
    npm install -g vue
    npm install -g vue-cli

    # 安装时可指定镜像地址
    npm install -g typescript --registry=http://registry.npm.taobao.org/ --disturl=https://npm.taobao.org/dist
    npm install -g vue --registry=http://registry.npm.taobao.org/ --disturl=https://npm.taobao.org/dist
    npm install -g vue-cli --registry=http://registry.npm.taobao.org/ --disturl=https://npm.taobao.org/dist
    
> npm 镜像地址切换

    # 切换npm taobao镜像地址
    npm config set registry http://registry.npm.taobao.org/
    # 切换npm官方地址
    npm config set registry https://registry.npmjs.org/

- CentOS 环境配置

>
    # 查看
    update-alternatives --list
    
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

- 卸载

>
    # 目录 /usr/local/lib/node_modules
    sudo npm uninstall -g typescript
    sudo npm uninstall -g vue
    sudo npm uninstall -g vue-cli
    sudo npm cache clean

- npm install

>
    # 进入到前端工程目录
    npm install
    # chrome driver 被墙
    npm install --chromedriver_cdnurl=https://npm.taobao.org/mirrors/chromedriver

- 文档

>
    # Bootstrap-VUE
    https://bootstrap-vue.js.org/docs/
    # Bootstrap V4.1
    https://getbootstrap.com/docs/4.1/getting-started/introduction/

## 后端开发配置

- Java

>

- Maven

>

- Docker

>

- CentOS 环境配置

>
    # 查看
    update-alternatives --list
    # 切换java版本
    update-alternatives --install /usr/bin/java java /usr/local/lib/java/jdk1.8.0_171/bin/java 60
    update-alternatives --install /usr/bin/java java /usr/local/lib/java/jdk-9.0.4/bin/java 60
    update-alternatives --config java
    # 切换maven版本
    update-alternatives --install /usr/bin/mvn maven /usr/local/lib/java/apache-maven-3.5.3/bin/mvn 60
    update-alternatives --config maven