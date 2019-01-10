define([
    'base/BaseController',
    'app/directive/adAnalytics',
    'service/CouponService'
], function (BaseController) {

    var CashListController = BaseController.extend({

        _VIEW_SIZE: 10,

        init: function (_rootScope, _scope, _stateParams, _uibModal, _couponService) {
            this.$rootScope = _rootScope;
            this.$stateParams = _stateParams;
            this.$uibModal = _uibModal;
            this.$couponService = _couponService;
            this._super(_scope);
        },

        defineScope: function () {
            this._defineViewHandler();
            this._refreshListView(0, this._VIEW_SIZE);
            this._scrollShowMore();
        },

        defineListeners: function () {
            var owner = this;
            this._contextReadyListener = this.$rootScope.$on('contextReady', function (event, userSelf) {
                if (userSelf && !owner.$scope.viewList) {
                    owner._refreshListView(0, owner._VIEW_SIZE);
                }
            });
        },

        destroy: function () {
            this._contextReadyListener();
            this._contextReadyListener = null;
        },

        _defineViewHandler: function () {
            // TODO
        },

        _refreshListView: function (page, size) {
            var owner = this;
            owner.$scope.loadingComleted = false;
            this.$couponService.cashList(page, size).then(
                function (resp) {
                    owner._searchSuccessHandler(resp.data, page);
                    owner.$scope.loadingCompleted = true;
                },
                function (resp) {
                    console.log(resp);
                }
            )
        },

        _searchSuccessHandler: function (data, page) {
            var lastList = this.$scope.viewList || {};
            lastList.page = page;
            lastList.total = data.total;
            lastList.last = data.last;
            if (page == 0) {
                lastList.result = [];
            } else {
                lastList.result = this.$scope.viewList.result;
            }
            for (var index = 0; index < data.result.length; index++) {
                lastList.result.push(data.result[index]);
            }
            if (data.result.length == 0) {
                lastList.last = true;
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

    CashListController.$inject = ['$rootScope', '$scope', '$stateParams', '$uibModal', 'CouponService'];

    angular.module('module.CashListController', ['service.CouponService']).controller('CashListController', CashListController);

});