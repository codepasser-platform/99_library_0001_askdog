define(['angular'], function () {

    function mapLocation($http) {

        var _wx = undefined;

        function _loadWechatConfig() {
            require(['wechat'], function (wx) {
                $http.post(AskDog.ApiUtil.apiUrl('/api/wechat/jsapi/config'), location.href.split('#')[0], {
                    headers: {
                        'Content-Type': 'text/plain'
                    }
                }).success(
                    function (config) {
                        _initializeWechatLocation(wx, config);
                    }
                );
            });
        }

        function _initializeWechatLocation(wx, config) {
            wx.config({
                appId: config.app_id,
                timestamp: config.timestamp,
                nonceStr: config.nonce_str,
                signature: config.signature,
                jsApiList: [
                    'openLocation'
                ]
            });
            wx.ready(function () {
                _wx = wx;
            });
        }

        return {
            restrict: 'AE',
            scope: {
                location: '=',
                name: '=',
                address: '='
            },
            link: function (scope, element, attributes) {

                scope.$watch(
                    function () {
                        return scope;
                    },
                    function (scope) {
                        if (!scope) {
                            return;
                        }

                        _loadWechatConfig(scope);

                        $(element).on('click', function () {
                            if (!_wx) {
                                return;
                            }
                            var locationOpts = {
                                latitude: scope.location.lat, // 纬度，浮点数，范围为90 ~ -90
                                longitude: scope.location.lng, // 经度，浮点数，范围为180 ~ -180。
                                name: scope.name, // 位置名
                                address: scope.address, // 地址详情说明
                                scale: 20, // 地图缩放级别,整形值,范围从1~28。默认为最大
                                infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                            };
                            _wx.openLocation(locationOpts);
                        });

                    }
                );

            }
        }
    }

    mapLocation.$inject = ['$http'];
    angular.module('app.directive.mapLocation', []).directive('mapLocation', mapLocation);
});