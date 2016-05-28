(function() {
    'use strict';
    angular
        .module('myHutApp')
        .factory('Cabin', Cabin);

    Cabin.$inject = ['$resource'];

    function Cabin ($resource) {
        var resourceUrl =  'api/cabins/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
