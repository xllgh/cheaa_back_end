/**
 * Created by Roc on 2015/11/20.
 */
(function ($) {
    // 设置jQuery Ajax全局的参数
    /*$.ajaxSetup({
     timeout:60000
     });*/
    //全局处理异步请求之前
    $(document).on('ajaxSend', function (event, jqxhr, settings) {
        jqxhr.setRequestHeader("RequestType", "ajax");
    });
    //全局处理异步请求成功
    $(document).on('ajaxSuccess', function (event, xhr, settings) {
        /*console.info("Success!", xhr.responseJSON);*/
        var response = xhr.responseJSON;
        if (response.code == 70001) {
            window.location.href = "login" + window.location.hash;
        }
        if (response.code == 40000) {
            bootbox.hideAll();
            bootbox.alert(response.message);
        }
    });
    //全局处理异步请求错误
    $(document).on('ajaxError', function (event, jqxhr, settings, thrownError) {
        /*window.location.href = "error";*/
        /*console.info(thrownError);*/
        bootbox.hideAll();
        var tips = "";
        if (thrownError == "timeout") {
            tips = "网络异常，请检查网络后再试";
        } else {
            switch (jqxhr.status) {
                case 0:
                    tips = "网络异常，请检查网络后再试";
                    break;
                case 408 :
                    tips = "请求超时！";
                    break;
                default :
                    tips = "系统异常," + jqxhr.status + "！";
                    break;
            }
        }
        bootbox.alert(tips);
    });
    /*//全局处理异步请求开始
     $(document).on('ajaxStart',function(event, jqxhr, settings, thrownError ){
     Metronic.startPageLoading();
     });
     //全局处理异步请求结束
     $(document).on('ajaxStop',function(event, jqxhr, settings, thrownError ){
     Metronic.stopPageLoading();
     });*/

    //获取故障信息
    var fetchFaultInfo = function() {
        var $faultTips = $("#faultTips");
        if ($faultTips.length == 0) return;
        $faultTips.on("click",function(){
            window.location.href = "#/broken_warning/list";
            $faultTips.removeClass("active");
        });
        $.get("fault/countUnresolvedFault",function(response){
            if(response.code ==0){
                var result = response.result;
                if(result > 0){
                    $faultTips.removeClass("active");
                    $faultTips.find("span").text("发现" + result + "个新故障");
                    $faultTips.addClass("active");
                    setTimeout(function(){
                        $faultTips.removeClass("active");
                    },5000);
                }
            }
            setTimeout(fetchFaultInfo,60000);
        });
    };
    fetchFaultInfo();

    /*处理浏览器Tab切换登录账号*/
    window.onfocus = function() {
        var currentUserId = $("input[name='userid']").val();
        if (currentUserId === undefined) return;
        $.get("whoami",function(response){
            if(response.code == 0){
                if(response.hasOwnProperty("msg") && currentUserId != response.msg){
                    bootbox.hideAll();
                    bootbox.alert("当前用户已经切换，请刷新页面！",function(){
                        window.location.reload();
                    });
                    return true;
                }
                if(!response.hasOwnProperty("msg") || (response.hasOwnProperty("msg") && response.msg == "")){
                    bootbox.hideAll();
                    bootbox.alert("当前用户已经注销，请重新登录！",function(){
                        window.location.href = "login" + window.location.hash;
                    });
                    return true;
                }
            }
        });
    };

    //时长格式化
    Date.prototype.duration = function (seconds) {
        var s = seconds % 60;
        var minites = parseInt(seconds / 60);
        var m = minites % 60;
        var hours = parseInt(minites / 60);
        if (hours < 1000) {
            var text = hours + ":" + (m >= 0 && m <= 9 ? "0" + m : m) + ":"
                + (s >= 0 && s <= 9 ? "0" + s : s);
        } else {
            var h = hours % 24;
            var y = parseInt(hours / 24);
            var text = y + "天" + h + "小时" + m + "分钟";
        }
        return text;
    }
})(jQuery);

//图片预览插件
(function ($) {
    $.fn.preview = function (options) {
        var self = this;
        var option = $.extend(true, {
            previewType: "hover",   //预览方式
            xOffset: 10,            //鼠标与预览图X轴距离（数字类型）
            yOffset: 20,            //鼠标与预览图Y轴距离（数字类型）
            width: "auto",       //预览图宽度
            height: "auto",       //预览图高度
            maxWidth: "auto",       //预览图最大宽度
            maxHeight: "auto",       //预览图最大宽度
            minHeight: "auto"        //预览图最小宽度
        }, options);
        var windowWidth = $(window).width();
        var windowHeight = $(window).width();
        switch (option.previewType) {
            case "click":
                $(self).each(function () {
                    var imageHref = $(this).attr("data-href");
                    if (imageHref) {
                        $(this).css({
                            cursor: "pointer"
                        }).click(function () {
                            var previewClass  = [
                                "position: fixed;",
                                "top: 0;",
                                "left: 0;",
                                "width: 100%;",
                                "height: 100%;",
                                "background-color: rgba(0,0,0,.5);",
                                "z-index: 9997;",
                                "display: -webkit-box;",      /* OLD - iOS 6-, Safari 3.1-6 */
                                "display: -moz-box;",         /* OLD - Firefox 19- (buggy but mostly works) */
                                "display: -ms-flexbox;",      /* TWEENER - IE 10 */
                                "display: -webkit-flex;",     /* NEW - Chrome */
                                "display: flex;",             /* NEW, Spec - Opera 12.1, Firefox 20+ */
                                "justify-content: center;",
                                "align-items: center;"
                            ].join("");
                            var closeClass = [
                                "position: absolute;",
                                "top: 25px;",
                                "right: 25px;",
                                "width: 50px;",
                                "height: 50px;",
                                "line-height: 45px;",
                                "font-size: 40px;",
                                "color: rgba(255, 255, 255, 0.498039);",
                                "text-align: center;",
                                "border: 2px solid rgb(85, 85, 85);",
                                "cursor: pointer;",
                                "background-color: rgba(0, 0, 0, 0.498039);",
                                "-webkit-border-radius: 100% !important;",
                                "-moz-border-radius: 100% !important;",
                                "-ms-border-radius: 100% !important;",
                                "-o-border-radius: 100% !important;",
                                "border-radius: 100% !important;"
                            ].join("");
                            var $preview = $('<div id="preview" style="' + previewClass + '"></div>');
                            var $close = $('<div id="close-preview" style="'+ closeClass + '">×</div>');
                            var $img = $('<img src="' + imageHref + '" />');
                            $img.css({
                               maxWidth:"98%",
                               maxHeight:"98%"
                            });
                            $close.hover(function(){
                                $(this).css({
                                    backgroundColor: "rgba(0, 0, 0, .9)",
                                    color: "rgba(255, 255, 255, .9)"
                                });
                            },function(){
                                $(this).css({
                                    backgroundColor: "rgba(0, 0, 0, .5)",
                                    color: "rgba(255, 255, 255, .5)"
                                });
                            }).click(function(){
                                $preview.remove();
                            });
                            $preview.append($close);
                            $preview.append($img);
                            $("body").append($preview);
                        });
                    }
                });
                break;
            default :
                $(self).each(function () {
                    var imageHref = $(this).attr("data-href");
                    if (imageHref) {
                        $(this).hover(function (e) {
                            if (/.png$|.gif$|.jpg$|.bmp$|.jpeg$/.test(imageHref)) {
                                $("body").append("<div id='preview'><div><img src='" + imageHref + "' /><p>" + ($(this).attr('title') || "") + "</p></div></div>");
                            } else {
                                $("body").append("<div id='preview'><div><p>" + ($(this).attr('title') || "") + "</p></div></div>");
                            }
                            var $preview = $("#preview");
                            var previewHeight = $preview.height();
                            $preview.css({
                                position: "absolute",
                                padding: "4px",
                                border: "1px solid #f3f3f3",
                                backgroundColor: "#eeeeee",
                                zIndex: 1000
                            });
                            $("#preview img").css({
                                width: option.width,
                                height: option.height,
                                maxWidth: option.maxWidth,
                                maxHeight: option.maxHeight,
                                minWidth: option.minWidth,
                                minHeight: option.minHeight
                            });
                            $("#preview > div").css({
                                padding: "5px",
                                backgroundColor: "white",
                                border: "1px solid #cccccc"
                            });
                            $("#preview > div > p").css({
                                textAlign: "center",
                                fontSize: "12px",
                                padding: "8px 0 3px",
                                margin: "0"
                            });
                            if (e.pageX < windowWidth / 2) {
                                $preview.css({
                                    left: e.pageX + option.xOffset + "px",
                                    right: "auto"
                                }).fadeIn("fast");
                            } else {
                                $preview.css({
                                    "right": (windowWidth - e.pageX + option.yOffset) + "px",
                                    "left": "auto"
                                }).fadeIn("fast");
                            }
                            if ((e.pageY + previewHeight) > windowHeight) {
                                $preview.css({
                                    top: (e.pageY - option.yOffset - $preview.height()) + "px"
                                }).fadeIn("fast");
                            } else {
                                $preview.css({
                                    top: (e.pageY - option.yOffset) + "px"
                                }).fadeIn("fast");
                            }
                        }, function () {
                            $("#preview").remove();
                        }).mousemove(function (e) {
                            var $preview = $("#preview");
                            var previewHeight = $preview.height();
                            if (e.pageX < windowWidth / 2) {
                                $preview.css({
                                    "left": (e.pageX + option.yOffset) + "px",
                                    "right": "auto"
                                });
                            } else {
                                $preview.css({
                                    "right": (windowWidth - e.pageX + option.yOffset) + "px",
                                    "left": "auto"
                                });
                            }
                            if ((e.pageY + previewHeight) > windowHeight) {
                                $preview.css({
                                    top: (e.pageY - option.yOffset - $preview.height()) + "px"
                                });
                            } else {
                                $preview.css({
                                    top: (e.pageY - option.yOffset) + "px"
                                });
                            }
                        });
                    }
                });
                break;
        }
        return $(self);
    };
})(jQuery);