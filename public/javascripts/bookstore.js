// Ellipsis function
var ellipsis = function(max,currentText){
   var currentTextArr = currentText.split(' ');
   var numWords = currentTextArr.length;
   var result = "";
   for(var i=0;i<numWords;i++){
      // Word length + space should less than 65 characters
      if(result.length+i < max){
         result += currentTextArr[i] + " ";
      }
   }
   return result.trim()+"...";
 }

// Ellipsis in words
$(".bs-navbar-wishlist-body a span").html(function(index, currentText){
    return ellipsis(75, currentText);
});

// initialize the bootstrap star rating
$("#reviewStar").rating();

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
            if(typeof rating != 'undefined')
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
        $scope.editBook = function(event){
            var bookHref = event.currentTarget.attributes["data-redirect"].nodeValue;
            window.location.href = bookHref;
        }

        // Load address of a current user
        $scope.loadAddresses = function(){
            var responsePromise = $http.get("/loadAddresses");
            responsePromise.success(function(data, status, header, config){
                $scope.addresses = data;
            });
            responsePromise.error(function(data, status, header, config){
                $scope.addresses = [];                
                console.log("Error: No address found");
            });
        }

        // Add a new address
        $scope.upsertAddress = function(){
            // All the field are required
            if($scope.editAddress["street"] == null ||
                $scope.editAddress["city"] == null ||
                $scope.editAddress["country"] == null ||
                $scope.editAddress["zipcode"] == null){
                return;
            }
            // Add
            if($scope.addressIndex == null){
                $scope.addresses.push($scope.editAddress);
                // Add to MongoDB
                var responsePromise = $http.post("/addAddress", angular.toJson($scope.editAddress));
            }
            // Edit
            else{
                var address = angular.copy($scope.addresses[$scope.addressIndex]);
                // Combine objects into one
                for(var attributeName in $scope.editAddress){
                    address["new"+attributeName] = $scope.editAddress[attributeName];
                }
                var responsePromise = $http.post("/editAddress", address);
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

        // Load account of a current user
        $scope.loadAccounts = function(){
            var responsePromise = $http.get("/loadAccounts");
            responsePromise.success(function(data, status, header, config){
                $scope.accounts = data;
            });
            responsePromise.error(function(data, status, header, config){
                $scope.accounts = [];
                console.log("Error: No address found");
            });
        }

        // Add a new account
        $scope.upsertAccount = function(){
            // All the field are required
            if($scope.editAccount["accountId"] == null ||
                $scope.editAccount["type"] == null ||
                $scope.editAccount["balance"] == null){
                return;
            }
            // Add
            if($scope.accountIndex == null){
                $scope.accounts.push($scope.editAccount);
                // Add to MongoDB
                var responsePromise = $http.post("/addAccount", angular.toJson($scope.editAccount));
            }
            // Edit
            else{
                var account = angular.copy($scope.accounts[$scope.accountIndex]);
                // Combine objects into one
                for(var attributeName in $scope.editAccount){
                    account["new"+attributeName] = $scope.editAccount[attributeName];
                }
                var responsePromise = $http.post("/editAccount", account);
                $scope.accounts[$scope.accountIndex] = $scope.editAccount;
                $scope.accountIndex = null;
            }
            // Clear the address field
            $scope.editAccount = null;
        }

        // Fill the form when click on the edit button
        $scope.editAccountForm = function(index){
            $scope.editAccount = angular.copy($scope.accounts[index]);
            $scope.accountIndex = index;
        }

        // Remove an account
        $scope.removeAccount = function(index){
            // Remove in MongoDB
            var responsePromise = $http.post("/removeAccount", angular.toJson($scope.accounts[index]))
            $scope.accounts.splice(index,1);
        }

        // Add review
        $scope.reviews = [];
        $scope.addReview = function(){
            // If user doesn't enter either the description field or all the input fields
            if(typeof $scope.reviewField != 'undefined' && typeof $scope.reviewField.description != 'undefined'){
                var productId = document.getElementById('productId').getAttribute('data-productId');
                $scope.reviewField.productId = productId;
                var responsePromise = $http.post("/addReview", angular.toJson($scope.reviewField));
                responsePromise.success(function(data, status, header, config){
                    // In case that the user didn't login
                    if(typeof data.user == 'undefined'){
                         $scope.isLogin = false;
                    }
                    else{
                        // Recalculate rating
                        var newData = [];
                        newData.push(data);
                        $scope.calculateRating(newData);
                        // Add the new data into array
                        $scope.review = data;
                        $scope.review.title = ellipsis(30, $scope.review.description);
                        $scope.reviews.push($scope.review);

                    }
                });
            }
        }

        // Load comments of the current book
        $scope.loadComments = function(){
            var product = {};
            product.id = document.getElementById('productId').getAttribute('data-productId');
            var responsePromise = $http.post("/loadComments", angular.toJson(product));
            responsePromise.success(function(data, status, header, config){
                $scope.rating = {one: 0, two: 0, three: 0, four: 0, five: 0, all: 0};
                $scope.reviews = data;
                for(var i = 0; i< $scope.reviews.length; i++){
                    $scope.reviews[i].title = ellipsis(30,  $scope.reviews[i].description);
                }
                $scope.calculateRating($scope.reviews);
            });
            responsePromise.error(function(){
                console.log("Error: No comment found");
            });


        }

        $scope.calculateRating = function(updatedRating){
                for(var i=0; i<updatedRating.length;i++){
                    // Set up rating object
                    switch(updatedRating[i].rating){
                        case 1:
                            $scope.rating.one += 1;
                            break;
                        case 2:
                            $scope.rating.two += 1;
                            break;
                        case 3:
                            $scope.rating.three += 1;
                            break;
                        case 4:
                            $scope.rating.four += 1;
                            break;
                        case 5:
                            $scope.rating.five += 1;
                            break;
                    }
                    $scope.rating.all += 1;
                }
                // Calculate percentage for the rating bars
                $scope.rating.onePc =  $scope.rating.one * 100 /  $scope.rating.all;
                $scope.rating.twoPc =  $scope.rating.two * 100 /  $scope.rating.all;
                $scope.rating.threePc =  $scope.rating.three * 100 /  $scope.rating.all;
                $scope.rating.fourPc =  $scope.rating.four * 100 /  $scope.rating.all;
                $scope.rating.fivePc =  $scope.rating.five * 100 /  $scope.rating.all;
                // Calculate rating average
                $scope.rating.average = Math.round(
                    ($scope.rating.one +
                     $scope.rating.two * 2 +
                     $scope.rating.three * 3 +
                     $scope.rating.four * 4 +
                     $scope.rating.five * 5) / $scope.rating.all
                );
        }
});
