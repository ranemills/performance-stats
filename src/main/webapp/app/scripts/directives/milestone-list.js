'use strict';

import _ from 'lodash';

/**
 * @ngdoc directive
 * @name PerformanceDashboard.directive:milestoneList
 * @description
 * # milestoneList
 */
function milestoneList(ModalService) {
  return {
    templateUrl: 'views/directives/milestone-list.html',
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

module.exports = milestoneList;
