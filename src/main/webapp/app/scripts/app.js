'use strict';

angular.module('PerformanceDashboard', ['angularMoment', 'ui.router', 'nvd3', 'ui.bootstrap'])

  .constant('JavaHost', 'http://localhost:8080')

  .constant('_', window._)
  .constant('moment', window.moment)
  .constant('humanizeDuration', window.humanizeDuration)


  .run(function($rootScope, $state) {
    $rootScope.$on('$stateChangeStart', function(evt, to, params) {
      if (to.redirectTo) {
        evt.preventDefault();
        $state.go(to.redirectTo, params);
      }
    });
  })

  .run(function ($rootScope, $state, $http, JavaHost, AuthService) {
    $rootScope.logout = function () {
      $http.post(JavaHost + '/logout', {}).finally(function () {
        $rootScope.authenticated = false;
        $state.go('login');
      });
    };

    $rootScope.$on('$stateChangeStart', function (event, toState) {
      if (!$rootScope.authenticated){
        AuthService.authenticate({}, function() {
          if (!$rootScope.authenticated && toState.name !== 'login') {
            event.preventDefault();
            $state.go('login');
          }
        });
      }
    });
  });
