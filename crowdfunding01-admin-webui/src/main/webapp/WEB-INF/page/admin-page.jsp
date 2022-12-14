<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="utf-8">
<head>
    <%@ include file="include-head.jsp" %>
    <script type="text/javascript">
        $(function(){
            initPagination();
        });
        function initPagination() {
            //总记录数
            var num_entries = ${requestScope.pageInfo.total};
            // 渲染分页导航栏
            $("#Pagination").pagination(num_entries, {
                num_edge_entries: 2, //边缘页数
                num_display_entries: 4, //主体页数
                items_per_page:${requestScope.pageInfo.pageSize}, //每页显示1项
                current_page:${requestScope.pageInfo.pageNum-1},
                prev_text:"上一页",
                next_text:"下一页",
                callback: pageselectCallback,//回调函数
            });
        };
        //pageIndex表示当前页
        function pageselectCallback(pageIndex, jq){
            //第一步获取当前页，数据
            var currentPage = pageIndex + 1;

            window.location.href="admin/get/page.html?pageNum="+currentPage+"&keyword=${param.keyword}";

            //表示每个页码都是一个超链接形式返回false，取消超链接的默认属性，直接执行当前回调函数即可
            return false;
        }
    </script>
</head>
<body>
<%@ include file="include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@ include file="include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page.html" method="get" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <a href="admin/to/add/page.html" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            <thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list }">
                                <tr>
                                    <td colspan="6">抱歉！没有查询到相关的数据！</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list }">
                                <c:forEach items="${requestScope.pageInfo.list }" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct }</td>
                                        <td>${admin.userName }</td>
                                        <td>${admin.email }</td>
                                        <td>

                                            <a href="assign/admin/to/role/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" type="button" class="btn btn-success btn-xs">
                                                <i class=" glyphicon glyphicon-check"></i>
                                            </a>
                                            <%--更新按钮--%>
                                            <a href="admin/to/update/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-primary btn-xs">
                                                <i class=" glyphicon glyphicon-pencil"></i>
                                            </a>
                                            <%--删除按扭--%>
                                            <a id="remove" href="admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html" class="btn btn-danger btn-xs">
                                                <i class=" glyphicon glyphicon-remove"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                           <%-- 引入pagenition中的分页部分代码;--%>
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>

        </div>
        </div>
    </div>
</div>
</body>
</html>
