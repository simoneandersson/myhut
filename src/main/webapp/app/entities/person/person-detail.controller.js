(function() {
    'use strict';

    angular
        .module('myHutApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Person'];

    function PersonDetailController($scope, $rootScope, $stateParams, entity, Person) {
        var vm = this;

        vm.person = entity;

        var unsubscribe = $rootScope.$on('myHutApp:personUpdate', function(event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
