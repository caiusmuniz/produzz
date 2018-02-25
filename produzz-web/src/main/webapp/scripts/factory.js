mobi4e.factory("WebService", function(Config) {
    var _get = function(req, success, error) {
        var url = Config.baseUrl + req;
        $.ajax({
            type: 'GET',
            url: req,
            crossDomain: true,
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: success,
            error: error
        });
    };
    
    return {
        get: _get
    };
});

produzz.factory("ContatoAPI", function($http, Config) {
    var _verificar = function(data) {
        var _temp = Config.baseUrl + '/ws/contatos/' + data.email + '/' + md5(data.password);
        return $http.get(_temp);
    };

    var _buscar = function(data) {
        var _temp = Config.baseUrl + '/ws/contatos/' + data.email + '/' + data.ddi + '/' + data.ddd + '/' + data.telefone;
        return $http.get(_temp);
    };

    var _saveData = function(data) {
        var _temp = Config.baseUrl + '/ws/contatos';
        return $http.post(_temp, data);
    };

    return {
        verificar: _verificar,
        saveData: _saveData
    };
});

produzz.factory("DashboardAPI", function($http, Config) {
    var _verificar = function(data) {
        var _temp = Config.baseUrl + '/ws/dashboard/' + data.email + '/' + md5(data.password);
        return $http.get(_temp);
    };

    var _buscar = function(data) {
        var _temp = Config.baseUrl + '/ws/dashboard/' + data.email + '/' + data.ddi + '/' + data.ddd + '/' + data.telefone;
        return $http.get(_temp);
    };

    var _saveData = function(data) {
        var _temp = Config.baseUrl + '/ws/dashboard';
        return $http.post(_temp, data);
    };

    return {
        verificar: _verificar,
        saveData: _saveData
    };
});
