(function(){
    'use strict';
    var produzz = angular.module('produzzApp');
    produzz.controller('forgotCtrl', function($scope, $rootScope, $location, forgotSrv){
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

        $scope.forgot = function(email) {
        		forgotSrv.forgot(email)
            .then(function(data) {
        			$rootScope.temInfo = forgotSrv.temInfo;
        			$rootScope.temErro = forgotSrv.temErro;
            		$rootScope.messages = forgotSrv.msgsErro;
            		if(forgotSrv.temInfo || !forgotSrv.temErro) {
            			$location.path('/login');
            		}
            });
        		$scope.$apply;
        };
        
        $scope.init();
    });
})();
