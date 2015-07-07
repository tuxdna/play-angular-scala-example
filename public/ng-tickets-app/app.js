// var apiBaseUrl = "http://localhost:9000/api";

var apiBaseUrl = "https://safe-ocean-7770.herokuapp.com/api";
function HomeController($scope, $http) {

}

function CustomersListController($scope, $http) {
	var listUrl = apiBaseUrl + "/customers";
	$http.get(listUrl).success(function(response) {
		$scope.customers = response;
	});	
}


function CustomersGetController($scope, $http, $routeParams) {
	var customerId = $routeParams.customerId;
	var getUrl = apiBaseUrl + "/customer/" + customerId;
	$scope.customer = {};
	$http.get(getUrl).success(function(response) {
		$scope.customer = response;
    });
}


function CustomersNewController($scope, $http) {
	var addUrl = apiBaseUrl + "/customer/";
	$scope.c = {};
	
	$scope.addCustomer = function() {
		var c = $scope.c;
		var postData = {
			"name" : c.name,
			"email" : c.email,
			"phone" : c.phone
		};

		$http.put(addUrl, postData).success(function(response) {
			$scope.c = {};
		});

	};
}



function TicketsListController($scope, $http) {
	var listUrl = apiBaseUrl + "/tickets";
	$http.get(listUrl).success(function(response) {
		$scope.tickets = response;
	});	
}


function TicketsNewController($scope, $http) {
	var addUrl = apiBaseUrl + "/ticket/";
	
	$scope.t = {};
	
	$scope.addTicket = function() {
		var postData = {
			"title" : $scope.t.title,
			"area" : $scope.t.area,
			"customerId" : $scope.t.customerId
		};

		$http.put(addUrl, postData).success(function(response) {
			$scope.t = {};
		});

	};

}

function TicketsGetController($scope, $http, $routeParams, $route) {
	var ticketId = $routeParams.ticketId;
	var getUrl = apiBaseUrl + "/ticket/" + ticketId;
	$scope.ticket = {};
	$http.get(getUrl).success(function(response) {
		$scope.ticket = response;
		
		var customerId = response.customerId;
		var customerUrl = apiBaseUrl + "/customer/" + customerId;
		$http.get(customerUrl).success(function(response) {
			$scope.ticket.customer = response;
		});
		
		var csrId = response.assignedTo;
		if (csrId) {
			var csrUrl = apiBaseUrl + "/csr/" + csrId;
			$http.get(csrUrl).success(function(response) {
				$scope.ticket.csr = response;
			});
		}
						
		var commentsUrl = apiBaseUrl + "/ticket/"+ticketId+"/comments/";
		$http.get(commentsUrl).success(function(response) {
			$scope.ticket.comments = response;
		});
	});
	
	$scope.csrs = [];
	var getCsrsUrl = apiBaseUrl + "/csrs";
	$http.get(getCsrsUrl).success(function(response) {
		$scope.csrs = response;
	});
	
	$scope.commentText = "";
	
	$scope.addComment = function() {
		
		var commentText = $scope.commentText;
		if (commentText) {
			var ticket = $scope.ticket;
			var postData = {
				"ticketId" : ticket.id,
				"text" : commentText,
				"csrId" : ticket.assignedTo
			};

			var commentUrl = apiBaseUrl + "/ticket/" + ticketId + "/comment/";

			$http.post(commentUrl, postData).success(function(response) {
				$route.reload();
			});
		} else {
			alert("No text in comment box");
		}

	};

	$scope.assignTicket = function() {
		var selectedCsrId = $scope.selectedCsrId;
		if (selectedCsrId) {
			var ticket = $scope.ticket;
			var ticketId = ticket.id;

			var assignUrl = apiBaseUrl + "/ticket/" + ticketId + "/assignTo/"
					+ selectedCsrId;
			$http.post(assignUrl, {}).success(function(response) {
				$route.reload();
			});
		} else {
			alert("No assignee selected");
		}
	};


	$scope.changeStatus = function() {
		var selectedChangeStatus = $scope.selectedChangeStatus;
		if (selectedChangeStatus) {
			var ticket = $scope.ticket;
			var ticketId = ticket.id;

			var assignUrl = apiBaseUrl + "/ticket/" + ticketId + "/status/"
					+ selectedChangeStatus;
			$http.post(assignUrl, {}).success(function(response) {
				$route.reload();
			});
		} else {
			alert("No status selected");
		}
	};

}

function routeHandler($routeProvider) {
	$routeProvider.when('/', {
		controller : 'HomeController',
		templateUrl : 'partials/home.html'
	}).when('/tickets/list', {
		controller : 'TicketsListController',
		templateUrl : 'partials/tickets/list.html'
	}).when('/tickets/new', {
		controller : 'TicketsNewController',
		templateUrl : 'partials/tickets/new.html'
	}).when('/ticket/:ticketId', {
		controller : 'TicketsGetController',
		templateUrl : 'partials/tickets/details.html'
	}).when('/customers/list', {
		controller : 'CustomersListController',
		templateUrl : 'partials/customers/list.html'
	}).when('/customers/new', {
		controller : 'CustomersNewController',
		templateUrl : 'partials/customers/new.html'
	}).when('/customers/:customerId', {
		controller : 'CustomersGetController',
		templateUrl : 'partials/customers/details.html'
	}).otherwise({
		redirectTo : '/'
	});
}

var myApp = angular.module('ticketsApp', [ 'ngRoute' ])
    // .factory('TicketsFactory', ['$http', TicketsFactory])
    .controller('HomeController', [ '$scope', '$http', HomeController ])
    .controller('TicketsListController', [ '$scope', '$http', TicketsListController ])
    .controller('TicketsNewController', [ '$scope', '$http', '$routeParams', TicketsNewController])
    .controller('TicketsGetController', [ '$scope', '$http', '$routeParams', '$route', TicketsGetController])
    .controller('CustomersListController', [ '$scope', '$http', CustomersListController ])
    .controller('CustomersNewController', [ '$scope', '$http', '$routeParams', CustomersNewController])
    .controller('CustomersGetController', [ '$scope', '$http', '$routeParams', CustomersGetController])
    .config(routeHandler);

