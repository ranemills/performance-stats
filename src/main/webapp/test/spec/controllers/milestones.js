'use strict';

describe('Controller: MilestonesCtrl', function () {

  // load the controller's module
  beforeEach(module('PerformanceDashboard'));

  var MilestonesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MilestonesCtrl = $controller('MilestonesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(MilestonesCtrl.awesomeThings.length).toBe(3);
  });
});
