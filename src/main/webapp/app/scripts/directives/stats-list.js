'use strict';

const _ = require('lodash');

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:statsList
 * @description
 * # statsList
 */
module.exports = function() {
  return {
    restrict: 'E',
    template: require('../../views/directives/stats-list.html'),
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
}
