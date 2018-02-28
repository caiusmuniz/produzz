(function(){
    'use strict'
    var produzz = angular.module('produzzApp');
    produzz.service('uploadSrv', function($http, $q, $rootScope, WebService){
        var _upload = this;
        
        _upload.temErro = false;
        _upload.msgsErro = {};
        _upload.plataformaList = {};
        _upload.categoriaList = {};

        _upload.getCategorias = function() {
            var _defer = $q.defer();
            WebService.read($rootScope.endPoint + '/categoria/all')
            .then(function(res) {
            		_upload.temInfo = res.temInfo;
            		_upload.temErro = res.temErro;
            		_upload.msgsErro = res.msgsErro;
                if(!res.temErro) {
                		_upload.categoriaList = res.categorias;
                }
                _defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        _upload.publicar = function(data) {
            var _defer = $q.defer();
            var _data = JSON.stringify({
                "id": $rootScope.contato.contas[0].id,
                "idVideo": $rootScope.idVideo,
                "idThumbnail": $rootScope.idThumbnail,
                "titulo": data.titulo,
                "descricao": data.descricao,
                "tags": data.tags,
                "locale": data.locale,
                "privacidade": data.privacidade,
                "categoria": data.categoria
            });

            WebService.create($rootScope.endPoint + '/videos/publish', _data)
            .then(function(res) {
            		_upload.temInfo = res.temInfo;
            		_upload.temErro = res.temErro;
            		_upload.msgsErro = res.msgsErro;

           		_defer.resolve(res);

            },function(err) {
                console.error(err);
                _defer.reject(err)
            });
            return _defer.promise;
        };

        return _upload;
    });
})();
