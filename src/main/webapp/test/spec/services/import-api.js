'use strict';

describe('Service: importApi', function () {

  // load the service's module
  beforeEach(module('PerformanceDashboard'));

  // instantiate service
  var importApi;
  beforeEach(inject(function (_importApi_) {
    importApi = _importApi_;
  }));

  it('should do something', function () {
    expect(!!importApi).toBe(true);
  });

});
