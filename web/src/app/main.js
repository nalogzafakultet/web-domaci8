var app = angular.module("tweets", ["ngRoute"]);
const get_tweets_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets";
const register_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/users";
const login_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/users/login";
const add_tweet_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets";
const search_tweet_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets/search";
const page_tweets_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets/page/";

var ListController = function($scope, TweetService) {
	$scope.tweets = [];
	TweetService.getTweets().then(function(response) {
		console.log(response.data);
		$scope.tweets = response.data;
	});
};

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
};

var LoginController = function($scope, AccountService) {
	$scope.user = {
		username: '',
		password: ''
	};

	$scope.successText = '';

	$scope.login = AccountService.login($scope.user).then((response) => {
		console.log('Response: ' + response.data);
		if (response.data == false) {
			$scope.successText = "Successfully logged in!";
			return;
		} else {
			$scope.successText = "User already exists!";
			return;
		}
	});
};

var NewTweetController = function ($scope, TweetService) {
	$scope.tweet = {
		username: '',
		messageBody: ''
	};
	$scope.successText = "";

	$scope.sendTweet = function () {
		TweetService.addTweet($scope.tweet).then(function (response) {
			if (response.data == true) {
				console.log(response.data);
				$scope.successText = "Your tweet has been sent to DB.";
			} else {
				$scope.successText = "Tweet failed to send."
			}
        })
    }

};

var PageController = function ($scope, TweetService) {
	$scope.tweets = [];
	$scope.page_number = 0;
	$scope.searchField = "";

	$scope.getPage = function() {
        TweetService.getNthPage($scope.page_number).then((response) => {
            $scope.tweets = response.data;
        });
	};

    $scope.getNextPage = function () {
		$scope.page_number++;
		$scope.getPage();
    };

    $scope.getPrevPage = function () {
    	$scope.page_number--;
    	$scope.getPage();
	};

    $scope.getNextPage();
};

var AccountService = function($http) {
	service = {
		register: function (user) {
			return $http.post(register_url, user);
        },
		login: function (user) {
			return $http.post(login_url, user);
        }
	};
	return service;
}

var TweetService = function($http) {
	service = {
		getTweets: function() {
			return $http.get(get_tweets_url);
        },
		addTweet: function(tweet) {
			return $http.post(add_tweet_url, tweet);
		},
		searchTweetByUser: function(username_q) {
			return $http.get(search_tweet_url, {
                params: {
                    username: username_q
                }
            });
		},
        getNthPage: function (page_number) {
			return $http.get(page_tweets_url + page_number);
        }
	};
	return service;
};

var routes = function($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'partials/main.html'
    }).when('/list', {
        templateUrl: 'partials/list_all.html'
    }).when('/login', {
        templateUrl: 'partials/login.html'
    }).when('/add', {
    	templateUrl: 'partials/new_tweet.html'
	}).when('/pages', {
		templateUrl: 'partials/pages.html'
	}).otherwise({
        templateUrl: 'partials/main.html'
    });
};

app.factory('AccountService', AccountService);
app.factory('TweetService', TweetService);
app.controller('ListController', ListController);
app.controller('RegisterController', RegisterController);
app.controller('NewTweetController', NewTweetController);
app.controller('PageController', PageController);

app.config(routes);