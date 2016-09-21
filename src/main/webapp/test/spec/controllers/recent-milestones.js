'use strict';

describe('Controller: RecentMilestonesCtrl', function () {

  // load the controller's module
  beforeEach(module('PerformanceDashboard'));

  var RecentMilestonesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    RecentMilestonesCtrl = $controller('RecentMilestonesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(RecentMilestonesCtrl.awesomeThings.length).toBe(3);
  });
});
