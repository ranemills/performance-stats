'use strict';

describe('Directive: statsWidget', function () {

  // load the directive's module
  beforeEach(module('PerformanceDashboard'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<stats-widget></stats-widget>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the statsWidget directive');
  }));
});
