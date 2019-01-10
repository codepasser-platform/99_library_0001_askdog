<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
<#--<link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">-->

    <title>惠券 | 认证</title>

    <!-- Bootstrap core CSS -->
    <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/default.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="container"></div>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="lib/bootstrap/js/bootstrap.min.js"></script>
<div class="main container">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
        <#if Session["SPRING_SECURITY_LAST_EXCEPTION"]??>
            <div class="alert alert-danger">${Session["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
        </#if>
            <form class="thumbnail" role="form" action="login" method="post">
                <div class="form-group">
                    <label class="control-label" for="username">用户名</label>
                    <input type="text" class="form-control" name="username" id="username" placeholder="用户名/手机号/邮箱地址">
                </div>
                <div class="form-group">
                    <label class="control-label" for="password">密码</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="密码">
                </div>
                <div class="checkbox">
                    <label class="control-label">
                        <input type="checkbox" name="remember_me">记住我</label>
                </div>
                <button type="submit" class="btn btn-primary" style="width: 100%">登陆</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>