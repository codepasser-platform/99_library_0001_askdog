define(['angular', 'service/CouponService'], function () {

    function wechatShare($http, $couponService, $uibModal) {

        var _scope = undefined;

        function _loadWechatShareConfig() {
            require(['wechat'], function (wx) {
                $http.post(AskDog.ApiUtil.apiUrl('/api/wechat/jsapi/config'), location.href.split('#')[0], {
                    headers: {
                        'Content-Type': 'text/plain'
                    }
                }).success(
                    function (config) {
                        _initializeWechatShare(wx, config);
                    }
                );
            });
        }


        function _initializeWechatShare(wx, config) {
            wx.config({
                appId: config.app_id,
                timestamp: config.timestamp,
                nonceStr: config.nonce_str,
                signature: config.signature,
                jsApiList: [
                    'onMenuShareTimeline',
                    'onMenuShareAppMessage',
                    'onMenuShareQQ',
                    'onMenuShareWeibo',
                    'onMenuShareQZone'
                ]
            });
            wx.ready(function () {
                console.log(_scope.content.name);
                var _content = {
                    title: _scope.content.name,
                    imgUrl: _scope.content.cover_image,
                    desc: _scope.content.description
                };
                var shareOptions = {
                    title: _content.title,
                    imgUrl: _content.imgUrl,
                    desc: _content.desc,
                    link: window.location.href,
                    type: 'link',
                    success: function () {
                        if (_scope.type == 'store-detail') {
                            var upgradeCoupon = undefined;
                            for (var i = 0; i < _scope.coupons.length; i++) {
                                if (_scope.coupons[i].type == 'FORWARDED') {
                                    upgradeCoupon = _scope.coupons[i];
                                    break;
                                }
                            }
                            if (upgradeCoupon) {
                                var productId = _scope.content.special_product.id;
                                $couponService.upgradeCoupon(upgradeCoupon.id, productId).then(
                                    function () {
                                        $("#video-player_html5_api").hide();
                                        var toastModal = $uibModal.open({
                                            windowTemplateUrl: 'views/dialog/modal-window.html',
                                            windowTopClass: 'modal-toast',
                                            templateUrl: 'views/dialog/toast.html',
                                            controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                                                $scope.$uibModalInstance = $uibModalInstance;
                                                $scope.toast = {
                                                    success: true,
                                                    qrcode: true,
                                                    message: '领取成功, 关注公众号可随时查看您的优惠券'
                                                };
                                            }]
                                        });
                                        toastModal.result.then(
                                            function () {
                                            },
                                            function () {
                                                $("#video-player_html5_api").show();
                                            }
                                        );
                                    },
                                    function (resp) {
                                        if (resp.data && resp.data.code == 'SRV_CONFLICT_USER_COUPON') {
                                            $("#video-player_html5_api").hide();
                                            var toastModal = $uibModal.open({
                                                windowTemplateUrl: 'views/dialog/modal-window.html',
                                                windowTopClass: 'modal-toast',
                                                templateUrl: 'views/dialog/toast.html',
                                                controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                                                    $scope.$uibModalInstance = $uibModalInstance;
                                                    $scope.toast = {
                                                        message: '您已经领取过优惠券'
                                                    };
                                                }]
                                            });
                                            toastModal.result.then(
                                                function () {
                                                },
                                                function () {
                                                    $("#video-player_html5_api").show();
                                                }
                                            );
                                        }
                                    }
                                );
                            }
                        }
                    }
                };
                wx.onMenuShareTimeline(shareOptions);
                wx.onMenuShareAppMessage(shareOptions);
                wx.onMenuShareQQ(shareOptions);
                wx.onMenuShareWeibo(shareOptions);
                wx.onMenuShareQZone(shareOptions);
            });
        }

        return {
            restrict: 'AE',
            scope: {
                content: '=',
                coupons: '=',
                type: '='
            },
            link: function (scope, element, attributes) {
                scope.$watch(
                    function () {
                        return scope.content;
                    },
                    function (content) {
                        if (!content) {
                            return;
                        }
                        if (!scope || !AskDog.BrowserUtil.isWeChat()) {
                            return;
                        }
                        _scope = scope;
                        _loadWechatShareConfig();
                    }
                );
            }
        };
    }


    wechatShare.$inject = ['$http', 'CouponService', '$uibModal'];

    angular.module('app.directive.wechatShare', ['service.CouponService']).directive('wechatShare', wechatShare);
});