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
            $('#reset').click(function () {
                $('#exampleLoginAccount').attr('value',"");
                $('#exampleUserName').attr('value',"");
                $('#exampleInputEmail1').attr('value',"");
            })
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
                <li class="active">更新</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form action="admin/update.html" method="post" role="form">
                        <%--隐藏提交的键值--%>
                        <input type="hidden" name="id" value="${requestScope.admin.id}" />
<%--                        <input type="hidden" name="pageNum" value="${param.pageNum}" />--%>
<%--                        <input type="hidden" name="keyword" value="${param.keyword}" />--%>
                        <div class="form-group">
                            <label for="exampleLoginAccount">登陆账号</label>
                            <input
                                    name="loginAcct"
                                    value="${requestScope.admin.loginAcct}"
                                    type="text" class="form-control" id="exampleLoginAccount" placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="exampleUserName">用户昵称</label>
                            <input
                                    name="userName"
                                    value="${requestScope.admin.userName}"
                                    type="text" class="form-control" id="exampleUserName" placeholder="请输入用户昵称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input
                                    name="email"
                                    value="${requestScope.admin.email}"
                                    type="email" class="form-control" id="exampleInputEmail1" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> 修改</button>
                        <button id="reset" type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>