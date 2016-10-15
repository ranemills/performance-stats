'use strict';

describe('Controller: RecentMilestonesCtrl', function () {

  // load the controller's module
  beforeEach(module('PerformanceDashboard'));

  var recentMilestonesCtrl,
    scope,
    MilestonesService;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $q, $rootScope, _MilestonesService_) {
    scope = $rootScope.$new();
    MilestonesService = _MilestonesService_;

    spyOn(MilestonesService, 'recentMilestones').and.callFake(function() {
      var deferred = $q.defer();
      deferred.resolve({data: {hello: 'hello'}});
      return deferred.promise;
    });

    recentMilestonesCtrl = $controller('RecentMilestonesController');
  }));

  it('should request recent milestones on load', function () {
    expect(MilestonesService.recentMilestones).toHaveBeenCalled();
  });

  it('should set the milestones with the response from recent milestones', function() {
    expect(recentMilestonesCtrl.milestones).toEqual({hello: 'hello'});
  });
});
