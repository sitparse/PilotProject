'use strict';
myApp.directive('myWidget', function () {
    return {
        templateUrl: 'partials/home.html',
        controller: 'searchController'
    }
});
