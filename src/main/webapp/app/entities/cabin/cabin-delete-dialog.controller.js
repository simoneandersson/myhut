(function() {
    'use strict';

    angular
        .module('myHutApp')
        .controller('CabinDeleteController',CabinDeleteController);

    CabinDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cabin'];

    function CabinDeleteController($uibModalInstance, entity, Cabin) {
        var vm = this;

        vm.cabin = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cabin.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
