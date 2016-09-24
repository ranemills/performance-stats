'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:DashboardCtrl
 * @description
 * # DashboardCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('DashboardController', function (QuartersApi) {

    var dashboardCtrl = this;

    QuartersApi.get().then(function (response) {
      dashboardCtrl.performances = response.data;
    });

  });
