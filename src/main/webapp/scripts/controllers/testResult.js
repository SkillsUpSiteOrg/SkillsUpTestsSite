angular.module('SkillsUpTests')
    .controller('TestResultCtrl',function ($rootScope, $scope, localStorageService, $http, $location, $timeout, $resource) {
        var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
        $scope.testResult = $rootScope.resultOfTest;
        $scope.passedTest = $rootScope.testForEdit.testName;
    })