'use strict';

const _ = require('lodash');

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:milestoneList
 * @description
 * # milestoneList
 */
module.exports = function(ModalService) {
  return {
    template: require('../../views/directives/milestone-list.html'),
    restrict: 'E',
    scope: {
      milestones: '='
    },
    link: function postLink(scope, element, attrs) {
      scope.small = !_.isUndefined(attrs.small);
      scope.maxDisplayed = scope.small ? 5 : 100;

      scope.selectMilestone = function (index) {
        ModalService.openPerformanceModal(scope.milestones[index].performance);
      };

      scope.emptyProperties = function (milestone) {
        return _.size(milestone.properties) === 0;
      };
    }
  };
}
