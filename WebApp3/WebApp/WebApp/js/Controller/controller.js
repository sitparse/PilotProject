'use strict';
myApp.controller('searchController', ['$scope', 'parseService', '$timeout', '$state', function ($scope, parseService, $timeout, $state) {
    $scope.data = [];
    var temp;
    $scope.$on('LOAD', function () {


        $scope.$parent.loading = true;
    });
    $scope.$on('UNLOAD', function () {

        $scope.$parent.loading = false;
    });
    $scope.submit = function () {

        // $scope.dataloaded = false;
        temp = parseService.startWorking($scope, $scope.name);
        if (!$state.is('/search')) {
            $timeout(function () {
                $state.go('/search');
            }, 700);
        }
        if (temp != false) {
            $timeout(function () {

                $scope.data = parseService.getUserData();
                $scope.displayList();
                $scope.displayMap();

                //       $scope.updatePlaceholder();
            }, 1500);


        }

        //   $scope.dataloaded = true;

    }
    $scope.displayList = function () {
        var container = angular.element(document.querySelector('#list'));
        container.html('');
        for (var i = 0; i < $scope.data.length; i++) {
            var items = angular.element("<li>");
            var res = "FirstName: " + $scope.data[i].get("FirstName") + "<br />" + "LastName: " + $scope.data[i].get("LastName") + "<br />" + "Address: " + $scope.data[i].get("Address");
            items.append(res);
            container.append(items);
        }
    }
    $scope.displayMap = function () {
        var myLatLng1 = { lat: 14, lng: 77 };
        var mapOptions = {
            center: new google.maps.LatLng(13, 77),
            zoom: 5,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map"),
              mapOptions
            );
        for (var i = 0; i < $scope.data.length; i++) {
            var lati = $scope.data[i].get("lat");
            var lang = $scope.data[i].get("lang");
            myLatLng1 = { lat: lati, lng: lang };
            var address = $scope.data[i].get("Address").toString();
            var marker = new google.maps.Marker({ position: myLatLng1, map: map, title: address });
        }

    }

    $scope.updatePlaceholder = function () {

        var container = document.getElementById("searchString");
        container.value = $scope.name;

    }
} ]);
                                         //end searchController