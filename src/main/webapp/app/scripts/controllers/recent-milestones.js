'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:RecentMilestonesCtrl
 * @description
 * # RecentMilestonesCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('RecentMilestonesController', function (_, moment, MilestonesService) {
    var recentMilestonesCtrl = this;

    recentMilestonesCtrl.milestones = [];
    MilestonesService.recentMilestones().then(function(response) {
      recentMilestonesCtrl.milestones = response.data;
    });

    recentMilestonesCtrl.selectMilestone = function(index) {
      console.log('hello');
      recentMilestonesCtrl.selectedMilestone = recentMilestonesCtrl.milestones[index];
    };

  });
