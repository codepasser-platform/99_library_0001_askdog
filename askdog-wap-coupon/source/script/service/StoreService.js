define(['base/BaseService'], function (BaseService) {

    var StoreService = BaseService.extend({

        init: function (http, scope) {
            this._super(http, scope);
        },

        stores: function (page,size) {
            return this.get("/api/stores?page={page}&size={size}".format({"page":page,"size":size}));
        },

        storeDetail:function(id){
            return this.get("/api/stores/" + id);
        }

    });

    angular.module('service.StoreService', [])
        .provider('StoreService', function () {
            this.$get = ['$http', '$rootScope', function (http, scope) {
                return new StoreService(http, scope);
            }];
        });

});