angular.module('SkillsUpTests')
    .controller('PassTestCtrl',function ($rootScope, $scope, localStorageService, $http, $location) {
        var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
        $scope.selectedTest = $rootScope.testForEdit;
        /*$http({
            method: 'POST',
            url: host+'getQuestionAnswersOfTest',
            data: $.param({"testName":$scope.selectedTest.testName,
                "maxTimeToPassInMinutes":$scope.selectedTest.maxTimeToPassInMinutes}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
            .success(function(data) {
                $scope.questionsOfTest = data;
                console.log($scope.questionsOfTest);
            });*/
        $scope.questionsOfTest = [{question: '', answers: [{answer: '', correct:''}]}];
        $scope.questionsOfTest[0].answers.push({answer: 'ans1', correct:''});
        $scope.questionsOfTest[0].answers.push({answer: 'ans2', correct:''});
        $scope.questionsOfTest[0].answers.push({answer: 'ans3', correct:''});
        $scope.questionsOfTest[0].question = "qeustion1???";
        console.log($scope.questionsOfTest);
        $scope.userAnswers=[];

        $scope.saveAnswer = function(){
            $scope.userAnswers.push({answer: '', correct: ''});
        }

    });