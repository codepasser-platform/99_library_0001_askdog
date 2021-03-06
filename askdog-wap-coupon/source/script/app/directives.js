/**
 * Defines un-managed directives in the application.
 * NOTE: only use this out side the customer controllers.
 */
define([
    'angular',
    'app/directive/pageTitle',
    'app/directive/authRequired',
    'app/directive/afterRender',
    'app/directive/animation',
    'app/directive/wechatShare',
    'app/directive/mapLocation',
    'app/directive/swiper'
], function () {
    'use strict';

    angular.module('app.directives', [
        'app.directive.pageTitle',
        'app.directive.authRequired',
        'app.directive.afterRender',
        'app.directive.animation',
        'app.directive.wechatShare',
        'app.directive.mapLocation',
        'app.directive.swiper'
    ]);
});