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
        return $http.post(JavaHost + '/api/imports', {bbUrl: bbUrl});
      }
    };
  });
