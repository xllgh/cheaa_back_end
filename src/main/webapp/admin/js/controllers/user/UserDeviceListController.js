/**
 * 用户管理
 */

'use strict';

MetronicApp.controller('UserDeviceListController', function ($rootScope, $scope, $stateParams, $http, $timeout) {

    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        Metronic.initAjax();

        var initTitle = function() {
            $("#user-devices-title").html("用户" + $stateParams.id + "的设备列表");
        }

        var DeviceList = function () {
            initTitle();
            var grid = new Datatable();

            grid.init({
                src: "#datatable_ajax",
                loadingMessage: 'Loading...',
                dataTable: {
                    "ajax": {
                        "url": "web/users/devices/list",
                        "data": {"userId": $stateParams.id}
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
                            "data": function (row, type, set, meta) {
                                return row.platformName || "平台名称";
                            }
                        },
                        {
                            "title": "设备名称",
                            "data": function (row, type, set, meta) {
                                return row.deviceName || "设备名称";
                            }
                        },
                        {
                            "title": "设备ID",
                            "data": function (row, type, set, meta) {
                                return row.deviceId || "设备ID";
                            }
                        },
                        {
                            "title": "操作",
                            "data": function (row, type, set, meta) {
                                var bindingId = row.id.toString();
                                return "<button class='unbind-device-button table-action-button' data-id='"+ row.id +"'>设备解绑</button>";
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

            // 设备解绑
            $(".unbind-device-button").die("click").live("click", function () {
                var id = $(this).attr('data-id');
                bootbox.dialog({
                    message: "确定解绑该设备？",
                    title: "设备解绑",
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
                                    "bindingId": id
                                };
                                $.ajax({
                                    type: "POST",
                                    url: "web/users/devices/unbind",
                                    data: postJson,
                                    dataType: "json",
                                    success: function (data) {
                                        if (data.code == 0) {
                                            Metronic.alert({
                                                type: 'success',
                                                icon: 'check',
                                                message: '设备解绑成功！',
                                                container: grid.getTableWrapper(),
                                                place: 'prepend',
                                                closeInSeconds: 5
                                            });
                                            grid.getDataTable().ajax.reload();
                                        } else {
                                            Metronic.alert({
                                                type: 'danger',
                                                icon: 'warning',
                                                message: '设备解绑失败！',
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
