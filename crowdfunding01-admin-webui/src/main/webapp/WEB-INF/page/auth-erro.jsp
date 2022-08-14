<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2022/8/14
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="utf-8">
    <head>
        <%@include file="include-head.jsp"%>
    </head>

    <body>
    <%@include file="include-nav.jsp"%>
        <div class="container-fluid">
            <div class="row">
                <%@include file="include-sidebar.jsp"%>
                权限不足，请更换账号再次尝试。
                <div style="margin-top: 15px">
                    <a id="submitBtn" href="admin/to/login/index.html"
                           class="btn btn-default btn-success dropdown-toggle"  value=">> 登录页" />
                </div>

            </div>
        </div>
    </body>
</html>
