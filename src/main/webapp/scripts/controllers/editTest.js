angular.module('SkillsUpTests')
    .controller('EditTestCtrl',function ($scope, localStorageService, $http, $location) {
        var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
        $scope.editTest = function () {
            console.log($scope.selected);
            $http({
                method: 'POST',
                url: host+'editTestDescription',
                data: $.param({"testName":$scope.testName, "maxTimeToPassInMinutes":$scope.maxTimeToPassInMinutes}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            })
                .success(function(data) {
                    $scope.message = data;
                    console.log($scope.message);
                });
        };
    });