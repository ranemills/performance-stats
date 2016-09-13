'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:LogincontrollerCtrl
 * @description
 * # LogincontrollerCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('LoginController', function ($state, $rootScope, AuthService) {
    AuthService.authenticate();

    var self = this;

    self.credentials = {username: 'millsy3', password: 'password'};
    self.login = function () {
      AuthService.authenticate(self.credentials, function (user) {
        if ($rootScope.authenticated) {
          if (!user.hasImported) {
            $state.go("import");
          }
          else {
            $state.go("explore");
          }

          self.error = false;
        } else {
          $state.go("login");
          self.error = true;
        }
      });
    };

    self.register = function () {
      AuthService.register(self.credentials, function (success) {
        self.error = !success;
        if (success) {
          self.login()
        }
      });
    };
  })
