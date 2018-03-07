(function(){
    'use strict'
    var produzz = angular.module('produzzApp');
    produzz.service('plataformaSrv', function($http, $q, $rootScope, WebService){
        var _plataforma = this;
        
        _plataforma.temErro = false;
        _plataforma.msgsErro = {};
        _plataforma.facebookUser = [];
        _plataforma.googleUser = [];

        _plataforma.getCategoria = function() {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/categoria/all')
            .then(function(res) {
            		_plataforma.temInfo = res.temInfo;
            		_plataforma.temErro = res.temErro;
            		_plataforma.msgsErro = res.msgsErro;

            		if(res.temInfo || !res.temErro) {
            			_plataforma.categorias = res.categorias;
            		}
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _plataforma.getContaCanal = function(conta) {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/plataforma/' + conta)
            .then(function(res) {
            		_plataforma.temInfo = res.temInfo;
            		_plataforma.temErro = res.temErro;
            		_plataforma.msgsErro = res.msgsErro;

            		if(res.temInfo || !res.temErro) {
            			_plataforma.facebookUser = res.facebookUser;
            			_plataforma.googleUser = res.googleUser;
            		}
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _plataforma.connectYoutube = function(conta, data) {
            var _defer = $q.defer();
            var _data = JSON.stringify({
                "id": data.additionalUserInfo.profile.id,
                "idConta": conta,
                "nome": data.additionalUserInfo.profile.given_name,
                "sobrenome": data.additionalUserInfo.profile.family_name,
                "email": data.additionalUserInfo.profile.email,
                "link": data.additionalUserInfo.profile.link,
                "foto": data.additionalUserInfo.profile.picture,
                "locale": data.additionalUserInfo.profile.locale,
                "idToken": data.credential.idToken,
                "tokenAcesso": data.credential.accessToken,
                "tokenRenovacao": data.user.refreshToken
            });

            WebService.create($rootScope.endPoint + '/plataforma/conectar/google', _data)
            .then(function(res) {
            		_plataforma.temInfo = res.temInfo;
            		_plataforma.temErro = res.temErro;
            		_plataforma.msgsErro = res.msgsErro;

                _defer.resolve(res);
            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _plataforma.disconnectYoutube = function(conta) {
            var _defer = $q.defer();
            var _data = JSON.stringify({
                "idConta": conta
            });

            WebService.read($rootScope.endPoint + '/plataforma/desconectar/google/' + conta)
            .then(function(res) {
            		_plataforma.temInfo = res.temInfo;
            		_plataforma.temErro = res.temErro;
            		_plataforma.msgsErro = res.msgsErro;

                _defer.resolve(res);
            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _plataforma.connectFacebook = function(conta, data) {
            var _defer = $q.defer();
            var _data = JSON.stringify({
                "id": data.additionalUserInfo.profile.id,
                "idConta": conta,
                "nome": data.additionalUserInfo.profile.first_name,
                "sobrenome": data.additionalUserInfo.profile.last_name,
                "email": data.additionalUserInfo.profile.email,
                "link": data.additionalUserInfo.profile.link,
                "foto": data.additionalUserInfo.profile.picture.data.url,
                "locale": data.additionalUserInfo.profile.locale,
                "idToken": data.user.G,
                "tokenAcesso": data.credential.accessToken
            });

            WebService.create($rootScope.endPoint + '/plataforma/conectar/facebook', _data)
            .then(function(res) {
            		_plataforma.temInfo = res.temInfo;
            		_plataforma.temErro = res.temErro;
            		_plataforma.msgsErro = res.msgsErro;

                _defer.resolve(res);
            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _plataforma.disconnectFacebook = function(conta) {
            var _defer = $q.defer();
            var _data = JSON.stringify({
                "idConta": conta
            });

            WebService.read($rootScope.endPoint + '/plataforma/desconectar/facebook/' + conta)
            .then(function(res) {
            		_plataforma.temInfo = res.temInfo;
            		_plataforma.temErro = res.temErro;
            		_plataforma.msgsErro = res.msgsErro;

                _defer.resolve(res);
            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        return _plataforma;
    });
})();
