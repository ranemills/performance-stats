'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:ExplorecontrollerCtrl
 * @description
 * # ExplorecontrollerCtrl
 * Controller of the PerformanceDashboard
 */
angular.module('PerformanceDashboard')
  .controller('ExploreController', function ($scope, _, QuartersApi) {
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

        exploreCtrl.maxDisplayed = _.transform(exploreCtrl.filters, function(result, value, key) {
          return result[key] = 10;
        }, {});

        exploreCtrl.chartsData = _.transform(exploreCtrl.filters, function (result, value, key) {
          result[key] = [{
            key: key,
            values: _.take(value, 10)
          }];
        }, {});

        exploreCtrl.newFilters = _.transform(exploreCtrl.filters, function(result, value, key) {
          result[key] = {};
          result[key].chartData = [{
            key: key,
            values: _.take(value, 10)
          }];
          result[key].listData = value;
        }, {});
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
      update()
    };

    exploreCtrl.reset = function () {
      exploreCtrl.select = {};
      update();
    };

    exploreCtrl.reset();

    exploreCtrl.state = 'lists';

    exploreCtrl.showLists = function () {
      return exploreCtrl.state === 'lists';
    };

    exploreCtrl.selectLists = function () {
      exploreCtrl.state = 'lists';
    };

    exploreCtrl.showCharts = function () {
      return exploreCtrl.state === 'charts';
    };

    exploreCtrl.selectCharts = function () {
      exploreCtrl.state = 'charts';
    };


    var baseChartOptions = {
      chart: {
        type: 'discreteBarChart',
        height: 450,
        margin: {
          top: 20,
          right: 20,
          bottom: 100,
          left: 55
        },
        x: function (d) {
          return d.property;
        },
        y: function (d) {
          return d.count;
        },
        showValues: true,
        valueFormat: function (d) {
          return d3.format(',d')(d);
        },
        xAxis: {
          rotateLabels: -45
        },
        yAxis: {
          axisLabel: 'Count',
          axisLabelDistance: -10
        }
      }
    };

    exploreCtrl.chartOptions = function (key) {
      _.set(baseChartOptions, 'chart.discretebar.dispatch.elementClick', function (e) {
        exploreCtrl.selectFilter(key, e.data.label);
      });
      return baseChartOptions;
    };

    exploreCtrl.maxDisplayed = {

    }

  })
