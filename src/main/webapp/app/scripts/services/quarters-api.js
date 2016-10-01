'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.quartersApi
 * @description
 * # quartersApi
 * Service in the PerformanceDashboard.
 */
angular.module('PerformanceDashboard')
  .service('QuartersApi', function ($http, JavaHost) {
    return {
      get: function (params) {
        return $http.get(JavaHost + '/api/performances/list', {params: params});
      },
      getAvailableFilters: function (params) {
        return $http.get(JavaHost + '/api/stats/available', {params: params});
      },
      getFilters: function (filters, params) {
        // if (!_.isEmpty(filters)) {
        //   params[filters] = filters;
        // }
        return $http.get(JavaHost + '/api/stats/filters', {params: params});
      }
    };
  });
