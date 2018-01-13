'use strict';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:performance
 * @description
 * # performance
 */
function performance() {
  return {
    templateUrl: 'views/performance.html',
    restrict: 'E',
    scope: {
      show: '='
    }
  };
}

module.exports = performance;
