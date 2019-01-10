define([
    'base/BaseController'
], function (BaseController) {

    var HeaderController = BaseController.extend({

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

        _refreshListView: function () {
            this.$scope.display = !AskDog.BrowserUtil.isWeChat();
        }

    });

    HeaderController.$inject = ['$rootScope', '$scope', '$stateParams', '$uibModal'];

    angular.module('module.HeaderController', []).controller('HeaderController', HeaderController);

});