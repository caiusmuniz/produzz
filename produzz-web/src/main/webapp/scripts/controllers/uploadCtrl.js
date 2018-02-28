(function(){
    'use strict';
    var produzz = angular.module('produzzApp');
    produzz.controller('uploadCtrl', function($scope, $rootScope, $location, uploadSrv) {    	
    		$rootScope.upload = {};

    		$scope.init = function() {
    			if($rootScope.showModal == true) {
    				$rootScope.toggleModal();
    				$scope.$apply;
    			}

    			uploadSrv.getCategorias()
    			.then(function(data) {
    				$scope.temInfo = uploadSrv.temInfo;
    				$scope.temErro = uploadSrv.temErro;
    				$scope.messages = uploadSrv.msgsErro;

    				if(!uploadSrv.temErro) {
    					$rootScope.categorias = uploadSrv.categoriaList;
    				}
    			}, function(err) {
    				console.error(err);
    			});

    			$scope.$apply;
        };

        $scope.init();

        //Set options for dropzone
        //Visit http://www.dropzonejs.com/#configuration-options for more options
        $scope.dzOptions = {
        		url : 'ws/videos/upload/' + $rootScope.contato.contas[0].id,
        		paramName : 'file',
        		maxFiles : 1,
        		maxFilesize : '100',
        		acceptedFiles : 'audio/*, video/*, image/*',
        		addRemoveLinks : true,
        };

	    	//Handle events for dropzone
	    	//Visit http://www.dropzonejs.com/#events for more events
	    	$scope.dzCallbacks = {
	    		'addedfile' : function(file){
	    			console.log(file);
	    			$scope.newFile = file;
	    		},
	    		'success' : function(file, xhr){
	    			console.log(xhr);
	    			$rootScope.idVideo = xhr.videos[0].id;
	    			$location.path('/upload2');
	     		$scope.$apply;
	    		},
	    	};

	    	//Apply methods for dropzone
	    	//Visit http://www.dropzonejs.com/#dropzone-methods for more methods
	    	$scope.dzMethods = {};

	    	$scope.removeNewFile = function(){
	    		$scope.dzMethods.removeFile($scope.newFile); //We got $scope.newFile from 'addedfile' event callback
	    	}

        //Set options for dropzone
        //Visit http://www.dropzonejs.com/#configuration-options for more options
        $scope.dzOptions2 = {
        		url : 'ws/videos/upload/thumbnail/' + $rootScope.contato.contas[0].id + '/' + $rootScope.idVideo,
        		paramName : 'file',
        		maxFiles : 1,
        		maxFilesize : '5',
        		acceptedFiles : 'image/*',
        		addRemoveLinks : true,
        };

	    	//Handle events for dropzone
	    	//Visit http://www.dropzonejs.com/#events for more events
	    	$scope.dzCallbacks2 = {
	    		'addedfile' : function(file){
	    			console.log(file);
	    			$scope.newFile = file;
	    		},
	    		'success' : function(file, xhr){
	    			console.log(xhr);
	    			$rootScope.idThumbnail = xhr.thumbnails[0].id;
	     		$scope.$apply;
	    		},
	    	};

        $scope.publicar = function(video) {
        		uploadSrv.publicar(video)
            .then(function(data) {
   				$scope.temInfo = uploadSrv.temInfo;
   				$scope.temErro = uploadSrv.temErro;
       			$scope.messages = uploadSrv.msgsErro;
            }, function(err) {
                console.error(err);
            });
        		$scope.$apply;
        };
    });
})();
