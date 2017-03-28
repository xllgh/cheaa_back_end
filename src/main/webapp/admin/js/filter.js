/**
 * Created by Roc on 16/4/19.
 */

/*全局过滤器*/

/**
 * 信任图片路径过滤器
 */
MetronicApp.filter('trustSrc',function($sce){
    return function(item) {
        return $sce.trustAsResourceUrl(item);
    };
});

/**
 * 信任Html过滤器
 */
MetronicApp.filter('trustAsHtml',function($sce){
    return function(item) {
        return $sce.trustAsHtml(item);
    };
});

/**
 * ui-select过滤器
 */
MetronicApp.filter('propsFilter', function() {
    return function(items, props) {
        var out = [];

        if (angular.isArray(items)) {
            items.forEach(function(item) {
                var itemMatches = false;
                var keys = Object.keys(props);
                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = props[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = items;
        }

        return out;
    };
});

/**
 * 支付状态过滤器
 */
MetronicApp.filter  ('payStatus', function () {
    return function (payStatus, totalPrice) {
        if (payStatus == "1") {
            return "0";
        } else {
            return totalPrice;
        }
    };
});

/**
 * 设备状态过滤器
 */
MetronicApp.filter('devStatus', function () {
    return function (devStatus) {
        switch (devStatus) {
            case 1:
                return "在线";
                break;
            case 2:
                return "离线";
                break;
            case 0:
                return "离网";
                break;
            default:
                return "离线";
                break;
        }
    }
});

/**
 * 设备状态过滤器
 */
MetronicApp.filter('importErrorType', function () {
    return function (errorType) {
        switch (errorType) {
            case 1:
                return "空值";
                break;
            case 2:
                return "内容错误";
                break;
            case 4:
                return "内容重复";
                break;
            case 8:
                return "未知错误";
                break;
        }
    }
});

/**
 * 设备运行状态过滤器
 */
MetronicApp.filter('runStatus', function () {
    return function (runStatus) {
        switch (runStatus) {
            case 0:
            case -1:
                return "未知";
            case 1:
                return "空闲";
            case 2:
                return "连接未充电";
            case 3:
                return "充电中";
            case 4:
                return "离线";
            case 5:
                return "检修中";
            case 6:
                return "预约中";
            case 7:
                return "故障";
            case 8:
                return "自动充满";
            case 9:
                return "长期掉线";
            default:
                return "检修中";
        }
    }
});

/**
 * 桩详情的订单状态过滤器
 */
MetronicApp.filter('pileDetailOrderStatus', function () {
    return function (orderStatus) {
        var orderStatusName = "";
        switch (orderStatus) {
            case 0:
                orderStatusName = "待处理";
                break;
            case 1:
                orderStatusName = "已同意";
                break;
            case 2:
                orderStatusName = "拒绝";
                break;
            case 3:
                orderStatusName = "取消";
                break;
            case 4:
                orderStatusName = "充电完成未付款";
                break;
            case 5:
                orderStatusName = "充电完成已支付";
                break;
            case 6:
                orderStatusName = "订单过期";
                break;
            case 7:
                orderStatusName = "已评价";
                break;
            case 8:
                orderStatusName = "充电中";
                break;
            case 9:
                orderStatusName = "充电记录错误";
                break;
            case 10:
                orderStatusName = "充电金额异常";
                break;
            case 12:
                orderStatusName = "第三方运营商支付";
                break;
            case 13:
                orderStatusName = "支付给第三方运营商";
                break;
            default :
                orderStatusName = "其他";
                break;
        }
        return orderStatusName;
    };
});

/**
 * 订单状态的过滤器
 */
MetronicApp.filter('orderStatus',function(){
    return function(orderStatus){
        var orderStatusName = "";
        switch (orderStatus){
            case 0:
                orderStatusName = "待处理";
                break;
            case 1:
                orderStatusName = "已确认";
                break;
            case 2:
                orderStatusName = "拒绝";
                break;
            case 3:
                orderStatusName = "取消";
                break;
            case 4:
                orderStatusName = "充电完成未付款";
                break;
            case 5:
                orderStatusName = "充电完成已支付";
                break;
            case 6:
                orderStatusName = "订单过期";
                break;
            case 7:
                orderStatusName = "已评价";
                break;
            case 8:
                orderStatusName = "充电中";
                break;
            case 9:
                orderStatusName = "充电记录错误";
                break;
            case 10:
                orderStatusName = "充电金额异常";
                break;
            case 12:
                orderStatusName = "第三方运营商支付";
                break;
            case 13:
                orderStatusName = "支付给第三方运营商";
                break;
            default :
                orderStatusName = "其他";
                break;
        }
        return orderStatusName;
    };
});