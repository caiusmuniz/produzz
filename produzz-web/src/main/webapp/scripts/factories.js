(function() {
    'use strict'

    var produzz = angular.module("ProduzzApp")
    produzz.factory("ContatoFactory", function($http) {
        var _WS_CONTATO_ADDRESS = '/admin/ws/contatos';
        var _verificar = function(data) {
            var _temp = _WS_CONTATO_ADDRESS + '/' + data.email + '/' + md5(data.password);
            return $http.get(_temp);
        };

        var _buscar = function(data) {
            var _temp = _WS_CONTATO_ADDRESS + '/' + data.email + '/' + data.ddi + '/' + data.ddd + '/' + data.telefone;
            return $http.get(_temp);
        };

        var _saveData = function(data) {
            var _temp = _WS_CONTATO_ADDRESS;
            return $http.post(_temp, data);
        };

        return {
            verificar: _verificar,
            saveData: _saveData
        };
    });

    var produzz = angular.module("ProduzzApp")
    produzz.factory("RegisterFactory", function($http) {
        var _WS_CONTATO_ADDRESS = '/admin/ws/contatos';
        var _saveData = function(data) {
            var _temp = _WS_CONTATO_ADDRESS;
            return $http.post(_temp, data);
        };

        return {
            saveData: _saveData
        };
    });

    var produzz = angular.module("ProduzzApp")
    produzz.factory("ForgotFactory", function($http) {
        var _WS_CONTATO_ADDRESS = '/admin/ws/contatos';
        var _buscar = function(data) {
            var _temp = _WS_CONTATO_ADDRESS + '/' + data.email + '/' + data.ddi + '/' + data.ddd + '/' + data.telefone;
            return $http.get(_temp);
        };
        return {
            buscar: _buscar,
        };
    });

    produzz.factory("DashboardFactory", function($http, $rootScope) {
        var _WS_DASHBOARD_ADDRESS = '/admin/ws/dashboard';
        var _getPlanos = function(conta) {
            return $http.get(_WS_DASHBOARD_ADDRESS + '/plano/' + conta);
        };
        return {
            getPlanos: _getPlanos
        }
    });

    produzz.factory("ContaFactory", function($http) {
        var _WS_CONTA_ADDRESS = '/admin/ws/contas';
        var _buscar = function(data) {
            var _temp = _WS_CONTA_ADDRESS + '/' + data.id;
            return $http.get(_temp);
        };

        var _saveData = function(data) {
            var _temp = _WS_CONTA_ADDRESS;
            return $http.post(_temp, data);
        };

        return {
            saveData: _saveData
        }
    });

    produzz.factory("PagamentoFactory", function($http) {
        var _WS_PAGAMENTO_ADDRESS = '/admin/ws/pagamento';
        var _saveData = function(pagamento) {
            return $http.post(_WS_PAGAMENTO_ADDRESS, pagamento);
        };

        return {
            saveData: _saveData
        }
    });

    produzz.factory("PlataformaFactory", function($http) {
        var _WS_PLATAFORMA_ADDRESS = '/admin/ws/plataforma';
        var _saveData = function(plataforma) {
            return $http.post(_WS_PLATAFORMA_ADDRESS, plataforma);
        };

        return {
            saveData: _saveData
        }
    });

    produzz.factory("WebService", function($http) {
        var find = function(o) {
            console.log(o);
        };

        var findAll = function(o) {
            console.log(o);
        };

        var saveData = function(o) {
            console.log(o);
        };

        return {
            find: _find,
            findAll: _findAll,
            saveData: _saveData
        };
    });
})();
