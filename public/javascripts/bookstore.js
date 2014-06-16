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
            var responsePromise = $http.get("/loadProducts/"+count);
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
            	console.log("Error: There are some errors occurred during the fetching products from the database");
            });
        };

        // Number of rating 
        $scope.getRating = function(rating){
        	return new Array(parseInt(rating));
        }

        // Search
        $scope.searchProducts = function(name){
            var responsePromise = $http.get("/searchProducts?name="+name);
            responsePromise.success(function(data, status, header, config){
                $scope.products = data;
            });
            responsePromise.error(function(data, status, header, config){
                $scope.products = [];
                console.log("Error: No data found")
            });
        }

        // Edit Book
        $scope.editBook = function($event){
            var bookHref = $event.currentTarget.attributes[1].nodeValue;
            window.location.href = bookHref;
        }

        // Load address of a current user
        $scope.loadAddresses = function(){
            var responsePromise = $http.get("/loadAddresses");
            responsePromise.success(function(data, status, header, config){
                console.dir(data);
                $scope.addresses = data;
            });
            responsePromise.error(function(data, status, header, config){
                $scope.addresses = [];                
                console.log("Error: No address found");
            });
        }

        // Add a new address
        $scope.upsertAddress = function(){
            console.log($scope.addressIndex);
            // Add
            if($scope.addressIndex == null){
                $scope.addresses.push($scope.editAddress);
                // Add to MongoDB
                var responsePromise = $http.post("/addAddress", angular.toJson($scope.editAddress));
            }
            // Edit
            else{
               $scope.addresses[$scope.addressIndex] = $scope.editAddress;
               $scope.addressIndex = null;
            }
            // Clear the address field
            $scope.editAddress = null;
        }

        // Fill the form when click on the edit button
        $scope.editAddressForm = function(index){
            $scope.editAddress = angular.copy($scope.addresses[index]);
            $scope.addressIndex = index;
        }

        // Remove an address
        $scope.removeAddress = function(index){
            // Remove in MongoDB
            var responsePromise = $http.post("/removeAddress", angular.toJson($scope.addresses[index]))
            $scope.addresses.splice(index,1);
        }
});
