
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
            });
        $http({
            method: 'GET',
            url: host+'getAllQuestionAnswers',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data) {
                $scope.allQuestions = data;
            });

        $scope.setSelectedExistingQuestion = function(index) {
            $scope.selectedExistingQuestion = this.question;
            $scope.index = index;
        };

        $scope.removeSelectedExistinqQuestion = function(index){
            $scope.selectedExistingQuestion = this.question;
            $scope.index = index;
            $scope.questionsOfTest.splice(index, 1);
            $http({
                method: 'POST',
                url: host+'deleteQuestionAnswersFromTest',
                data: $.param({"question":$scope.selectedExistingQuestion.question,
                "testName":$scope.selectedTest.testName}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function(data) {
                $scope.message = data;
            });
            $location.path('/testEditor');
        };

        $scope.editTest = function () {
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
                });
            $location.path('/main');
        };

        $scope.newQuestion = {text: '', answers: []};
        $scope.addNewAnswer = function() {
            var newAnswerNo = $scope.newQuestion.answers.length + 1;
            $scope.newQuestion.answers.push({number: newAnswerNo, answerText: '', answerCorrect: false});
        };

        $scope.addNewQuestionToTest = function(){
            $location.path('testEditor');
            $http({
                method: 'POST',
                url: host+'addNewQuestionAnswers',
                data: $.param({"testName":$scope.selectedTest.testName,
                    "question":$scope.newQuestion.text,
                    "answers": JSON.stringify($scope.newQuestion.answers)}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function(){
                $http({
                    method: 'POST',
                    url: host+'getQuestionAnswersOfTest',
                    data: $.param({"testName":$scope.selectedTest.testName,
                        "maxTimeToPassInMinutes":$scope.selectedTest.maxTimeToPassInMinutes}),
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.questionsOfTest = data;
                    $http({
                        method: 'GET',
                        url: host+'getAllQuestionAnswers',
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).success(function(data) {
                        $scope.allQuestions = data;
                    });
                })
            });
            $scope.newQuestion = {text: '', answers: []};
        };

        $scope.addExistingQuestionsToTest = function(){
            $http({
                method: 'POST',
                url: host+'addRelationForTestAndQuestion',
                data: $.param({"testName":$scope.selectedTest.testName,
                    "questions": JSON.stringify($scope.selectedAllQuestions)}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function(){
                $http({
                    method: 'POST',
                    url: host+'getQuestionAnswersOfTest',
                    data: $.param({"testName":$scope.selectedTest.testName,
                        "maxTimeToPassInMinutes":$scope.selectedTest.maxTimeToPassInMinutes}),
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.questionsOfTest = data;
                });
            })
            //TODO This code left here for descendants to show how stuff shouldn't be done
            /*angular.forEach($scope.selectedAllQuestions, function(questionValue, questionKey){
                    if(questionKey = "question"){
                        console.log(questionValue);
                        $http({
                            method: 'POST',
                            url: host+'addRelationForTestAndQuestion',
                            data: $.param({"testName":$scope.selectedTest.testName,
                                "question":questionValue.question}),
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                        })
                    }
                }
            );*/
        }

    });