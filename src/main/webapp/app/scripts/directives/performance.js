'use strict';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:performance
 * @description
 * # performance
 */
module.exports = function() {
  return {
    templateUrl: 'views/performance.html',
    restrict: 'E',
    scope: {
      show: '='
    }
  };
}
