'use strict';

angular.module('PerformanceDashboard', ['angularMoment', 'ui.router', 'nvd3', 'ui.bootstrap'])

  .constant('JavaHost', 'http://localhost:8080')

  .constant('moment', window.moment)
  .constant('humanizeDuration', window.humanizeDuration)

  .run(function ($rootScope, $state, $http, $transitions, JavaHost, AuthService) {
    $rootScope.logout = function () {
      $http.post(JavaHost + '/logout', {}).finally(function () {
        $rootScope.authenticated = false;
        $state.go('login');
      });
    };

    $transitions.onBefore({}, function (transition) {
      if (!$rootScope.authenticated){
        return AuthService.authenticate({}, function() {
          if (!$rootScope.authenticated && transition.to().name !== 'login') {
            return transition.router.stateService.target('login');
          }
        });
      }
    });
  });

require('./config');
require('./controllers');
require('./directives');
require('./services');

require('bootstrap');

require('bootstrap/dist/css/bootstrap.min.css');

require('../styles');
