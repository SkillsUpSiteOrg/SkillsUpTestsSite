/*
"use strict";
*/

/**
 * @ngdoc overview
 * @name SkillsUpTests
 * @description
 * # SkillsUpTests
 *
 * Main module of the application.
 */
angular
  .module('SkillsUpTests', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.sortable',
	'LocalStorageModule'
  ])
  .config(['localStorageServiceProvider', function(localStorageServiceProvider){
    localStorageServiceProvider.setPrefix('ls');
  }])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'pages/main.html',
        controller: 'MainCtrl'
      })
      .when('/creator', {
        templateUrl: 'pages/creator.html',
        controller: 'CreatorCtrl'
      })
        .when('/main', {
            templateUrl: 'pages/main.html',
            controller: 'MainCtrl'
        })
      .otherwise({
        redirectTo: '/'
      });
  });