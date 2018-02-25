(function(){
    'use strict';
    var produzz = angular.module('produzzApp');
    produzz.controller('registerCtrl', function($scope, $rootScope, $location, registerSrv){
        $scope.lembreDeMim = false;
        $rootScope.temInfo = false;
        $rootScope.temErro = false;
        $rootScope.messages = {};

        $scope.init = function() {
            if($rootScope.showModal == true) {
                $rootScope.toggleModal();
                $scope.$apply;
            }
        };

        $scope.register = function(contato) {
        		registerSrv.register(contato)
            .then(function(data){
        			$rootScope.temInfo = registerSrv.temInfo;
            		$rootScope.temErro = registerSrv.temErro;
            		$rootScope.messages = registerSrv.msgsErro;
            		$rootScope.contato = [];
                if(registerSrv.temInfo) {
                		$location.path('/login');
                }
            });
            $scope.$apply;
        };
        
        $scope.init();
    });
})();
