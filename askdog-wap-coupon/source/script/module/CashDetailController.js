define([
    'base/BaseController',
    'app/directive/adAnalytics',
    'service/CouponService'
], function (BaseController) {

    var CashDetailController = BaseController.extend({

        init: function (_rootScope, _scope, _stateParams, _uibModal, _couponService) {
            this.$rootScope = _rootScope;
            this.$stateParams = _stateParams;
            this.$uibModal = _uibModal;
            this.$couponService = _couponService;
            this._super(_scope);
        },

        defineScope: function () {
            this._defineViewHandler();
            this._refreshListView();
        },

        defineListeners: function () {
            var owner = this;
            this._contextReadyListener = this.$rootScope.$on('contextReady', function (event, userSelf) {
                if (userSelf && !owner.$scope.cashDetail) {
                    owner._refreshListView();
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


        _refreshListView: function () {
            var owner = this;
            this.$couponService.cashDetail(owner.$stateParams.id).then(
                function (resp) {
                    owner.$scope.cashDetail = resp.data;
                }
            )
        }
    });

    CashDetailController.$inject = ['$rootScope', '$scope', '$stateParams', '$uibModal', 'CouponService'];

    angular.module('module.CashDetailController', ['service.CouponService']).controller('CashDetailController', CashDetailController);

});