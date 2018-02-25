(function() {
    'use strict';

    var app = angular.module('hs.WebService', []);
    app.factory('WebService', function() {
        var _ws = function(method, url, data, contentType) {
            return $.ajax({
                type: method,
                url: url,
                data: data,
                contentType: contentType,
                dataType: 'json'
            });
        };
        var _custom = function(config) {
            return $.ajax(config);
        };
        return {
            read: function(url) {
                return _ws('GET', url, {}, 'application/json');
            },
            create: function(url, data) {
                return _ws('POST', url, data, 'application/json');
            },
            update: function(url, data) {
                return _ws('PUT', url, data, 'application/json');
            },
            delete: function(url) {
                return _ws('DELETE', url, {}, 'application/json');
            },
            upload: function(url, data) {
                return _ws('POST', url, data, 'multipart/form-data');
            },
            custom: function(config) {
                return _custom(config);
            }
        };
    });
})();
