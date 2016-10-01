'use strict';

/**
 * @ngdoc service
 * @name PerformanceDashboard.modalService
 * @description
 * # modalService
 * Service in the PerformanceDashboard.
 */
angular.module('PerformanceDashboard')
  .service('ModalService', function ($uibModal) {

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
    }

  });
