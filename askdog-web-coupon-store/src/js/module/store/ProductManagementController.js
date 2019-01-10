define([
    'base/BaseController',
    'service/ProductService'
], function (BaseController) {

    var ProductManagementController = BaseController.extend({

        _VIEW_SIZE: 4,

        _onPaginationListener: null,

        init: function ($rootScope, $scope, $state, $stateParams, $uibModal, $productService) {
            this.$rootScope = $rootScope;
            this.$state = $state;
            this.$stateParams = $stateParams;
            this.$uibModal = $uibModal;
            this.$productService = $productService;
            this._super($scope);
        },

        defineScope: function () {
            this._defineViewHandler();
            this._defineRefreshView();
        },

        _defineViewHandler: function () {
            var owner = this;
            this.$scope.openCreateModal = function () {
                var productModel = owner.$uibModal.open({
                    windowTemplateUrl: 'views/dialog/modal-window.html',
                    windowTopClass: 'modal-default',
                    templateUrl: 'views/dialog/product.html',
                    controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                        $scope.$uibModalInstance = $uibModalInstance;
                    }]
                });

                productModel.result.then(
                    function () {
                        owner._loadProductList(0);
                    },
                    function () {
                    }
                );
            }
        },

        _defineRefreshView: function () {
            this._loadProductList(0);
        },


        _loadProductList: function (page) {
            var owner = this;
            this.$productService.products(this.$stateParams.storeId, page, this._VIEW_SIZE).then(
                function (resp) {
                    owner._loadProductListSuccess(resp.data, page);
                }
            );
        },

        _loadProductListSuccess: function (data, page) {
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
                        owner._loadProductList(pageNo - 1);
                    }
                );
            }
        }

    });

    ProductManagementController.$inject = ['$rootScope', '$scope', '$state', '$stateParams', '$uibModal', 'ProductService'];

    angular.module('module.ProductManagementController', ['service.ProductService']).controller('ProductManagementController', ProductManagementController);

});
