'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:RecentMilestonesCtrl
 * @description
 * # RecentMilestonesCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('RecentMilestonesController', function (moment, MilestonesService) {
    var recentMilestonesCtrl = this;

    recentMilestonesCtrl.milestones = [];
    MilestonesService.recentMilestones().then(function(milestones) {
      recentMilestonesCtrl.milestones = milestones;
    });
  });
