<#import "spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Ask.Dog | 让知识触手可及 - 登录</title>
    <link rel="shortcut icon" href="/images/favicon.ico">

    <meta name="viewport" content="width=1200, initial-scale=no">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="ASKDog"/>
    <meta name="description" content="向 ASKDOG 提问，让知识触手可及"/>

    <link href="/css/bootstrap.min.css" rel="stylesheet" type='text/css'/>
    <link href="/iconfont/iconfont.css" rel="stylesheet" type='text/css'/>
    <link href="/css/common.css" rel="stylesheet" type='text/css'/>
    <link href="/css/style.css" rel="stylesheet" type='text/css'/>

    <style>
        .login-layout {
            position: fixed;
            left: 0px;
            right: 0px;
            top: 80px;
            bottom: 0px;
            margin-left: auto;
            margin-right: auto;
            width: 472px;
            height: 503px;
            text-align: center;
        }

        a {
            text-decoration: none;
        }

        form {
            margin: 0 64px 42px;
        }

        .form-group {
            position: relative;
        }

        input[type="text"] {
            padding: 0 20px 0 38px;
            width: 100%;
            height: 42px;
            line-height: 38px;
            border: 1px solid #c1c8da;
            font-size: 14px;
            color: #8792a4;
            border-radius: 5px;
        }

        input[type="password"] {
            padding: 0 20px 0 38px;
            width: 100%;
            height: 42px;
            line-height: 38px;
            border: 1px solid #c1c8da;
            font-size: 14px;
            color: #8792a4;
            border-radius: 5px;
        }

        .link-forgetpwd {
            font-size: 12px;
            color: #20b5ff;
        }

        .icon-input {
            position: absolute;
            top: 15px;
            left: 15px;
            color: #20b5ff;
        }
    </style>
</head>
<body>
<div class="login-layout">
    <h1 class="login-title">登录</h1>
    <form action="login" method="post">
    <#if SPRING_SECURITY_LAST_EXCEPTION??>
        <#assign exceptionType = SPRING_SECURITY_LAST_EXCEPTION.getClass().getName()>
        <div class="form-group">
            <div class="alert alert-danger text-center alert-shake">
                <@spring.messageText exceptionType exceptionType/>
            </div>
        </div>
    </#if>
        <div class="form-group">
            <input type="text" name="username" placeholder="邮件地址/用户名"/>
            <i class="iconfont icon-user1 icon-input"></i>
        </div>
        <div class="form-group">
            <input type="password" name="password" placeholder="密码（不能少于6位）"/>
            <i class="iconfont icon-password icon-input"></i>
        </div>
        <div class="form-group clearfix hidden">
            <a class="link-forgetpwd pull-left" href="javascript:void(0);">忘记密码</a>
        </div>
        <div class="form-group">
            <button type="submit" class="btn flat-btn flat-btn-l">登录</button>
        </div>
    </form>
    <div class="other-way">
        <p>第三方账户登录</p>
        <div class="other-link">
            <a third-login href="/login/qq?request=http%3A%2F%2Fwww.askdog.com%2F%23%26auth" class="fl"><i
                    class="iconfont icon-qq"></i></a>
            <a third-login href="/login/weibo?request=http%3A%2F%2Fwww.askdog.com%2F%23%26auth" class="fr"><i
                    class="iconfont icon-sina"></i></a>
        </div>
    </div>
</div>
</body>
</html>