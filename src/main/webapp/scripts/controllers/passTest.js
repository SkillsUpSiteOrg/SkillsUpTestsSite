angular.module('SkillsUpTests')
    .controller('PassTestCtrl',function ($rootScope, $scope, localStorageService, $http, $location, $timeout, $resource) {
        var host = $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
        $scope.selectedTest = $rootScope.testForEdit;
        $http({
            method: 'POST',
            url: host+'getQuestionAnswersOfTest',
            data: $.param({"testName":$scope.selectedTest.testName,
                "maxTimeToPassInMinutes":$scope.selectedTest.maxTimeToPassInMinutes}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data) {
                $scope.questionsOfTest = data;
                console.log($scope.questionsOfTest);
                $scope.initUserAnswers();
            });

        $scope.initUserAnswers = function(){
            $scope.userAnswers=[];
            angular.forEach($scope.questionsOfTest, function(questionValue, questionKey){
                    var variantsOfAnswer = [];
                    angular.forEach(questionValue.answersText, function(answerValue, answerKey){
                        variantsOfAnswer.push({variantOfAnswer: answerValue, correct: false});
                    })
                    $scope.userAnswers.push({question: questionValue.question, variantsOfAnswer: variantsOfAnswer});
                }

            );
            console.log($scope.userAnswers);
        }

        $scope.counter = $scope.selectedTest.maxTimeToPassInMinutes;
        $scope.onTimeout = function(){
            $scope.counter--;
            mytimeout = $timeout($scope.onTimeout,60000);
        }
        var mytimeout = $timeout($scope.onTimeout,60000);

        $scope.stop = function(){
            $timeout.cancel(mytimeout);
        }

        $scope.finishTest = function(){
            console.log($scope.userAnswers);
            /*var testResult = $resource(host+'getResultOfPassedTest', {}, {saveData: {method:'POST', isArray: true}});
            $scope.doSubmit = function() {
                testResult.saveData($scope.userAnswers);
            }*/
            $http({
                method: 'POST',
                url: host+'getResultOfPassedTest',
                data: $.param({
                    "userAnswers": JSON.stringify($scope.userAnswers),
                    "testName": $scope.selectedTest.testName,
                    "login": $scope.userLogin,
                    "password": $scope.userPassword
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            })
        }

    });