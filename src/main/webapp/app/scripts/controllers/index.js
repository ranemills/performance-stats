'use strict';

let angular = require('angular');

angular.module('PerformanceDashboard')
       .controller('CreateMilestoneController', require('./create-milestone-controller'))
       .controller('DashboardController', require('./dashboard'))
       .controller('ExploreController', require('./explore-controller'))
       .controller('ImportController', require('./import-controller'))
       .controller('LoginController', require('./login-controller'))
       .controller('ManageMilestonesController', require('./manage-milestones'))
       .controller('MilestonesController', require('./milestones-controller'))
       .controller('PerformanceModalController', require('./performance-modal'))
       .controller('RecentMilestonesController', require('./recent-milestones'));