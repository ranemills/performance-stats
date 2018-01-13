'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.authService
 * @description
 * # authService
 * Service in the PerformanceDashboard.
 */
function AuthService($http, $rootScope, JavaHost) {
  function authenticate(credentials, callback) {
    var headers = credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {};

    $http.get(JavaHost + '/api/auth/user', {headers: headers}).then(function (response) {
      $rootScope.authenticated = !!response.data.name;
      callback(response.data);
    }, function () {
      $rootScope.authenticated = false;
      callback();
    });
  }

  function register(credentials, callback) {
    $http.post(JavaHost + '/api/auth/register', credentials).then(function () {
      callback(true);
    }, function () {
      callback(false);
    });
  }

  return {
    authenticate: authenticate,
    register: register
  };
}

module.exports = AuthService;
