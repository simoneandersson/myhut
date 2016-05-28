(function() {
    'use strict';

    angular
        .module('myHutApp')
        .controller('CabinDetailController', CabinDetailController);

    CabinDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Cabin'];

    function CabinDetailController($scope, $rootScope, $stateParams, entity, Cabin) {
        var vm = this;

        vm.cabin = entity;

        var unsubscribe = $rootScope.$on('myHutApp:cabinUpdate', function(event, result) {
            vm.cabin = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
