'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:ImportcontrollerCtrl
 * @description
 * # ImportcontrollerCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('ImportController', function ($state, ImportApi) {
    var self = this;

    self.import = function () {
      // do some validation
      ImportApi.import(self.bbUrl).then(function () {
        $state.go('explore');
      }, function () {
        self.error = true;
      });
    }
  })
