'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.modalService
 * @description
 * # modalService
 * Service in the PerformanceDashboard.
 */
function ModalService($uibModal) {

  function performanceModal(performance) {
    return $uibModal.open({
      templateUrl: 'views/performance-modal.html',
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

module.exports = ModalService;
