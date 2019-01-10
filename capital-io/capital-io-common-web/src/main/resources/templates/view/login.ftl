<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>MS Platform | Daemon - JokerSoft</title>

  <!-- Bootstrap core CSS -->
  <link href="/library/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
  <![endif]-->
</head>

<body>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/library/jquery/1.12.4/jquery.min.js"></script>
<script src="/library/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<div class="container w-100 h-100">
  <div class="mt-lg-5 col-sm-8">
    <form class="thumbnail" role="form" action="login" method="post">
      <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="username">Username</label>
        <div class="col-sm-10">
          <input type="text" class="form-control" name="username" id="username" placeholder="Username/Phone/Email">
        </div>
      </div>
      <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="password">Password</label>
        <div class="col-sm-10">
          <input type="password" class="form-control" name="password" id="password" placeholder="password">
        </div>
      </div>
      <div class="form-group row">

        <div class="col-sm-6">
          <div class="checkbox">
            <label>
              <input type="checkbox" name="remember_me">RememberMe
            </label>
          </div>
        </div>
        <div class="col-sm-6 text-right">
          <a class="btn-link" href="javascript:void(0);" id="corsBtn">cors test</a>
          <a class="btn-link" href="javascript:void(0);">Sign up</a>
        </div>
      </div>
    </form>
    <button type="submit" class="btn btn-primary w-100">Sign In</button>
  </div>
</div>
</body>
<script type="application/javascript">

  /*Mock cors*/
  $(function () {
    $("#corsBtn").click(function () {
      $.ajax({
        type: 'GET',
        url: 'http://dev.valueonline.cn:9001/master/sample/framework/uncaught/runtime',
        cache: false,
        // data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json",
        success: function (result) {
          console.log(">success", result);
        },
        error: function (result) {
          console.log(">error", result);
        }
      });
    });
  })

</script>
</html>