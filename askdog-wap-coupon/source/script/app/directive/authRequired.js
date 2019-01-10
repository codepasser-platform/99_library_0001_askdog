define(['angular', 'service/UserService'], function () {

    /**
     * loader - Directive for loader
     */
    function authRequired($rootScope, $userService, $uibModal) {
        return {
            priority: 100,
            restrict: 'A',
            compile: function ($element, attr) {

                function authenticated(authenticated, anonymous) {
                    $userService.status().then(
                        function (resp) {
                            if (resp.data == "AUTHENTICATED") {
                                authenticated && authenticated.call(this, resp);
                            } else if (resp.data == "ANONYMOUS") {
                                anonymous && anonymous.call(this, resp);
                            }
                        }
                    );
                }

                function doContinue(scope, element) {
                    var elementType = $(element).attr("type");
                    var handler = $(element).attr("ng-click");
                    var animateEvent = $(element).attr("animate-event");
                    if (elementType == "submit") {
                        scope.continue = false;
                        var form = $(element).closest("form");
                        handler = $(form).attr("ng-submit");
                        var invalid = $(form).hasClass("ng-invalid");
                        if (handler && !invalid) {
                            scope.$eval(handler);
                        } else {
                            $(form).submit();
                        }
                    } else if (handler) {
                        scope.continue = false;
                        if (animateEvent) {
                            $(element).trigger(animateEvent);
                        }
                        scope.$eval(handler);
                    } else {
                        scope.continue = true;
                        $(element).click();
                    }
                }

                return {
                    pre: function link(scope, element) {
                        element.on('click', function (event) {
                            if (!scope.continue) {
                                // prevents ng-click to be executed
                                event.stopImmediatePropagation();
                                // prevents href
                                event.preventDefault();

                                authenticated(
                                    function () {
                                        doContinue(scope, element);
                                    },
                                    function () {
                                        if (AskDog.BrowserUtil.isWeChat()) {
                                            if ($rootScope.modalOpened) {
                                                return;
                                            }
                                            $rootScope.modalOpened = true;
                                            var messageModal = $uibModal.open({
                                                windowTemplateUrl: 'views/dialog/modal-window.html',
                                                windowTopClass: 'modal-default',
                                                templateUrl: 'views/dialog/message.html',
                                                controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                                                    $scope.$uibModalInstance = $uibModalInstance;
                                                    $scope.message = '即将通过微信登录' +'<br />'+'登录后方可继续操作';
                                                }]
                                            });

                                            messageModal.opened.then(function () {
                                                $("video").hide();
                                            });

                                            messageModal.closed.then(function () {
                                                $("video").show();
                                                $rootScope.modalOpened = false;
                                            });

                                            messageModal.result.then(function () {
                                                window.location.href = "/login/wechat?request=" + encodeURIComponent(window.location.href);
                                            });
                                        } else {
                                            $rootScope.signIn(
                                                function () {
                                                    doContinue(scope, element);
                                                },
                                                function () {
                                                    scope.continue = false;
                                                }
                                            );
                                        }
                                    });

                                return false;
                            }

                            scope.continue = false;
                        });
                    },
                    post: function () {
                    }
                }
            }
        };
    }

    authRequired.$inject = ['$rootScope', 'UserService', '$uibModal'];
    angular.module('app.directive.authRequired', ['service.UserService']).directive('authRequired', authRequired);
});