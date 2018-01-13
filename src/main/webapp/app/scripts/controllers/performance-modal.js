'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:PerformanceModalCtrl
 * @description
 * # PerformanceModalCtrl
 * Controller of the PerformanceDashboard
 */
function PerformanceModalController(performance) {
  var performanceModalCtrl = this;
  performanceModalCtrl.performance = performance;
}

module.exports = PerformanceModalController;
