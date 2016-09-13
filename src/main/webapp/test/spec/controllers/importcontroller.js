'use strict';

describe('Controller: ImportcontrollerCtrl', function () {

  // load the controller's module
  beforeEach(module('PerformanceDashboard'));

  var ImportcontrollerCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ImportcontrollerCtrl = $controller('ImportcontrollerCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ImportcontrollerCtrl.awesomeThings.length).toBe(3);
  });
});
