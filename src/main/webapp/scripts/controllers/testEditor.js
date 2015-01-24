
angular.module('SkillsUpTests')
    .controller('TestEditorCtrl',function ($rootScope, $scope, localStorageService, $http, $location) {
        var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
        //console.log($rootScope.testForEdit);
        $scope.selectedTest = $rootScope.testForEdit;
        //console.log($scope.selectedTest);
        $http({
            method: 'POST',
            url: host+'getQuestionAnswersOfTest',
            data: $.param({"testDescriptionId":$scope.selectedTest.testDescriptionId}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
            .success(function(data) {
                $scope.questionsOfTest = data;
                console.log($scope.questionsOfTest);
            });
        $http({
            method: 'GET',
            url: host+'getAllQuestionAnswers',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
            .success(function(data) {
                $scope.allQuestions = data;
                console.log($scope.allQuestions);
            });

        $scope.setSelectedExistingQuestion = function(indexOfSelectedExistingQuestion) {
            $scope.selectedExistingQuestion = this.question;
            $scope.indexOfSelectedExistingQuestion = indexOfSelectedExistingQuestion;
            console.log($scope.selectedExistingQuestion);
            //console.log($scope.index);
        };

        $scope.removeSelectedExistinqQuestion = function(){
            $http({
                method: 'POST',
                url: host+'deleteQuestionAnswersFromTest',
                data: $.param({"questionAnswerId":$scope.selectedExistingQuestion.questionAnswerId}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            });
        };

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

        $scope.newQuestion = {text: '', answers: []};
        $scope.addNewAnswer = function() {
            var newAnswerNo = $scope.newQuestion.answers.length + 1;
            $scope.newQuestion.answers.push({number: newAnswerNo, answerText: '', answerCorrect: ''});
        };

        $scope.addNewQuestionToTest = function(){
            console.log($scope.newQuestion);
            console.log($scope.newQuestion.answers);
        };

        $scope.addQuestionToTest = function(){
            console.log($scope.selectedAllQuestions);
        }

    });