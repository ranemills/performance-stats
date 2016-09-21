'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:RecentMilestonesCtrl
 * @description
 * # RecentMilestonesCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('RecentMilestonesController', function (moment) {
    this.milestones = [
      {
        date: moment().format('ll'),
        number: 50,
        properties: {'STAGE': 'Major'},
        performanceId: '123'
      }
    ];
  });
