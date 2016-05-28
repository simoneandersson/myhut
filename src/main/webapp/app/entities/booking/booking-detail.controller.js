(function() {
    'use strict';

    angular
        .module('myHutApp')
        .controller('BookingDetailController', BookingDetailController);

    BookingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Booking', 'Cabin', 'Person'];

    function BookingDetailController($scope, $rootScope, $stateParams, entity, Booking, Cabin, Person) {
        var vm = this;

        vm.booking = entity;

        var unsubscribe = $rootScope.$on('myHutApp:bookingUpdate', function(event, result) {
            vm.booking = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
