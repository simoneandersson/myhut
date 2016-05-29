(function() {
    'use strict';

    angular
        .module('myHutApp')
        .controller('MyBookingController', MyBookingController);

    MyBookingController.$inject = ['$scope', '$state', 'Booking', 'Principal'];

    function MyBookingController ($scope, $state, Booking, Principal) {
        var vm = this;
        vm.account = null;
        Principal.identity().then(function(account) {
            vm.account = account;
            vm.isAuthenticated = Principal.isAuthenticated;
        });
        vm.bookings = [];

        loadAll();

        function loadAll() {
            Booking.query(function(result) {
                vm.bookings = result;
                console.log(result);
            });
        }
    }
})();
