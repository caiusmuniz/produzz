(function(){
    'use strict'
    var produzz = angular.module('produzzApp');
    produzz.service('analyticsSrv', function($rootScope, $q, WebService){
        var _analytics = this;

        _analytics.temInfo = false;
        _analytics.temErro = false;
        _analytics.msgsErro = {};
        _analytics.metricaList = {};

        _analytics.get = function(conta) {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/videos/analytics/' + conta)
            .then(function(res) {
            		_analytics.temInfo = res.temInfo;
            		_analytics.temErro = res.temErro;
            		_analytics.msgsErro = res.msgsErro;

            		if(res.temInfo || !res.temErro) {
            			_analytics.metricaList = res.metricas;
            			_analytics.teste = res.teste;
            		}
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        return _analytics;
    });
})();
