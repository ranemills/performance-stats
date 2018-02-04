'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.modalService
 * @description
 * # modalService
 * Service in the PerformanceDashboard.
 */
module.exports = function($uibModal) {

  function performanceModal(performance) {
    return $uibModal.open({
      template: require('../../views/performance-modal.html'),
      controller: 'PerformanceModalController',
      controllerAs: 'performanceModalCtrl',
      resolve: {
        performance: performance
      }
    });
  }

  return {
    openPerformanceModal: performanceModal
  };

}
