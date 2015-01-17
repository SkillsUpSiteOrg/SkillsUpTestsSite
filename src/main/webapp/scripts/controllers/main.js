/*
"use strict";
*/

angular.module('SkillsUpTests')
  .controller('MainCtrl',function ($scope, localStorageService, $http, $location) {
    var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));

    $scope.getAllTests = function () {
        $http.get(host+'getAllTestDescriptions').success(function(data) {
            console.log(data);
            $scope.tests = data;
            localStorageService.set('tests', $scope.tests);

        })
    };

    $scope.setSelected = function(index) {
        $scope.selected = this.test;
        $scope.index = index;
        console.log($scope.selected);
    };

    $scope.addNewTest = function() {
        console.log($scope.testName);
        console.log($scope.maxTimeToPassInMinutes);
        $http({
            method: 'POST',
            url: host+'addNewTestDescription',
            data: $.param({"testName":$scope.testName, "maxTimeToPassInMinutes":$scope.maxTimeToPassInMinutes}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
            .success(function(data) {
            $scope.message = data;
                console.log($scope.message);
        });
        $scope.testName = '';
        $scope.maxTimeToPassInMinutes = '';
    };

    $scope.editSelectedTest = function(){
        window.location = 'pages/editTest.html';
    };

  });