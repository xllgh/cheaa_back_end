/***
GLobal Directives
***/

// Route State Load Spinner(used on page or content load)
MetronicApp.directive('ngSpinnerBar', ['$rootScope',
    function($rootScope) {
        return {
            link: function(scope, element, attrs) {
                // by defult hide the spinner bar
                element.addClass('hide'); // hide spinner bar by default

                // display the spinner bar whenever the route changes(the content part started loading)
                $rootScope.$on('$stateChangeStart', function() {
                    element.removeClass('hide'); // show spinner bar
                });

                // hide the spinner bar on rounte change success(after the content loaded)
                $rootScope.$on('$stateChangeSuccess', function() {
                    element.addClass('hide'); // hide spinner bar
                    $('body').removeClass('page-on-load'); // remove page loading indicator
                    Layout.setSidebarMenuActiveLink('match'); // activate selected link in the sidebar menu
                   
                    // auto scorll to page top
                    setTimeout(function () {
                        Metronic.scrollTop(); // scroll to the top on content load
                    }, $rootScope.settings.layout.pageAutoScrollOnLoad);     
                });

                // handle errors
                $rootScope.$on('$stateNotFound', function() {
                    element.addClass('hide'); // hide spinner bar
                });

                // handle errors
                $rootScope.$on('$stateChangeError', function() {
                    element.addClass('hide'); // hide spinner bar
                });
            }
        };
    }
])

// Handle global LINK click
MetronicApp.directive('a', function() {
    return {
        restrict: 'E',
        link: function(scope, elem, attrs) {
            if (attrs.ngClick || attrs.href === '' || attrs.href === '#') {
                elem.on('click', function(e) {
                    e.preventDefault(); // prevent link click for above criteria
                });
            }
        }
    };
});

// Handle Dropdown Hover Plugin Integration
MetronicApp.directive('dropdownMenuHover', function () {
  return {
    link: function (scope, elem) {
      elem.dropdownHover();
    }
  };  
});

/**
 * The ng-thumb directive
 * @author: nerv
 * @version: 0.1.2, 2014-01-09
 */
MetronicApp.directive('ngThumb', ['$window', function($window) {
    var helper = {
        support: !!($window.FileReader && $window.CanvasRenderingContext2D),
        isFile: function(item) {
            return angular.isObject(item) && item instanceof $window.File;
        },
        isImage: function(file) {
            var type =  '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    };

    return {
        restrict: 'A',
        template: '<canvas/>',
        link: function(scope, element, attributes) {
            if (!helper.support) return;

            var params = scope.$eval(attributes.ngThumb);

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) return;

            var canvas = element.find('canvas');
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({ width: width, height: height });
                canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
            }
        }
    };
}]);

//渲染完毕的回调
MetronicApp.directive('repeatFinish',function($timeout){
    return {
        restrict:"A",
        scope:{
            callback:"&finishCallback"
        },
        link: function(scope,element,attr){
            //console.log(scope.$parent.$index);
            if(scope.$parent.$last == true){
                //console.log('ng-repeat执行完毕');
                if(scope.callback instanceof Function){
                    $timeout(function() {
                        scope.callback();
                    });
                }
            }
        }
    }
});

//验证时间HH:mm
MetronicApp.directive('isTime',function($timeout){
    return {
        restrict:"A",
        scope:true,
        require: "ngModel",
        link: function(scope,element, attrs, ngModelController){
            var regex = /^((?:[01]*\d|2[0-3])(?::[0-5]*\d))|(24:00)$/;
            ngModelController.$parsers.unshift(function (value) {
                if (regex.test(value)) {
                    ngModelController.$setValidity('isTime', true);
                } else {
                    ngModelController.$setValidity('isTime', false);
                }
                return value;
            });
        }
    }
});

/**
 * 输入款只能输入数字。
 * only-digits="2.1"：可以输入小数，小数点左边最大长度为1为，右边最大长度为1为
 * only-digits="2":智能输入整数，2为长度
 */
MetronicApp.directive('onlyDigits',function(){
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function (scope, element, attr, ctrl) {
            function inputValue(val) {
                if (val) {
                    var digits = val.replace(/[^0-9.]/g, '');

                    var format = attr.onlyDigits;
                    var pos = format.indexOf(".");
                    var dotGroupLength = 2;
                    var bHead = -1, bTail = -1;
                    if (pos == -1) {
                        // 不支持小数
                        dotGroupLength = 1;
                        bHead = parseInt(format);
                    } else {
                        bHead = parseInt(format.substring(0, pos));
                        bTail = parseInt(format.substring(pos + 1));
                    }

                    if (digits.split('.').length > dotGroupLength) {
                        digits = digits.substring(0, digits.length - 1);
                    }
                    var split = digits.split('.');
                    if (split[0].length > bHead)
                        split[0] = split[0].substring(0, bHead);
                    if (bTail != -1 && split.length > 1 && split[1].length > bTail)
                        split[1] = split[1].substring(0, bTail);

                    digits = split.join(".");

                    if (digits !== val) {
                        ctrl.$setViewValue(digits);
                        ctrl.$render();
                    }
                    return parseFloat(digits);
                }
                return undefined;
            }
            ctrl.$parsers.push(inputValue);
        }
    };
});
