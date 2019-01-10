define(['base/BaseService'], function (BaseService) {

    var StoreService = BaseService.extend({

        init: function (http, scope) {
            this._super(http, scope);
        },

        list: function (page, size) {
            return this.get("/api/stores?page={page}&size={size}".format({"page": page, "size": size}));
        },

        storeList:function(page,size){
            return this.get('/api/stores/list?page={page}&size={size}'.format({"page":page,"size":size}));
        },

        deleteStoreInfo:function(id){
            return this.delete('/api/stores/' +  id);
        },

        getStoreDetail:function(storeId){
            return this.get('/api/stores/'+ storeId);
        },

        updateStoreInfo:function(storeId,storeInfo){
            var data = {
                name:storeInfo.name,
                address:storeInfo.address,
                phone:storeInfo.phone,
                description:storeInfo.description,
                user_id:storeInfo.owner.id,
                cover_image_link_id:storeInfo.linkId,
                pure_contacts_user:{
                    name:storeInfo.contacts_user_detail.name,
                    phone:storeInfo.phone
                }
            };
            return this.put('/api/stores/'+ storeId,data);
        },

        storeSearch:function(key){
            return this.get('/api/stores/search/' + key);
        },

        addStore:function(storeInfo){
            var data = {
                name:storeInfo.name,
                address:storeInfo.address,
                phone:storeInfo.phone,
                description:storeInfo.description,
                user_id:storeInfo.userId,
                cover_image_link_id:storeInfo.linkId,
                pure_contacts_user:{
                    name:storeInfo.contacts_user_detail.name,
                    phone:storeInfo.phone
                },
                location:{
                    lat:storeInfo.lat,
                    lng:storeInfo.lng
                },
                type:storeInfo.type,
                cpc:storeInfo.money,
                business_hours:storeInfo.time

            };
            console.log(data);
            return this.post('/api/stores',data)
        },

        getStoreData:function(){
            return this.get('/api/stores/storedata');
        },

        getCouponData:function(){
            return this.get('api/stores/coupondata');
        }
    });

    angular.module('service.StoreService', [])
        .provider('StoreService', function () {
            this.$get = ['$http', '$rootScope', function (http, scope) {
                return new StoreService(http, scope);
            }];
        });

});