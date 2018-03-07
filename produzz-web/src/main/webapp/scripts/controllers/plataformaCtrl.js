(function(){
    'use strict';
    var produzz = angular.module('produzzApp');
    produzz.controller('plataformaCtrl', function($scope, $rootScope, $location, plataformaSrv){
        $rootScope.temInfo = false;
        $rootScope.temErro = false;
        $rootScope.messages = {};
        $rootScope.facebookUser = [];
        $rootScope.googleUser = [];

        $scope.init = function() {
        		if($rootScope.showModal == true) {
        			$rootScope.toggleModal();
        			$scope.$apply;
        		}

        		plataformaSrv.getCategoria()
	    		.then(function(data) {
				$scope.temInfo = plataformaSrv.temInfo;
				$scope.temErro = plataformaSrv.temErro;
	   			$scope.messages = plataformaSrv.msgsErro;
	
	   			$rootScope.categorias = plataformaSrv.categorias;
	    		}, function(err) {
	    			console.error(err);
	    		});

        		$scope.buscarContaCanal();

        		$scope.$apply;
        };

        $scope.buscarContaCanal = function() {
	    		plataformaSrv.getContaCanal($rootScope.contato.contas[0].id)
	    		.then(function(data) {
				$scope.temInfo = plataformaSrv.temInfo;
				$scope.temErro = plataformaSrv.temErro;
	   			$scope.messages = plataformaSrv.msgsErro;
	
	   			$scope.loggedFacebook = false;
	   			$scope.notLoggedFacebook = false;
				$scope.loggedGoogle = false;
				$scope.notLoggedGoogle = false;
	
	   			$rootScope.facebookUser = plataformaSrv.facebookUser;
	   			if ($rootScope.facebookUser) {
	   				$scope.loggedFacebook = true;
	   			} else {
	   				$scope.notLoggedFacebook = true;
	   			}
	
	   			$rootScope.googleUser = plataformaSrv.googleUser;
	   			if ($rootScope.googleUser) {
	   				$scope.loggedGoogle = true;
	   			} else {
	   				$scope.notLoggedGoogle = true;
	   			}
	    		}, function(err) {
	    			console.error(err);
	    		});
        }

		function connectYoutube(googleUser) {
			console.log('idConta', $rootScope.contato.contas[0].id);

			plataformaSrv.connectYoutube($rootScope.contato.contas[0].id, googleUser)
       		.then(function(data) {
   				$scope.temInfo = plataformaSrv.temInfo;
   				$scope.temErro = plataformaSrv.temErro;
       			$scope.messages = plataformaSrv.msgsErro;

				if (plataformaSrv.temInfo || !plataformaSrv.temErro) {
					$scope.buscarContaCanal();
				}
       		}, function(err) {
       			console.error(err);
       		});

            $scope.$apply;
		}
        window.connectYoutube = connectYoutube;

		function disconnectYoutube() {
			console.log('idConta', $rootScope.contato.contas[0].id);

			plataformaSrv.disconnectYoutube($rootScope.contato.contas[0].id)
       		.then(function(data) {
   				$scope.temInfo = plataformaSrv.temInfo;
   				$scope.temErro = plataformaSrv.temErro;
       			$scope.messages = plataformaSrv.msgsErro;

				if (plataformaSrv.temInfo || !plataformaSrv.temErro) {
					$scope.buscarContaCanal();
				}
       		}, function(err) {
       			console.error(err);
       		});

            $scope.$apply;
		}
        window.disconnectYoutube = disconnectYoutube;

		function connectFacebook(facebookUser) {
			console.log('idConta', $rootScope.contato.contas[0].id);

			plataformaSrv.connectFacebook($rootScope.contato.contas[0].id, facebookUser)
       		.then(function(data) {
   				$scope.temInfo = plataformaSrv.temInfo;
   				$scope.temErro = plataformaSrv.temErro;
       			$scope.messages = plataformaSrv.msgsErro;

				if (plataformaSrv.temInfo || !plataformaSrv.temErro) {
					$scope.buscarContaCanal();
				}
       		}, function(err) {
       			console.error(err);
       		});

            $scope.$apply;
		}
        window.connectFacebook = connectFacebook;

		function disconnectFacebook() {
			console.log('idConta', $rootScope.contato.contas[0].id);

			plataformaSrv.disconnectFacebook($rootScope.contato.contas[0].id)
       		.then(function(data) {
   				$scope.temInfo = plataformaSrv.temInfo;
   				$scope.temErro = plataformaSrv.temErro;
       			$scope.messages = plataformaSrv.msgsErro;

				if (plataformaSrv.temInfo || !plataformaSrv.temErro) {
					$scope.buscarContaCanal();
				}
       		}, function(err) {
       			console.error(err);
       		});

            $scope.$apply;
		}
        window.disconnectFacebook = disconnectFacebook;

        $scope.init();
    });
})();
