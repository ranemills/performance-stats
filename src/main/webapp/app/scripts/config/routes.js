'use strict';

angular.module('PerformanceDashboard')
  .config(function ($httpProvider, $stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/explore');

    $stateProvider
      .state('explore', {
        url: '/explore',
        templateUrl: 'views/explore.html',
        controller: 'ExploreController as exploreCtrl'
      })
      .state('dashboard', {
        url: '/dashboard',
        controller: 'DashboardController as dashboardCtrl',
        templateUrl: 'views/dashboard.html'
      })
      .state('milestones', {
        url: '/milestones',
        templateUrl: 'views/milestones.html',
        redirectTo: 'milestones.recent'
      })
      .state('milestones.recent', {
        url: '/recent',
        templateUrl: 'views/recent-milestones.html',
        controller: 'RecentMilestonesController as recentMilestonesCtrl'
      })
      .state('milestones.manage', {
        url: '/manage',
        templateUrl: 'views/manage-milestones.html',
        controller: 'ManageMilestonesController as manageMilestonesCtrl'
      })
      .state('milestones.create', {
        url: '/new',
        templateUrl: 'views/create-milestone.html',
        controller: 'CreateMilestoneController as createMilestoneCtrl'
      })
      .state('login', {
        url: '/login',
        controller: 'LoginController as loginCtrl',
        templateUrl: 'views/login.html'
      })
      .state('import', {
        url: '/import',
        controller: 'ImportController as importCtrl',
        templateUrl: 'views/import.html'
      });

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
  });
