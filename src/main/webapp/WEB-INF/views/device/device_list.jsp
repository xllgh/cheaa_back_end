<%--
  设备管理
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- BEGIN PAGE HEADER-->
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <img src="assets/global/img/home-bar-icon.png">
            <a href="#/home">CHEAA管理系统</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="#/devices">设备管理</a>
        </li>
    </ul>
</div>

<!-- END PAGE HEADER-->
<!-- BEGIN MAIN CONTENT -->
<div ng-controller="DeviceListController">
    <div class="portlet">
        <div class="portlet-title">
            <div class="caption">
                设备列表
            </div>
        </div>
        <div class="portlet-body">
            <div class="table-container">
                <div class="table-actions-wrapper">
                    <div style="line-height:30px;">
                        <select class="table-group-action-input form-control input-inline input-sm"
                                style="width:100px;" name="searchType">
                            <option value="deviceName">设备名称</option>
                            <option value="platformName">平台名称</option>
                            <option value="deivceId">设备ID</option>
                        </select>
                        <input type="search" class="form-control input-inline input-sm" placeholder="请输入关键字"
                               style="width:180px;" name="keyword">
                        <%--<div class="pull-right">--%>
                            <button class="btn btn-sm dark table-group-action-search bg-blue-sharp"
                                    style="width: 80px;">
                                <i class="fa fa-search"></i>
                                搜索
                            </button>
                        <%--</div>--%>
                    </div>
                </div>
                <table class="table table-striped table-bordered table-hover text-center" id="datatable_ajax"></table>
            </div>
        </div>
    </div>
</div>
<!-- END MAIN CONTENT -->