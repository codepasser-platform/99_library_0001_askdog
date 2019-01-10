define([
    'angular.ui.router'
], function () {
    'use strict';

    angular.module('app.routes', ['ui.router']).config(['$stateProvider', '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise('/');
            $stateProvider
            /************************************************************layout**/
                .state('layout', stateOptions({
                    abstract: true,
                    templateUrl: 'views/base/layout.html'
                }))
                .state('layout.view', stateOptions({
                    abstract: true,
                    templateUrl: 'views/base/view.html'
                }))
                .state('layout.view-index', stateOptions({
                    abstract: true,
                    templateUrl: 'views/base/view-index.html'
                }))
                /************************************************************index**/
                .state('layout.view.index', {
                    url: '',
                    templateUrl: 'views/home.html',
                })
                .state('layout.view.index.main', {
                    url: '/',
                    parent: 'layout.view.index'
                })
                /************************************************************pages**/
                .state('layout.view.store', {
                    url: '/store/:id',
                    templateUrl: 'views/store.html'
                })
                .state('layout.view.detail', {
                    url: '/detail/:id',
                    templateUrl: 'views/detail.html'
                })
                .state('layout.view.cash', {
                    url: '/cash',
                    templateUrl: 'views/cash-list.html'
                })
                .state('layout.view.cash-detail', {
                    url: '/cash-detail/:id',
                    templateUrl: 'views/cash-detail.html'
                })
                .state('layout.view.qr-code', {
                    url: '/qr-code',
                    templateUrl: 'views/qr-code.html'
                })
                .state('layout.view.contact-us', {
                    url: '/contact-us',
                    templateUrl: 'views/contact-us.html'
                });

        }
    ]).run(['$rootScope', '$state', '$stateParams',
        function ($rootScope, $state, $stateParams) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
        }
    ]);

    function stateOptions(options) {
        var defaultOptions = {
            controller: ['$rootScope', '$state', function ($rootScope, $state) {
                // title should be defined in resolve section, but not work !
                if ($state.current.data) {
                    var title = $state.current.data.title;
                    title && ($rootScope.title = title);
                }
            }]
        };
        return angular.extend(defaultOptions, options);
    }
});