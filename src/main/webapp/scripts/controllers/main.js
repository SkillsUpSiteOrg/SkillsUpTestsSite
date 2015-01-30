/*
"use strict";
*/

angular.module('SkillsUpTests')
  .controller('MainCtrl',function ($rootScope, $scope, localStorageService, $http, $location) {
    var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
    $http.get(host+'getAllTestDescriptions').success(function(data) {
        $scope.tests = data;
        localStorageService.set('tests', $scope.tests);
    })

    $scope.getAllTests = function () {
        $http.get(host+'getAllTestDescriptions').success(function(data) {
            $scope.tests = data;
            localStorageService.set('tests', $scope.tests);
        })
    };

    $scope.setSelected = function(index) {
        $scope.selected = this.test;
        $rootScope.testForEdit = $scope.selected;
        $scope.index = index;
    };

    $scope.addNewTest = function() {
        $http({
            method: 'POST',
            url: host+'addNewTestDescription',
            data: $.param({"testName":$scope.testName, "maxTimeToPassInMinutes":$scope.maxTimeToPassInMinutes}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data) {
            $scope.tests = data;

        });
        $scope.testName = '';
        $scope.maxTimeToPassInMinutes = '';
    };

    $scope.editSelectedTest = function(){
        $location.path('testEditor');
    };

    $scope.passSelectedTest = function(){
        $location.path('passTest');
    };

    $scope.removeSelectedTest = function(){
        $http({
            method: 'POST',
            url: host+'removeSelectedTest',
            data: $.param({"testName":$scope.selected.testName}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data) {
            $scope.tests = data;
            });
    }

  });
