'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:ImportcontrollerCtrl
 * @description
 * # ImportcontrollerCtrl
 * Controller of the PerformanceDashboard
 */
function ImportController($state, ImportApi) {
  var self = this;

  self.loading = false;

  self.import = function () {
    // do some validation
    self.loading = true;
    ImportApi.import(self.bbUrl).then(function () {
      $state.go('explore');
    }, function () {
      self.error = true;
      self.loading = false;
    });
  };
}

module.exports = ImportController;
