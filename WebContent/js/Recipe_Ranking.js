function get_parameter_by_name(name, url) {
	if (!url) url = window.location.href;
	name = name.replace(/[\[\]]/g, "\\$&");
	let regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
	results = regex.exec(url);
	if (!results){
		return null;
	}
	if (!results[2]) {
		return "";
	}
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}

 /*Search?sw=&w=&k=&s=&min-t=&max-t=&level=&order_by=*/

function get_new_url(url, key, new_value) {
	let re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
	let separator = url.indexOf('?') !== -1 ? "&" : "?";

	if (url.match(re)) {
		return url.replace(re, '$1' + key + "=" + new_value + '$2');
	} else {
		return url + separator + key + "=" + new_value;
	}
}
$(document).ready(function(){
	// 현재 페이지의 URL 가져오기
	let current_URL = window.location.href;
	
	// URL에서 파라미터 가져오기
	let url_params = new URLSearchParams(current_URL);
	// 특정 파라미터 값 가져오기
	let rtype = url_params.get("rtype");
	let ptype = url_params.get("ptype");
	
	if(ptype == "d"){
		$('li[ptype="d"]').siblings("li.active").removeClass("active").addClass("non-active");
		$('li[ptype="d"]').removeClass("non-active").addClass("active");
	}
	if(ptype == "w"){
		$('li[ptype="w"]').siblings("li.active").removeClass("active").addClass("non-active");
		$('li[ptype="w"]').removeClass("non-active").addClass("active");
	}
	if(ptype == "m"){
		$('li[ptype="m"]').siblings("li.active").removeClass("active").addClass("non-active");
		$('li[ptype="m"]').removeClass("non-active").addClass("active");
	}
	
	if(rtype == "r"){
		$('li[rtype="r"]').siblings("li.active").removeClass("active").addClass("non-active");
		$('li[rtype="r"]').removeClass("non-active").addClass("active");
		$('.rank_recipe').show();
		$('.ranking_search_word').hide();
		$('.row').hide();
		$('.ranker_user_list').hide();
	}
	if(rtype == "c"){
		$('li[rtype="c"]').siblings("li.active").removeClass("active").addClass("non-active");
		$('li[rtype="c"]').removeClass("non-active").addClass("active");
		$('.row').show();
		$('.ranker_user_list').show();
		$('.rank_recipe').hide();
		$('.ranking_search_word').hide();
	}
	if(rtype == "k"){
		$('li[rtype="k"]').siblings("li.active").removeClass("active").addClass("non-active");
		$('li[rtype="k"]').removeClass("non-active").addClass("active");
		$('.ranking_search_word').show();
		$('.row').hide();
		$('.rank_recipe').hide();
		$('.ranker_user_list').hide();
	}
	
	
	$(".rtype").click(function() {
		let new_rtype = $(this).attr("rtype");
		let current_URL = window.location.href;
		
		location.href = get_new_url(current_URL, "rtype", new_rtype);
		location.href = url.toString();
	});
	
	$(".ptype").click(function() {
		let new_ptype = $(this).attr("ptype");
		let current_URL = window.location.href;
		
		location.href = get_new_url(current_URL, "ptype", new_ptype);
		location.href = url.toString();
	});
});

	