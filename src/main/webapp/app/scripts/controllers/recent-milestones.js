'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:RecentMilestonesCtrl
 * @description
 * # RecentMilestonesCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('RecentMilestonesController', function (_, $uibModal, moment, MilestonesService) {
    var recentMilestonesCtrl = this;

    recentMilestonesCtrl.milestones = {};
    MilestonesService.recentMilestones().then(function(response) {
      recentMilestonesCtrl.milestones = response.data;
    });

    recentMilestonesCtrl.selectMilestone = function(index) {
      recentMilestonesCtrl.selectedMilestone = recentMilestonesCtrl.milestones[index];

      $uibModal.open({
        templateUrl: 'views/performance-modal.html',
        controller: 'PerformanceModalController',
        controllerAs: 'performanceModalCtrl',
        resolve: {
          performance: recentMilestonesCtrl.selectedMilestone.performance
        }
      });
    };

    recentMilestonesCtrl.emptyProperties = function(milestone) {
      return _.size(milestone.properties) === 0;
    };

  });
