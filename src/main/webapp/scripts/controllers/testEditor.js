
angular.module('SkillsUpTests')
    .controller('TestEditorCtrl',function ($rootScope, $scope, localStorageService, $http, $location) {
        var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
        //console.log($rootScope.testForEdit);
        $scope.selectedTest = $rootScope.testForEdit;
        //console.log($scope.selectedTest);

        $scope.editTest = function () {
            //console.log($scope.selected);
            console.log($scope.selectedTest.testName);
            console.log($scope.selectedTest.maxTimeToPassInMinutes);
            /*$http({
                method: 'POST',
                url: host+'editTestDescription',
                data: $.param({"testName":$scope.testName, "maxTimeToPassInMinutes":$scope.maxTimeToPassInMinutes}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            })
                .success(function(data) {
                    $scope.message = data;
                    console.log($scope.message);
                });*/
        };
    });