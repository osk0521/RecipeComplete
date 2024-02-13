$(function(){
	
	$('#sum_qty').text(0);
	$('#sum_total').text(0);
	$('#sum_deli').text(0);
	$('#sum_reserves').text(0);
	$('#total_order').text(0);
	$('input[type=checkbox][checked]').each(
		function(){
			$(this).attr('checked',false);
		}
	);
	
	$('.tr_option .comma').each(function(index,item){
		let value = Number($(item).text());
		let value_after = addComma(value);
		$(item).text(value_after);
	})
	
	
	
	$('#all_cont').click(function(){
		let check1 = $('#all_cont').is(':checked');
		if(check1){
			$('input:checkbox').prop('checked',true);
		}else{
			$('input:checkbox').prop('checked',false);
		}
		let check = $('.goods_check').is(':checked');
		
		
		
		if(check){
			let sum_reserves = 0;
			let sum_total = 0;
			let sum_deli = 0;
			let sum_qty = 0;
			$('.tr_option').each(function(index,item){
			
				let total = Number($(item).data("total"));
				let deli = Number($(item).data("deli"));
				let qty = Number($(item).data("qty"));
				let reserves = Number($(item).data("reserves"));
				sum_total = sum_total+total;
				sum_deli = sum_deli+deli;
				sum_qty  = sum_qty+qty;
				sum_reserves = sum_reserves+reserves;
				$('#sum_qty').text(sum_qty);
				$('#sum_total').text(sum_total);
				$('#sum_deli').text(sum_deli);
				$('#sum_reserves').text(sum_reserves);
				$('#total_order').text(sum_total+sum_deli);
			
				let total_value = Number($('#sum_total').text());
				let total_value_after = addComma(total_value);
				$('#sum_total').text(total_value_after);
			
				let deli_value = Number($('#sum_deli').text());
				let deli_value_after = addComma(deli_value);
				$('#sum_deli').text(deli_value_after);
				
				let reserves_value = Number($('#sum_reserves').text());
				let reserves_value_after = addComma(reserves_value);
				$('#sum_reserves').text(reserves_value_after);
				
				let order_value = Number($('#total_order').text());
				let order_value_after = addComma(order_value);
				$('#total_order').text(order_value_after);
				
			})
		}else{
				$('#sum_qty').text(0);
				$('#sum_total').text(0);
				$('#sum_deli').text(0);
				$('#sum_reserves').text(0);
				$('#total_order').text(0);
		}
	})
	
	$('.goods_check').change(function(){
		let check = $(this).is(':checked');
		
		let total = Number($(this).parent().parent().data("total"));
		let deli = Number($(this).parent().parent().data("deli"));
		let qty = Number($(this).parent().parent().data("qty"));
		let reserves = Number($(this).parent().parent().data("reserves"));
		
		let sum_total = Number($('#sum_total').text().replace(/[^\d]+/g, ""));
		let sum_deli = Number($('#sum_deli').text().replace(/[^\d]+/g, ""));
		let sum_qty = Number($('#sum_qty').text().replace(/[^\d]+/g, ""));
		let sum_reserves = Number($('#sum_reserves').text().replace(/[^\d]+/g, ""));
		let total_order = Number($('#total_order').text().replace(/[^\d]+/g, ""));
		
		if(check){
			$('#sum_total').text(total+sum_total);
			$('#sum_deli').text(deli+sum_deli);
			$('#sum_qty').text(qty+sum_qty);
			$('#sum_reserves').text(reserves+sum_reserves);
			$('#total_order').text(total_order+total+deli);
			
			
		}else{
			$('#sum_total').text(sum_total-total);
			$('#sum_deli').text(sum_deli-deli);
			$('#sum_qty').text(sum_qty-qty);
			$('#sum_reserves').text(sum_reserves-reserves);
			$('#total_order').text(total_order-total-deli);
			
			
		}
		
			let total_value = Number($('#sum_total').text());
			let total_value_after = addComma(total_value);
			$('#sum_total').text(total_value_after);
		
			let deli_value = Number($('#sum_deli').text());
			let deli_value_after = addComma(deli_value);
			$('#sum_deli').text(deli_value_after);
			
			let reserves_value = Number($('#sum_reserves').text());
			let reserves_value_after = addComma(reserves_value);
			$('#sum_reserves').text(reserves_value_after);
			
			let order_value = Number($('#total_order').text());
			let order_value_after = addComma(order_value);
			$('#total_order').text(order_value_after);
		
	})
	
	$('.a_option_qty').click(function(){
		
		$('.option_optionDetail_wrap').css(
			"display","none"
		);
		
		const first_option = "<option>옵션을 선택해주세요 </option>";
		$('.select_option').html(first_option);
		
		let image = $(this).parent().parent().parent().find('.image').attr("src");
		$('#option_image').attr("src",image);
		
		let name = $(this).parent().parent().parent().find('.good_opt_info2 a').text();
		$('#p_title').text(name);
		
		let product_id = $(this).parent().parent().parent().find('.input_productId').val();
		$('#productId_input').val(product_id);
		$.ajax({
			type: 'post',
			dataType: 'json',
			data:{
				productId : product_id,
				command : "cart_get_update_option"
			},
			url: "Controller",
			success: function(data){
				console.log(data);
				for(let i=0;i<data.length;i++){
					let optionNum = data[i].optionNum
					let optionContent = data[i].optionContent
					let optionPrice = data[i].optionPrice
					let optionQty = data[i].optionQty
					
					let option = "<option data-num='"+optionNum+"' data-price="+optionPrice+" data-qty="+optionQty+" data-name='"+optionContent+"'>"+optionContent+" "+optionPrice+"(재고수량:"+optionQty+"개)</option>";
					$('.select_option').append(option);
				}
		},
		error: function(request,status,error){
			alert("에러코드:"+request.status)
		}
		})
		
		$('.option_box').css(
			"display","block"
		);
		$('.option_boxbg').css(
			"display","block"
		);
	})
	
	$('#option_btn_cancel').click(function(){
		$('.option_box').css(
			"display","none"
		);
		$('.option_boxbg').css(
			"display","none"
		);
	})
	
	
	$('.select_option').change(function(){
		$('#qty_input').val(1);
		
		let option_num = $('option:selected').data("num");
		let name = $('option:selected').data("name");
		let price = $('option:selected').data("price")+"원";
		$('.option_optionDetail_wrap').css(
			"display","block"
		);
		$('.optionDetail_name').text(name);
		$('.optionDetail_price_cont').text(price);
		$('#optionNum_input').val(option_num);
		$('option:first').prop('selected',true);
	})
	
	$('#qty_btn_minus').click(function(){
		let qty = $('#qty_input').val();
		if(qty>1){
			qty = qty-1;
			$('#qty_input').val(qty);
		}
	})
	
	$('#qty_btn_plus').click(function(){
		let qty = Number($('#qty_input').val());
		qty = qty+1;
		$('#qty_input').val(qty);
	})
	
	$('.optionDetail_remove').click(function(){
		$('.option_optionDetail_wrap').css(
			"display","none"
		);
	})	
	
	$('.select_delete').click(function(){
		
		let sum_total = Number($('#sum_total').text().replace(/[^\d]+/g, ""));
		let sum_deli = Number($('#sum_deli').text().replace(/[^\d]+/g, ""));
		let sum_qty = Number($('#sum_qty').text().replace(/[^\d]+/g, ""));
		let sum_reserves = Number($('#sum_reserves').text().replace(/[^\d]+/g, ""));
		let total_order = Number($('#total_order').text().replace(/[^\d]+/g, ""));
		
		let count = 0;
		let optionList = [];
		$('.tr_option').each(function(index,item){
			let check = $(item).find('.goods_check').is(':checked');
			if(check){
				
				count++;
				
				let option = $(item).data("option");
				optionList.push(option);
				
			}
		});
			if(count==0){
				alert("상품을 선택해주세요!");
			}else {
				
				$.ajax({
					type: 'post',
					dataType: 'json',
					traditional: true,
					data: {
						"optionList" : optionList,
						"command": "cart_delete"
					},
					url: "Controller",
					success: function(data){
						alert(data.msg);
						location.href="Controller?command=cart_detail_view";
					},
					error: function(request,status,error){
						alert("에러코드"+request.status);
					}
				});
				
			}
	});
	
	$('#select_order').click(function(){
		let optionNumList = [];
		let count = 0;
		$('.tr_option').each(function(index,item){
			let check = $(item).find('.goods_check').is(':checked');
			if(check){
				count++;
				let optionNum = $(item).find('.input_optionNum').val();
				optionNumList.push(optionNum);
			}
		})
		if(count==0){alert("상품을 선택해주세요");}
		else{
			location.href="Controller?command=order_form&optionNumList="+optionNumList;
		}
	
	})
	
	$('#all_order').click(function(){
		let optionNumList = [];
		let count = 0;
		$('.tr_option').each(function(index,item){
			count++;
			let optionNum = $(item).find('.input_optionNum').val();
			optionNumList.push(optionNum);
		})
		if(count==0){alert("상품을 장바구니에 넣어주세요");}
		else{
			location.href="Controller?command=order_form&optionNumList="+optionNumList;
		}
	})

})
function addComma(value){
	return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
