$(function() {
	$(window).scroll(function() {
		if(Number($(window).scrollTop()) >= 1753) {
			$(".main_cont_goodsdetailsecond").addClass("main_cont_goodsdetailsecond_fixed");
		};
		if(Number($(window).scrollTop()) < 1753) {
			$(".main_cont_goodsdetailsecond").removeClass("main_cont_goodsdetailsecond_fixed");
		};
	});
});
