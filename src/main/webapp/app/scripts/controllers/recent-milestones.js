'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:RecentMilestonesCtrl
 * @description
 * # RecentMilestonesCtrl
 * Controller of the PerformanceDashboard
 */
module.exports = function(MilestonesService) {
  var recentMilestonesCtrl = this;

  recentMilestonesCtrl.milestones = {};

  MilestonesService.recentMilestones().then(function (response) {
    recentMilestonesCtrl.milestones = response.data;
  });

}
