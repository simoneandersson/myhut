(function() {
    'use strict';

    angular
        .module('myHutApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person', {
            parent: 'entity',
            url: '/person',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myHutApp.person.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person/people.html',
                    controller: 'PersonController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('person');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-detail', {
            parent: 'entity',
            url: '/person/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myHutApp.person.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person/person-detail.html',
                    controller: 'PersonDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('person');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Person', function($stateParams, Person) {
                    return Person.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('person.new', {
            parent: 'person',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person/person-dialog.html',
                    controller: 'PersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                enterpriseId: null,
                                sap: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person', null, { reload: true });
                }, function() {
                    $state.go('person');
                });
            }]
        })
        .state('person.edit', {
            parent: 'person',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person/person-dialog.html',
                    controller: 'PersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Person', function(Person) {
                            return Person.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person.delete', {
            parent: 'person',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person/person-delete-dialog.html',
                    controller: 'PersonDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Person', function(Person) {
                            return Person.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
