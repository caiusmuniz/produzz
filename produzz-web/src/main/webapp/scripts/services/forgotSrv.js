(function(){
    'use strict'
    var produzz = angular.module('produzzApp');
    produzz.service('forgotSrv', function($http, $q, $rootScope, WebService){
        var _contato = this;
        
        _contato.temInfo = false;
        _contato.temErro = false;
        _contato.msgsErro = {};
        
        _contato.forgot = function(email) {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/contatos/forgot/' + email)
            .then(function(res){
    				_contato.temInfo = res.temInfo;
    				_contato.temErro = res.temErro;
        			_contato.msgsErro = res.msgsErro;
                _defer.resolve(res);

            },function(err){
                console.error(err);
                _defer.reject(err);
            })
            return _defer.promise;
        };
        
        return _contato;
    });
})();
