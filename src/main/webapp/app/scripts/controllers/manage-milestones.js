'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:ManageMilestonesCtrl
 * @description
 * # ManageMilestonesCtrl
 * Controller of the PerformanceDashboard
 */
function ManageMilestonesController(MilestonesService) {
  var manageMilestonesCtrl = this;
  manageMilestonesCtrl.milestones = [];
  MilestonesService.getMilestonesFacets().then(function (response) {
    manageMilestonesCtrl.milestones = response.data;
  });
}

module.exports = ManageMilestonesController;
