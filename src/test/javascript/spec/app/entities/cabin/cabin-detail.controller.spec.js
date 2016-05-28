'use strict';

describe('Controller Tests', function() {

    describe('Cabin Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCabin;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCabin = jasmine.createSpy('MockCabin');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cabin': MockCabin
            };
            createController = function() {
                $injector.get('$controller')("CabinDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'myHutApp:cabinUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
