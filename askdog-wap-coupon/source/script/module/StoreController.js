define([
    'base/BaseController',
    'app/directive/adAnalytics',
    'service/StoreService',
    'service/ProductService',
    'app/directive/wechatShare',
    'app/directive/videoView',
    'service/CouponService',
    'app/directive/carousel'
], function (BaseController) {

    var StoreController = BaseController.extend({


        init: function (_rootScope, _scope, _stateParams, _uibModal, _storeService, _productService, _couponService) {
            this.$rootScope = _rootScope;
            this.$stateParams = _stateParams;
            this.$uibModal = _uibModal;
            this.$storeService = _storeService;
            this.$productService = _productService;
            this.$couponService = _couponService;
            this._super(_scope);
        },

        defineScope: function () {
            this._defineViewHandler();
            this._refreshListView();
        },

        _defineViewHandler: function () {
            var owner = this;
            owner.$scope.receiveCoupon = function (id) {
                var receiveCoupon = undefined;
                for (var i = 0; i < owner.$scope.detail.special_product.coupons.length; i++) {
                    if (owner.$scope.detail.special_product.coupons[i].type == 'NORMAL') {
                        receiveCoupon = owner.$scope.detail.special_product.coupons[i];
                        break;
                    }
                }
                if (receiveCoupon) {
                    owner.$couponService.receiveCoupon(receiveCoupon.id, owner.$scope.detail.special_product.id).then(
                        function (resp) {
                            if (owner.$rootScope.modalOpened) {
                                return;
                            }
                            owner.$rootScope.modalOpened = true;
                            var toastModal = owner.$uibModal.open({
                                windowTemplateUrl: 'views/dialog/modal-window.html',
                                windowTopClass: 'modal-toast',
                                templateUrl: 'views/dialog/toast.html',
                                controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                                    $scope.$uibModalInstance = $uibModalInstance;
                                    $scope.toast = {
                                        success: true,
                                        qrcode: true,
                                        message: '领取成功',
                                        remarks: '关注公众号查看优惠券' +'<br />'+'转发视频领取' + owner.$scope.detail.coupon_forwarded + '元优惠券'
                                    };
                                }]
                            });

                            toastModal.opened.then(function () {
                                $("video").hide();
                            });

                            toastModal.closed.then(function () {
                                $("video").show();
                                owner.$rootScope.modalOpened = false;
                            });
                        },
                        function (resp) {
                            if (resp.status == 409) {
                                if (owner.$rootScope.modalOpened) {
                                    return;
                                }
                                owner.$rootScope.modalOpened = true;
                                var toastModal = owner.$uibModal.open({
                                    windowTemplateUrl: 'views/dialog/modal-window.html',
                                    windowTopClass: 'modal-toast',
                                    templateUrl: 'views/dialog/toast.html',
                                    controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                                        $scope.$uibModalInstance = $uibModalInstance;
                                        $scope.toast = {
                                            success: false,
                                            qrcode: false,
                                            message: '您已经领取过优惠券'
                                        };
                                    }]
                                });

                                toastModal.opened.then(function () {
                                    $("video").hide();
                                });

                                toastModal.closed.then(function () {
                                    $("video").show();
                                    owner.$rootScope.modalOpened = false;
                                });
                            }
                        }
                    );
                }
            };
            owner.$scope.vote = function () {
                owner.$productService.createVote(owner.$scope.detail.special_product.id, 'UP')
                    .then(function (resp) {
                            owner.$scope.detail.special_product.statistics.up_vote_count++;
                            owner.$scope.detail.special_product.vote = 'UP';
                        },
                        function (resp) {
                            if (resp && resp.status == 409) {
                                owner.$productService.createVote(owner.$scope.detail.special_product.id, 'DOWN')
                                    .then(function () {
                                        owner.$scope.detail.special_product.statistics.up_vote_count--;
                                        owner.$scope.detail.special_product.vote = 'DOWN';
                                    });
                            }
                        });
            };
        },

        _refreshListView: function () {
            var owner = this;
            owner.$scope.loadingCompleted = false;
            owner.$storeService.storeDetail(owner.$stateParams.id).then(
                function (resp) {
                    owner.$scope.loadingCompleted = true;
                    owner.$scope.detail = resp.data;
                    if (owner.$scope.detail.special_product) {
                        var detail = owner.$scope.detail.special_product.coupons;
                        for (var i = 0; i < detail.length; i++) {
                            if (detail[i].type == 'NORMAL') {
                                owner.$scope.detail.coupon_normal = detail[i].rule;
                            }
                            if (detail[i].type == 'FORWARDED') {
                                owner.$scope.detail.coupon_forwarded = detail[i].rule;
                            }
                        }
                    }
                });
        }
    });

    StoreController.$inject = ['$rootScope', '$scope', '$stateParams', '$uibModal', 'StoreService', 'ProductService', 'CouponService'];

    angular.module('module.StoreController', ['service.StoreService', 'service.ProductService', 'service.CouponService']).controller('StoreController', StoreController);

});