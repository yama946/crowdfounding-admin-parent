<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false" %>
<div id="updateModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">角色更新</h4>
            </div>
            <div class="modal-body">
                <%--这里添加一个表单但是不进行提交，只是为了以表单的形式进行显示--%>
                <form class="form-signin" role="form">
                    <div class="form-group has-success has-feedback">
                        <input type="text" name="roleName" class="form-control" id="inputSuccess4" placeholder="请输入角色名称信息" autofocus>
                        <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="updateRoleBtn" type="button" class="btn btn-success">更新</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
