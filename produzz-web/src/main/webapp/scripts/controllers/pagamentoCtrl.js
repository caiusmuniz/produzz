(function(){
    'use strict';
    var produzz = angular.module('produzzApp');
    produzz.controller('pagamentoCtrl', function($scope, $rootScope, pagamentoSrv) {    	
    		$rootScope.pagamento = {};

    		$scope.init = function() {
    			if($rootScope.showModal == true) {
    				$rootScope.toggleModal();
    				$scope.$apply;
    			}

            $scope.$apply;
        };

        $scope.init();
    });
})();