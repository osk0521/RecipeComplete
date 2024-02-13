$(function(){
	$("#result_table tr").click(function(){
		
		let str ="";
		let tr = $(this);
		let td = tr.children();
		
		let good_code = (td.eq(0).find("span").text()).trim();
		let good_name = (td.eq(1).find("span").text()).trim();
		let good_price = (td.eq(2).find("span").text()).trim();
		let good_opt = (td.eq(3).find("span").text()).trim();
		let good_qty = (td.eq(4).find("span").text()).trim();
		
		$("#good_code").text(good_code);
	})
})