/**
 * Created by Roc on 2015/10/21.
 */
var PasswordReset = function() {

    var handlePasswordReset = function() {

        $('.password-reset-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'font-red-thunderbird', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                newPwd: {
                    required: true
                },
                confirmPwd: {
                    required: true,
                    rangelength: [6, 16],
                    equalTo:"input[name='newPwd']"
                }
            },
            messages:{
                newPwd: {
                    required: "输入新密码"
                },
                confirmPwd:{
                    required: "输入确认密码",
                    rangelength:"请输入长度为 {0} 至 {1} 之间的字串",
                    equalTo: "两次输入密码不一致"
                }
            },
            submitHandler: function(form) {
                //form.submit(); // form validation success, call ajax form submit
                var submit = $(form).find("button[type='submit']");
                submit.text("提交中...");
                $.post("login",$(form).serialize(),function (data) {
                    submit.text("确&nbsp;&nbsp;&nbsp;&nbsp;认");
                    if(data.code == 0) {
                        $(".password-reset-form").hide();
                        $(".password-reset-success").show();
                    }else{
                        $('.alert-danger span', $('.password-reset-form')).text(data.message);
                        $('.alert-danger', $('.password-reset-form')).show();
                        setTimeout(function () {
                            $('.alert-danger', $('.password-reset-form')).hide();
                        },5000);
                    }
                });
            }
        });

        $('.password-reset-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.password-reset-form').validate().form()) {
                    $('.password-reset-form').submit(); //form validation success, call ajax form submit
                }
                return false;
            }
        });
    };

    return {
        //main function to initiate the module
        init: function() {
            handlePasswordReset();
        }
    };

}();