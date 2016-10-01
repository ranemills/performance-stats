'use strict';

describe('Service: quartersApi', function () {

  // load the service's module
  beforeEach(module('PerformanceDashboard'));

  // instantiate service
  var quartersApi;
  beforeEach(inject(function (_quartersApi_) {
    quartersApi = _quartersApi_;
  }));

  it('should do something', function () {
    expect(!!quartersApi).toBe(true);
  });

});
