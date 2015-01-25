angular.module('SkillsUpTests')
    .controller('PassTestCtrl',function ($rootScope, $scope, localStorageService, $http, $location) {
        var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
        $scope.selectedTest = $rootScope.testForEdit;
        $scope.oldTestName = $rootScope.testForEdit.testName;
        $scope.oldMaxTimeToPassInMinutes = $rootScope.testForEdit.maxTimeToPassInMinutes;



    });