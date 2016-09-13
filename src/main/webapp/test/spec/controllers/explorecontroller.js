'use strict';

describe('Controller: ExplorecontrollerCtrl', function () {

  // load the controller's module
  beforeEach(module('PerformanceDashboard'));

  var ExplorecontrollerCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ExplorecontrollerCtrl = $controller('ExplorecontrollerCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ExplorecontrollerCtrl.awesomeThings.length).toBe(3);
  });
});
