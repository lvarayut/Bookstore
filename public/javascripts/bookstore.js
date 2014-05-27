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


$(window).scroll(function(){
	if($(window).scrollTop() === $(document).height() - $(window).height()){
		$("#ajaxloader").show();
		//$.ajax({
		// 	url: ""
		// 	sucess: function(html){

		// 	}
		// });
	}
	else{
		$("#ajaxloader").hide();
	}
});
