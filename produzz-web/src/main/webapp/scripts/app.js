(function() {
    'use strict'
	Dropzone.autoDiscover = false;
    var produzz = angular.module('produzzApp', ['ngRoute','ngMessages','ngAnimate','ngSanitize','ngTouch','hs.WebService', 'thatisuday.dropzone']);
    
    produzz.config(function($routeProvider, dropzoneOpsProvider){
        $routeProvider
        .when('/login', {
            templateUrl: 'views/login.html',
            controller: 'contatoCtrl'
        })

        .when('/register', {
            templateUrl: 'views/register.html',
            controller: 'registerCtrl'
        })

        .when('/forgot', {
            templateUrl: 'views/forgot.html',
            controller: 'forgotCtrl'
        })

        .when('/dashboard', {
            templateUrl: 'views/dashboard.html',
            controller: 'dashboardCtrl'
        })

        .when('/contato', {
            templateUrl: 'views/contato.html',
            controller: 'contatoCtrl'
        })

        .when('/conta', {
            templateUrl: 'views/conta.html',
            controller: 'contaCtrl'
        })

        .when('/pagamento', {
            templateUrl: 'views/pagamento.html',
            controller: 'pagamentoCtrl'
        })

        .when('/plataforma', {
            templateUrl: 'views/plataforma.html',
            controller: 'plataformaCtrl'
        })

        .when('/upload', {
            templateUrl: 'views/upload.html',
            controller: 'uploadCtrl'
        })

        .when('/upload2', {
            templateUrl: 'views/upload2.html',
            controller: 'uploadCtrl'
        })

        .when('/analytics', {
            templateUrl: 'views/analytics.html',
            controller: 'analyticsCtrl'
        })

        .otherwise({redirectTo: '/login'});

    	dropzoneOpsProvider.setOptions({
    		url : '/upload_1.php',
    		acceptedFiles : 'image/jpeg, images/jpg, image/png',
    		addRemoveLinks : true,
    		dictDefaultMessage : 'Click to add or drop photos',
    		dictRemoveFile : 'Remove photo',
    		dictResponseError : 'Could not upload this photo'
    	});
    });
    
	produzz.run(['$rootScope', '$location', function($rootScope, $location){
//        $rootScope.endPoint = 'https://52.67.239.80:8443/app/ws';
        $rootScope.endPoint = 'http://localhost:8080/app/ws';
        $rootScope.loggedIn = false;
        $rootScope.showModal = false;

        $rootScope.toggleLogged = function() {
            $('#top-nav').toggleClass('hide');
            $('aside').toggleClass('hide');
            $('footer').toggleClass('hide');
        };

        var _preventNavigationUrl = null;

        $rootScope.preventNavigation = function(url) {
        		_preventNavigationUrl = url;
        }

        $rootScope.$on('$locationChangeStart', function (event, newUrl, oldUrl) {
    			if (_preventNavigationUrl == null) {
        			$rootScope.preventNavigation(newUrl);

    			} else  if (_preventNavigationUrl == newUrl) {
        			event.preventDefault();

    			} else {
    				$rootScope.preventNavigation(oldUrl);
    			}
        });
	}]);
})();
