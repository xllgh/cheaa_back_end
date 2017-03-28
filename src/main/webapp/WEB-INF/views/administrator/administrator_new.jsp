<%--
  Created by IntelliJ IDEA.
  User: Roc
  Date: 2015/9/7
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- BEGIN PAGE HEADER-->
<%--<h3 class="page-title">
    系统成员管理
    <small>添加成员</small>
</h3>--%>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <img src="assets/global/img/home-bar-icon.png">
            <a href="#/home">CHEAA管理系统</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="#/administrators">管理员管理</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="#">新增管理员</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN MAIN CONTENT -->
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">
                <div class="caption">
                    新增管理员
                </div>
            </div>
            <div class="portlet-body form">
                <!-- BEGIN FORM-->
                <form class="form-horizontal" method="post" id="systemUserNewForm" autocomplete="off">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label class="control-label col-md-4"><span class="font-red">*</span>用户名：</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" placeholder="请输入5-16位的登录账号（支持数字、字母、下划线）" <%--onkeyup="this.value=this.value.replace(/[^\w\.\/]/ig,'')"--%> name="userName" autocomplete="off">
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label class="control-label col-md-4"><span class="font-red">*</span>密码：</label>
                                    <div class="col-md-8">
                                        <div class="input-icon right">
                                            <input style="display:none"><!-- for disable autocomplete on chrome -->
                                            <i class="fa fa-eye show-password" style="cursor:pointer;"></i>
                                            <input type="password" class="form-control" placeholder="请输入8-12位的登录密码（支持数字、字母、特殊字符）"
                                                   name="password" autocomplete="off">
                                            <span class="help-block"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label class="control-label col-md-4"><span class="font-red">*</span>确认密码：</label>
                                    <div class="col-md-8">
                                        <div class="input-icon right">
                                            <input style="display:none"><!-- for disable autocomplete on chrome -->
                                            <i class="fa fa-eye show-password" style="cursor:pointer;"></i>
                                            <input type="password" class="form-control" placeholder="请输入与密码信息一致的确认密码"
                                                   name="try_password" autocomplete="off">
                                            <span class="help-block"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row">
                                    <div class="col-md-offset-4 col-md-8">
                                        <button type="submit" class="btn bg-blue-sharp">提交</button>
                                        <button type="button" class="btn default"
                                                onclick="window.location.href = '#/administrators';">取消
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                            </div>
                        </div>
                    </div>
                </form>
                <!-- END FORM-->
            </div>
        </div>
        <!-- End: life time stats -->
    </div>
</div>
<!-- END MAIN CONTENT -->
