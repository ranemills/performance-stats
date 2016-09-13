'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:QuerycontrollerCtrl
 * @description
 * # QuerycontrollerCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('QueryController', function ($scope, $rootScope, QuartersApi) {
    // Store the performances
    $scope.performances = [];
    // Store the available filters and their options
    $scope.filters = {};
    // Store the current search options
    $scope.select = {};
    // Store the currently available filter names
    $scope.availableFilters = [];
    // Store the filter currently being looked at
    $scope.currentFilter = null;

    // Update the available filters
    function updateAvailableFilters() {
      QuartersApi.getAvailableFilters().then(function (response) {
        $scope.availableFilters = response.data;
      });
    }

    // Update the performances
    $scope.updatePerformances = function () {
      QuartersApi.get($scope.select).then(function (response) {
        $scope.performances = response.data;
      });
    };

    // Select a filter category
    $scope.selectFilter = function (filterKey) {
      $scope.currentFilter = filterKey;
      var params = _.omit($scope.select, filterKey);
      QuartersApi.getFilters([filterKey], params).then(function (response) {
        $scope.filters = response.data;
      });
      updateAvailableFilters()
    };

    // Reset everything
    $scope.reset = function () {
      $scope.select = {};
      $scope.updatePerformances();
      updateAvailableFilters();
    };

    $scope.reset();

  });
