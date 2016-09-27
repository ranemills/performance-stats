'use strict';

angular.module('PerformanceDashboard', ['angularMoment', 'ui.router', 'nvd3'])

  .constant('JavaHost', 'http://localhost:8080')

  .constant('_', window._)
  .constant('moment', window.moment)

  .service('QuartersApi', function ($http, $q, JavaHost) {
    return {
      get: function (params) {
        return $http.get(JavaHost + '/api/performances/list', {params: params});
      },
      getAvailableFilters: function (params) {
        return $http.get(JavaHost + '/api/stats/available', {params: params});
      },
      getFilters: function (filters, params) {
        // if (!_.isEmpty(filters)) {
        //   params[filters] = filters;
        // }
        return $http.get(JavaHost + '/api/stats/filters', {params: params});
      }
    };
  })

  .service('ImportApi', function ($http, JavaHost) {
    return {
      import: function (bbUrl) {
        return $http.get(JavaHost + '/api/bellboard/import', {params: {bbUrl: bbUrl}});
      }
    };
  })

  .service('AuthService', function ($http, $rootScope, JavaHost) {
    function authenticate(credentials, callback) {
      var headers = credentials ? {
        authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
      } : {};

      $http.get(JavaHost + '/api/auth/user', {headers: headers}).then(function (response) {
        $rootScope.authenticated = !!response.data.name;
        callback(response.data);
      }, function () {
        $rootScope.authenticated = false;
        callback();
      });
    }

    function register(credentials, callback) {
      $http.get(JavaHost + '/api/auth/register', {params: credentials}).then(function () {
        callback(true);
      }, function () {
        callback(false);
      });
    }

    return {
      authenticate: authenticate,
      register: register
    };
  })

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
  })

  .run(function($rootScope, $state) {
    $rootScope.$on('$stateChangeStart', function(evt, to, params) {
      if (to.redirectTo) {
        evt.preventDefault();
        $state.go(to.redirectTo, params);
      }
    });
  })

  .run(function ($rootScope, $state, $http, JavaHost, AuthService) {
    $rootScope.logout = function () {
      $http.post(JavaHost + '/logout', {}).finally(function () {
        $rootScope.authenticated = false;
        $state.go('login');
      });
    };

    $rootScope.$on('$stateChangeStart', function (event, toState) {
      if (!$rootScope.authenticated){
        AuthService.authenticate({}, function() {
          if (!$rootScope.authenticated && toState.name !== 'login') {
            event.preventDefault();
            $state.go('login');
          }
        });
      }
    });
  })

  //TODO: Add collapse functionality to this onSelect
  .directive('filterSelector', function () {
    return {
      restrict: 'E',
      templateUrl: 'views/directives/filter-selector.html',
      scope: {
        filters: '=filters',
        onSelectFn: '=onSelect',
        select: '=selectorObj'
      },
      link: function ($scope) {
        $scope.activeKey = null;
        $scope.onSelect = function (filterKey) {
          $scope.activeKey = filterKey;
          $scope.onSelectFn(filterKey);
        };
        $scope.active = function (filterKey) {
          return $scope.activeKey === filterKey;
        };
      }
    };
  })

  .directive('performanceList', function () {
    return {
      restrict: 'E',
      templateUrl: 'views/directives/performance-list.html',
      scope: {
        performances: '='
      },
      link: function ($scope) {
        var expanded = null;

        $scope.expand = function (id) {
          if (expanded === id) {
            expanded = null;
          } else {
            expanded = id;
          }
        };

        $scope.isExpanded = function (id) {
          return expanded === id;
        };
      }
    };
  })

  .directive('statsList', function (_) {
    return {
      restrict: 'E',
      templateUrl: 'views/directives/stats-list.html',
      scope: {
        items: '=',
        updateFn: '=update',
        selectorObj: '=selector',
        selectorKey: '=selectorKey',
        maxDisplayed: '='
      },
      link: function ($scope) {
        $scope.active = function (item) {
          return $scope.selectorObj[$scope.selectorKey] === item;
        };
        $scope.select = function (item) {
          if ($scope.active(item)) {
            $scope.selectorObj[$scope.selectorKey] = null;
          } else {
            $scope.selectorObj[$scope.selectorKey] = item;
          }
          $scope.updateFn();
        };
        $scope.show = function (item) {
          return _.isNull($scope.selectorObj[$scope.selectorKey]) ||
            _.isUndefined($scope.selectorObj[$scope.selectorKey]) ||
            $scope.selectorObj[$scope.selectorKey] === item;
        };
        $scope.increaseMaxDisplayed = function() {
          $scope.maxDisplayed = $scope.maxDisplayed + 10;
        };
      }
    };
  });
