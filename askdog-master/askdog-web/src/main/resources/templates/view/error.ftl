<#import "spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Error</title>
</head>
<body>
<h1>Error occurs !!!</h1>
<div>
    <div>Error: ${error}</div>
    <div>Message: ${message}</div>
    <div>Status: ${status}</div>
    <div>Path: ${path}</div>
</div>
</body>
</html>