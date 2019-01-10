define([
    'base/BaseController',
    'service/UserService'
], function (BaseController) {

    var HeaderController = BaseController.extend({

        init: function ($rootScope, $scope, $state, $stateParams, $userService) {
            this.$rootScope = $rootScope;
            this.$state = $state;
            this.$stateParams = $stateParams;
            this.$userService = $userService;

            this._super($scope);
        },

        defineScope: function () {
            this._defineSignOut();
        },

        _defineSignOut: function () {
            var owner = this;
            this.$rootScope.signOut = function () {
                owner.$userService.logout().then(
                    function () {
                        window.session = {user: undefined};
                        window.location.href = "/";
                    }
                );
            }
        }
    });

    HeaderController.$inject = ['$rootScope', '$scope', '$state', '$stateParams', 'UserService'];
    angular.module('module.HeaderController', ['service.UserService']).controller('HeaderController', HeaderController);

});
