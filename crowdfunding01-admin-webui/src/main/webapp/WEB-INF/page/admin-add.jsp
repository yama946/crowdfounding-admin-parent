<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2022/7/27
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="utf-8">
<head>
    <%@include file="include-head.jsp"%>
    <script type="text/javascript">
        $(function () {
            var message = "${requestScope.exception.message}";
            console.log(message);
            if (message){
                layer.alert(message);
            }
            function getform(){
                var text = $("#LoginAccount,#InputPassword,#InputUsername,#InputEmail").val().toString();
                console.log(text);
                if (text.length==0){
                    layer.alert("未输入合法数据！");
                    return;
                }else{
                    var content1 = $("#LoginAccount").val();
                    if (content1.length == 0 || content1.length < 3) {
                        layer.alert("账号输入不合法！");
                        return;
                    }
                    var conten2 = $("#InputPassword").val();
                    if (conten2.length==0 || conten2.length<5){
                        layer.alert("密码输入不合法！");
                        return;
                    }
                    var conten3 = $("#InputUsername").val();
                    if (conten3.length==0 || conten3.length<5){
                        layer.alert("用户名输入不合法！");
                        return;
                    }
                    var conten4 = $("#InputEmail").val();
                    if (conten4.length==0 || conten4.length<5){
                        layer.alert("邮箱输入不合法！");
                        return;
                    }
                }
                $("#form").submit();
            }
            $("#add").on("click",getform);
        })
    </script>
</head>
<body>
<%@include file="include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/login/page.html">首页</a></li>
                <li><a href="admin/get/page.html">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form id="form" action="admin/save.html" method="post" role="form">
                        <div class="form-group">
                            <label for="LoginAccount">登陆账号</label>
                            <input
                                    name="loginAcct"
                                    type="text" class="form-control" id="LoginAccount" placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="InputPassword">登陆密码</label>
                            <input name="userPswd"
                                    type="text" class="form-control" id="InputPassword" placeholder="请输入登陆密码">
                        </div>
                        <div class="form-group">
                            <label for="InputUsername">用户昵称</label>
                            <input name="userName" type="text" class="form-control" id="InputUsername" placeholder="请输入用户昵称">
                        </div>
                        <div class="form-group">
                            <label for="InputEmail">邮箱地址</label>
                            <input name="email" type="email" class="form-control" id="InputEmail" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button id="add" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>



