(function() {
    'use strict';

    angular
        .module('myHutApp')
        .controller('BookingDialogController', BookingDialogController);

    BookingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Booking', 'Cabin', 'Person'];

    function BookingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Booking, Cabin, Person) {
        var vm = this;

        vm.booking = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cabins = Cabin.query();
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.booking.id !== null) {
                Booking.update(vm.booking, onSaveSuccess, onSaveError);
            } else {
                Booking.save(vm.booking, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myHutApp:bookingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
