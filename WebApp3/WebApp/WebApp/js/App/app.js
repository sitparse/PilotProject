var myApp = angular.module('WebApp', ['ui.router']);

myApp.config(['$urlRouterProvider', '$stateProvider', function ($urlRouterProvider, $stateProvider) {
    $urlRouterProvider.otherwise('/');
    $stateProvider
    .state('/', {
        url: '/',
        templateUrl: 'partials/display.html'
    })
    .state('/search', {
        url: '/search',
        templateUrl: 'partials/home.html'
        
    })
} ]);

                   