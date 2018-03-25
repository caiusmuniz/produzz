(function(){
    'use strict';
    var produzz = angular.module('produzzApp');
    produzz.controller('analyticsCtrl', function($scope, $rootScope, dashboardSrv) {    	
        $rootScope.temInfo = false;
        $rootScope.temErro = false;
        $rootScope.messages = {};
        $rootScope.plano = {};
        $rootScope.contaList = {};
        $rootScope.teste = [];

        $scope.init = function() {
        		if($rootScope.showModal == true) {
        			$rootScope.toggleModal();
        			$scope.$apply;
        		}

        		analyticsSrv.get($rootScope.contato.contas[0].id)
        		.then(function(data) {
   				$scope.temInfo = analyticsSrv.temInfo;
   				$scope.temErro = analyticsSrv.temErro;
       			$scope.messages = analyticsSrv.msgsErro;

       			$rootScope.metrica = analyticsSrv.metricaList[0];
       			$rootScope.teste = analyticsSrv.teste;
        		}, function(err) {
        			console.error(err);
        		});

        		$scope.$apply;
        };

        $scope.init();
    });
})();
