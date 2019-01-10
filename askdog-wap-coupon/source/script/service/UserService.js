define(['base/BaseService'], function (BaseService) {

    var UserService = BaseService.extend({

        init: function (http, scope) {
            this._super(http, scope);
        },

        status: function () {
            return this.get("/api/users/me/status");
        },

        me: function () {
            return this.get("/api/users/me");
        },

        login: function (userInfo) {
            return this.postForm(AskDog.ApiUtil.APP_LOCATION + "/login?ajax=true", userInfo);
        },

        logout: function () {
            return this.get(AskDog.ApiUtil.APP_LOCATION + "/logout?ajax=true");
        },

        register: function (userInfo, phoneRegister) {
            var registerUri = '/api/users/phone';
            if (!phoneRegister) {
                registerUri = '/api/users';
            }
            return this.post(registerUri, userInfo);
        }

    });

    angular.module('service.UserService', [])
        .provider('UserService', function () {
            this.$get = ['$http', '$rootScope', function (http, scope) {
                return new UserService(http, scope);
            }];
        });

});