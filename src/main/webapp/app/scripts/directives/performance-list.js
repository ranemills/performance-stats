'use strict';

import _ from 'lodash';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:performanceList
 * @description
 * # performanceList
 */
function performanceList(ModalService) {
  return {
    restrict: 'E',
    templateUrl: 'views/directives/performance-list.html',
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

module.exports = performanceList;
