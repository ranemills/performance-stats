'use strict';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:statsList
 * @description
 * # statsList
 */
angular.module('PerformanceDashboard')
  .directive('statsList', function (_) {
    return {
      restrict: 'E',
      templateUrl: 'views/directives/stats-list.html',
      scope: {
        items: '=',
        selectFn: '=',
        selectorKey: '=selectorKey'
      },
      link: function ($scope) {
        var selectedItem = null;
        $scope.maxDisplayed = 10;

        $scope.active = function (item) {
          return selectedItem === item;
        };
        $scope.show = function (item) {
          return _.isNull(selectedItem) || $scope.active(item);
        };
        $scope.select = function (item) {
          selectedItem = $scope.active(item) ? null : item;
          $scope.selectFn($scope.selectorKey, selectedItem);
        };
        $scope.showMore = function () {
          $scope.maxDisplayed = $scope.maxDisplayed + 10;
        };
      }
    };
  });
