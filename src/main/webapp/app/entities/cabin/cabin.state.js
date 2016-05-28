(function() {
    'use strict';

    angular
        .module('myHutApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cabin', {
            parent: 'entity',
            url: '/cabin',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myHutApp.cabin.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cabin/cabins.html',
                    controller: 'CabinController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cabin');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cabin-detail', {
            parent: 'entity',
            url: '/cabin/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myHutApp.cabin.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cabin/cabin-detail.html',
                    controller: 'CabinDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cabin');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cabin', function($stateParams, Cabin) {
                    return Cabin.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('cabin.new', {
            parent: 'cabin',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cabin/cabin-dialog.html',
                    controller: 'CabinDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                location: null,
                                capacity: null,
                                photos: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cabin', null, { reload: true });
                }, function() {
                    $state.go('cabin');
                });
            }]
        })
        .state('cabin.edit', {
            parent: 'cabin',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cabin/cabin-dialog.html',
                    controller: 'CabinDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cabin', function(Cabin) {
                            return Cabin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cabin', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cabin.delete', {
            parent: 'cabin',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cabin/cabin-delete-dialog.html',
                    controller: 'CabinDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cabin', function(Cabin) {
                            return Cabin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cabin', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
