/*
"use strict";
*/

angular.module('SkillsUpTests')
  .controller('MainCtrl',function ($rootScope, $scope, localStorageService, $http, $location) {
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
        $rootScope.testForEdit = $scope.selected;
        $scope.index = index;
        console.log($scope.selected);
        //console.log($scope.index);
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
        //console.log($scope.selected);
        $location.path('testEditor');
    };

    $scope.passSelectedTest = function(){
        //console.log($scope.selected);
        $location.path('passTest');
    };

    $scope.removeSelectedTest = function(){
        $http({
            method: 'POST',
            url: host+'removeSelectedTest',
            data: $.param({"testName":$scope.selected.testName}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
            .success(function(data) {
                $scope.message = data;
                console.log($scope.message);
            });
    }

  });
