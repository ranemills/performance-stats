'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.milestones
 * @description
 * # milestones
 * Service in the PerformanceDashboard.
 */
angular.module('PerformanceDashboard')
  .service('MilestonesService', function ($q, moment) {

    function recentMilestones() {
      return $q.resolve([
          {
            id: '1',
            date: moment().format('ll'),
            number: 50,
            properties: {'STAGE': 'Major'},
            performanceId: '123'
          }
        ]);
    }

    function getMilestonesFacets() {
      return $q.resolve([
          {
            id: '1',
            count: 122,
            properties: {'STAGE': 'Major'}
          }
        ]);
    }

    function newMilestoneFacet() {
      return $q.resolve({
        id: '2'
      });
    }

    return {
      recentMilestones: recentMilestones,
      getMilestonesFacets: getMilestonesFacets,
      newMilestoneFacet: newMilestoneFacet
    };

  });
