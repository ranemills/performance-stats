'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:ExplorecontrollerCtrl
 * @description
 * # ExplorecontrollerCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('ExploreController', function ($scope, $window, _, ModalService, QuartersApi) {
    var exploreCtrl = this;

    // Store the currently available filter names
    exploreCtrl.availableFilters = [];

    // Store the current search options
    exploreCtrl.select = {};

    // Store the available filters and their options
    exploreCtrl.filters = {};

    exploreCtrl.chartsData = {};

    function getPerformances() {
      QuartersApi.get(exploreCtrl.select).then(function (response) {
        exploreCtrl.performances = response.data;
      });
    }

    // Update the filter values
    function update() {
      QuartersApi.getAvailableFilters().then(function (response) {
        exploreCtrl.availableFilters = response.data;
      });

      QuartersApi.getFilters([''], exploreCtrl.select).then(function (response) {
        exploreCtrl.filters = response.data;
      });

      getPerformances();
    }

    exploreCtrl.selectFilter = function (filter, value) {
      if (exploreCtrl.select[filter] === value) {
        _.unset(exploreCtrl.select, filter);
      }
      else {
        _.set(exploreCtrl.select, filter, value);
      }
      update();
    };

    exploreCtrl.reset = function () {
      exploreCtrl.select = {};
      update();
    };

    exploreCtrl.reset();

    exploreCtrl.maxDisplayed = {};

  });
