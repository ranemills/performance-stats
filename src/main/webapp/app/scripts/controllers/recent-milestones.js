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

    recentMilestonesCtrl.milestones = {};
    MilestonesService.recentMilestones().then(function(response) {
      recentMilestonesCtrl.milestones = response.data;
    });

    recentMilestonesCtrl.selectMilestone = function(index) {
      recentMilestonesCtrl.selectedMilestone = recentMilestonesCtrl.milestones[index];
    };

    recentMilestonesCtrl.emptyProperties = function(milestone) {
      return _.size(milestone.properties) === 0;
    };

  });
