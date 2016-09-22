'use strict';

describe('Controller: ManageMilestonesCtrl', function () {

  // load the controller's module
  beforeEach(module('PerformanceDashboard'));

  var ManageMilestonesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ManageMilestonesCtrl = $controller('ManageMilestonesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ManageMilestonesCtrl.awesomeThings.length).toBe(3);
  });
});
