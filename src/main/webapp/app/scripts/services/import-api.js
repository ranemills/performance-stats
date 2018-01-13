'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.importApi
 * @description
 * # importApi
 * Service in the PerformanceDashboard.
 */
function ImportApi($http, JavaHost) {
  return {
    import: function (bbUrl) {
      return $http.post(JavaHost + '/api/imports', {bbUrl: bbUrl});
    }
  };
}

module.exports = ImportApi;
