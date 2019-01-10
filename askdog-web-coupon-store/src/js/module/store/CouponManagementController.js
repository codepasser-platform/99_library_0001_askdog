define(['base/BaseController', 'jquery.morris', 'service/CouponService'], function (BaseController) {

    var CouponManagementController = BaseController.extend({

        _VIEW_SIZE: 10,

        init: function ($rootScope, $scope, $state, $stateParams, $uibModal, _couponService) {
            this.$rootScope = $rootScope;
            this.$state = $state;
            this.$stateParams = $stateParams;
            this.$uibModal = $uibModal;
            this.$couponService = _couponService;
            this._super($scope);

        },

        defineScope: function () {
            this._defineViewHandler();
            this._refreshListView(0);

        },

        _defineViewHandler: function () {
            var owner = this;
            this.$scope.createCoupon = function () {
                var couponModel = owner.$uibModal.open({
                    windowTemplateUrl: 'views/dialog/modal-window.html',
                    windowTopClass: 'modal-default',
                    templateUrl: 'views/dialog/coupon.html',
                    controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                        $scope.$uibModalInstance = $uibModalInstance;
                    }]
                });

                couponModel.result.then(
                    function () {
                        owner._refreshListView(0);
                    },
                    function () {
                    }
                );
            };
        },

        _refreshListView: function (page) {
            var owner = this;
            this.$couponService.couponList(owner.$stateParams.storeId, page, this._VIEW_SIZE).then(
                function (resp) {
                    owner._refreshListViewSuccess(resp.data, page)
                }
            )
        },

        _refreshListViewSuccess: function (data, page) {
            var owner = this;
            var viewData = this.$scope.viewData || {};
            viewData.page = page;
            viewData.total = data.total;
            viewData.last = data.last;
            viewData.result = data.result;
            if (data.result.length == 0) {
                viewData.last = true;
            }
            this.$scope.viewData = viewData;

            if (this._onPaginationListener == null) {
                this._onPaginationListener = $('pagination').on('_onPaginationListener',
                    function (event, pageNo) {
                        owner._refreshListView(pageNo - 1);
                    }
                );
            }
        }
    });

    CouponManagementController.$inject = ['$rootScope', '$scope', '$state', '$stateParams', '$uibModal', 'CouponService'];
    angular.module('module.store.CouponManagementController', ['service.CouponService']).controller('CouponManagementController', CouponManagementController);

});
