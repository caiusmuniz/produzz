(function() {
    'use strict'
    
    var produzz = angular.module("ProduzzApp")
    produzz.config(function ($routeProvider) {
        $routeProvider.when("/login", {
            templateUrl: "pages/login.html",
            controller: "contatoCtrl"
        });

        $routeProvider.when("/register", {
            templateUrl: "pages/register.html",
            controller: "registerCtrl"
        });

        $routeProvider.when("/forgot", {
            templateUrl: "pages/forgot.html",
            controller: "forgotCtrl"
        });

        $routeProvider.when("/contato", {
            templateUrl: "pages/contato.html",
            controller: "contatoCtrl"
        });

        $routeProvider.when("/conta", {
            templateUrl: "pages/conta.html",
            controller: "contaCtrl"
        });

        $routeProvider.when("/pagamento", {
            templateUrl: "pages/pagamento.html",
            controller: "pagamentoCtrl"
        });

        $routeProvider.when("/plataforma", {
            templateUrl: "pages/plataforma.html",
            controller: "plataformaCtrl"
        });

        $routeProvider.when("/dashboard/:conta", {
            templateUrl: "pages/dashboard.html",
            controller: "DashboardCtrl",
            resolve: {
                campanhas: function (DashboardFactory,$route) {
                    return DashboardFactory.getCampanhas($route.current.params.conta);
                },
                planos: function (DashboardFactory,$route) {
                    return DashboardFactory.getPlanos($route.current.params.conta);
                }
            }
        });

        $routeProvider.when("/dcontato/:conta", {
            templateUrl: "pages/contatoDetalhe.html",
            controller: "DetalheListaContatoCtrl"
        });	

        $routeProvider.otherwise({redirectTo: "/login"});
    });
})();
    