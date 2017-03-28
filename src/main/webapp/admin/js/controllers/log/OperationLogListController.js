/**
 * 操作日志
 */

'use strict';

MetronicApp.controller('OperationLogListController', function ($rootScope, $scope, $http, $timeout) {

    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        Metronic.initAjax();

        var LogList = function () {
            var grid = new Datatable();

            grid.init({
                src: "#datatable_ajax",
                loadingMessage: 'Loading...',
                dataTable: {
                    "ajax": {
                        "url": "web/log/list",
                    },
                    "columns": [
                        {
                            "title": "序号",
                            "width": "26px",
                            "data": function (row, type, set, meta) {
                                return meta.settings.oAjaxData.start + meta.row + 1;
                            }
                        },
                        {
                            "title": "管理员",
                            "data": function (row, type, set, meta) {
                                return row.userName || "";
                            }
                        },
                        {
                            "title": "操作内容",
                            "data": function (row, type, set, meta) {
                                return row.operationContent || "";
                            }
                        },
                        {
                            "title": "操作时间",
                            "data": function (row, type, set, meta) {
                                if (row.operationTime == null) {
                                    return "";
                                }
                                return DateFormat.format.date(row.operationTime, "yyyy-MM-dd HH:mm:ss");
                            }
                        }
                    ]
                }
            });

            /*搜索*/
            grid.getTableWrapper().on('click', '.table-group-action-search', function (e) {
                e.preventDefault();
                grid.clearAjaxParams();
                $(".table-group-actions select,.table-group-actions input[type!='checkbox']", grid.getTableWrapper()).each(function (index) {
                    grid.setAjaxParam($(this).attr("name"), $(this).val());
                });
                $(".table-group-actions input[type='checkbox']:checked", grid.getTableWrapper()).each(function (index) {
                    var value = $(this).val();
                    grid.setAjaxParam($(this).attr("name"), value);
                });
                grid.getDataTable().ajax.reload();
            });

            /*选择搜索类型清空关键字搜索*/
            grid.getTableWrapper().on('change', 'select[name="searchType"]', function (e) {
                $(this).next("input[name='keyword']").val("");
            });
        }
        LogList();
    });

});
