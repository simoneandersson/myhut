(function() {
    'use strict';

    angular
        .module('myHutApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('myBooking', {
      parent: 'app',
            url: '/myBooking',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myHutApp.booking.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/myBooking/bookings.html',
                    controller: 'MyBookingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('booking');
                    $translatePartialLoader.addPart('bookingType');
                    $translatePartialLoader.addPart('bookingStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
    }

})();
