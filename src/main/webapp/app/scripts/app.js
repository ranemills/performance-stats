'use strict';

angular.module('PerformanceDashboard', ['angularMoment', 'ui.router', 'nvd3'])

  .constant('JavaHost', '')//'http://quarters-rmills.rhcloud.com/')

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
    }
  })

  .service('ImportApi', function ($http, JavaHost) {
    return {
      import: function (bbUrl) {
        return $http.get(JavaHost + '/api/bellboard/import', {params: {bbUrl: bbUrl}})
      }
    }
  })

  .service('AuthService', function ($http, $rootScope, JavaHost) {
    var authenticate = function (credentials, callback) {
      var headers = credentials ? {
        authorization: "Basic "
        + btoa(credentials.username + ":" + credentials.password)
      } : {};

      $http.get(JavaHost + '/api/auth/user', {headers: headers}).then(function (response) {
        $rootScope.authenticated = !!response.data.name;
        callback && callback(response.data);
      }, function () {
        $rootScope.authenticated = false;
        callback && callback();
      });
    };

    var register = function (credentials, callback) {
      $http.get(JavaHost + '/api/auth/register', {params: credentials}).then(function () {
        callback && callback(true);
      }, function () {
        callback && callback(false);
      });
    };

    return {
      authenticate: authenticate,
      register: register
    }
  })

  .config(function ($httpProvider, $stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/explore");

    $stateProvider
      .state('explore', {
        url: "/explore",
        templateUrl: 'views/explore.html',
        controller: 'ExploreController as exploreCtrl'
      })
      .state('query', {
        url: "/query",
        controller: 'QueryController as queryCtrl',
        templateUrl: 'views/query.html'
      })
      .state('login', {
        url: "/login",
        controller: 'LoginController as loginCtrl',
        templateUrl: 'views/login.html'
      })
      .state('import', {
        url: "/import",
        controller: 'ImportController as importCtrl',
        templateUrl: 'views/import.html'
      });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
  })

  .run(function ($rootScope, $state, $http, JavaHost) {
    $rootScope.logout = function () {
      $http.post(JavaHost + '/logout', {}).finally(function () {
        $rootScope.authenticated = false;
        $state.go("login");
      });
    };

    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams, options) {
      if (!$rootScope.authenticated && toState.name !== 'login') {
        event.preventDefault();
        $state.go('login');
      }
    });
  })

  .controller('LoginController', function ($state, $rootScope, AuthService) {
    AuthService.authenticate();

    var self = this;

    self.credentials = {username: 'millsy3', password: 'password'};
    self.login = function () {
      AuthService.authenticate(self.credentials, function (user) {
        if ($rootScope.authenticated) {
          if (!user.hasImported) {
            $state.go("import");
          }
          else {
            $state.go("explore");
          }

          self.error = false;
        } else {
          $state.go("login");
          self.error = true;
        }
      });
    };

    self.register = function () {
      AuthService.register(self.credentials, function (success) {
        self.error = !success;
        if (success) {
          self.login()
        }
      });
    };
  })

  .controller('ImportController', function ($state, ImportApi) {
    var self = this;

    self.import = function () {
      // do some validation
      ImportApi.import(self.bbUrl).then(function () {
        $state.go('explore');
      }, function () {
        self.error = true;
      });
    }
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
        }
      }
    }
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
    }
  })

  .directive('statsList', function () {
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
          return _.isNull($scope.selectorObj[$scope.selectorKey])
            || _.isUndefined($scope.selectorObj[$scope.selectorKey])
            || $scope.selectorObj[$scope.selectorKey] === item;
        };
        $scope.increaseMaxDisplayed = function() {
          $scope.maxDisplayed = $scope.maxDisplayed + 10;
        }
      }
    }
  })

  .controller('ExploreController', function ($scope, QuartersApi) {
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
