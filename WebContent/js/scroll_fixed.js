$(function() {
	$(window).scroll(function() {
		if(Number($(window).scrollTop()) >= 210) {
			$(".scroll_right").addClass("fixed");
		}
		if(Number($(window).scrollTop()) < 210) {
			$(".scroll_right").removeClass("fixed");
		}
	});
})
