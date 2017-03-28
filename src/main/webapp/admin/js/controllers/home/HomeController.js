'use strict';

MetronicApp.controller('HomeController', function ($rootScope, $scope, $http, $timeout) {

    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        Metronic.initAjax();

        var Home = function () {}

    });

});