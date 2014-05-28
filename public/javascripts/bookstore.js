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


//AngularJS
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
app.controller("BookStoreController",function($scope,$http){
        $scope.loadBooks = function(){
            console.log("Run");
            $("#ajaxloader").show();
        };
});