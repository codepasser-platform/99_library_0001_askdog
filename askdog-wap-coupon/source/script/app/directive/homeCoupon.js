define(['angular'], function (angular, videojs) {

    function homeCoupon() {

        return {
            restrict: 'AE',
            scope: {
                coupon: '='
            },
            link: function (scope, element, attributes) {
                if (scope.coupon.coupons) {
                    for (var i = 0; i < scope.coupon.coupons.length; i++) {
                        if (scope.coupon.coupons[i].type == 'NORMAL') {
                            scope.coupon.coupon_normal = Number(scope.coupon.coupons[i].rule);
                        }
                        if (scope.coupon.coupons[i].type == 'FORWARDED') {
                            scope.coupon.coupon_forwarded = Number(scope.coupon.coupons[i].rule);
                        }
                    }
                }

            }
        }
    }

    homeCoupon.$inject = [];
    angular.module('app.directive.homeCoupon', []).directive('homeCoupon', homeCoupon);
});