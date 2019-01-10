define([
    'base/BaseController',
    'jquery.validator',
    'vod-sdk',
    'service/StorageService',
    'service/CouponService',
    'service/ProductService'
], function (BaseController) {

    var ProductController = BaseController.extend({

        linkIds: [],

        init: function ($rootScope, $scope, $state, $stateParams, $storageService, $couponService, $productService) {
            this.$rootScope = $rootScope;
            this.$state = $state;
            this.$stateParams = $stateParams;
            this.$uibModalInstance = $scope.$parent.$uibModalInstance;

            this.$storageService = $storageService;
            this.$couponService = $couponService;
            this.$productService = $productService;

            this._super($scope);
        },

        defineScope: function () {
            this._bindValidator();
            this._defineRefreshView();
            this._defineViewHandler();
        },


        _bindValidator: function () {
            var owner = this;
            this.$scope.product = {};
            this.$scope.bindValidator = function (element) {
                $(element).validate({
                    rules: {
                        productName: {
                            required: true,
                            maxlength: 20
                        },
                        couponNormal: {
                            required: true
                        },
                        couponForward: {
                            required: true
                        },
                        productVideo: {
                            required: false
                        },
                        description: {
                            required: true
                        }

                    },
                    messages: {
                        productName: {
                            required: '',
                            maxlength: '产品名称不能超过20个文字'
                        },
                        couponNormal: {
                            required: ''
                        },
                        couponForward: {
                            required: ''
                        },
                        productVideo: {
                            required: ''
                        },
                        description: {
                            required: ''
                        }
                    },
                    submitHandler: function () {
                        owner._createProduct();
                    }
                });
            }
        },

        _defineRefreshView: function () {
            this._loadCouponData();
        },

        _loadCouponData: function () {
            var owner = this;
            this.$couponService.storeOwnedCoupons(this.$stateParams.storeId).then(
                function (resp) {
                    owner.$scope.couponNormalList = [];
                    owner.$scope.couponForwardList = [];
                    for (var index = 0; index < resp.data.result.length; index++) {
                        var item = resp.data.result[index];
                        if (item.type == 'NORMAL') {
                            owner.$scope.couponNormalList.push(item);
                        }
                        if (item.type == 'FORWARDED') {
                            owner.$scope.couponForwardList.push(item);
                        }
                    }
                }
            )
        },

        _defineViewHandler: function () {
            var owner = this;
            this.$scope.cancel = function () {
                owner.$uibModalInstance.dismiss("cancel");
            };

            this.$scope.videoLoad = function () {
                owner.$scope.validationMessage = undefined;
                var file = $("#video-file")[0].files[0];
                var extension = file.name.split('.').pop().toLowerCase();
                if (extension != "mov" && extension != "mp4" && extension != "avi") {
                    owner.$scope.validationMessage = "视频文件格式不支持，支持mp4、mov、avi格式";
                    owner.$scope.$digest();
                    return false;
                }
                if ((file.size / 1024 / 1024) > owner._FILE_LIMIT) {
                    owner.$scope.validationMessage = "文件过大无法上传，最大支持1GB";
                    owner.$scope.$digest();
                    return false;
                }
                //Get token.
                owner.$storageService.getToken(AskDog.TokenUtil.type.PRODUCT_VIDEO, extension).then(
                    function (resp) {
                        var token = resp.data;
                        owner.$scope.product.video_id = token.resource_id;
                        //file upload
                        owner._upload(token, file);
                    }
                );
            };
            this.$scope.imageLoad = function (index) {
                var file = $(".product-image")[index].files[0];
                if (file) {
                    var extension = file.name.split('.').pop().toLowerCase();
                    owner.$storageService.getToken(AskDog.TokenUtil.type.PRODUCT_COVER, extension).then(
                        function (resp) {
                            var token = resp.data;
                            owner.$scope._imageUpload(token, file);
                        }
                    );
                }
            };
            this.$scope._imageUpload = function (token, file) {
                owner.$storageService.upload(token, file).then(
                    function (resp) {
                        owner.linkIds.push(resp.data.linkId);
                    }
                );
            };
        },

        _upload: function (token, file) {
            var owner = this;
            this.$scope.uploadComplete = false;
            this._uploader = new VODUpload({
                'onUploadFailed': function (fileName, code, message) {
                },
                'onUploadSucceed': function (fileName) {
                    owner.$scope.uploadComplete = true;
                    owner.$scope.$digest();
                    $("#video-progress .progress-bar").css("width", "100%");
                    $("#video-progress .progress-bar").text("100%");
                },
                'onUploadProgress': function (fileName, totalSize, uploadedSize) {
                    var percentage = Math.ceil(uploadedSize * 100 / totalSize) + "%";
                    $("#video-progress .progress-bar").css("width", percentage);
                    $("#video-progress .progress-bar").text(percentage);
                }
            });
            this._uploader.init(token.OSSAccessKeyId, token.secret_key);
            this._uploader.addFile(file, token.endpoint, token.bucket, token.key);
            this._uploader.startUpload();
        },

        _createProduct: function () {
            var owner = this;
            if(this.$scope.product.special){
                if(owner.$scope.product.video_id == undefined){
                    owner.$scope.error = '请上传视频';
                    owner.$scope.$digest();
                    return;
                }
            }
            var pureProduct = {
                name: this.$scope.product.name,
                description: this.$scope.product.description,
                video_id: this.$scope.product.video_id,
                store_id: this.$stateParams.storeId,
                coupons: [this.$scope.product.couponNormal, this.$scope.product.couponForward],
                pictures: owner.linkIds,
                tags: this.$scope.product.special ? ['SPECIAL']: null
            };
            this.$productService.createProduct(pureProduct).then(
                function (resp) {
                    owner.$uibModalInstance.close();
                }
            );

        }

    });

    ProductController.$inject = ['$rootScope', '$scope', '$state', '$stateParams', 'StorageService', 'CouponService', 'ProductService'];

    angular.module('module.ProductController', ['service.StorageService', 'service.CouponService', 'service.ProductService']).controller('ProductController', ProductController);

});
