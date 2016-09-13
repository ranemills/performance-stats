'use strict';

describe('Controller: QuerycontrollerCtrl', function () {

  // load the controller's module
  beforeEach(module('PerformanceDashboard'));

  var QuerycontrollerCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    QuerycontrollerCtrl = $controller('QuerycontrollerCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(QuerycontrollerCtrl.awesomeThings.length).toBe(3);
  });
});
