(function() {
    'use strict';

    angular
        .module('myHutApp')
        .controller('BookingController', BookingController);

    BookingController.$inject = ['$scope', '$state', 'Booking'];

    function BookingController ($scope, $state, Booking) {
        var vm = this;
        
        vm.bookings = [];

        loadAll();

        function loadAll() {
            Booking.query(function(result) {
                vm.bookings = result;
            });
        }
    }
})();
