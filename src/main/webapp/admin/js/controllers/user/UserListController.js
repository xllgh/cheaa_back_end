/**
 * 用户管理
 */

'use strict';

MetronicApp.controller('UserListController', function ($rootScope, $scope, $http, $timeout) {

    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        Metronic.initAjax();

        var UserList = function () {
            var grid = new Datatable();

            grid.init({
                src: "#datatable_ajax",
                loadingMessage: 'Loading...',
                dataTable: {
                    "ajax": {
                        "url": "web/users/list",
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
                            "title": "用户名",
                            "data": function (row, type, set, meta) {
                                return row.userId || "";
                            }
                        },
                        {
                            "title": "显示全列表",
                            "data": function (row, type, set, meta) {
                                if(row.isAll==0){
                                    return "<input type='checkbox' name='list_all' data-id='"+row.userId+"' >";
                                }else{
                                    return "<input type='checkbox' name='list_all' data-id='"+row.userId+"' checked>";
                                }

                            }
                        },
                        {
                            "title": "操作",
                            "data": function (row, type, set, meta) {
                                var href = "#/users/" + row.userId + "/devices";
                                return "<a style='color:#3288cb;background-color:#edf5ff;border:1px solid #3288cb;' class='btn btn-xs' href='"+ href +"'>查看设备列表</a>";
                            }
                        }
                    ]
                }
            });

            /*搜索*/
            $(".table-group-action-search").on("click", function (e) {
                e.preventDefault();
                grid.clearAjaxParams();
                $(".action_groups select,.action_groups input[type!='checkbox']").each(function (index) {
                    grid.setAjaxParam($(this).attr("name"), $(this).val());
                });
                $(".action_groups input[type='checkbox']:checked").each(function (index) {
                    var value = $(this).val();
                    grid.setAjaxParam($(this).attr("name"), value);
                });
                grid.getDataTable().ajax.reload();
            });
            // grid.getTableWrapper().on('click', '.table-group-action-search', function (e) {
            //     e.preventDefault();
            //     grid.clearAjaxParams();
            //     $(".table-group-actions select,.table-group-actions input[type!='checkbox']", grid.getTableWrapper()).each(function (index) {
            //         grid.setAjaxParam($(this).attr("name"), $(this).val());
            //     });
            //     $(".table-group-actions input[type='checkbox']:checked", grid.getTableWrapper()).each(function (index) {
            //         var value = $(this).val();
            //         grid.setAjaxParam($(this).attr("name"), value);
            //     });
            //     grid.getDataTable().ajax.reload();
            // });

            /*选择搜索类型清空关键字搜索*/
            grid.getTableWrapper().on('change', 'select[name="searchType"]', function (e) {
                $(this).next("input[name='keyword']").val("");
            });

            $("input[type='checkbox'][name='list_all']").die('click').live('click',function(e){
                var the = this;
                var data = {
                    "userId":$(this).attr("data-id"),
                    "isAll":$(this).is(":checked")?1:0
                };

                $.ajax({
                    type: "POST",
                    url: "web/users/changePermission",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: function (data) {
                        if (data.code == 0) {
                            Metronic.alert({
                                type: 'success',
                                icon: 'warning',
                                message: '操作成功',
                                container: $("#datatable_ajax_wrapper"),
                                place: 'prepend',
                                closeInSeconds:3
                            });
                        } else {
                            $(the).attr("checked",!$(the).is(":checked"));
                            Metronic.alert({
                                type: 'warning',
                                icon: 'warning',
                                message: '操作失败，请重新尝试。',
                                container: $("#datatable_ajax_wrapper"),
                                place: 'prepend',
                                closeInSeconds:3
                            });
                        }
                    },
                    timeout: 3000
                });

            });
        }
        UserList();
    });

});
