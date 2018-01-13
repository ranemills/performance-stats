'use strict';

let angular = require('angular');

angular.module('PerformanceDashboard')
.directive('filterSelector', require('./filter-selector'))
.directive('milestoneList', require('./milestone-list'))
.directive('performance', require('./performance'))
.directive('performanceList', require('./performance-list'))
.directive('statsList', require('./stats-list'))
.directive('statsWidgetCard', require('./stats-widget-card'));