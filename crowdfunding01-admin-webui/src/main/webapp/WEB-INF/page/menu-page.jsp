<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="utf-8">
<head>
    <%@ include file="include-head.jsp" %>
    <script type="text/javascript" src="crowd/my-menu.js"></script>
    <script type="text/javascript">
        //第一步：绘制属性结构，生成增删改图标，为图标绑定单击响应函数，打开模态框
        $(function () {
            // 调用专门封装好的函数初始化树形结构
            generateTree();

            // 1、给添加子节点按钮绑定单击响应函数，打开模态框
            $("#treeDemo").on("click",".addBtn",function(){
                // 将当前节点的id，作为新节点的pid 保存到全局变量
                window.pid = this.id;
                // 打开模态框
                $("#menuAddModal").modal("show");

                return false;
            });


            // 2、给编辑按钮绑定单击响应函数，打开模态框，并回显数据
            $("#treeDemo").on("click",".updateBtn",function(){
                console.log("尝试激活修改模态框");
                // 将当前节点的id 保存到全局变量
                window.id = this.id;
                // 打开模态框
                $("#menuUpdateModal").modal("show");
                // 获取zTreeObj 对象
                var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
                // 根据id 属性查询节点对象
                // 用来搜索节点的属性名
                var key = "id";
                // 用来搜索节点的属性值
                var value = window.id;
                var currentNode = zTreeObj.getNodeByParam(key, value);
                // 回显表单数据
                $("#menuUpdateModal [name=name]").val(currentNode.name);
                $("#menuUpdateModal [name=url]").val(currentNode.url);
                // 回显radio 可以这样理解：被选中的radio 的value 属性可以组成一个数组，
                // 然后再用这个数组设置回radio，就能够把对应的值选中
                $("#menuUpdateModal [name=icon]").val([currentNode.icon]);
                //当前连接不跳转
                return false;
            });

            // 3、给“×”按钮绑定单击响应函数，打开模态框，并回显数据
            $("#treeDemo").on("click",".removeBtn",function(){
                // 将当前节点的id 保存到全局变量
                window.id = this.id;
                // 打开模态框
                $("#menuConfirmModal").modal("show");
                // 获取zTreeObj 对象
                var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
                // 根据id 属性查询节点对象
                // 用来搜索节点的属性名
                var key = "id";
                // 用来搜索节点的属性值
                var value = window.id;
                var currentNode = zTreeObj.getNodeByParam(key, value);
                $("#removeNodeSpan").html(" 【<iclass='"+currentNode.icon+"'></i>"+currentNode.name+"】");
                return false;
            });


        });

        //第二步：模态框中保存按扭添加单机响应函数，执行增加菜单节点的功能
        $(function () {
            //1、添加字节点
            $("#menuSaveBtn").click(function(){
                // 收集表单项中用户输入的数据
                var name = $.trim($("#menuAddModal [name=name]").val());
                var url = $.trim($("#menuAddModal [name=url]").val());
                // 单选按钮要定位到“被选中”的那一个
                var icon = $("#menuAddModal [name=icon]:checked").val();
                // 发送Ajax 请求
                var requestParam = {
                    "pid": window.pid,
                    "name":name,
                    "url":url,
                    "icon":icon
                };
                var str = JSON.stringify(requestParam);
                console.log("转换的后的json：---"+str)
                $.ajax({
                    "url":"menu/save.json",
                    "type":"post",
                    "data":str,
                    "contentType":"application/json;charset=UTF-8",
                    "dataType":"json",
                    "success":function(response){
                        var result = response.result;
                        if(result) {
                            layer.msg("操作成功！");
                            // 重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新
                            // 否则有可能刷新不到最新的数据，因为这里是异步的
                            generateTree();
                        }
                        if(!result) {
                            layer.msg(response.data);
                        }
                    },
                    "error":function(response){
                        layer.msg(response.status+" "+response.statusText);
                    }
                });
                // 关闭模态框
                $("#menuAddModal").modal("hide");
                // 清空表单
                // jQuery 对象调用click()函数，里面不传任何参数，相当于用户点击了一下
                $("#menuResetBtn").click();
            });

            //2、 给更新模态框中的更新按钮绑定单击响应函数,更新数据
            $("#menuUpdateBtn").click(function(){
                // 收集表单数据
                var name = $("#menuUpdateModal [name=name]").val();
                var url = $("#menuUpdateModal [name=url]").val();
                var icon = $("#menuUpdateModal [name=icon]:checked").val();
                // 发送Ajax 请求
                $.ajax({
                    "url":"menu/update.json",
                    "type":"post",
                    "data":{
                        "id": window.id,
                        "name":name,
                        "url":url,
                        "icon":icon
                    },
                    "dataType":"json",
                    "success":function(response){
                        var result = response.result;
                        if(!result) {
                            layer.msg("操作成功！");
                            // 重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新
                            // 否则有可能刷新不到最新的数据，因为这里是异步的
                            generateTree();
                        }
                        if(!result) {
                            layer.msg("操作失败！"+response.data);
                        }
                    },
                    "error":function(response){
                        layer.msg(response.status+" "+response.statusText);
                    }
                });
                // 关闭模态框
                $("#menuUpdateModal").modal("hide");
            });
            //3、删除节点
            $("#confirmBtn").click(function(){
                $.ajax({
                    "url":"menu/remove.json",
                    "type":"post",
                    "data":{
                        "id":window.id
                    },
                    "dataType":"json",
                    "success":function(response){
                        var result = response.result;
                        if(result) {
                            layer.msg("操作成功！");
                            // 重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新
                            // 否则有可能刷新不到最新的数据，因为这里是异步的
                            generateTree();
                        }
                        if(!result) {
                            layer.msg("操作失败！"+response.message);
                        }
                    },
                    "error":function(response){
                        layer.msg(response.status+" "+response.statusText);
                    }
                });
                // 关闭模态框
                $("#menuConfirmModal").modal("hide");
            });
        });
    </script>
</head>
<body>
<%@ include file="include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@ include file="include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-menu-add.jsp"%>
<%@include file="modal-menu-confirm.jsp"%>
<%@include file="modal-menu-update.jsp"%>
</body>
</html>
