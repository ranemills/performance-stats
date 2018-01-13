'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:DashboardCtrl
 * @description
 * # DashboardCtrl
 * Controller of the PerformanceDashboard
 */
module.exports = function(humanizeDuration, moment, MilestonesService, QuartersApi) {
  var dashboardCtrl = this;

  QuartersApi.get().then(function (response) {
    dashboardCtrl.performances = response.data;
  });

  QuartersApi.getSnapshot().then(function(response) {
    dashboardCtrl.snapshot = response.data;
    console.log(dashboardCtrl.snapshot);
    dashboardCtrl.snapshot.time = humanizeDuration(dashboardCtrl.snapshot.time*60*1000, {
      units: ['y', 'mo', 'd', 'h', 'm']
    });
  });

  MilestonesService.recentMilestones().then(function (response) {
    dashboardCtrl.milestones = response.data;
  });

}