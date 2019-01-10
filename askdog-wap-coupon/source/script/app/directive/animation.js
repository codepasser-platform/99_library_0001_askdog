define(['angular', 'jquery.velocity'], function () {

    function animation() {
        return {
            priority: 1000,
            restrict: 'AE',
            scope: {},
            link: function (scope, element, attributes) {
                // TODO animte type : zoom
                var animateType = attributes.animateType || 'zoom';
                var animateEvent = attributes.animateEvent || 'click';
                var animateDuration = !isNaN(Number(attributes.animateDuration)) ? Number(attributes.animateDuration) : 500;
                var animateSelector = attributes.animateSelector ? $(element).find(attributes.animateSelector) : $(element);

                $(element).bind(animateEvent, function () {
                    console.log('click')
                    $(animateSelector).css("color", "#ff7b00");
                    $(animateSelector).velocity(
                        {
                            "font-size": "2em",
                            "opacity": 0,
                            "left": "-3px"
                        },
                        {
                            duration: animateDuration,
                            complete: function () {
                                $(animateSelector).css("font-size", "").css("opacity", "").css("color", "").css("left", "");
                            }
                        }
                    );
                });
            }
        }
    }

    animation.$inject = [];
    angular.module('app.directive.animation', []).directive('animation', animation);
});