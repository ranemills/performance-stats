'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.importApi
 * @description
 * # importApi
 * Service in the PerformanceDashboard.
 */
module.exports = function($http, JavaHost) {
  return {
    import: function (bbUrl) {
      return $http.post(JavaHost + '/api/imports', {bbUrl: bbUrl});
    }
  };
}
