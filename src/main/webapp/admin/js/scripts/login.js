var Login = function() {

    var handleLogin = function() {

        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
                username: {
                    required: "Username is required."
                },
                password: {
                    required: "Password is required."
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit
                var username = $("input[name='username']").val();
                var password = $("input[name='password']").val();
                var tips = "";
                if(username == "" && password == ""){
                    tips = "请输入用户名和密码";
                }else{
                    if(password == ""){
                        tips = "请输入密码";
                    }else{
                        tips = "请输入用户名";
                    }
                }
                $('.alert-danger span', $('.login-form')).text(tips);
                $('.alert-danger', $('.login-form')).show();
                setTimeout(function () {
                    $('.alert-danger', $('.login-form')).hide();
                },5000);
            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function(form) {
                //form.submit(); // form validation success, call ajax form submit
                var submit = $(form).find("button[type='submit']");
                submit.text("登录中...");
                $.ajax({
                    type: "POST",
                    url: "login",
                    data: $(form).serialize(),
                    error: function() {
                        submit.text("登录");
                        $('.alert-danger span', $('.login-form')).text("网络异常，请检查网络后再试");
                        $('.alert-danger', $('.login-form')).show();
                        setTimeout(function () {
                            $('.alert-danger', $('.login-form')).hide();
                        },5000);
                    },
                    success: function(data) {
                        if(data.code == 0) {
                            //是否记住账号
                            var remember = $("input[name='remember']").attr("checked");
                            if(remember){
                                localStorage.username = $("input[name='username']").val();
                            }else{
                                localStorage.removeItem("username");
                            }
                            var hash = window.location.hash;
                            if(hash){
                                window.location.href = $("base").attr("href") + hash;
                            }else{
                                window.location.href = "index#/home";
                            }
                        }else{
                            $('.alert-danger span', $('.login-form')).text(data.msg);
                            $('.alert-danger', $('.login-form')).show();
                            setTimeout(function () {
                                $('.alert-danger', $('.login-form')).hide();
                            },5000);
                        }
                        submit.text("登录");
                    },
                    timeout: 5000
                });
            }
        });

        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    $('.login-form').submit(); //form validation success, call ajax form submit
                }
                return false;
            }
        });
    };

    var handleForgetPassword = function() {
        $('.forget-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                email: {
                    required: true,
                    email: true
                }
            },

            messages: {
                email: {
                    required: "Email is required."
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   

            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function(form) {
                form.submit();
            }
        });

        $('.forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.forget-form').validate().form()) {
                    $('.forget-form').submit();
                }
                return false;
            }
        });

        jQuery('#forget-password').click(function() {
            jQuery('.login-form').hide();
            jQuery('.forget-form').show();
        });

        jQuery('#back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.forget-form').hide();
        });

    };


    return {
        //main function to initiate the module
        init: function() {

            handleLogin();
            handleForgetPassword();

        }

    };

}();