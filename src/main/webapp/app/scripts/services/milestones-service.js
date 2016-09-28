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

    function newMilestoneFacet(facet) {
      return $http.post(JavaHost + '/api/facets/new', facet);
    }

    function getAvailableProperties() {
      return $http.get(JavaHost + '/api/facets/properties');
    }

    return {
      recentMilestones: recentMilestones,
      getMilestonesFacets: getMilestonesFacets,
      newMilestoneFacet: newMilestoneFacet,
      getAvailableProperties: getAvailableProperties
    };

  });
