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
$(function(){
	$(document).on("click", "#show", function() {
	    $("#id_search_category_text").text("카테고리 열기");
		$("#id_search_category_img").attr("src","https://recipe1.ezmember.co.kr/img/icon_arrow9_down.gif");
		$("#search_category").slideUp();
		$(this).attr("id", "hide");
    });
	$(document).on("click", "#hide", function() {
		$("#id_search_category_text").text("카테고리 닫기");
		$("#id_search_category_img").attr("src","https://recipe1.ezmember.co.kr/img/icon_arrow9_up.gif");
		$("#search_category").css("display", "inline-block");
		$("#search_category").slideDown();
		$(this).attr("id", "show");
    });
	let order_by_value = get_parameter_by_name("order_by");
	let what_value = get_parameter_by_name("w");
	let kind_value = get_parameter_by_name("k");
	let situation_value = get_parameter_by_name("s");
	let max_t_value = get_parameter_by_name("max-t");
	let min_t_value = get_parameter_by_name("min-t");
	let level_value = get_parameter_by_name("level");
	/*if(order_by_value==null){
		$("li#order_by_hits").addClass("active");
	}*/
	if(order_by_value==="n"){
		$("li#order_by_name").addClass("active");
	}
	if(order_by_value==="r"){
		$("li#order_by_recent").addClass("active");
	}
	if(order_by_value==null || order_by_value==="h"){
		$("li#order_by_hits").addClass("active");
	}
	//what을 선택 안한 경우 & 전체를 선택한 경우
	
	if(what_value==null||what_value==0){
		let first_what_child = $("div#what :first-child");
		first_what_child.addClass("active");
		//아닐 때
	} else{
		let what_new_child = Number(what_value)+Number(1);
		$("a.whats:nth-child("+what_new_child+")").addClass("active");
	}
	
	if(kind_value==null || kind_value==0){
		let first_kind_child = $("div#kind :first-child");
		first_kind_child.addClass("active");
	} else{
		let kind_new_child = Number(kind_value)+Number(1);
		$("a.kinds:nth-child("+kind_new_child+")").addClass("active");
	}
	
	if(situation_value==null||situation_value==0){
		
		let first_situations_child = $("div#situation :first-child");
		first_situations_child.addClass("active");
	} else{
		let situation_new_child = Number(situation_value)+Number(1);
		$("a.situations:nth-child("+situation_new_child+")").addClass("active");
	}
	
	if(max_t_value!=null){
		let max_t_new_value = Number(max_t_value); 
		$("input#max_time").val(max_t_new_value);
	}
	if(min_t_value!==null){
		let min_t_new_value = Number(min_t_value); 
		$("input#min_time").val(min_t_new_value);
	}
	if(level_value!==null){
		let level_new_value = Number(level_value); 
		$('#level').val(level_new_value);
	}
	
	let now_url = window.location.href;
	$(".whats").click(function() {
		let new_whats_active_children = $(".whats").index(this);
		//:nth-child(6){}
		let ex_whats_active_children = $("#what" > ".active");
		ex_whats_active_children.removeClass("active");
		location.href = get_new_url(now_url, "w",new_whats_active_children);
	});
	$(".kinds").click(function() {
		let new_kinds_active_children = $(".kinds").index(this);
		let ex_kinds_active_children = $("#kind" > ".active");
		ex_kinds_active_children.removeClass("active");
		location.href = get_new_url(now_url, "k",new_kinds_active_children);
	});
	$(".situations").click(function() {
		let new_situations_active_children = $(".situations").index(this);
		let ex_situations_active_children = $("#situation" > ".active");
		ex_situations_active_children.removeClass("active");
		location.href = get_new_url(now_url, "s",new_situations_active_children);
	});
	$("#order_by_name").click(function() {
		location.href = get_new_url(now_url, "order_by","n");
	});
	$("#order_by_recent").click(function() {
		location.href = get_new_url(now_url, "order_by","r");
	});
	$("#order_by_hits").click(function() {
		location.href = get_new_url(now_url, "order_by","h");
	});
	$("#min_time").on("change", function() {
		let new_min_time_value = $("#min_time").val();
		if(new_min_time_value){
			location.href = get_new_url(now_url, "min-t", new_min_time_value);
 		}
    });
	$("#max_time").on("change", function() {
		let new_max_time_value = $("#max_time").val();
		if(new_max_time_value){
			location.href = get_new_url(now_url, "max-t", new_max_time_value);
		} 
    });
	$("#level").on("change", function() {
		let new_level_value = $("#level").val();
		if(new_level_value!=null){
			location.href = get_new_url(now_url, "level",new_level_value);
		}
    });

});