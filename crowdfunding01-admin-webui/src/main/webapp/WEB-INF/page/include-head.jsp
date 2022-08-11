<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false" %>
<%--    禁止 favicon.ico 请求--%>
<link rel="icon" href="data:;base64,=">
<title>微众筹</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort }${pageContext.request.contextPath}/"/>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="pagination/pagination.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<style>
    .tree li {
        list-style-type: none;
        cursor:pointer;
    }
    .tree-closed {
        height : 40px;
    }
    .tree-expanded {
        height : auto;
    }
</style>
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="script/docs.min.js"></script>
<script type="text/javascript" src="pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
</script>
