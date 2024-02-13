$(function() {
	$(".search_word_object > li.non-active.rtype").click(function() {
		console.log("Selected element: ", this);
		$(this).siblings("li.active").removeClass("active").addClass("non-active");
		$(this).removeClass("non-active").addClass("active");;
    });
	$(".peroiod > li.non-active.ptype").click(function() {
		console.log("Selected element: ", this);
		$(this).siblings("li.active").removeClass("active").addClass("non-active");
		$(this).removeClass("non-active").addClass("active");;
    });
		// 현재 페이지의 URL 가져오기
	var currentURL = window.location.href;
	
	// URL에서 파라미터 가져오기
	var urlParams = new URLSearchParams(currentURL);
	
	// 특정 파라미터 값 가져오기
	var rtype = urlParams.get('rtype');
	var ptype = urlParams.get('ptype');
	$(".search_word_object.find()").click(function() {
		if(ptype === "d"){
			
		}
	});
});

	