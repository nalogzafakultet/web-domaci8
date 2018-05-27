let app = angular.module("tweets", ["ngRoute"]);
const get_tweets_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets";
const register_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/users";
const login_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/users/login";
const add_tweet_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets";
const search_tweet_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets/search";
const page_tweets_url = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets/page/";

const ROOT_HREF = "/Web_Domaci7_war_exploded/#/";

let ListController = function(AuthService, $scope, TweetService) {

	if (!AuthService.isAuthorized()) {
		AuthService.redirectToLogin();
	}

	$scope.tweets = [];
	TweetService.getTweets().then(function(response) {
		console.log(response.data);
		$scope.tweets = response.data;
	});
};

app.run(function ($rootScope, $window) {
	$rootScope.user = null;
});

let MainController = function (AuthService, $scope) {
	$scope.loggedIn = false;
	$scope.loggedIn = AuthService.isAuthorized();
	$scope.username = AuthService.getUsername();
};

let RegisterController = function($window, $rootScope, $scope, AccountService) {
	$scope.user = {
		username: '',
		password: ''
	};

	$scope.successText = "";

	$scope.register = function() {
        AccountService.register($scope.user).then((response) => {
            console.log('Response: ' + response.data);
            if (response.data == true) {
                $scope.successText = "Successfully Registred!";
                $rootScope.user = angular.copy($scope.user);
                $window.location.href = ROOT_HREF + 'list';
                return;
            } else {
                $scope.successText = "User already exists!";
                $scope.user.username = "";
                $scope.user.password = "";
                return;
            }
        });
    };
};

let LoginController = function($window, $rootScope, $scope, AccountService) {
	$scope.user = {
		username: '',
		password: ''
	};

	$scope.successText = '';

	$scope.login = function() {
        AccountService.login($scope.user).then((response) => {
            if (response.data == true) {
                $scope.successText = "Successfully logged in!";
                $rootScope.user = angular.copy($scope.user);
                $window.location.href = ROOT_HREF + "list"
                return;
            } else {
                $scope.successText = "Wrong username/password!";
                // Clear user data
                $scope.user.username = "";
                $scope.user.password = "";
                return;
            }
        });
    }
};

let NewTweetController = function (AuthService, $scope, TweetService) {

	if (!AuthService.isAuthorized()) {
		AuthService.redirectToLogin();
	}

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

let PageController = function (AuthService, $scope, TweetService) {

	if (!AuthService.isAuthorized()) {
		AuthService.redirectToLogin();
	}

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

let AccountService = function($http) {
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

let TweetService = function($http) {
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

let AuthService = function ($rootScope, $window) {
	service = {
		isAuthorized: function() {
			if ($rootScope.user == null ||
				$rootScope.user.username == '') {
				return false;
			} else {
				return true;
			}
        },

		getUsername: function () {
			return $rootScope.user.username;
        },

		redirectToLogin: function() {
			$window.location.href = ROOT_HREF + 'login';
		}
	};
	return service;
};

let routes = function($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'partials/main.html',
		controller: 'MainController'
    }).when('/list', {
        templateUrl: 'partials/list_all.html',
		controller: 'ListController'
    }).when('/login', {
        templateUrl: 'partials/login.html',
		controller: 'LoginController'
    }).when('/add', {
    	templateUrl: 'partials/new_tweet.html',
		controller: 'NewTweetController'
	}).when('/pages', {
		templateUrl: 'partials/pages.html',
		controller: 'PageController'
	}).when('/register', {
		templateUrl: 'partials/register.html',
		controller: 'RegisterController'
	}).otherwise({
        templateUrl: 'partials/main.html',
		controller: 'MainController'
    });
};

app.factory('AccountService', AccountService);
app.factory('TweetService', TweetService);
app.factory('AuthService', AuthService);
app.controller('MainController', MainController);
app.controller('ListController', ListController);
app.controller('LoginController', LoginController);
app.controller('RegisterController', RegisterController);
app.controller('NewTweetController', NewTweetController);
app.controller('PageController', PageController);

app.config(routes);