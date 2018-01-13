'use strict';

let angular = require('angular');

angular.module('PerformanceDashboard')
.service('AuthService', require('./auth-service'))
.service('ImportApi', require('./import-api'))
.service('MilestonesService', require('./milestones-service'))
.service('ModalService', require('./modal-service'))
.service('QuartersApi', require('./quarters-api'));