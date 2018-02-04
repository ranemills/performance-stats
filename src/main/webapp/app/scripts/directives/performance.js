'use strict';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:performance
 * @description
 * # performance
 */
module.exports = function() {
  return {
    template: require('../../views/performance.html'),
    restrict: 'E',
    scope: {
      show: '='
    }
  };
}
