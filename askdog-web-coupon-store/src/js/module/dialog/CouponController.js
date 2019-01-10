define([
    'base/BaseController',
    'jquery.validator',
    'service/CouponService'
], function (BaseController) {

    var CouponController = BaseController.extend({

        init: function ($rootScope, $scope, $state, $stateParams, $couponService) {
            this.$rootScope = $rootScope;
            this.$state = $state;
            this.$stateParams = $stateParams;
            this.$uibModalInstance = $scope.$parent.$uibModalInstance;
            this.$couponService = $couponService;
            this._super($scope);
        },

        defineScope: function () {
            this._defineCancel();
            this._bindValidator();
        },

        _defineCancel: function () {
            var owner = this;
            this.$scope.cancel = function () {
                owner.$uibModalInstance.dismiss("cancel");
            }
        },

        _createCoupon: function () {
            var owner = this;
            this.$couponService.createCoupons(this.$scope.coupon).then(
                function () {
                    owner.$uibModalInstance.close();
                }
            );
        },

        _bindValidator: function () {
            var owner = this;
            this.$scope.couponTypeData = [
                {type: 'NORMAL', typeName: '普通优惠券'},
                {type: 'FORWARDED', typeName: '转发升级优惠券'}
            ];
            this.$scope.coupon = {
                store_id: this.$stateParams.storeId
            };
            this.$scope.bindValidator = function (element) {
                $(element).validate({
                    rules: {
                        couponName: {
                            required: true,
                            maxlength: 20
                        },
                        couponType: {
                            required: true
                        },
                        couponRule: {
                            required: true,
                            number: true
                        }

                    },
                    messages: {
                        couponName: {
                            required: '',
                            maxlength: '优惠券名不能超过20个字'
                        },
                        couponType: {
                            required: ''
                        },
                        couponRule: {
                            required: '',
                            number: "请输入正确优惠额"
                        }
                    },
                    submitHandler: function () {
                        owner._createCoupon();
                    }
                });
            }
        }

    });

    CouponController.$inject = ['$rootScope', '$scope', '$state', '$stateParams', 'CouponService'];

    angular.module('module.CouponController', ['service.CouponService']).controller('CouponController', CouponController);

});
