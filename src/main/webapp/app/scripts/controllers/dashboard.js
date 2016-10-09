'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:DashboardCtrl
 * @description
 * # DashboardCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('DashboardController', function (humanizeDuration, moment, MilestonesService, QuartersApi) {

    var dashboardCtrl = this;

    QuartersApi.get().then(function (response) {
      dashboardCtrl.performances = response.data;
    });

    QuartersApi.getSnapshot().then(function(response) {
      dashboardCtrl.snapshot = response.data;
      dashboardCtrl.snapshot.time = 896760;
      dashboardCtrl.snapshot.time = humanizeDuration(dashboardCtrl.snapshot.time*1000, {
        units: ['y', 'mo', 'd', 'h', 'm']
      });
      console.log(dashboardCtrl.snapshot);
    });

    MilestonesService.recentMilestones().then(function (response) {
      dashboardCtrl.milestones = response.data;
    });

  });
