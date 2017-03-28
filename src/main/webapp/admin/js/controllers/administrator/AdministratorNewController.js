/**
 * 管理员管理
 */

'use strict';

MetronicApp.controller('AdministratorNewController', function ($rootScope, $scope, $http, $timeout) {

    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
    $scope.$on('$viewContentLoaded', function () {
        // initialize core components
        Metronic.initAjax();
        // set default layout mode
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        $.validator.addMethod("regex", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Please enter a valid value.");

        var SystemUserNew = function(){
            var iniForm = function(){
                $("#systemUserNewForm").validate({
                    rules:{
                        userName:{
                            required:true,
                            rangelength:[5,16],
                            regex: /^\w+$/,
                            remote:"web/administrator/checkUsername"
                        },
                        password:{
                            required:true,
                            rangelength:[8,12]
                        },
                        try_password:{
                            required:true,
                            rangelength:[8,12]
                        }
                    },
                    messages:{
                        userName:{
                            regex: "请输入5-16位的登录账号（支持数字、字母、下划线）",
                            remote:"此账号已被注册",
                            rangelength:"请输入{0}-{1}位的登录账号（支持数字、字母、下划线）"
                        },
                        password:{
                            rangelength:"请输入{0}-{1}位的登录密码（支持数字、字母、特殊字符）"
                        },
                        try_password:{
                            rangelength:"请输入{0}-{1}位的登录密码（支持数字、字母、特殊字符）"
                        }
                    },
                    ignore:"",
                    errorClass : "font-red-thunderbird",
                    submitHandler:function(form){
                        if($.trim($("input[name='password']").val())!=$.trim($("input[name='try_password']").val())){
                            Metronic.alert({
                                type: 'danger',
                                icon: 'warning',
                                message: "确认密码与密码不一致，请重新输入",
                                container: "#systemUserNewForm",
                                place: 'prepend',
                                closeInSeconds: 5
                            });
                            return false;
                        }
                        var post_data = {
                            "userName":$.trim($("input[name='userName']").val()),
                            "password":$.trim($("input[name='password']").val())
                        };

                        $.ajax({
                            type: "POST",
                            url: "web/administrator/add",
                            contentType: "application/json",
                            data: JSON.stringify(post_data),
                            dataType: "json",
                            success: function (data) {
                                if (data.code == 0) {
                                    window.location.href="#/administrators";
                                } else {
                                    Metronic.alert({
                                        type: 'danger',
                                        icon: 'warning',
                                        message: "添加失败",
                                        container: "#systemUserNewForm",
                                        place: 'prepend',
                                        closeInSeconds: 5
                                    });
                                }
                            },
                            timeout: 3000
                        });

                        // $http.post("/web/administrator/add",post_data).then(function (res) {
                        //     if(res.data.code == 0) {
                        //         window.location.href="#/administrators";
                        //     }else{
                        //         Metronic.alert({
                        //             type: 'danger',
                        //             icon: 'warning',
                        //             message: "添加失败",
                        //             container: "#systemUserNewForm",
                        //             place: 'prepend',
                        //             closeInSeconds: 5
                        //         });
                        //     }
                        // });
                    }
                });
                $(".show-password").on("click",function(){
                    var input = $(this).next("input");
                    if(input.attr("type") == "password") {
                        $(this).removeClass("fa-eye").addClass("fa-eye-slash");
                        input.attr("type","text");
                    }else{
                        $(this).removeClass("fa-eye-slash").addClass("fa-eye");
                        input.attr("type","password");
                    }
                });
            };
            return {
                init:function(){
                    iniForm();
                }
            }
        }();
        SystemUserNew.init();
    });

});
