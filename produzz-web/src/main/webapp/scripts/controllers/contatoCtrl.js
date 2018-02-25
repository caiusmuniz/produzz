(function(){
	'use strict';
	var produzz = angular.module('produzzApp');
	produzz.controller('contatoCtrl', function($scope, $rootScope, $location, contatoSrv){
		$scope.lembreDeMim = false;
		$scope.showExcluirModal = false;

		$scope.init = function() {
			if($rootScope.showModal == true) {
				$rootScope.toggleModal();
				$rootScope.toggleExcModal();
				$scope.temInfo = false;
				$scope.temErro = false;
				$scope.messages = {};
				$scope.$apply;
			}
		};

		$rootScope.logout = function() {
			$rootScope.toggleModal();
			$rootScope.loggedIn = false;
			$scope.temInfo = false;
			$scope.temErro = false;
			$scope.messages = {};
			$rootScope.contato = [];

			if ($rootScope.facebookUser || $rootScope.googleUser) {
				firebase.auth().signOut().then(function() {
					// Sign-out successful.
					console.log("success");
				}).catch(function(error) {
					// An error happened.
					console.log("error");
				});
			}

			$rootScope.facebookUser = [];
			$rootScope.googleUser = [];

			$location.path('/login');
 			$scope.$apply;
		};

		function onSignInFacebook(facebookUser) {
			console.log('Facebook Auth Response', facebookUser);
			$rootScope.facebookUser = facebookUser;

            contatoSrv.facebook(facebookUser)
       		.then(function(data) {
   				$scope.temInfo = contatoSrv.temInfo;
   				$scope.temErro = contatoSrv.temErro;
       			$scope.messages = contatoSrv.msgsErro;

       			if(contatoSrv.logado) {
       				$rootScope.toggleLogged();
       				$rootScope.loggedIn = contatoSrv.logado;
       				$rootScope.contato = contatoSrv.contatoList[0];
       				if($scope.lembreDeMim) {
       					// TODO gravar cookie
       				}
       				$location.path('/dashboard');
       			};
       		}, function(err) {
       			console.error(err);
       		});

            $scope.$apply;
		}
        window.onSignInFacebook = onSignInFacebook;

		function onSignInGoogle(googleUser) {
			console.log('Google Auth Response', googleUser);
			$rootScope.googleUser = googleUser;

			var unsubscribe = firebase.auth().onAuthStateChanged(function(firebaseUser) {
				unsubscribe();

				if (!isUserEqualGoogle(googleUser, firebaseUser)) {
					var credential = firebase.auth.GoogleAuthProvider.credential(googleUser.Zi.id_token);

					firebase.auth().signInWithCredential(credential).catch(function(error) {
						var errorCode = error.code;
						var errorMessage = error.message;
						var email = error.email;
						var credential = error.credential;
						if (errorCode === 'auth/account-exists-with-different-credential') {
							alert('You have already signed up with a different auth provider for that email.');
						} else {
							console.error(error);
						}
					});

		            contatoSrv.google(googleUser.additionalUserInfo.profile, googleUser.credential)
		       		.then(function(data) {
		   				$scope.temInfo = contatoSrv.temInfo;
		   				$scope.temErro = contatoSrv.temErro;
		       			$scope.messages = contatoSrv.msgsErro;

		   				if(contatoSrv.logado) {
		                   $rootScope.toggleLogged();
		                   $rootScope.loggedIn = contatoSrv.logado;
		                   $rootScope.contato = contatoSrv.contatoList[0];
		                   if($scope.lembreDeMim) {
		                       // TODO gravar cookie
		                   }
		                   $location.path('/dashboard');
		               };
		       		}, function(err) {
		       			console.error(err);
		       		});

		            $scope.$apply;
				} else {
					console.log('User already signed-in Firebase.');

		            contatoSrv.google(googleUser.additionalUserInfo.profile, googleUser.credential)
		       		.then(function(data) {
		   				$scope.temInfo = contatoSrv.temInfo;
		   				$scope.temErro = contatoSrv.temErro;
		       			$scope.messages = contatoSrv.msgsErro;

		   				if(contatoSrv.logado) {
		                   $rootScope.toggleLogged();
		                   $rootScope.loggedIn = contatoSrv.logado;
		                   $rootScope.contato = contatoSrv.contatoList[0];
		                   if($scope.lembreDeMim) {
		                       // TODO gravar cookie
		                   }
		                   $location.path('/dashboard');
		               };
		       		}, function(err) {
		       			console.error(err);
		       		});

		            $scope.$apply;
				}
			});
		}
		window.onSignInGoogle = onSignInGoogle;

		function isUserEqualGoogle(googleUser, firebaseUser) {
			if (firebaseUser) {
				var providerData = firebaseUser.providerData;
				for (var i = 0; i < providerData.length; i++) {
					if (providerData[i].providerId === firebase.auth.GoogleAuthProvider.PROVIDER_ID &&
							providerData[i].uid === googleUser.additionalUserInfo.profile.id) {
						return true;
					}
				}
			}
			return false;
		}

		$scope.get = function(contato) {
			contatoSrv.get(contato)
			.then(function(data) {
				$scope.temInfo = contatoSrv.temInfo;
				$scope.temErro = contatoSrv.temErro;
				$scope.messages = contatoSrv.msgsErro;

				if(contatoSrv.temInfo || !contatoSrv.temErro) {
					$rootScope.contato = contatoSrv.contatoList[0];
				}
       		}, function(err) {
       			console.error(err);
       		});
       		$scope.$apply;
        };

        $scope.logon = function(email, pwd) {
        		contatoSrv.logon(email, sha1(pwd))
        		.then(function(data) {
        			$scope.temInfo = contatoSrv.temInfo;
        			$scope.temErro = contatoSrv.temErro;
        			$scope.messages = contatoSrv.msgsErro;

        			if(contatoSrv.logado) {
        				$rootScope.toggleLogged();
        				$rootScope.loggedIn = contatoSrv.logado;
        				$rootScope.contato = contatoSrv.contatoList[0];
        				if($scope.lembreDeMim) {
        					// TODO gravar cookie
        				}
        				$location.path('/dashboard');
        			};
        		});
        		$scope.$apply;
        };

        $scope.update = function(contato) {
        		contatoSrv.update(contato)
        		.then(function(data) {
        			$scope.temInfo = contatoSrv.temInfo;
        			$scope.temErro = contatoSrv.temErro;
        			$scope.messages = contatoSrv.msgsErro;
        		});
        		$scope.$apply;
        };

        $rootScope.excluir = function() {
        		$rootScope.toggleExcModal();

        		contatoSrv.excluir()
        		.then(function(data) {
        			$scope.temInfo = contatoSrv.temInfo;
        			$scope.temErro = contatoSrv.temErro;
        			$scope.messages = contatoSrv.msgsErro;

        			$rootScope.loggedIn = false;
        			$scope.messages = {};
        			$rootScope.contato = [];
        		});

        		$location.path('/login');
        		$scope.$apply;
        };

        $scope.init();
	});
})();
