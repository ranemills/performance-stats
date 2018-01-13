'use strict';

module.exports = function($httpProvider, $stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/explore');

  $stateProvider
    .state('explore', {
      url: '/explore',
      template: require('../../views/explore.html'),
      controller: 'ExploreController as exploreCtrl'
    })
    .state('dashboard', {
      url: '/dashboard',
      controller: 'DashboardController as dashboardCtrl',
      template: require('../../views/dashboard.html')
    })
    .state('milestones', {
      url: '/milestones',
      template: require('../../views/milestones.html'),
      redirectTo: 'milestones.recent'
    })
    .state('milestones.recent', {
      url: '/recent',
      template: require('../../views/recent-milestones.html'),
      controller: 'RecentMilestonesController as recentMilestonesCtrl'
    })
    .state('milestones.manage', {
      url: '/manage',
      template: require('../../views/manage-milestones.html'),
      controller: 'ManageMilestonesController as manageMilestonesCtrl'
    })
    .state('milestones.create', {
      url: '/new',
      template: require('../../views/create-milestone.html'),
      controller: 'CreateMilestoneController as createMilestoneCtrl'
    })
    .state('login', {
      url: '/login',
      controller: 'LoginController as loginCtrl',
      template: require('../../views/login.html')
    })
    .state('import', {
      url: '/import',
      controller: 'ImportController as importCtrl',
      template: require('../../views/import.html')
    });

  $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
};
