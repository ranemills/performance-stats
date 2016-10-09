'use strict';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:statsWidget
 * @description
 * # statsWidget
 */
angular.module('PerformanceDashboard')
  .directive('statsWidgetCard', function (_, $window) {
    return {
      templateUrl: 'views/directives/stats-widget-card.html',
      scope: {
        filterName: '=',
        listData: '=',
        selectFn: '=',
        selectObj: '='
      },
      restrict: 'E',
      link: function postLink(scope) {
        // scope.filterName = scope.$eval(attrs.filterName);
        // scope.filterObj = scope.$eval(attrs.filterObj);
        scope.view = 'chart';

        var d3 = $window.d3;

        scope.chartData = [{
          key: scope.filterName,
          values: _.take(scope.listData, 10)
        }];

        var baseChartOptions = {
          chart: {
            type: 'discreteBarChart',
            height: 450,
            margin: {
              top: 20,
              right: 20,
              bottom: 100,
              left: 55
            },
            x: function (d) {
              return d.property;
            },
            y: function (d) {
              return d.count;
            },
            showValues: true,
            valueFormat: function (d) {
              return d3.format(',d')(d);
            },
            xAxis: {
              rotateLabels: -45
            },
            yAxis: {
              axisLabel: 'Count',
              axisLabelDistance: -10
            }
          }
        };

        scope.chartOptions = function () { //key) {
          // _.set(baseChartOptions, 'chart.discretebar.dispatch.elementClick', function (e) {
            // exploreCtrl.selectFilter(key, e.data.label);
          // });
          return baseChartOptions;
        };

      }
    };
  });