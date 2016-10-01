'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:PerformanceModalCtrl
 * @description
 * # PerformanceModalCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('PerformanceModalController', function (performance) {
    var performanceModalCtrl = this;
    performanceModalCtrl.performance = performance;
  });
