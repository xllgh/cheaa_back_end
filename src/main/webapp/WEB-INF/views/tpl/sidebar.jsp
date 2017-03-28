<%@ page import="org.apache.shiro.SecurityUtils" %><%--
  Created by IntelliJ IDEA.
  User: Roc
  Date: 2015/8/18
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 不缓存菜单，修改权限后，重新登录马上更改菜单 -->
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -10);
%>
<div class="page-sidebar navbar-collapse collapse">
    <!-- BEGIN SIDEBAR MENU -->
    <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
    <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
    <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
    <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <ul class="page-sidebar-menu" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200"
        ng-class="{'page-sidebar-menu-closed': settings.layout.pageSidebarClosed}">
        <!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
        <li class="sidebar-search-wrapper">
            <!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
            <!-- DOC: Apply "sidebar-search-bordered" class the below search form to have bordered search box -->
            <!-- DOC: Apply "sidebar-search-bordered sidebar-search-solid" class the below search form to have bordered & solid search box -->
            <%--<form class="sidebar-search sidebar-search-bordered" action="extra_search.html" method="POST">
              <a href="javascript:;" class="remove">
                <i class="icon-close"></i>
              </a>
              <div class="input-group">
                <input type="text" class="form-control" placeholder="Search...">
                          <span class="input-group-btn">
                          <a href="javascript:;" class="btn submit"><i class="icon-magnifier"></i></a>
                          </span>
              </div>
            </form>--%>
            <!-- END RESPONSIVE QUICK SEARCH FORM -->
        </li>
        <li class="start" style="background-color: #45aae1">
            <a href="#">
                <img src="assets/global/img/home-icon.png">
                <span class="title" style="color: #fff;">CHEAA管理系统</span>
            </a>
        </li>
            <li>
                <a href="#/devices">
                    <span class="title">设备管理</span>
                </a>
            </li>
            <li>
                <a href="#/administrators">
                    <span class="title">管理员管理</span>
                </a>
            </li>
            <li>
                <a href="#/users">
                    <span class="title">用户管理</span>
                </a>
            </li>

            <li>
                <a href="#/logs">
                    <span class="title">操作日志</span>
                </a>
            </li>
    </ul>
    <!-- END SIDEBAR MENU -->
</div>
