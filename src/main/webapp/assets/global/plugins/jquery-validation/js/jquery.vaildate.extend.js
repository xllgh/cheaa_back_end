/**
 * Created by Roc on 2015/11/18.
 */
/*表单验证扩展*/
(function( factory ) {
    factory( jQuery );
}(function( $ ) {

    (function () {
        // 手机号码验证
        $.validator.addMethod("isMobile", function (value, element) {
            var length = value.length;
            return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
        }, "请正确填写您的手机号码。");

        // 电话号码验证
        $.validator.addMethod("isPhone", function (value, element) {
            var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
            return this.optional(element) || (tel.test(value));
        }, "请正确填写您的电话号码。");

        // 联系电话(手机/电话皆可)验证
        $.validator.addMethod("isTel", function (value, element) {
            var length = value.length;
            var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
            var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
            return this.optional(element) || tel.test(value) || (length == 11 && mobile.test(value));
        }, "请正确填写您的联系方式");

        // 时间验证
        $.validator.addMethod("time", function (value, element) {
            var time = /^(?:(?:0?|1)\d|2[0-3]):[0-5]\d$/g;
            return this.optional(element) || time.test(value);
        }, "请正确填写时间");

    }());
}));

/*!function(a) {
    "function" == typeof define && define.amd ? define(["jquery", "./jquery.validate.min"], a) : a(jQuery)
} (function(a) { !
    function() {
        debugger;
        // 手机号码验证
        a.validator.addMethod("isMobile", function (value, element) {
            var length = value.length;
            return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
        }, "请正确填写您的手机号码。");

        // 电话号码验证
        a.validator.addMethod("isPhone", function (value, element) {
            var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
            return this.optional(element) || (tel.test(value));
        }, "请正确填写您的电话号码。");

        // 联系电话(手机/电话皆可)验证
        a.validator.addMethod("isTel", function (value, element) {
            var length = value.length;
            var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
            var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
            return this.optional(element) || tel.test(value) || (length == 11 && mobile.test(value));
        }, "请正确填写您的联系方式");
    } ()
});*/
