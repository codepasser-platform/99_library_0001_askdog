define([
    'base/BaseController',
    'service/StoreService',
    'jquery.validator',
    'app/directive/qrCode'
], function (BaseController) {

    var StoreManagementController = BaseController.extend({

        _VIEW_SIZE: 4,

        init: function ($rootScope, $scope, $state, $stateParams, $storeService, $uibModal) {
            this.$rootScope = $rootScope;
            this.$state = $state;
            this.$stateParams = $stateParams;
            this.$storeService = $storeService;
            this.$uibModal = $uibModal;
            this._super($scope);
        },

        defineScope: function () {
            this._refreshListView(0);
            this._defineViewHandler();
        },

        _defineViewHandler: function () {
            var owner = this;
            this.$scope.deleteStore = function (id) {
                owner.$storeService.deleteStoreInfo(id)
                    .then(function () {
                        owner._refreshListView(owner.$scope.viewData.page);
                    });
            };
            this.$scope.addStore = function () {
                var storeInfoModal = owner.$uibModal.open({
                    windowTemplateUrl: 'views/dialog/modal-window.html',
                    windowTopClass: 'pg-show-modal',
                    templateUrl: 'views/dialog/store-info.html',
                    controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                        $scope.$uibModalInstance = $uibModalInstance;
                        $scope.message = {
                            title: '添加商户',
                            addStore: true,
                            active: '添加'
                        }
                    }]
                });
                storeInfoModal.result.then(function () {
                    owner._refreshListView(owner.$scope.viewData.page);
                });
            };
            this.$scope.updateStore = function (id) {
                owner.$storeService.getStoreDetail(id)
                    .then(function (resp) {
                        console.log(resp.data);
                        var storeInfoModal = owner.$uibModal.open({
                            windowTemplateUrl: 'views/dialog/modal-window.html',
                            windowTopClass: 'pg-show-modal',
                            templateUrl: 'views/dialog/store-info.html',
                            controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                                $scope.$uibModalInstance = $uibModalInstance;
                                $scope.message = {
                                    title: '修改商户信息',
                                    addStore: false,
                                    active: '保存'
                                };
                                $scope.storeDetail = resp.data;
                            }]
                        });
                        storeInfoModal.result.then(function () {
                            owner._refreshListView(owner.$scope.viewData.page);
                        });
                    });
            }
        },

        _refreshListView: function (page) {
            var owner = this;
            owner.$storeService.storeList(page, this._VIEW_SIZE)
                .then(
                    function (resp) {
                        owner._refreshListViewSuccess(resp.data, page)
                    });
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

    StoreManagementController.$inject = ['$rootScope', '$scope', '$state', '$stateParams', 'StoreService', '$uibModal'];

    angular.module('module.StoreManagementController', ['service.StoreService']).controller('StoreManagementController', StoreManagementController);

});
