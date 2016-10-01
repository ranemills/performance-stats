'use strict';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:performanceList
 * @description
 * # performanceList
 */
angular.module('PerformanceDashboard')
  .directive('performanceList', function (ModalService) {
    return {
      restrict: 'E',
      templateUrl: 'views/directives/performance-list.html',
      scope: {
        performances: '='
      },
      link: function ($scope) {
        var expanded = null;

        $scope.expand = function (id) {
          if (expanded === id) {
            expanded = null;
          } else {
            expanded = id;
          }
        };

        $scope.isExpanded = function (id) {
          return expanded === id;
        };

        $scope.openPerformance = function (performance) {
          ModalService.openPerformanceModal(performance);
        };
      }
    };
  });
