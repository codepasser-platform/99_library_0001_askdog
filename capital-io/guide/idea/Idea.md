## 解决 centos 中文输入法问题

        # idea.sh 头部声明输入法
        export XMODIFIERS="@im=ibus"
        export GTK_IM_MODULE="ibus"
        export QT_IM_MODULE="ibus"

## Idea 文件头内容设置

        打开菜单 File -> Settings -> Editor -> File and Code Templates -> Includes

        将File Header的值改为如下内容：

        /**
         * [${NAME}].
         *
         * @author 开发者.
         * @date ${DATE} ${TIME}
         * @version 0.0.1
         */

## Idea Code Style配置

        在Idea的File->Settings->Editor->Code Style->Manage->Import中选择Intellij Idea code style XML,然后选择对应的文件导入.

        在File->Settings->Editor->Code Style->页面的scheme下拉框中，选择idea-code-style.xml

        代码编写完后，使用idea格式化代码，从而保证代码的一致性

## 安装google-java-format插件

        安装如下步骤安装google-java-format插件，以便于CheckStyle规范对应。

        进入File → Settings → Plugins.

        点击Browse Repositories.

        输入google-java-format进行搜索，找到插件并安装.

        重启Idea

        为当前工程进行设置：进入File → Settings → Other Settings -> google-java-format Settings，选中Enable google-java-format的复选框

        更改Idea的默认设置：进入File → Other Settings → Default Settings → Other Settings -> google-java-format Settings，选中Enable google-java-format的复选框

## Idea CheckStyle配置

        CheckStyle-IDEA插件安装。

        如果IDEA中未安装过此插件，操作步骤如下：Settings -> Plugins -> Browse Repositories，输入CheckStyle-IDEA，进行安装

        如果已经安装过此插件，请先升级到最新版本，操作步骤如下：Settings -> Plugins，输入CheckStyle-IDEA，进行升级

        插件安装成功后，进行配置：Settings -> Others Settings -> Checkstyle。Checkstyle version选择最新版本。
        
        Congfigguation File选择Google Checks(插件自带版本)

        # CheckStyle maven命令行执行

        在命令行执行命令：mvn checkstyle:checkstyle

        命令执行成功后，可以查看生成的checkstyle文件（xml和html）

        xml文件路径：工程路径target -> check-result.xml

        html文件路径：工程路径target -> site -> checkstyle.html
