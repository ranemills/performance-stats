'use strict';

/**
 * @ngdoc function
 * @name PerformanceDashboard.controller:CreateMilestoneCtrl
 * @description
 * # CreateMilestoneCtrl
 * Controller of the PerformanceDashboard
 */
function CreateMilestoneController($state, MilestonesService) {
  var createMilestoneCtrl = this;

  createMilestoneCtrl.availableProperties = [];

  createMilestoneCtrl.new = {
    properties: [
      {
        property: 'STAGE',
        value: ''
      }
    ]
  };

  MilestonesService.getAvailableProperties().then(function (response) {
    createMilestoneCtrl.availableProperties = response.data;
  });

  createMilestoneCtrl.create = function () {
    var creation = {
      properties: {}
    };

    creation.properties[createMilestoneCtrl.new.properties[0].property] = createMilestoneCtrl.new.properties[0].value;


    MilestonesService.newMilestoneFacet(creation).then(function(response)
    {
      $state.go('milestones');
    });
  };

  createMilestoneCtrl.newPropertyRow = function () {
    createMilestoneCtrl.new.properties.push({
      property: 'STAGE',
      value: ''
    });
  };

  createMilestoneCtrl.canAddNewRow = function () {
    return createMilestoneCtrl.new.properties.length !== createMilestoneCtrl.availableProperties.length;
  };
}

module.exports = CreateMilestoneController;