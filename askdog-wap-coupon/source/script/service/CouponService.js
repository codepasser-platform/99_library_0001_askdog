define(['base/BaseService'], function (BaseService) {

    var CouponService = BaseService.extend({

        init: function (http, scope) {
            this._super(http, scope);
        },

        cashList: function (page, size) {
            return this.get("/api/usercoupons?page={page}&size={size}".format({"page": page, "size": size}));
        },

        cashDetail: function (id) {
            return this.get("/api/usercoupons/" + id);
        },

        receiveCoupon: function (coupon_id, product_id) {
            var data = {
                coupon_id: coupon_id,
                product_id: product_id
            };
            return this.post('/api/usercoupons', data);
        },

        upgradeCoupon: function (coupon_id, product_id) {
            var data = {
                coupon_id: coupon_id,
                product_id: product_id
            };
            return this.put('/api/usercoupons', data);
        }
    });

    angular.module('service.CouponService', [])
        .provider('CouponService', function () {
            this.$get = ['$http', '$rootScope', function (http, scope) {
                return new CouponService(http, scope);
            }];
        });

});