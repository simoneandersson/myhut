(function() {
    'use strict';

    angular
        .module('myHutApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$rootScope', '$state', '$timeout', 'Auth', 'Cabin'];

    function HomeController ($scope, Principal, LoginService, $rootScope, $state, $timeout, Auth, Cabin) {
        var vm = this;
        vm.authenticationError = false;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.rememberMe = true;
        vm.username = null;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.cabins = [];

        getCabins();

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });
      $timeout(function (){angular.element('#username').focus();});
        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function login (event) {
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('home');
                }

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is succesful, go to stored previousState and clear previousState
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                }
            }).catch(function () {
                vm.authenticationError = true;
            });
        }
        function getCabins()
        {
            Cabin.query(function(result) {
                vm.cabins = result;
            });
        }
    }
})();

function toggleChoose(selectedCabin)
{
    $("#" + selectedCabin).toggleClass("selected");
}
function toggleInfo(selectedCabin)
{
    $(selectedCabin).toggleClass("selected");
}