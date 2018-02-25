(function(){
    'use strict';
    var produzz = angular.module('produzzApp');
    produzz.controller('contaCtrl', function($scope, $rootScope, $location, contaSrv){
        $scope.init = function() {
            if($rootScope.showModal == true) {
                $rootScope.toggleModal();
       			$scope.temInfo = false;
       			$scope.temErro = false;
       			$scope.messages = {};
                $scope.$apply;
            }

            contaSrv.getAtividades()
            .then(function(data) {
   				$scope.temInfo = contaSrv.temInfo;
   				$scope.temErro = contaSrv.temErro;
       			$scope.messages = contaSrv.msgsErro;

   				if(!contaSrv.temErro) {
            		$rootScope.atividades = contaSrv.atividadeList;
            	}
            }, function(err) {
                console.error(err);
            });

            contaSrv.get($rootScope.contato.contas[0].id)
            .then(function(data) {
   				$scope.temInfo = contaSrv.temInfo;
   				$scope.temErro = contaSrv.temErro;
       			$scope.messages = contaSrv.msgsErro;

   				if(!contaSrv.temErro) {
            		$rootScope.conta = contaSrv.contaList[0];
            	}
            }, function(err) {
                console.error(err);
            });
            $scope.$apply;
        };

        $scope.update = function(conta) {
            contaSrv.update(conta)
            .then(function(data) {
   				$scope.temInfo = contaSrv.temInfo;
   				$scope.temErro = contaSrv.temErro;
       			$scope.messages = contaSrv.msgsErro;
            }, function(err) {
                console.error(err);
            });
            $scope.$apply;
        };

        $scope.init();
    });
})();
