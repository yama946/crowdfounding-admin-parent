<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="utf-8">
<head>
    <%@include file="include-head.jsp"%>
    <script type="text/javascript" src="crowd/my-role.js"></script>
    <script type="text/javascript">
        $(function () {
            window.pageNum = 1;
            window.pageSize = 5;
            window.keyword = "";
            //指定分页操作
            generatePage();
            // 3.给查询按钮绑定单击响应函数
            $("#searchBtn").click(function(){
                // ①获取关键词数据赋值给对应的全局变量
                window.keyword = $("#keywordInput").val();
                // ②调用分页函数刷新页面
                generatePage();
            });
            //绑定单击响应函数，进行激活模态框
            $("#showAddModalBtn").click(function () {
                $("#addModal").modal("show");
            });

            // 5.给新增模态框中的保存按钮绑定单击响应函数
            $("#saveRoleBtn").click(function () {
                // ①获取用户在文本框中输入的角色名称
                // #addModal 表示找到整个模态框
                // 空格表示在后代元素中继续查找
                // [name=roleName]表示匹配name 属性等于roleName 的元素
                //jquery.trim()去掉字符串起始和结尾的空格。

                var roleName = $.trim($("#inputSuccess4[name='roleName']").val());

                console.log("开始打印要插入的角色名："+roleName);

                // ②发送Ajax 请求
                $.ajax({
                    url:"role/save.json",
                    type:"post",
                    data:{
                        name:roleName
                    },
                    async:false,
                    dataType:"json",
                    success:function (response) {
                        console.log("ajax中的返回值：");
                        console.log(response);
                        var result = response.result;
                        if (result){
                            layer.msg("操作成功");
                            //将页码设置为最大，以便于显示最后一页，查看添加结果
                            window.keyword = roleName;
                            //刷新页面
                            generatePage();
                            //清理模态框
                            $("#addModal [name=roleName]").val("");
                        }
                        if (!result){
                            layer.msg("操作失败:"+response.message);
                        }
                    },
                    error:function (response) {
                        layer.msg(response.status+" "+response.statusText);
                    }
                });

                //关闭模态框
                $("#addModal").modal("hide");
            });
        });

        //更新操作
        $(function () {
            // 6.给页面上的“铅笔”按钮绑定单击响应函数，目的是打开模态框
            // 传统的事件绑定方式只能在第1个页面有效，翻页后失效了,
            //因为这些标签被重新生成了

            /* $(".pencilBtn").click(function(){
                 alert("aaaa...");
             });*/

            // 使用jQuery 对象的on()函数可以解决上面问题
            // ①首先找到所有“动态生成”的元素所附着的“静态”元素
            // ②on()函数的第一个参数是事件类型
            // ③on()函数的第二个参数是找到真正要绑定事件的元素的选择器
            // ③on()函数的第三个参数是事件的响应函数
            $("#rolePageBody").on("click",".pencilBtn",function(){
                // 打开模态框
                $("#updateModal").modal("show");
                // 获取表格中当前行中的角色名称
                var roleName = $(this).parent().prev().text();
                // 获取当前角色的id
                // 依据是：var pencilBtn = "<button id='"+roleId+"' ……这段代码中我们把roleId 设置到id 属性了
                // 为了让执行更新的按钮能够获取到roleId 的值，把它放在全局变量上
                //也就是说使用var进行定义变量，作用范围只在当前定义的函数中
                window.roleId = this.id;

                // 使用roleName 的值设置模态框中的文本框
                $("#updateModal [name=roleName]").val(roleName);
            });



            // 7.给更新模态框中的更新按钮绑定单击响应函数
            $("#updateRoleBtn").click(function(){
                // ①从文本框中获取新的角色名称
                var roleName = $("#updateModal [name=roleName]").val();
                // ②发送Ajax 请求执行更新
                $.ajax({
                    "url":"role/update.json",
                    "type":"post",
                    "data":{
                        "id":window.roleId,
                        "name":roleName
                    },
                    "dataType":"json",
                    "success":function(response){
                        var result = response.result;
                        if(result) {
                            // ③关闭模态框

                            layer.msg("操作成功！");
                            window.keyword=roleName;
                            // 重新加载分页数据
                            generatePage();
                        }
                        if(!result) {
                            console.log(result);
                            layer.msg("操作失败！"+response.data);
                        }
                    },
                    "error":function(response){
                        console.log(response)
                        layer.msg(response.responseJson.data);
                    }
                });
                $("#updateModal").modal("hide");
            });

            //8.点击删除模态框的确认按扭，执行删除操作
            $("#removeRoleBtn").click(function () {
                //将从全局变量中获取id数组对象转成json对象
                var requestBody = JSON.stringify(window.roleIdArray);

                //发送ajax请求执行删除操作
                $.ajax({
                    url:"role/remove/by/id/array.json",
                    type:"post",
                    //后台会自动将json转换为java对象
                    data:requestBody,
                    contentType:"application/json;charset=UTF-8",
                    dataType:"json",
                    success:function(response){
                        var result = response.result;
                        if(result) {
                            layer.msg("操作成功！");
                            // 重新加载分页数据
                            generatePage();
                        }
                        if(!result) {
                            layer.msg("操作失败！"+response.message);
                        }
                    },
                    error:function(response){
                        layer.msg(response.status+" "+response.statusText);
                    }
                });
                //关闭模态框
                $("#confirmModal").modal("hide");
            });

            //9、给单条删除绑定单击响应函数
            $("#rolePageBody").on("click",".removeBtn",function(){
                //获取角色对象数据传入函数
                var roleName = $(this).parent().prev().text();

                var roleArray = [{
                    roleId:this.id,
                    roleName:roleName
                }];

                //调用专门的函数打开模态框
                showConfirmModal(roleArray)
            });

            //10.给总的checkbox绑定单击响应函数
            $("#summaryBox").click(function () {
                //1.获取当前多选框当前的状态
                var currentStatus = this.checked;

                //2.用当前多选框的状态设置其他多选框
                $(".itemBox").prop("checked",currentStatus);
            });

            //11.全选、全不选的反向操作,当所有都选，全选框也选
            $("#rolePageBody").on("click",".itemBox",function(){

                //获取当前已经选中的itemBox数量
                var checkedBoxCount = $(".itemBox:checked").length;

                //获取全部的itemBox的数量
                var totalBoxCount = $(".itemBox").length;

                //使用两者的比较结果设置全选框的状态
                $("#summaryBox").prop("checked",checkedBoxCount==totalBoxCount);
            });

            //12.给批量删除的按钮绑定单击响应函数
            $("#batchRemoveBtn").click(function () {
                //创建数组，存放角色对象
                var roleArray = [];
                //遍历当前选中的多选框
                $(".itemBox:checked").each(function () {
                    //使用this引用当前遍历得到的多选框
                    var roleId = this.id;
                    //通过DOM操作获取角色名称
                    var roleName = $(this).parent().next().text();
                    roleArray.push({
                        "roleId":roleId,
                        "roleName":roleName
                    });
                });
                //判断当前是否选择
                if (roleArray == 0){
                    layer.msg("请选择删除的角色...")
                    return ;
                }
                //显示模态框
                showConfirmModal(roleArray);
            });

        });

        //角色分配权限
        $(function () {
            // 13.给分配权限按钮绑定单击响应函数
            $("#rolePageBody").on("click",".checkBtn",function(){
                // 打开模态框
                $("#assignModal").modal("show");

                window.roleId = this.id;

                // 在模态框中装载树Auth 的形结构数据
                fillAuthTree();
            });

            // 14.给分配权限模态框中的“分配”按钮绑定单击响应函数
            $("#assignBtn").click(function(){
                // ①收集树形结构的各个节点中被勾选的节点
                // [1]声明一个专门的数组存放id
                var authIdArray = [];
                // [2]获取zTreeObj 对象
                var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
                // [3]获取全部被勾选的节点
                var checkedNodes = zTreeObj.getCheckedNodes();
                // [4]遍历checkedNodes
                for(var i = 0; i < checkedNodes.length; i++) {
                    var checkedNode = checkedNodes[i];
                    var authId = checkedNode.id;
                    authIdArray.push(authId);
                }
                // ②发送请求执行分配
                var requestBody = {
                    "authIdArray":authIdArray,
                    // 为了服务器端handler 方法能够统一使用List<Integer>方式接收数据，roleId 也存入数组
                    "roleId":[window.roleId]
                };
                requestBody = JSON.stringify(requestBody);
                console.log(requestBody);
                $.ajax({
                    "url":"assign/do/role/assign/auth.json",
                    "type":"post",
                    "data":requestBody,
                    "contentType":"application/json;charset=UTF-8",
                    "dataType":"json",
                    "success":function(response){
                        var result = response.result;
                        if(result) {
                            layer.msg("操作成功！");
                        }
                        if(!result) {
                            layer.msg("操作失败！"+response.message);
                        }
                    },
                    "error":function(response) {
                        layer.msg(response.status+" "+response.statusText);
                    }
                });
                $("#assignModal").modal("hide");
            });
        })

    </script>
</head>
<body>
<%@include file="include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody"><!--此处使用js操作进行填充表格--></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
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
<%--引入模态框，模态框在不调用的情况下是隐藏的。--%>
<%@include file="modal-role-add.jsp"%>
<%@include file="modal-role-assign-auth.jsp"%>
<%@include file="modal-role-remove.jsp"%>
<%@include file="modal-role-update.jsp"%>
<%--模态框调用方式，获取模态框对象，通过.modal("show")进行调用弹出--%>
</body>
</html>



