(function(){
    'use strict'
	var produzz = angular.module('produzzApp');
    produzz.service('contaSrv', function($http, $q, $rootScope, WebService){
        var _conta = this;

        _conta.temInfo = false;
        _conta.temErro = false;
        _conta.msgsErro = {};
        _conta.contaList = {};
        _conta.atividadeList = {};

        _conta.getAtividades = function() {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/contas/atividades')
            .then(function(res) {
            	_conta.temInfo = res.temInfo;
           		_conta.temErro = res.temErro;
           		_conta.msgsErro = res.msgsErro;
                if(!res.temErro) {
                		_conta.atividadeList = res.atividades;
                }
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _conta.get = function(conta) {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/contas/' + conta)
            .then(function(res) {
            	_conta.temInfo = res.temInfo;
           		_conta.temErro = res.temErro;
           		_conta.msgsErro = res.msgsErro;
                if(!res.temErro) {
                		_conta.contaList = res.contas;
                }
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _conta.update = function(data) {
            var _defer = $q.defer();
            var _data = JSON.stringify({
                    "id": $rootScope.conta.id,
                    "empresa": data.empresa,
                    "ddi": data.ddi,
                    "ddd": data.ddd,
                    "telefone": data.telefone,
                    "cep": data.cep,
                    "endereco": data.endereco,
                    "numero": data.numero,
                    "complemento": data.complemento,
                    "municipio": data.municipio,
                    "estado": data.estado,
                    "pais": data.pais,
                    "atividadeEconomica": data.atividadeEconomica.id
            });

            WebService.update($rootScope.endPoint + '/contas/' + $rootScope.conta.id, _data)
            .then(function(res) {
            	_conta.temInfo = res.temInfo;
                _conta.temErro = res.temErro;
                _conta.msgsErro = res.msgsErro;
                _defer.resolve(res);
            },function(err) {
                console.error(err);
                _defer.reject(err);
            });

            return _defer.promise;
        };

        return _conta;
    });
})();
