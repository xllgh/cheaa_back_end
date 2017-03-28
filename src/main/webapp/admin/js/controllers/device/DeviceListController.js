/**
 * 设备管理
 */

'use strict';

MetronicApp.controller('DeviceListController', function ($rootScope, $scope, $http, $timeout) {

    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        Metronic.initAjax();

        var DeviceList = function () {
            var grid = new Datatable();

            grid.init({
                src: "#datatable_ajax",
                loadingMessage: 'Loading...',
                dataTable: {
                    "ajax": {
                        "url": "web/devices/list",
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
                            "title": "平台名称",
                            "data": "platformName"
                        },
                        {
                            "title": "设备名称",
                            "data": "deviceName"
                        },
                        {
                            "title": "设备ID",
                            "data": "deviceId"
                        },
                        {
                            "title": "在线状态",
                            "data": function (row, type, set, meta) {
                                return row.online ? "在线" : "离线";
                            }
                        },
                        {
                            "title": "操作",
                            "width": "120px",
                            "data": function (row, type, set, meta) {
                                return "<button class='delete-button table-action-button' data-id='"+ row.deviceId +"'><i class='fa fa-trash'/>删除</button>";
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

            // 删除
            $(".delete-button").die("click").live("click", function () {
                var id = $(this).attr('data-id');
                bootbox.dialog({
                    message: "确定删除该设备？",
                    title: "删除提示",
                    buttons: {
                        success: {
                            label: "取消",
                            className: "default"
                        },
                        danger: {
                            label: "确定",
                            className: "red",
                            callback: function () {
                                var postJson = {
                                    "deviceId": id
                                };
                                $.ajax({
                                    type: "POST",
                                    url: "web/devices/delete",
                                    data: postJson,
                                    dataType: "json",
                                    success: function (data) {
                                        if (data.code == 0) {
                                            Metronic.alert({
                                                type: 'success',
                                                icon: 'check',
                                                message: '删除成功！',
                                                container: grid.getTableWrapper(),
                                                place: 'prepend',
                                                closeInSeconds: 5
                                            });
                                            grid.getDataTable().ajax.reload();
                                        } else {
                                            Metronic.alert({
                                                type: 'danger',
                                                icon: 'warning',
                                                message: '删除失败！',
                                                container: grid.getTableWrapper(),
                                                place: 'prepend',
                                                closeInSeconds: 5
                                            });
                                        }
                                    },
                                    timeout: 3000
                                });
                            }
                        }
                    }
                });
            });
        }
        DeviceList();
    });

});
