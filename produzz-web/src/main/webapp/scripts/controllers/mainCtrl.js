(function(){
    'use strict';
    var produzz = angular.module('produzzApp');
    produzz.controller('mainCtrl', function($scope, $rootScope, $location, $anchorScroll, $window) {
        $rootScope.showModal = false;
        $rootScope.showExcModal = false;

        $rootScope.toggleModal = function() {
            $rootScope.showModal = !$rootScope.showModal;
            $('#myModal').modal($rootScope.showModal ? 'show' : 'hide');
        };

        $rootScope.toggleExcModal = function() {
            $rootScope.showExcModal = !$rootScope.showExcModal;
            $('#excluirModal').modal($rootScope.showExcModal ? 'show' : 'hide');
        };

//        $window.scroll(function() {
//            var position = $window.screenTop();
//            if(position >= 200) {
//                $('#scroll-to-top').attr('style','bottom:8px;');
//
//            } else {
//                $('#scroll-to-top').removeAttr('style');
//            }
//        });
        
        $scope.init = function() {
            $rootScope.toggleLogged();
        };

        $scope.scrollTo = function() {
            $("html, body").animate({ scrollTop: 0 }, 600);
             return false;
        };
        
        $scope.menuToggle = function() {
            $('#wrapper').toggleClass('sidebar-hide');
            $('.main-menu').find('.openable').removeClass('open');
            $('.main-menu').find('.submenu').removeAttr('style');
        };
        
        $scope.sidebarToggle = function() {
            $('#wrapper').toggleClass('sidebar-display');
            $('.main-menu').find('.openable').removeClass('open');
            $('.main-menu').find('.submenu').removeAttr('style');
        };
        
        $scope.sizeToggle = function() {
            $('#wrapper').off("resize");
            $('#wrapper').toggleClass('sidebar-mini');
            $('.main-menu').find('.openable').removeClass('open');
            $('.main-menu').find('.submenu').removeAttr('style');
        };
        
        $scope.toggleClass = function() {
            $('.main-menu').find('.submenu').toggleClass('show');
        };

        $scope.init();
    });
})();
