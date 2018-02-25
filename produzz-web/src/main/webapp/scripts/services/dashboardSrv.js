(function(){
    'use strict'
    var produzz = angular.module('produzzApp');
    produzz.service('dashboardSrv', function($rootScope, $q, WebService){
        var _dashb = this;

        _dashb.temInfo = false;
        _dashb.temErro = false;
        _dashb.msgsErro = {};
        _dashb.planoList = {};

        _dashb.getPlano = function(conta) {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/dashboard/plano/' + conta)
            .then(function(res) {
            		_dashb.temInfo = res.temInfo;
            		_dashb.temErro = res.temErro;
            		_dashb.msgsErro = res.msgsErro;

            		if(res.temInfo || !res.temErro) {
            			_dashb.planoList = res.planos;
            		}
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _dashb.getContas = function(usuario) {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/dashboard/contas/' + usuario)
            .then(function(res) {
            		_dashb.temInfo = res.temInfo;
            		_dashb.temErro = res.temErro;
            		_dashb.msgsErro = res.msgsErro;

            		if(res.temInfo || !res.temErro) {
            			_dashb.contaList = res.contas;
                }
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        return _dashb;
    });
})();
