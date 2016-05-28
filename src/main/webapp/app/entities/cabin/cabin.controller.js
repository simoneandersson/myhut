(function() {
    'use strict';

    angular
        .module('myHutApp')
        .controller('CabinController', CabinController);

    CabinController.$inject = ['$scope', '$state', 'Cabin'];

    function CabinController ($scope, $state, Cabin) {
        var vm = this;
        
        vm.cabins = [];

        loadAll();

        function loadAll() {
            Cabin.query(function(result) {
                vm.cabins = result;
            });
        }
    }
})();
