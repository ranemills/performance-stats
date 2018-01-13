'use strict';

const _ = require('lodash');

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:performanceList
 * @description
 * # performanceList
 */
module.exports = function(ModalService) {
  return {
    restrict: 'E',
    template: require('../../views/directives/performance-list.html'),
    scope: {
      performances: '='
    },
    link: function ($scope, element, attrs) {
      $scope.small = !_.isUndefined(attrs.small);

      $scope.maxDisplayed = $scope.small ? 5 : 100;

      $scope.openPerformance = function (performance) {
        ModalService.openPerformanceModal(performance);
      };
    }
  };
}
