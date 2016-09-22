'use strict';

describe('Controller: CreateMilestoneCtrl', function () {

  // load the controller's module
  beforeEach(module('PerformanceDashboard'));

  var CreateMilestoneCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CreateMilestoneCtrl = $controller('CreateMilestoneCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(CreateMilestoneCtrl.awesomeThings.length).toBe(3);
  });
});
