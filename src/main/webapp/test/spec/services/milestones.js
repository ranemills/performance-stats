'use strict';

describe('Service: MilestonesService', function () {

  // load the service's module
  beforeEach(module('PerformanceDashboard'));

  // instantiate service
  var milestones;
  beforeEach(inject(function (_milestones_) {
    milestones = _milestones_;
  }));

  it('should do something', function () {
    expect(!!milestones).toBe(true);
  });

});
