(function(){
    'use strict'
    var produzz = angular.module('produzzApp');
    produzz.service('registerSrv', function($http, $q, $rootScope, WebService){
        var _contato = this;
        
        _contato.temInfo = false;
        _contato.temErro = false;
        _contato.msgsErro = {};
        
        _contato.register = function(data) {
            var _defer = $q.defer();
            var _data = JSON.stringify({
                    "id": null,
                    "nome": data.nome,
                    "sobrenome": data.sobrenome,
                    "login": data.email,
                    "senha": sha1(data.password)
            });
            
            WebService.create($rootScope.endPoint + '/contatos', _data)
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
