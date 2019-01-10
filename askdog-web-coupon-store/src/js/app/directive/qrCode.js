define(['angular', 'jquery.qrcode'], function (angular) {

    function qrCode() {
        return {
            restrict: 'AE',
            scope: {},
            link: function (scope, element, attributes) {
                if (attributes) {
                    var options = {
                        width: attributes.width || 120,
                        height: attributes.height || 120,
                        text: AskDog.ApiUtil.appUrl(attributes.text) || AskDog.ApiUtil.appUrl('/')
                    };
                    $(element).qrcode(options);
                }
            }
        };
    }

    qrCode.$inject = [];

    angular.module('app.directive.qrCode', []).directive('qrCode', qrCode);
});