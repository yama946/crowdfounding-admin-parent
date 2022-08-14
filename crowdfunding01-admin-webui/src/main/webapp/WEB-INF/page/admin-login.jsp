<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <title>微众筹</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort }${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">后台管理系统页面</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <!--<form class="form-signin" role="form" action="admin/do/login.html" method="post">-->
    <form class="form-signin" role="form" action="security/admin/do/login.html" method="post">
        <!--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 管理员登录</h2>
        ${requestScope.exception.message}
        <p>${SPRING_SECURITY_LAST_EXCEPTION.message }</p>
        <div class="form-group has-success has-feedback">
            <input name="loginAccount" type="text" class="form-control" id="inputSuccess4" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input name="loginPassword" type="text" class="form-control" id="inputSuccess" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <button type="submit" class="btn btn-lg btn-success btn-block" >登录</button>
    </form>
</div>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
