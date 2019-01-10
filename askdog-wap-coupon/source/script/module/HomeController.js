define([
    'base/BaseController',
    'app/directive/adAnalytics',
    'service/StoreService',
    'app/directive/homeCoupon'
], function (BaseController) {

    var HomeController = BaseController.extend({

        _VIEW_SIZE: 13,

        init: function (_rootScope, _scope, _stateParams, _uibModal, _storeService) {
            this.$rootScope = _rootScope;
            this.$stateParams = _stateParams;
            this.$uibModal = _uibModal;
            this.$storeService = _storeService;

            this._super(_scope);
        },

        defineScope: function () {
            this._defineViewHandler();
            this._refreshListView(0, this._VIEW_SIZE);
            this._scrollShowMore();
        },

        defineListeners: function () {
            this._contextChangeListener = this.$rootScope.$on('contextChange', function () {
            });
            this._contextReadyListener = this.$rootScope.$on('contextReady', function (event, userSelf) {
            });
        },

        destroy: function () {
            this._contextChangeListener();
            this._contextReadyListener();
            this._contextChangeListener = null;
            this._contextReadyListener = null;
        },

        _defineViewHandler: function () {
            // TODO
        },

        _refreshListView: function (page, size) {
            var owner = this;
            owner.$scope.loadingCompleted = false;
            this.$storeService.stores(page, size).then(
                function (resp) {
                    owner._searchSuccessHandler(resp.data, page);
                    owner.$scope.loadingCompleted = true;
                },
                function (resp) {
                    if (resp.status == 404) {
                        owner.$scope.loadingCompleted = true;
                        owner.$scope.viewList = {
                            page: 0,
                            total: 0,
                            last: true,
                            result: []
                        }
                    }
                }
            );
        },

        _searchSuccessHandler: function (data, page) {
            var lastList = this.$scope.viewList || {};
            lastList.page = page;
            lastList.total = data.total;
            lastList.last = data.last;
            if (this.$scope.viewList && this.$scope.viewList.result) {
                lastList.result = this.$scope.viewList.result;
            } else {
                lastList.result = [];
            }
            if (data.total == 0 || data.result.length == 0) {
                lastList.last = true;
            } else {
                for (var index = 0; index < data.result.length; index++) {
                    lastList.result.push(data.result[index]);
                }
            }
            this.$scope.viewList = lastList;
        },

        _scrollShowMore: function () {
            var owner = this;
            window.addEventListener("scroll", function () {
                if (!owner.$scope.loadingCompleted) {
                    return;
                }
                if (Math.ceil($(document).scrollTop()) >= $(document).height() - $(window).height() && !owner.$scope.viewList.last) {
                    owner._refreshListView((owner.$scope.viewList.page + 1), owner._VIEW_SIZE);
                }
            });
        }
    });

    HomeController.$inject = ['$rootScope', '$scope', '$stateParams', '$uibModal', 'StoreService'];

    angular.module('module.HomeController', ['service.StoreService']).controller('HomeController', HomeController);

});