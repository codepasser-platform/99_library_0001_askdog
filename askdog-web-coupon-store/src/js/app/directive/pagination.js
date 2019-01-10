define(['angular'], function (angular) {

    var _GROUP_SIZE = 5;

    function pagination() {

        var _pageGroup = function (pageCount) {
            var groupCount = Math.ceil(pageCount / _GROUP_SIZE);
            var group = new Array();
            for (var i = 0; i < groupCount; i++) {
                var groupItems = new Array();
                for (var j = 1; j <= _GROUP_SIZE; j++) {
                    if ((i * _GROUP_SIZE) + j > pageCount) {
                        break;
                    }
                    groupItems.push((i * _GROUP_SIZE) + j);
                }
                group.push(groupItems);
            }
            return group;
        };

        var _paginationContent = function (pageNo, groupIndex, pageGroup, pageCount) {
            var navStart = '<li groupIndex="{groupIndex}" pageNo="{index}"  class="page-pre {disabled}" ><a>&laquo;</a></li>';
            var navItem = '<li groupIndex="{groupIndex}" pageNo="{index}" class="page {active}"><a>{index}</a></li>';
            var navEnd = '<li groupIndex="{groupIndex}" pageNo="{index}" class="page-next {disabled}"><a>&raquo;</a></li>';

            var groupItems = pageGroup[groupIndex];

            var nav = '<nav><ul class="pagination" pageCount="{pageCount}">'.format({pageCount: pageCount});
            for (var i = 0; i < groupItems.length; i++) {
                if (i == 0) {
                    nav = nav + navStart.format({
                            groupIndex: groupIndex,
                            index: groupItems[i] - 1,
                            disabled: (groupItems[i] - 1) <= 0 ? 'disabled' : ''
                        });
                }
                nav = nav + navItem.format({
                        groupIndex: groupIndex,
                        index: groupItems[i],
                        active: groupItems[i] == pageNo ? 'active' : ''
                    });
                if (i == groupItems.length - 1) {
                    nav = nav + navEnd.format({
                            groupIndex: groupIndex,
                            index: groupItems[i] + 1,
                            disabled: (groupItems[i] + 1) >= pageCount ? 'disabled' : ''
                        });
                }
            }
            nav = nav + '</ul></nav>';
            return nav;
        };

        var _paginationRender = function (element, pageNo, groupIndex, pageCount) {
            var pageGroup = _pageGroup(pageCount);
            var paginationNav = _paginationContent(pageNo, groupIndex, pageGroup, pageCount);
            $(element).empty();
            $(element).append(paginationNav);
            $(element).find('.pagination .page-pre:not(.disabled)').on('click', function () {
                var groupIndex = $(this).attr('groupIndex');
                var pageNo = $(this).attr('pageNo');
                groupIndex--;
                _paginationHandler(this, element, groupIndex, pageNo);
            });
            $(element).find('.pagination .page').on('click', function () {
                var groupIndex = $(this).attr('groupIndex');
                var pageNo = $(this).attr('pageNo');
                _paginationHandler(this, element, groupIndex, pageNo);
            });
            $(element).find('.pagination .page-next:not(.disabled)').on('click', function () {
                var groupIndex = $(this).attr('groupIndex');
                var pageNo = $(this).attr('pageNo');
                groupIndex++;
                _paginationHandler(this, element, groupIndex, pageNo);
            });

            /*$(element).unbind('_onPaginationListener');
             $(element).bind('_onPaginationListener', function (event, pageNo) {
             console.log('_onPaginationListener', pageNo);
             })*/
        };

        var _paginationHandler = function (handle, element, groupIndex, pageNo) {
            var pageCount = $(handle).closest('.pagination').attr('pageCount');
            var paginationElement = $(handle).closest('pagination');
            $(element).trigger('_onPaginationListener', pageNo);
            _paginationRender(paginationElement, pageNo, groupIndex, pageCount);
        };

        return {
            restrict: 'AE',
            scope: {
                pagingData: '='
            },
            link: function (scope, element, attributes) {
                scope.$watch(
                    function () {
                        return scope.pagingData;
                    },
                    function (pagingData) {
                        if (!pagingData) {
                            return;
                        }
                        var size = Number(attributes.size);
                        var total = Number(attributes.total);
                        if (isNaN(size) || isNaN(total)) {
                            return;
                        }
                        if (total <= 0 || size <= 0) {
                            return;
                        }
                        var pageCount = Math.ceil(total / size);
                        _paginationRender(element, 1, 0, pageCount)
                    }
                );
            }
        };
    }


    pagination.$inject = [];

    angular.module('app.directive.pagination', []).directive('pagination', pagination);
});