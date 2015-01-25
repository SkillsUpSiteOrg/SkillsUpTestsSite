
angular.module('SkillsUpTests')
    .controller('TestEditorCtrl',function ($rootScope, $scope, localStorageService, $http, $location) {
        var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
        $scope.selectedTest = $rootScope.testForEdit;
        $scope.oldTestName = $rootScope.testForEdit.testName;
        $scope.oldMaxTimeToPassInMinutes = $rootScope.testForEdit.maxTimeToPassInMinutes;
        $http({
            method: 'POST',
            url: host+'getQuestionAnswersOfTest',
            data: $.param({"testName":$scope.selectedTest.testName,
                "maxTimeToPassInMinutes":$scope.selectedTest.maxTimeToPassInMinutes}),
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
                data: $.param({"question":$scope.selectedExistingQuestion.question}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            });
        };

        $scope.editTest = function () {
            //console.log($scope.selected);
            console.log($scope.selectedTest.testName);
            console.log($scope.selectedTest.maxTimeToPassInMinutes);
            $http({
                method: 'POST',
                url: host+'editTestDescription',
                data: $.param({"testName":$scope.selectedTest.testName,
                    "maxTimeToPassInMinutes":$scope.selectedTest.maxTimeToPassInMinutes,
                    "oldTestName":$scope.oldTestName,
                    "oldMaxTimeToPassInMinutes":$scope.oldMaxTimeToPassInMinutes}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            })
                .success(function(data) {
                    $scope.message = data;
                    console.log($scope.message);
                });
        };

        $scope.newQuestion = {text: '', answers: []};
        $scope.addNewAnswer = function() {
            var newAnswerNo = $scope.newQuestion.answers.length + 1;
            $scope.newQuestion.answers.push({number: newAnswerNo, answerText: '', answerCorrect: ''});
        };

        $scope.addNewQuestionToTest = function(){
            console.log($scope.newQuestion);
            console.log($scope.newQuestion.answers);
            $http({
                method: 'POST',
                url: host+'addNewQuestionToTest',
                data: $.param({"testName":$scope.selectedTest.testName,
                    "question":$scope.newQuestion.text,
                    "answers":$scope.newQuestion.answers}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            })
                .success(function(data) {
                    $scope.message = data;
                    console.log($scope.message);
                });
        };

        $scope.addExistingQuestionsToTest = function(){
            console.log($scope.selectedAllQuestions);
            $http({
                method: 'POST',
                url: host+'addExistingQuestionsToTest',
                data: $.param({"testName":$scope.selectedTest.testName,
                    "questions":$scope.selectedAllQuestions}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            })
                .success(function(data) {
                    $scope.message = data;
                    console.log($scope.message);
                });
        }

    });