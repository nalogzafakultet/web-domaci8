var app = angular.module("tweets", ["ngRoute"]);

// app.controller("MainController", function($scope) {
// 	$scope.firstName = "Nemanja";
// 	$scope.lastName = "Sekularac";
// });

const get_tweets_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets";
const register_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/users";
const login_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/users/login";
const add_tweet_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets";
const search_tweet_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets/search";
const page_tweets_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets/page/";

var ListController = function($scope, TweetService) {
	TweetService.getTweets().then(function(response) {
		$scope.tweets = response.data;
	});
}

var RegisterController = function($scope, AccountService) {
	$scope.user = {
		username: '',
		password: ''
	};

	$scope.successText = "";

	$scope.register = AccountService.register($scope.user).then((response) => {
			console.log('Response: ' + response.data);
			if (response.data == false) {
				$scope.successText = "Successfully Registred!";
				return;
			} else {
				$scope.successText = "User already exists!";
				return;
			}
	});

}

var LoginController = function($scope, AccountService) {
	$scope.user = {
		username: '',
		password: ''
	};

	$scope.successText = '';

	$scope.login = AccountService.login($scope.user).then((response) => {
		console.log('Response: ' + response.data);
		if (response.data == false) {
			$scope.successText = "Successfully Registred!";
			return;
		} else {
			$scope.successText = "User already exists!";
			return;
		}
	});
}

var AccountService = function($http) {
	service = {};

	service.register = (user) => $http.post(register_url, user);
	service.login = (user) => $http.post(login_url, user);

	return service;
}

var TweetService = function($http) {
	service = {};

	service.getTweets = () => $http.get(get_tweets_url);
	service.addTweet = (tweet) => $http.post(add_tweet_url, tweet);
	service.searchTweetByUser = (username_q) => $http.get(search_tweet_url, {
		params: {
			username: username_q
		}
	});
	service.getNthPage = (page_number) => $http.get(page_tweets_url + page_number);

	return service;

}
app.factory('AccountService', AccountService);
app.controller('ListController', ListController);
app.controller('RegisterController', RegisterController);

var routes = function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl: 'partials/main.html'
	}).when('/list', {
		templateUrl: 'partials/list_all.html',
		controller: 'ListController'
	}).when('/login', {
		templateUrl: 'partials/login.html'
	}).otherwise({
		templateUrl: 'partials/main.html'
	});
};




app.config(routes);