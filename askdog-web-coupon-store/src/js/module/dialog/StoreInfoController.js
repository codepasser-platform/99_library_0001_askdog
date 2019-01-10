define([
    'base/BaseController',
    'jquery.validator',
    'service/CouponService',
    'service/StoreService',
    'service/StorageService'
], function (BaseController) {

    var StoreInfoController = BaseController.extend({

        init: function ($rootScope, $scope, $state, $stateParams, $couponService, $uibModal, $storeService, $storageService) {
            this.$rootScope = $rootScope;
            this.$state = $state;
            this.$stateParams = $stateParams;
            this.$uibModalInstance = $scope.$parent.$uibModalInstance;
            this.$couponService = $couponService;
            this.$uibModal = $uibModal;
            this.$message = $scope.$parent.message;
            this.$storeDetail = $scope.$parent.storeDetail;
            this.$storeService = $storeService;
            this.$storageService = $storageService;
            this._super($scope);
        },

        defineScope: function () {
            this._defineCancel();
            this._defineBindValidator();
            this._next();
            this._defineViewHandler();
            this._updateStore();
            this._previous();
            this._defineShowMap();
        },

        _defineCancel: function () {
            var owner = this;
            this.$scope.cancel = function () {
                owner.$uibModalInstance.dismiss("cancel");
            }
        },

        _defineViewHandler: function () {
            var owner = this;
            owner.$scope.storeSearch = function () {
                owner.$uibModalInstance.dismiss("cancel");
                var storeSearchModal = owner.$uibModal.open({
                    windowTemplateUrl: 'views/dialog/modal-window.html',
                    windowTopClass: 'pg-show-modal',
                    templateUrl: 'views/dialog/store-search.html',
                    controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                        $scope.$uibModalInstance = $uibModalInstance;
                        $scope.storeInfo = owner.$scope.storeDetail;
                    }]
                });
                storeSearchModal.result.then(function () {

                });
            };

            this.$scope.videoLoad = function () {
                var file = $("#pic_file")[0].files[0];
                if (file) {
                    var extension = file.name.split('.').pop().toLowerCase();
                    owner.$storageService.getToken(AskDog.TokenUtil.type.STORE_COVER, extension).then(
                        function (resp) {
                            var token = resp.data;
                            console.log(token);
                            //file upload
                            owner.$scope._upload(token, file);
                        }
                    );
                }
            };
            this.$scope._upload = function (token, file) {
                owner.$storageService.upload(token, file).then(
                    function (resp) {
                        if (resp && resp.status == 200) {
                            owner.$scope.avatarCache = {
                                linkId: resp.data.linkId,
                                avatar: resp.data.url
                            };
                            console.log( owner.$scope.avatarCache);
                            console.log(owner.$scope.storeDetail);
                            owner.$scope.storeDetail.linkId = resp.data.linkId;
                        }
                    }
                );
            };

            owner.$scope.searchUser = function () {
                owner.$storeService.storeSearch(owner.$scope.userKey)
                    .then(function (resp) {
                        owner.$scope.searchResult = resp.data;
                    });
            };

            owner.$scope.selectUser = function (id, e) {
                $("#btn_add").removeClass("disabled");
                owner.$scope.storeDetail.userId = id;
            };

            owner.$scope.createUser = function (e) {
                var createUserModal = owner.$uibModal.open({
                    windowTemplateUrl: 'views/dialog/modal-window.html',
                    windowTopClass: 'pg-show-modal',
                    templateUrl: 'views/dialog/create-user.html',
                    controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                        $scope.$uibModalInstance = $uibModalInstance;
                        $scope.index = 0.9;
                        $scope.createUserCallback = function (user) {
                            $("#btn_add").removeClass("disabled");
                            owner.$scope.createdUser = user;
                            owner.$scope.storeDetail.userId = user.id;
                        };
                    }]
                });
            };

            this.$scope.createStore = function () {
                if ($("#btn_add").hasClass("disabled")) {
                    return;
                }
                owner.$storeService.addStore(owner.$scope.storeDetail).then(
                    function () {
                        owner.$uibModalInstance.close();
                    }
                );
            }
        },

        _defineBindValidator: function () {
            var owner = this;
            this.$scope.bindValidator = function (element) {
                $(element).validate({
                    rules: {
                        storeName: {
                            required: true,
                            maxlength: 24
                        },
                        storeAddress: {
                            required: true,
                            maxlength: 50
                        },
                        contact: {
                            required: true
                        },
                        contactTel: {
                            required: true,
                            phone:true
                        },
                        picFile: {
                            required: true
                        },
                        description: {
                            required: true,
                            maxlength:150
                        },
                        hours:{
                            required: true
                        },
                        storeType:{
                            required:true,
                            maxlength:5
                        },
                        money:{
                            required:true
                        }
                    },
                    messages: {
                        storeName: {
                            required: '请填写商户名',
                            maxlength: "商户名过长"
                        },
                        storeAddress: {
                            required: '请填写商户地址',
                            maxlength: '地址过长'
                        },
                        contact: {
                            required: ''
                        },
                        contactTel: {
                            required: "",
                            phone:""
                        },
                        picFile: {
                            required: ''
                        },
                        description: {
                            required: '',
                            maxlength:'输入内容过长'
                        },
                        hours:{
                            required: "请填写营业时间"
                        },
                        storeType:{
                            required:'',
                            maxlength:""
                        },
                        money:{
                            required:""
                        }
                    },
                    submitHandler: function () {
                        if (owner.$message.addStore) {
                            owner.$scope.next();
                        } else {
                            owner.$scope.updateStoreMessage();
                        }
                    }
                });
            }
        },

        _next: function () {
            var owner = this;
            owner.$scope.next = function () {
                if (owner.$scope.storeDetail.linkId == undefined) {
                    owner.$scope.error = '图片上传出现问题，请重试';
                    owner.$scope.$digest();
                    return;
                }
                if( owner.$scope.storeDetail.lat == undefined){
                    owner.$scope.error ='请选取位置信息';
                    owner.$scope.$digest();
                    return;
                }
                var current = $(".form-view.active"), next = current.next(".form-view");
                if (next) {
                    current.removeClass("active").removeClass("a-fadeinB");
                    next.addClass("active").addClass("a-fadeinB");
                    $("#btn_previous").removeClass("hidden");
                    if (!next.next(".form-view").length) {
                        $("#btn_next").addClass("hidden");
                        $("#btn_add").removeClass("hidden");
                    }
                }
            }

        },

        _previous: function () {
            var owner = this;
            this.$scope.previous = function () {
                var current = $(".form-view.active"), prev = current.prev(".form-view");
                if (prev) {
                    current.removeClass("active").removeClass("a-fadeinB");
                    prev.addClass("active").addClass("a-fadeinB");
                    $("#btn_next").removeClass("hidden");
                    $("#btn_add").addClass("hidden");
                    if (!prev.prev(".form-view").length) {
                        $("#btn_previous").addClass("hidden");
                    }
                }
                owner.$scope.error = false;
            }
        },

        _updateStore: function () {
            var owner = this;
            owner.$scope.updateStoreMessage = function () {
                if (owner.$scope.storeDetail.description == '') {
                    owner.$scope.error = '请填写商户描述';
                    owner.$scope.$digest();
                    return;
                }
                owner.$storeService.updateStoreInfo(owner.$storeDetail.id, owner.$scope.storeDetail)
                    .then(function (resp) {
                        owner.$uibModalInstance.close();
                    });
            }


        },

        _defineShowMap:function(){
            var owner = this;
            owner.$scope.showMap = function(){
                $("#map-info").removeClass("hidden");
            }
            window.addEventListener('message', function(event) {
                // 接收位置信息，用户选择确认位置点后选点组件会触发该事件，回传用户的位置信息
                var loc = event.data;
                if (loc && loc.module == 'locationPicker') {//防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
                    owner.$scope.storeDetail.address = loc.poiaddress + loc.poiname;
                    owner.$scope.storeDetail.lat = loc.latlng.lat;
                    owner.$scope.storeDetail.lng = loc.latlng.lng;
                    $("#map-info").addClass("hidden");
                    owner.$scope.$digest();
                }
            }, false);
            owner.$scope.closeMap = function(){
                $("#map-info").addClass("hidden");
            }
        }
    });

    StoreInfoController.$inject = ['$rootScope', '$scope', '$state', '$stateParams', 'CouponService', '$uibModal', 'StoreService', 'StorageService'];

    angular.module('module.dialog.StoreInfoController', ['service.CouponService', 'service.StoreService', 'service.StorageService']).controller('StoreInfoController', StoreInfoController);

});
