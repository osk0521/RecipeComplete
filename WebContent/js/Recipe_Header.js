function move_write_recipe() {
	location.href = "Controller?command=recipe_write_form";
}

$(function() {
	
	
	$(window).click(function(e) {
		$("#div_user_mybutton").css("display", "none");
		e.stopPropagation();
	});
	$("#div_login_button").click(function(e) {
		$("#div_user_mybutton").toggle();
		e.stopPropagation();
	});
	
	$("#p_layer_middle > a").hover(function() {
		// 마우스 올렸을 때
		$(this).css("background", "#f9f9f9");
	}, function() {
		// 마우스 내렸을 때
		$(this).css("background", "#fff");
	});
})