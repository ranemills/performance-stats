'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:ManageMilestonesCtrl
 * @description
 * # ManageMilestonesCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('ManageMilestonesController', function (MilestonesService) {
    var manageMilestonesCtrl = this;
    manageMilestonesCtrl.milestones = [];
    MilestonesService.getMilestonesFacets().then(function(response) {
      manageMilestonesCtrl.milestones = response.data;
    });
  });
