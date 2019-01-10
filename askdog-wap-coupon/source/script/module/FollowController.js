define([
    'base/BaseController'
], function (BaseController) {

    var FollowController = BaseController.extend({

        init: function (_rootScope, _scope, _stateParams, _uibModal) {
            this.$rootScope = _rootScope;
            this.$stateParams = _stateParams;
            this.$uibModal = _uibModal;

            this._super(_scope);
        },

        defineScope: function () {
            this._defineViewHandler();
            this._refreshListView();
        },

        defineListeners: function () {
            var owner = this;
            this._contextChangeListener = this.$rootScope.$on('contextChange', function (event,userSelf) {
                owner.$rootScope.subscribed = (!!userSelf && userSelf.subscribed);
            });
            this._contextReadyListener = this.$rootScope.$on('contextReady', function (event, userSelf) {
                owner.$rootScope.subscribed = (!!userSelf && userSelf.subscribed);
            });
        },

        destroy: function () {
            this._contextChangeListener();
            this._contextReadyListener();
            this._contextChangeListener = null;
            this._contextReadyListener = null;
        },

        _defineViewHandler: function () {
        },

        _refreshListView: function () {
        }

    });

    FollowController.$inject = ['$rootScope', '$scope', '$stateParams', '$uibModal'];

    angular.module('module.FollowController', []).controller('FollowController', FollowController);

});