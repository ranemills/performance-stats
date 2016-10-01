'use strict';

describe('Controller: PerformanceModalCtrl', function () {

  // load the controller's module
  beforeEach(module('PerformanceDashboard'));

  var PerformanceModalCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    PerformanceModalCtrl = $controller('PerformanceModalCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(PerformanceModalCtrl.awesomeThings.length).toBe(3);
  });
});
