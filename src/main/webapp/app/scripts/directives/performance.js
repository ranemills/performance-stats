'use strict';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:performance
 * @description
 * # performance
 */
angular.module('PerformanceDashboard')
  .directive('performance', function () {
    return {
      templateUrl: 'views/performance.html',
      restrict: 'E',
      scope: {
        show: '='
      }
    };
  });
