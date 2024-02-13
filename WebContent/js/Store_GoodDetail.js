$(function(){
	$('.main_cont_goodsdetailFirst_left_slick').slick({
		slidesToShow: 5,
  		slidesToScroll: 1
	});
	
	$('.goodsdetail_slick').slick({
		slidesToShow: 4,
  		slidesToScroll: 4
	});
	
	const optqty = $('.main_cont_goodsdetailFirst_right_price_5_optqty')
	
	$('.slick_cont').click(function(){
		let img_src = $(this).find('.img').attr("src");
		let thumb_src =$(this).parents().find('#thumb_img').attr("src");
		$(this).parents().find('#thumb_img').attr('src',img_src);
	})
	
	//const opt_qty = '<li><div class="opt_name_wrap"><div class="opt_name">1. 삿뽀로 구루메 훗카이도 유바리 멜론카라멜 78g (0원)</div><div class="opt_btn"><button>X</button></div></div><div class="opt_qty_wrap"><div class="qty_price">5500원</div><div class="qty_btn"><div class="btn_wrap"><button>-</button><input value=1><button>+</button></div></div></div></li>';
	
	
	
	$('#select_opt').change(function(){
		let check = true;
		let name = $('option:selected').data("name");
		let price = Number($('option:selected').data("price"));
		let qty = $('option:selected').data("qty");
		let option = $('option:selected').data("option");
		
		
		
		$(this).parents().find('.ul_optqty li').each(function(index,item){
			let li_name = $(item).find('.opt_name').text();
			if(name == li_name)
				check = false;
		})
		if(check){
			let opt_qty = '<li data-option="'+option+'"data-price='+price+'><div class="opt_name_wrap"><div class="opt_name">'+name+'</div><div class="opt_btn"><div class="close_btn"><button class="close">X</button></div></div></div><div class="opt_qty_wrap"><div class="qty_price">'+price+'원</div><div class="qty_btn"><div class="qty_btn_wrap"><button class="btn_qty_minus">-</button><input class="input_qty" value=1><button class="btn_qty_plus">+</button></div></div></div></li>';
			$('.main_cont_goodsdetailFirst_right_price_5_optqty>ul').append(opt_qty);
			$('.main_cont_goodsdetailFirst_right_price_5_totalprice').removeClass('remove');
			
			let sum = 0;
			$(this).parents().find('.ul_optqty li').each(function(index,item){
				let price = Number($(item).data("price"));
				let qty = Number($(item).find('.input_qty').val());
				sum = sum + price*qty;
				$('#total_price').text(sum);
			})
		}
		else{
			alert("이미 선택한 옵션입니다");
		}
		
		$('option:first').prop('selected',true);
		
		
		$('.close').click(function(){
			
			let sum = 0;
			$(this).parents().find('.ul_optqty li').each(function(index,item){
				let price = Number($(item).data("price"));
				let qty = Number($(item).find('.input_qty').val());
				sum = sum + price*qty;
			})
			let li_price = Number($(this).parent().parent().parent().parent().data("price"));
			let li_qty = Number($(this).parent().parent().parent().parent().find('.input_qty').val());
			$(this).parents().find('#total_price').text(sum-(li_price*li_qty));
			
			let check_price = $(this).parents().find('.total_price span').text();
			
			if(check_price=='0원'){
				$('.main_cont_goodsdetailFirst_right_price_5_totalprice').addClass('remove');
			}
			
			$(this).parent().parent().parent().parent().remove();
		
		
		})
		
	})
	
	$(document).on("click",".btn_qty_plus",function(event){
		
		let qty=Number($(this).parent().children('.input_qty').val());
		qty = qty+1;
		$(this).parent().children('.input_qty').val(qty);
		
		let sum = 0;
		$(this).parents().find('.ul_optqty li').each(function(index,item){
			let price = Number($(item).data("price"));
			let qty = Number($(item).find('.input_qty').val());
			sum = sum + price*qty;
			$('#total_price').text(sum);
		})
		
	})
	
	$(document).on("click",".btn_qty_minus",function(event){
		
		let qty=Number($(this).parent().children('.input_qty').val());
		if(qty>=2){
			qty=qty-1;
			$(this).parent().children('.input_qty').val(qty);
		}
		
		let sum = 0;
		$(this).parents().find('.ul_optqty li').each(function(index,item){
			let price = Number($(item).data("price"));
			let qty = Number($(item).find('.input_qty').val());
			sum = sum + price*qty;
			$('#total_price').text(sum);
		})
		
	})
	
	const bar = $('.main_cont_goodsdetailsecond');
	const barTop = bar.offset().top;
	
	const detailInfo = $('.main_cont_goodsdetailfour_line');
	const li_detailInfo = $('.li_detailInfo');
	const review = $('.main_cont_goodsdetailfour_review_title');
	const deli_refund = $('.deli_exchange_refund');
	$('.li_detailInfo').click(function(){
		let detailInfoTop = detailInfo.offset().top;
		$('html').animate({scrollTop : detailInfoTop},500);
	})
	
	$('.li_review').click(function(){
		let reviewTop = review.offset().top;
		$('html').animate({scrollTop : reviewTop},500);
	})
	
	const li_deli_refund = $('.deli_exchange_refund');
	const li_deli_refund_cont = $('#deli_exchange_refund_cont');
	$('.li_refund').click(function(){
		let deli_refundTop = deli_refund.offset().top;
		$('html').animate({scrollTop:deli_refundTop},500);
		
		li_deli_refund.find('.arrow').animate({
			rotate:"-90deg"
		},500);
		
		li_deli_refund_cont.slideDown(1000);
	})
	
	const qna = $('#QnA');
	const qna_cont = $('#QnA_cont')
	$('.li_inquir').click(function(){
		let qnaTop = qna.offset().top;
		$('html').animate({
			scrollTop:qnaTop
		},500);
		qna.find('.arrow').animate({
			rotate:"-90deg"
		},500)
		qna_cont.slideDown(1000);
	})
	
	$(window).scroll(function(){
		
		let windowTop = $(this).scrollTop();
		let detailInfoTop = detailInfo.offset().top;
		let reviewTop = review.offset().top;
		
		if(windowTop>=barTop){
			bar.addClass('is_fixed')}else if(windowTop<=barTop){
				bar.removeClass('is_fixed')
			}
			
		if(windowTop>=detailInfoTop && windowTop<=reviewTop){
			li_detailInfo.addClass('is_emphasized')}else if(windowTop<=detailInfoTop || windowTop>=reviewTop){
				li_detailInfo.removeClass('is_emphasized')
			}
			
		
		
		})
	
	$('.show_detailInfo').click(function(){
		
		$(this).parents().find('.main_cont_goodsdetailthird_img').addClass('main_cont_goodsdetailthird_img_detail');
		$(this).parents().find('.main_cont_goodsdetailthird_img').removeClass('main_cont_goodsdetailthird_img');
		$(this).parents().find('.main_cont_goodsdetailthird_detail_close').removeClass('remove');
		$(this).parents().find('.main_cont_goodsdetailthird_detail_show').addClass('remove');
		
	})
	
	$('.close_detailInfo').click(function(){
		$(this).parents().find('.main_cont_goodsdetailthird_img_detail').addClass('main_cont_goodsdetailthird_img');
		$(this).parents().find('.main_cont_goodsdetailthird_img_detail').removeClass('main_cont_goodsdetailthird_img_detail');
		$(this).parents().find('.main_cont_goodsdetailthird_detail_show').removeClass('remove');
		$(this).parents().find('.main_cont_goodsdetailthird_detail_close').addClass('remove');
	})
	
	const current_review = $('.current_review');
	const total_review = $('.total_review');
	$('.moreview_review').click(function(){
		let current_review_value = Number($('.current_review').text());
		let total_review_value=Number(total_review.text());
		current_reivew_value = current_review_value+5;
		if(total_review_value-current_review_value>5)
			current_review.text(current_reivew_value);
		else if(total_review_value-current_review_value<5)
			current_review.text(total_review_value);
		
		
		
	})
	
	$('.goods_inform').click(function(){
		if($('.goods_inform_cont').css("display")=="none"){
			$(this).find('.arrow').animate({
				rotate:"-90deg"
			},500);
			$('.goods_inform_cont').slideDown(1000);
			$(this).addClass('on');
		}else{
			$(this).find('.arrow').animate({
				rotate:"0deg"
			},500);
			$('.goods_inform_cont').slideUp(1000);
			$(this).removeClass('on');
		}
	})
	
	$('.deli_exchange_refund').click(function(){
		if($('#deli_exchange_refund_cont').css("display") == "none") {
			$(this).find('.arrow').animate({
				rotate:"-90deg"
			},500);
			$('#deli_exchange_refund_cont').slideDown(1000);
			$(this).addClass('on');
		} else {
			$(this).find('.arrow').animate({
				rotate:"0deg"
			},500);
			$('#deli_exchange_refund_cont').slideUp(1000);
			$(this).removeClass('on');
		}
	});
	
	$('#QnA').click(function(){
		if($('#QnA_cont').css("display")=="none"){
			$(this).find('.arrow').animate({
				rotate:"-90deg"
			},500);
			$('#QnA_cont').slideDown(1000);
			$(this).addClass('on');
		} else{
			$(this).find('.arrow').animate({
				rotate:"0deg"
			},500);
			$('#QnA_cont').slideUp(1000);
			$(this).removeClass('on');
		}
	})
	
	let rating = $('.rating');
	rating.each(function(){
		let $this = $(this);
		let targetScore = $this.attr('data-rate');
		let firstDigit = targetScore.split('.');
		console.log(firstDigit);
		if(firstDigit.length>1){
			for(let i=0;i<firstDigit[0];i++){
				$this.find('.star').eq(i).css({width:'100%'})
			}
			$this.find('.star').eq(firstDigit[0]).css({width:firstDigit[1]+"0%"});
		}else{
			for(let i=0;i<targetScore;i++){
				$this.find('.star').eq(i).css({width:'100%'})
			}
		}
	})
	

	const review_bg = $('.review_bg');
	const review_box = $('.review_box');
	const point = $('.main_cont_goodsdetailthird_detail_show');
	$('#btn_write_review').click(function(e){
			let poinTop = $('.main_cont_goodsdetailfour_star').offset().top;
			review_box.css({
				'top': poinTop,
				'display': 'flex'
			}).show();
		review_bg.removeClass('remove');
	})
	
	$('#btn_review_cancel').click(function(){
		review_box.css({
			display: 'none'
		})
		review_bg.addClass('remove');
	})
	
})

function cart_unLogIn(){
	alert("로그인이 필요합니다.");
	location.href="Controller?command=login_form";
}
function cart_LogIn(){
	let product_id = $('#input_productId').val();
	
	let optionArray = [];
	let qtyArray = [];
	
	$('.ul_optqty li').each(function(index,item){
		let optionNum = $(item).data("option");
		let qty = Number($(item).find('.input_qty').val());
		optionArray.push(optionNum);
		qtyArray.push(qty);
		
	});
	
	$.ajax({
		type: 'post',
		dataType: 'json',
		traditional: true,
		data: {
			"optionList" : optionArray,
			"qtyList" : qtyArray,
			"productId" : product_id,
			"command" : "go_to_cart"
		},
		url: "Controller",
		success: function(data){
			alert(data.test);
			location.href="Controller?command=cart_detail_view";
		},
		error: function(request,status,error){
			alert("에러코드"+request.status);
		}
	});
}
function pay_unLogIn(){
	alert("로그인이 필요합니다.");
	location.href="Controller?command=login_form";
}
function pay_LogIn(){
	alert(1);
}