/***
 Metronic AngularJS App Main Script
 ***/

/* Metronic App */
var MetronicApp = angular.module("MetronicApp", [
    "ui.router",
    "ui.bootstrap",
    "oc.lazyLoad",
    "ngSanitize",
    "ng.ueditor",
    "angularFileUpload"
]);

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
MetronicApp.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
        // global configs go here
        debug:false
    });
}]);

/********************************************
 BEGIN: BREAKING CHANGE in AngularJS v1.3.x:
 *********************************************/
/**
 `$controller` will no longer look for controllers on `window`.
 The old behavior of looking on `window` for controllers was originally intended
 for use in examples, demos, and toy apps. We found that allowing global controller
 functions encouraged poor practices, so we resolved to disable this behavior by
 default.

 To migrate, register your controllers with modules rather than exposing them
 as globals:

 Before:

 ```javascript
 function MyController() {
  // ...
}
 ```

 After:

 ```javascript
 angular.module('myApp', []).controller('MyController', [function() {
  // ...
}]);

 Although it's not recommended, you can re-enable the old behavior like this:

 ```javascript
 angular.module('myModule').config(['$controllerProvider', function($controllerProvider) {
  // this option might be handy for migrating old apps, but please don't use it
  // in new ones!
  $controllerProvider.allowGlobals();
}]);
 **/

//AngularJS v1.3.x workaround for old style controller declarition in HTML
MetronicApp.config(['$controllerProvider', function($controllerProvider) {
    // this option might be handy for migrating old apps, but please don't use it
    // in new ones!
    $controllerProvider.allowGlobals();
}]);

/********************************************
 END: BREAKING CHANGE in AngularJS v1.3.x:
 *********************************************/

/* Setup global settings */
MetronicApp.factory('settings', ['$rootScope', function($rootScope) {
    // supported languages
    var settings = {
        layout: {
            pageSidebarClosed: false, // sidebar menu state
            pageBodySolid: false, // solid body color state
            pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
        },
        layoutImgPath: Metronic.getAssetsPath() + 'admin/layout/img/',
        layoutCssPath: Metronic.getAssetsPath() + 'admin/layout/css/'
    };

    $rootScope.settings = settings;

    return settings;
}]);

/* Setup App Main Controller */
MetronicApp.controller('AppController', ['$scope', '$rootScope', function($scope, $rootScope) {
    $scope.$on('$viewContentLoaded', function() {
        Metronic.initComponents(); // init core components
        //Layout.init(); //  Init entire layout(header, footer, sidebar, etc) on page load if the partials included in server side instead of loading with ng-include directive 
    });
}]);

/***
 Layout Partials.
 By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial
 initialization can be disabled and Layout.init() should be called on page load complete as explained above.
 ***/

/* Setup Layout Part - Header */
MetronicApp.controller('HeaderController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader(); // init header
    });
}]);

/* Setup Layout Part - Sidebar */
MetronicApp.controller('SidebarController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initSidebar(); // init sidebar
    });
}]);

/* Setup Layout Part - Quick Sidebar */
/*MetronicApp.controller('QuickSidebarController', ['$scope', function($scope) {
 $scope.$on('$includeContentLoaded', function() {
 setTimeout(function(){
 QuickSidebar.init(); // init quick sidebar
 }, 2000);
 });
 }]);*/

/* Setup Layout Part - Theme Panel */
/*MetronicApp.controller('ThemePanelController', ['$scope', function($scope) {
 $scope.$on('$includeContentLoaded', function() {
 Demo.init(); // init theme panel
 });
 }]);*/

/* Setup Layout Part - Footer */
MetronicApp.controller('FooterController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initFooter(); // init footer
    });
}]);

/* Setup Rounting For All Pages */
MetronicApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    // Redirect any unmatched url
    $urlRouterProvider.otherwise("/devices");

    $stateProvider

    // //首页
    // .state('home',{
    //     url: "/home",
    //     templateUrl: "home",
    //     data: {pageTitle: '首页'},
    //     controller: "HomeController",
    //     resolve: {
    //         deps: ['$ocLazyLoad', function($ocLazyLoad) {
    //             return $ocLazyLoad.load({
    //                 name: 'MetronicApp',
    //                 insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
    //                 files: [
    //                     'assets/global/plugins/echarts-2.2.7/build/dist/echarts.js',
    //                     'admin/js/controllers/home/HomeController.js'
    //                 ]
    //             });
    //         }]
    //     }
    // })

        .state('devices',{
            url: "/devices",
            templateUrl: "web/devices/list",
            data: {pageTitle: '设备管理'},
            controller: "DeviceListController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',
                            'assets/global/plugins/datatables/all.min.js',
                            'assets/global/scripts/datatable.js',
                            'admin/js/controllers/device/DeviceListController.js'
                        ]
                    });
                }]
            }
        })

        .state('administrators',{
            url: "/administrators",
            templateUrl: "web/administrator/list",
            data: {pageTitle: '管理员管理'},
            controller: "AdministratorListController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',
                            'assets/global/plugins/datatables/all.min.js',
                            'assets/global/scripts/datatable.js',
                            'admin/js/controllers/administrator/AdministratorListController.js'
                        ]
                    });
                }]
            }
        })

        .state('addAdministrators',{
            url: "/administrators/add",
            templateUrl: "web/administrator/add",
            data: {pageTitle: '管理员管理'},
            controller: "AdministratorNewController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'admin/js/controllers/administrator/AdministratorNewController.js'
                        ]
                    });
                }]
            }
        })

        .state('users',{
            url: "/users",
            templateUrl: "web/users/list",
            data: {pageTitle: '用户管理'},
            controller: "UserListController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',
                            'assets/global/plugins/datatables/all.min.js',
                            'assets/global/scripts/datatable.js',
                            'admin/js/controllers/user/UserListController.js'
                        ]
                    });
                }]
            }
        })

        .state('users-devices',{
            url: "/users/:id/devices",
            templateUrl: "web/users/devices/list",
            data: {pageTitle: '用户的设备列表'},
            controller: "UserDeviceListController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',
                            'assets/global/plugins/datatables/all.min.js',
                            'assets/global/scripts/datatable.js',
                            'admin/js/controllers/user/UserDeviceListController.js'
                        ]
                    });
                }]
            }
        })

        .state('logs',{
            url: "/logs",
            templateUrl: "web/log/list",
            data: {pageTitle: '操作日志'},
            controller: "OperationLogListController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',
                            'assets/global/plugins/datatables/all.min.js',
                            'assets/global/scripts/datatable.js',
                            'admin/js/controllers/log/OperationLogListController.js'
                        ]
                    });
                }]
            }
        })

}]);

/* Init global settings and run the app */
MetronicApp.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    $rootScope.$state = $state; // state to be accessed from view
}]);