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
        updateFn: '=update',
        selectorObj: '=selector',
        selectorKey: '=selectorKey',
        maxDisplayed: '='
      },
      link: function ($scope) {
        $scope.active = function (item) {
          return $scope.selectorObj[$scope.selectorKey] === item;
        };
        $scope.select = function (item) {
          if ($scope.active(item)) {
            $scope.selectorObj[$scope.selectorKey] = null;
          } else {
            $scope.selectorObj[$scope.selectorKey] = item;
          }
          $scope.updateFn();
        };
        $scope.show = function (item) {
          return _.isNull($scope.selectorObj[$scope.selectorKey]) ||
            _.isUndefined($scope.selectorObj[$scope.selectorKey]) ||
            $scope.selectorObj[$scope.selectorKey] === item;
        };
        $scope.increaseMaxDisplayed = function () {
          $scope.maxDisplayed = $scope.maxDisplayed + 10;
        };
      }
    };
  });
