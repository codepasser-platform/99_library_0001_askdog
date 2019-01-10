define(['angular', 'jquery.video'], function (angular, videojs) {

    function videoView() {

        return {
            restrict: 'AE',
            scope: {
                content: '='
            },
            link: function (scope, element, attributes) {
                scope.$watch(
                    function () {
                        return scope.content;
                    },
                    function (content) {
                        if (!content) {
                            return;
                        }
                        var videoUrl, thumbnail, videoInfo = content.video;
                        if (videoInfo.transcode_videos && videoInfo.transcode_videos.length > 0) {
                            videoUrl = videoInfo.transcode_videos[0].url;
                        } else if (videoInfo.source && videoInfo.source.url) {
                            videoUrl = videoInfo.source.url;
                        } else {
                            // // TODO
                            //  $(element).append("<div class='tran-code-fail'>" +
                            //  "<i class='iconfont icon-logo'></i>" +
                            //  "<img src='images/loading-fail.gif'>" +
                            //  "<div>视频正在转码中...</div>" +
                            //  "</div>"
                            //  );
                        }
                        if (videoInfo.snapshots && videoInfo.snapshots.length > 0) {
                            thumbnail = videoInfo.snapshots[0].url;
                        }
                        if (videoUrl) {
                            var videoPlayer = '<video id="video-player" src="' + videoUrl + '" class="video-js vjs-default-skin vjs-big-play-centered"  poster="' + thumbnail + '"></video>';
                            $(element).replaceWith(videoPlayer);
                            videoPlayer = $('#video-player')[0];
                            videojs(
                                videoPlayer,
                                {
                                    autoplay: false,
                                    loop: false,
                                    techOrder: ["html5", "flash"],
                                    controls: true,
                                    width: window.screen.width
                                },
                                function () {
                                    this.on('play', function () {
                                    });
                                    this.on('pause', function () {
                                    });
                                    this.on('ended', function () {
                                    });
                                });
                        }
                    }
                );
            }
        }
    }

    videoView.$inject = [];
    angular.module('app.directive.videoView', []).directive('videoView', videoView);
});