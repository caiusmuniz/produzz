(function(){
    'use strict'
    var produzz = angular.module('produzzApp');
    produzz.service('pagamentoSrv', function($rootScope, $q, WebService){
        var _pag = this;
        
        _pag.temErro = false;
        _pag.msgsErro = {};
        
        return _pag;
    });
})();
