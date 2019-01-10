(function (AskDog) {

    // String formatter
    String.prototype.format = function (a) {
        var args = typeof a == "object" ? a : arguments;
        return this.replace(/\{(\w+)\}/g, function (m, k) {
            return args[k];
        });
    };

    if (!String.prototype.startWith) {
        String.prototype.startWith = function (str) {
            if (str == null || str == "" || this.length == 0 || str.length > this.length) {
                return false;
            }

            return this.substr(0, str.length) == str;
        };
    }

    // Array useful functions
    if (!Array.prototype.remove) {
        Array.prototype.remove = function (predicate) {
            if (this == null) {
                throw new TypeError('Array.prototype.remove called on null or undefined');
            }
            if (typeof predicate !== 'function') {
                throw new TypeError('predicate must be a function');
            }
            var list = Object(this);
            var length = list.length >>> 0;
            var thisArg = arguments[1];
            var value;

            for (var i = 0; i < length; i++) {
                value = list[i];
                if (predicate.call(thisArg, value, i, list)) {
                    list.splice(i, 1);
                }
            }

            return undefined;
        };
    }

    if (!Array.prototype.find) {
        Array.prototype.find = function (predicate) {
            if (this == null) {
                throw new TypeError('Array.prototype.find called on null or undefined');
            }
            if (typeof predicate !== 'function') {
                throw new TypeError('predicate must be a function');
            }
            var list = Object(this);
            var length = list.length >>> 0;
            var thisArg = arguments[1];
            var value;

            for (var i = 0; i < length; i++) {
                value = list[i];
                if (predicate.call(thisArg, value, i, list)) {
                    return value;
                }
            }
            return undefined;
        };
    }

    if (!Date.prototype.format) {
        Date.prototype.format = function (fmt) {
            var o = {
                "M+": this.getMonth() + 1,
                "d+": this.getDate(),
                "h+": this.getHours(),
                "m+": this.getMinutes(),
                "s+": this.getSeconds(),
                "q+": Math.floor((this.getMonth() + 3) / 3),
                "S": this.getMilliseconds()
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        };
    }

    // Utilities
    var Util = {
        LIB_PATH: '../lib',
        libPath: function (lib) {
            return Util.LIB_PATH + '/' + lib;
        }
    };

    // Token  utilities
    var TokenUtil = {
        type: {
            USER_AVATAR: 'USER_AVATAR',
            STORE_COVER: 'STORE_COVER',
            PRODUCT_COVER: 'PRODUCT_COVER',
            PRODUCT_VIDEO: 'PRODUCT_VIDEO'
        },
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        isImage: function (fileName) {
            var result = {
                valid: true
            };
            var _formats = [];
            for (var index = 0; index < TokenUtil.imageFormats.length; index++) {
                _formats.push(TokenUtil.imageFormats[index]);
                _formats.push(TokenUtil.imageFormats[index].toUpperCase());
            }
            var isImage = new RegExp("(\\.(" + _formats.join("|") + "))$");
            if (!isImage.test(fileName)) {
                result.message = "允许上传的图片文件格式有：" + TokenUtil.imageFormats.join(", ");
                result.valid = false;
                return result;
            }
            return result;
        },
        TOKEN_URI: '/api/storage/access_token?type={type}&extention={extension}', // USER_AVATAR,EXPERIENCE_PICTURE,CHANNEL_THUMBNAIL
        getUri: function (type, extension) {
            return TokenUtil.TOKEN_URI.format({
                "type": type,
                "extension": extension
            })
        }
    };

    // API Utilities
    var ApiUtil = {
        BASE_DOMAIN: 'askdog.com',
        APP_DOMAIN: 'coupon',
        API_DOMAIN: 'store.coupon',
        // TODO DEV
        //BASE_DOMAIN: 'qydev.com',
        //APP_DOMAIN: 'ellenchia.tunnel',
        //API_DOMAIN: 'ellenchia.tunnel',
        appUrl: function (endpoint) {
            if (endpoint.startWith('http')) {
                return endpoint;
            }
            return 'http://' + ApiUtil.APP_DOMAIN + "." + ApiUtil.BASE_DOMAIN + endpoint;
        },
        apiUrl: function (endpoint) {
            if (endpoint.startWith('http')) {
                return endpoint;
            }
            return 'http://' + ApiUtil.API_DOMAIN + "." + ApiUtil.BASE_DOMAIN + endpoint;
        }
    };

    // precondition check
    var Precondition = {

        /**
         * Ensures the truth of an expression involving one or more parameters to the calling method.
         * @param expression
         * @param errorMsg
         */
        checkArgument: function (expression, errorMsg) {
            if (!expression) {
                throw new Error(errorMsg);
            }
        }
    };

    AskDog.Util = Util;
    AskDog.ApiUtil = ApiUtil;
    AskDog.TokenUtil = TokenUtil;
    AskDog.Precondition = Precondition;


})(window.AskDog || (window.AskDog = function () {
    }));


