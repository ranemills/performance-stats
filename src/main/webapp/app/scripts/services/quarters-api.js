'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.quartersApi
 * @description
 * # quartersApi
 * Service in the PerformanceDashboard.
 */
function QuartersApi($http, JavaHost) {
  return {
    get: function (params) {
      return $http.get(JavaHost + '/api/performances', {params: params});
    },
    getAvailableFilters: function (params) {
      return $http.get(JavaHost + '/api/stats/available', {params: params});
    },
    getFilters: function (filters, params) {
      // if (!_.isEmpty(filters)) {
      //   params[filters] = filters;
      // }
      return $http.get(JavaHost + '/api/stats/filters', {params: params});
    },
    getSnapshot: function () {
      return $http.get(JavaHost + '/api/stats/snapshot');
    }
  };
}

module.exports = QuartersApi;
