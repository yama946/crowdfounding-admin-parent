//-------角色分配权限----------

// 声明专门的函数用来在分配Auth 的模态框中显示Auth 的树形结构数据
function fillAuthTree() {
    // 1.发送Ajax 请求查询Auth 数据
    var ajaxReturn = $.ajax({
        "url":"assgin/get/all/auth.json",
        "type":"post",
        "dataType":"json",
        "async":false
    })
    console.log("获取所有权限信息返回值");
    console.log(ajaxReturn);
    if(ajaxReturn.status != 200) {
        layer.msg(" 请求处理出错！ 响应状态码是： "+ajaxReturn.status+" 说明是："+ajaxReturn.statusText);
        return ;
    }
    // 2.从响应结果中获取Auth 的JSON 数据
    // 从服务器端查询到的list 不需要组装成树形结构，这里我们交给zTree 去组装
    var authList = ajaxReturn.responseJSON.data;
    // 3.准备对zTree 进行设置的JSON 对象
    var setting = {
        "data": {
            "simpleData": {
                // 开启简单JSON 功能
                "enable": true,
                // 使用categoryId 属性关联父节点，不用默认的pId 了
                "pIdKey": "categoryId"
            },
            "key": {
                // 使用title 属性显示节点名称，不用默认的name 作为属性名了
                "name": "title"
            }
        },
        "check": {
            "enable": true
        }
    };
    // 4.生成树形结构
    // <ul id="authTreeDemo" class="ztree"></ul>
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);
    // 获取zTreeObj 对象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    // 调用zTreeObj 对象的方法，把节点展开
    zTreeObj.expandAll(true);


    // 5.查询已分配的Auth 的id 组成的数组,回显数据
    ajaxReturn = $.ajax({
        "url":"assign/get/assigned/auth/id/by/role/id.json",
        "type":"post",
        "data":{
            "roleId":window.roleId
        },
        "dataType":"json",
        "async":false
    });
    if(ajaxReturn.status != 200) {
        layer.msg(" 请求处理出错！ 响应状态码是： "+ajaxReturn.status+" 说明是："+ajaxReturn.statusText);
        return ;
    }
    // 从响应结果中获取authIdArray
    var authIdArray = ajaxReturn.responseJSON.data;
    // 6.根据authIdArray 把树形结构中对应的节点勾选上
    // ①遍历authIdArray
    for(var i = 0; i < authIdArray.length; i++) {
        var authId = authIdArray[i];
        // ②根据id 查询树形结构中对应的节点
        var treeNode = zTreeObj.getNodeByParam("id", authId);
        // ③将treeNode 设置为被勾选
        // checked 设置为true 表示节点勾选
        var checked = true;
        // checkTypeFlag 设置为false，表示不“联动”，不联动是为了避免把不该勾选的勾选上
        var checkTypeFlag = false;
        // 执行
        zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
    }
}



//删除角色操作，声明专门的函数显示确认模态框
function showConfirmModal(roleArray) {
    //显示模态框
    $("#confirmModal").modal("show");

    //清除旧的数据
    $("#roleNameDiv").empty();

    //在全局变量范围中创建数组来存放角色Id
    window.roleIdArray = [];

    //循环遍历数组
    for (var i=0;i<roleArray.length;i++){
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName+" ");
        var roleId = role.roleId;

        //调用数组对象的push方法存入新元素
        window.roleIdArray.push(roleId);

    }
}

//-------角色分配权限----------


//执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage(){
    //1.获取分页数据
    var pageInfo = getPageInfoRemote();

    //2.填充表格
    fillTableBody(pageInfo);
}

//远程访问服务端程序中获取pageinfo数据，ajax进行获取数据
function getPageInfoRemote(){
    //调用$.ajax()函数发送请求并接受$.ajax()函数的返回值
    var ajaxResult = $.ajax({
        url:"role/get/page/info.json",
        type:"post",
        data:{
            pageNum:window.pageNum,
            pageSize:window.pageSize,
            keyword:window.keyword
        },
        async:false,
        dataType:"json"
    });

    console.log("ajax函数返回值");
    console.log(ajaxResult);

    //判断当前响应状态码是否为200
    var statusCode = ajaxResult.status;

    //如果当前响应状态码不是200，说明发生了错误或者其他意外情况，显示提示信息，让当前函数停止执行
    if(statusCode != 200){
        layer.alert("失败！响应状态码="+statusCode+"说明信息="+ajaxResult.statusTest);
        return null;
    }

    //如果响应状态码时200，说明请求处理成功，获取pageInfo
    var resultEntity = ajaxResult.responseJSON;

    //从resultEntity中获取result属性
    var result=resultEntity.result;

    // 判断result 是否成功
    if(!result) {
        layer.alert(resultEntity.message);
        return null;
    }
    // 确认result 为成功后获取pageInfo
    var pageInfo = resultEntity.data;

    // 返回pageInfo
    return pageInfo;
}

// 填充表格
function fillTableBody(pageInfo) {

    // 清除tbody 中的旧的内容
    $("#rolePageBody").empty();

    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    // 判断pageInfo 对象是否有效
    if(pageInfo == null || pageInfo == undefined || pageInfo.list
        == null || pageInfo.list.length== 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>");
        return;
    }

    // 使用pageInfo 的list 属性填充tbody
    for(var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];

        var roleId = role.id;

        // console.log("打印id属性值："+roleId);

        var roleName = role.name;

        var numberTd = "<td>"+(i+1)+"</td>";

        var checkboxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";

        var roleNameTd = "<td>"+roleName+"</td>";

        var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class='glyphicon glyphicon-check'></i></button>";

        // 通过button 标签的id 属性（别的属性其实也可以）把roleId 值传递到button 按钮的单击响应函数中，在单击响应函数中使用this.id
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class='glyphicon glyphicon-pencil'></i></button>";

        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";

        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";

        $("#rolePageBody").append(tr);
    }
    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        num_edge_entries: 3,

        num_display_entries: 2,

        callback: paginationCallBack,

        items_per_page: pageInfo.pageSize,

        current_page: pageInfo.pageNum - 1,

        prev_text: "上一页",

        next_text: "下一页"
    }
    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}
// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {
    // 修改window 对象的pageNum 属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();

    // 取消页码超链接的默认行为
    return false;
}


//删除角色操作，声明专门的函数显示确认模态框
function showConfirmModal(roleArray) {
    //显示模态框
    $("#confirmModal").modal("show");

    //清除旧的数据
    $("#roleNameDiv").empty();

    //在全局变量范围中创建数组来存放角色Id
    window.roleIdArray = [];

    //循环遍历数组
    for (var i=0;i<roleArray.length;i++){
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName+" ");
        var roleId = role.roleId;

        //调用数组对象的push方法存入新元素
        window.roleIdArray.push(roleId);

    }
}
