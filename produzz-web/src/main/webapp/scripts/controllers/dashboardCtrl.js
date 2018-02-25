(function() {
    'use strict';
    var produzz = angular.module('produzzApp');
    produzz.controller('dashboardCtrl', function($scope, $rootScope, dashboardSrv) {    	
        $rootScope.temInfo = false;
        $rootScope.temErro = false;
        $rootScope.messages = {};
        $rootScope.plano = {};
        $rootScope.contaList = {};

        $scope.init = function() {
        		if($rootScope.showModal == true) {
        			$rootScope.toggleModal();
        			$scope.$apply;
        		}

        		dashboardSrv.getPlano($rootScope.contato.contas[0].id)
        		.then(function(data) {
   				$scope.temInfo = dashboardSrv.temInfo;
   				$scope.temErro = dashboardSrv.temErro;
       			$scope.messages = dashboardSrv.msgsErro;

       			$rootScope.plano = dashboardSrv.planoList[0];
        		}, function(err) {
        			console.error(err);
        		});

        		$scope.$apply;
        };

        $scope.init();
    });
})();
