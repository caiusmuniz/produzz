(function(){
    'use strict'
    var produzz = angular.module('produzzApp');
    produzz.service('contatoSrv', function($http, $q, $rootScope, WebService){
        var _contato = this;
        
        _contato.logado = false;
        _contato.temInfo = false;
        _contato.temErro = false;
        _contato.msgsErro = {};
        _contato.contatoList = {};
        _contato.emailEnviado = false;

        _contato.get = function(contato) {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/contatos/' + contato)
            .then(function(res) {
       			_contato.temInfo = res.temInfo;
       			_contato.temErro = res.temErro;
           		_contato.msgsErro = res.msgsErro;

           		if(res.temInfo || !res.temErro) {
                	_contato.contatoList = res.contatos;
                }
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _contato.google = function(data, acesso) {
        		console.log('data', data);
        		console.log('acesso', acesso);

        		var _defer = $q.defer();
            var _data = JSON.stringify({
                "id": data.id,
                "nome": data.given_name,
                "sobrenome": data.family_name,
                "email": data.email,
                "link": data.link,
                "foto": data.picture,
                "locale": data.locale,
                "idToken": acesso.idToken,
                "tokenAcesso": acesso.accessToken
            });

            WebService.create($rootScope.endPoint + '/plataforma/login/google', _data)
            .then(function(res) {
       			_contato.temInfo = res.temInfo;
       			_contato.temErro = res.temErro;
           		_contato.msgsErro = res.msgsErro;

           		if(res.temInfo || !res.temErro) {
           			_contato.logado = true;
           			_contato.contatoList = res.usuarios;

           		} else {
           			_contato.logado = false;
                }
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _contato.facebook = function(data) {
            var _defer = $q.defer();
            var _data = JSON.stringify({
                "id": data.additionalUserInfo.profile.id,
                "nome": data.additionalUserInfo.profile.first_name,
                "sobrenome": data.additionalUserInfo.profile.last_name,
                "email": data.additionalUserInfo.profile.email,
                "link": data.additionalUserInfo.profile.link,
                "foto": data.additionalUserInfo.profile.picture.data.url,
                "locale": data.additionalUserInfo.profile.locale,
                "idToken": data.user.G,
                "tokenAcesso": data.credential.accessToken
            });

            WebService.create($rootScope.endPoint + '/plataforma/login/facebook', _data)
            .then(function(res) {
       			_contato.temInfo = res.temInfo;
       			_contato.temErro = res.temErro;
           		_contato.msgsErro = res.msgsErro;

           		if(res.temInfo || !res.temErro) {
           			_contato.logado = true;
           			_contato.contatoList = res.usuarios;

           		} else {
           			_contato.logado = false;
                }
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _contato.logon = function(email, pwd) {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/contatos/' + email + '/' + pwd)
            .then(function(res) {
                _contato.temInfo = res.temInfo;
                _contato.temErro = res.temErro;
                _contato.msgsErro = res.msgsErro;

                if(res.temInfo || !res.temErro) {
                    _contato.logado = true;
                    _contato.contatoList = res.usuarios;

                } else {
                    _contato.logado = false;
                }
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _contato.update = function(data) {
            var _defer = $q.defer();
            var _data = JSON.stringify({
                    "id": $rootScope.contato.id,
                    "nome": data.nome,
                    "sobrenome": data.sobrenome,
                    "login": data.login,
                    "senha": sha1(data.password)
            });

            WebService.update($rootScope.endPoint + '/contatos/' + $rootScope.contato.id, _data)
            .then(function(res) {
            	_contato.temInfo = res.temInfo;
            	_contato.temErro = res.temErro;
            	_contato.msgsErro = res.msgsErro;
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err);
            });
            
            return _defer.promise;
        };

        _contato.excluir = function() {
            var _defer = $q.defer();

            WebService.delete($rootScope.endPoint + '/contatos/' + $rootScope.contato.id)
            .then(function(res) {
            	_contato.temInfo = res.temInfo;
            	_contato.temErro = res.temErro;
            	_contato.msgsErro = res.msgsErro;
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err);
            });
            
            return _defer.promise;
        };
        
        return _contato;
    });
})();
