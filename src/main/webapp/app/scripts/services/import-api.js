'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.importApi
 * @description
 * # importApi
 * Service in the PerformanceDashboard.
 */
angular.module('PerformanceDashboard')
  .service('ImportApi', function ($http, JavaHost) {
    return {
      import: function (bbUrl) {
        return $http.get(JavaHost + '/api/bellboard/import', {params: {bbUrl: bbUrl}});
      }
    };
  });
