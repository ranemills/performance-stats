'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.milestones
 * @description
 * # milestones
 * Service in the PerformanceDashboard.
 */
angular.module('PerformanceDashboard')
  .service('MilestonesService', function ($http, $q, JavaHost) {

    function recentMilestones() {
      return $http.get(JavaHost + '/api/milestones');
    }

    function getMilestonesFacets() {
      return $http.get(JavaHost + '/api/facets');
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
