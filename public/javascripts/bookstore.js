// Ellipsis in words
$(".bs-navbar-wishlist-body a span").html(function(index,currentText){
	var currentTextArr = currentText.split(' ');
	var numWords = currentTextArr.length;
	var result = "";
	for(var i=0;i<numWords;i++){
		// Word length + space should less than 65 characters
		if(result.length+i < 75){
			result += currentTextArr[i] + " ";
		}
	}
	return result.trim()+"...";
});


// AngularJS
var app = angular.module("BookStore",["infinite-scroll"]);
//app.directive("scroll",function($window){
//    return function(scope, element, attrs){
//        angular.element($window).bind("scroll", function(){
//            if($(window).scrollTop() > ($(document).height() - $(window).height())*0.7){
//                $("#ajaxloader").show();
//            }
//            else{
//                $("#ajaxloader").hide();
//            }
//            scope.$apply();
//        })
//    };
//});
app.controller("BookStoreController",function($scope, $http){
        var busy = false;
        var count = 0;

        // Load more books from DB
        $scope.loadBooks = function(){
            if(busy)return;
            busy = true;
            $("#ajaxloader").show();
            var responsePromise = $http.get("/loadBooks/"+count);
            responsePromise.success(function(data, status, header, config){
            	if(typeof $scope.products == 'undefined'){
            		$scope.products = data;
            	}
            	else{
            		$scope.products = $scope.products.concat(data);
            	}
                $("#ajaxloader").hide();
                console.log("Books are fetched out!");
                busy = false;
            });
            responsePromise.error(function(data, status, header, config){
            	console.log("There are some errors occured during the fetching products from the database");
            });
        };

        // Number of rating 
        $scope.getRating = function(rating){
        	return new Array(parseInt(rating));
        }
});
