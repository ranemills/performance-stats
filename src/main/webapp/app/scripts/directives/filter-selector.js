'use strict';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:filterSelector
 * @description
 * # filterSelector
 */
angular.module('PerformanceDashboard')
  .directive('filterSelector', function () {
    return {
      restrict: 'E',
      templateUrl: 'views/directives/filter-selector.html',
      scope: {
        filters: '=filters',
        onSelectFn: '=onSelect',
        select: '=selectorObj'
      },
      link: function ($scope) {
        $scope.activeKey = null;
        $scope.onSelect = function (filterKey) {
          $scope.activeKey = filterKey;
          $scope.onSelectFn(filterKey);
        };
        $scope.active = function (filterKey) {
          return $scope.activeKey === filterKey;
        };
      }
    };
  });
