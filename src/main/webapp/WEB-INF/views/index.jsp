<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.4
Version: 4.0.1
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js" data-ng-app="MetronicApp"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js" data-ng-app="MetronicApp"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" data-ng-app="MetronicApp">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <base href="${basePath}"/>
    <title data-ng-bind="'CHEAA云家电互联管理系统 | ' + $state.current.data.pageTitle"></title>

    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>

    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <!-- <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/> -->
    <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
    <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
    <link href="assets/global/plugins/bootstrap-datepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->

    <!-- BEGIN DYMANICLY LOADED CSS FILES(all plugin and page related styles must be loaded between GLOBAL and THEME css files ) -->
    <link id="ng_load_plugins_before"/>
    <!-- END DYMANICLY LOADED CSS FILES -->

    <!-- BEGIN THEME STYLES -->
    <!-- DOC: To use 'rounded corners' style just load 'components-rounded.css' stylesheet instead of 'components.css' in the below style tag -->
    <link href="assets/global/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
    <link href="assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
    <link href="assets/admin/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME STYLES -->

    <%--<link rel="shortcut icon" href="admin/favicon.ico"/>--%>
    <%--<link rel="shortcut icon"/>--%>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body ng-controller="AppController" class="page-header-fixed page-sidebar-closed-hide-logo page-quick-sidebar-over-content page-on-load" ng-class="{'page-container-bg-solid': settings.layout.pageBodySolid, 'page-sidebar-closed': settings.layout.pageSidebarClosed}">

<!-- BEGIN PAGE SPINNER -->
<div ng-spinner-bar class="page-spinner-bar">
    <div class="bounce1"></div>
    <div class="bounce2"></div>
    <div class="bounce3"></div>
</div>
<!-- END PAGE SPINNER -->

<!-- BEGIN AJAX PAGE SPINNER -->
<div class="page-ajax-spinner-bar">
    <div class="sk-circle">
        <div class="sk-circle1 sk-child"></div>
        <div class="sk-circle2 sk-child"></div>
        <div class="sk-circle3 sk-child"></div>
        <div class="sk-circle4 sk-child"></div>
        <div class="sk-circle5 sk-child"></div>
        <div class="sk-circle6 sk-child"></div>
        <div class="sk-circle7 sk-child"></div>
        <div class="sk-circle8 sk-child"></div>
        <div class="sk-circle9 sk-child"></div>
        <div class="sk-circle10 sk-child"></div>
        <div class="sk-circle11 sk-child"></div>
        <div class="sk-circle12 sk-child"></div>
    </div>
</div>
<!-- END AJAX PAGE SPINNER -->

<!-- BEGIN HEADER -->
<div data-ng-include="'tpl/header'" data-ng-controller="HeaderController" class="page-header navbar navbar-fixed-top">
</div>
<!-- END HEADER -->

<div class="clearfix">
</div>

<!-- BEGIN CONTAINER -->
<div class="page-container">
    <!-- BEGIN SIDEBAR -->
    <div data-ng-include="'tpl/sidebar'" data-ng-controller="SidebarController" class="page-sidebar-wrapper">
    </div>
    <!-- END SIDEBAR -->

    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <div class="page-content" style="padding-top:0;">
            <!-- BEGIN STYLE CUSTOMIZER(optional) 主题 -->
            <%--<div data-ng-include="'tpl/theme_panel'" data-ng-controller="ThemePanelController" class="theme-panel hidden-xs hidden-sm">
            </div>--%>
            <!-- END STYLE CUSTOMIZER -->

            <!-- BEGIN ACTUAL CONTENT -->
            <div ui-view class="fade-in-up">
            </div>
            <!-- END ACTUAL CONTENT -->
        </div>
    </div>
    <!-- END CONTENT -->

    <!-- BEGIN QUICK SIDEBAR -->
    <%--<a href="javascript:;" class="page-quick-sidebar-toggler"><i class="icon-close"></i></a>
    <div data-ng-include="'tpl/quick_sidebar'" data-ng-controller="QuickSidebarController" class="page-quick-sidebar-wrapper"></div>--%>
    <!-- END QUICK SIDEBAR -->
</div>
<!-- END CONTAINER -->

<!-- FAULT INFORMATION TIPS -->

<!-- FAULT INFORMATION TIPS -->

<!-- BEGIN FOOTER -->
<div data-ng-include="'tpl/footer'" data-ng-controller="FooterController" class="page-footer">
</div>
<!-- END FOOTER -->

<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->

<!-- BEGIN CORE JQUERY PLUGINS -->
<!--[if lt IE 9]>
<script src="assets/global/plugins/respond.min.js"></script>
<script src="assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/jquery-dateFormat/dist/dateFormat.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/jquery-validation/js/jquery.vaildate.extend.js" type="text/javascript"></script>
<script src="assets/global/plugins/datatables/all.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/jstree/dist/jstree.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datetimepicker.js" type="text/javascript"></script>
<!-- END CORE JQUERY PLUGINS -->

<!-- BEGIN CORE ANGULARJS PLUGINS -->
<script src="assets/global/plugins/angularjs/angular.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/angularjs/angular-sanitize.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/angularjs/angular-touch.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/angularjs/plugins/angular-ui-router.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/angularjs/plugins/ocLazyLoad.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/angularjs/plugins/ui-bootstrap-tpls.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/morris/raphael-min.js" type="text/javascript"></script>
<script src="assets/global/plugins/angularjs/plugins/angular-file-upload/angular-file-upload.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/angularjs/plugins/angular-ueditor/angular-ueditor.js" type="text/javascript"></script>
<!-- END CORE ANGULARJS PLUGINS -->

<!-- BEGIN Baidu Map -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=2271nKN2AMySCK2zSxZsbAdA"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
<!-- END Baidu Map -->

<!-- BEGIN APP LEVEL ANGULARJS SCRIPTS -->
<script src="admin/js/app.js" type="text/javascript"></script>
<script src="admin/js/filter.js" type="text/javascript"></script>
<script src="admin/js/directives.js" type="text/javascript"></script>
<!-- END APP LEVEL ANGULARJS SCRIPTS -->

<!-- BEGIN APP LEVEL JQUERY SCRIPTS -->`
<script src="assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="assets/global/scripts/common.js" type="text/javascript"></script>
<script src="assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<%--<script src="assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="assets/admin/layout/scripts/demo.js" type="text/javascript"></script>--%>
<!-- END APP LEVEL JQUERY SCRIPTS -->

<script type="text/javascript">
    /* Init Metronic's core jquery plugins and layout scripts */
    $(document).ready(function() {
        Metronic.init(); // Run metronic theme
        Metronic.setAssetsPath('assets/'); // Set the assets folder path
    });
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>