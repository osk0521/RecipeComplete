$(function(){
	
	$('.event').slick({
		dots: true,
		autoplay: true,
		autoplaySpeed: 3000,
	});
	
	$('.goods_cont3').slick({
		infinite: true,
  		slidesToShow: 3,
  		slidesToScroll: 3
	});
	
	$('.goods_cont4').slick({
		infinite: true,
  		slidesToShow: 4,
  		slidesToScroll: 4
  		
	});
	
	$('.hot_deal_cont1').click(function(){
		let product_id = Number($(this).children('.product_id').val());
		location.href="Controller?command=store_goodsDetail&product_id="+product_id;
	})
	
	$('.goods_cont_section').click(function(){
		let product_id = $(this).children('.product_id').val();
		location.href="Controller?command=store_goodsDetail&product_id="+product_id;
	})
	
})