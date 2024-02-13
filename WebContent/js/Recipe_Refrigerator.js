function close() {
	$(".close, .close_2").click(function() {
		$("#buy_missing_ingredients").css("display", "none");
	});
}
$(document).ready(function () {
	
	$(".search_results").on("click", "tbody > tr > td.inconsistent > span.missing_material", function() {
		clicked_ingredi = $(this).text();
		//alert(clicked_ingredi);
		$("#buy_missing_ingredients").css("display", "block");
		$(".not_have").empty();
		$(".not_have").append(clicked_ingredi);
	});
	close();
});

