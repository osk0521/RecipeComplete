$(function(){
	
	let price = 0;
	let delivery = 0;
	let reserves = 0;
	
	$('.tr_goods').each(function(index,item){
		 price = price + $(item).data('price');
		 delivery = delivery + $(item).data('delivery');
		 reserves = reserves + $(item).data('reserves');
	});
	
	$('#span_price').text(price);
	$('#span_delivery').text(delivery);
	$('#span_reserves').text(reserves);
	
	$('.a_btn_address').click(function(){
		let blank ="";
		$('.tbody_user_addressList').html(blank);
		
		$.ajax({
			type: 'post',
			dataType:'json',
			data:{
				"command": "order_get_user_addressList"
			},
			url: "Controller",
			success: function(data){
				console.log(data);
				for(let i=0;i<data.length;i++){
					let addressId = data[i].adrressId;
					let reciever = data[i].reciever;
					let address = data[i].address;
					let detailAddress = data[i].detailAddress;
					let zipCode = data[i].zipCode;
					let phoneNumber = data[i].phoneNumber;
					let defaultCheck = data[i].defaultAddress;
					let defaultAddress;
					if(defaultCheck==1){
						defaultAddress = "기본배송지";
					}else if(defaultCheck==0){
						defaultAddress = "";
					}
					
					let tr = "<tr data-addressId="+addressId+" data-reciever='"+reciever+"' data-address='"+address+"' data-detail='"+detailAddress+"' data-zipCode='"+zipCode+"' data-phoneNumber='"+phoneNumber+"' data-defaultCheck="+defaultCheck+"><td class='td_select'><a class='a_select'>선택</a></td><td><span>"+defaultAddress+"</span><br/><strong class='reciever'>"+reciever+"</strong></td><td>"+reciever+"</td><td><span class='zipCode'>"+zipCode+"</span><br/><span class='address'>"+address+"</span><span class='detailAddress'>"+detailAddress+"</span></td><td><span class='phoneNumber'>휴대폰:"+phoneNumber+"</span></td><td><div class='div_btn_modify'><a class='btn_modify'>수정</a></div><div class='div_btn_delete'><a class='btn_delete'>삭제</a></div></td></tr>";
					$('.tbody_user_addressList').append(tr);
				};
			},
			error: function(request,status,error){
				alert("에러코드"+request.status);
			}
		});
		
		
		$('.address_box').css(
			'display','block'
		);
		$('.address_bg').css(
			'display','block'
		);
		
	})
	
	$('.div_address_cancel').click(function(){
		$('.address_box').css(
			'display','none'
		);
		$('.address_bg').css(
			'display','none'
		);
		$('.address_content').css(
			'display','block'
		);
		$('.address_newaddress_regist').css(
			'display','none'
		);
	})
	
	$('.a_address_btn_newaddress').click(function(){
		
		const blank="";
		
		$('#input_update_receiver').val(blank);
		$('#input_update_zipCode').val(blank);
		$('#input_update_address').val(blank);
		$('#input_update_detailAddress').val(blank);
		$('#input_update_phoneNumber').val(blank);
		
		
		$('.address_content').css(
			'display','none'
		);
		$('.address_newaddress_regist').css(
			'display','block'
		);
	})
	
	$('#btn_cancel').click(function(){
		$('.address_box').css(
			'display','none'
		);
		$('.address_bg').css(
			'display','none'
		);
		$('.address_content').css(
			'display','block'
		);
		$('.address_newaddress_regist').css(
			'display','none'
		);
	})
	
	$('#base_address').change(function(){
		let check = $('#base_address').is(':checked');
		if(check){
			
			$.ajax({
				type: 'post',
				dataType: 'json',
				data:{
					command: "order_get_default_address"
				},
				url: "Controller",
				success: function(data){
					$('#input_receiver').val(data.receiver);
					$('#input_zipCode').val(data.zipCode);
					$('#output_address').val(data.address);
					$('#input_detailAddress').val(data.detailAddress);
					$('#input_phoneNumber').val(data.phoneNumber);
				},
				error: function(request,status,error){
					alert("에러코드"+request.status);
				}
			})
			
		}
	})
	
	$('#direct_input').change(function(){
		let check = $('#direct_input').is(':checked');
		if(check){
			const blank = "";
			$('#input_receiver').val(blank);
			$('#input_zipCode').val(blank);
			$('#output_address').val(blank);
			$('#input_detailAddress').val(blank);
			$('#input_phoneNumber').val(blank);
		}
	})
	
	$(document).on("click",".a_select",function(){ // 주소 모달창 선택 버튼 클릭시 이벤트
		let receiver = $(this).parent().parent().data('reciever'); //tr에 부여된 데이터 값으로 초기화
		let address = $(this).parent().parent().data('address');
		let detail = $(this).parent().parent().data('detail');
		let zipCode = $(this).parent().parent().data('zipcode');
		let phoneNumber = $(this).parent().parent().data('phonenumber');
		
		$('#input_receiver').val(receiver); // 부여된 값 input 값에 입력
		$('#input_zipCode').val(zipCode);
		$('#output_address').val(address);
		$('#input_detailAddress').val(detail);
		$('#input_phoneNumber').val(phoneNumber);
		modal_close(); // 주소 모달탕 종료
	})
	
	$('#btn_save').click(function(){
		let count = 0;
		let receiver = $('#input_update_receiver').val();
		let productId = $('#input_update_productId').val();
		let zipcode = $('#input_update_zipCode').val();
		let address = $('#input_update_address').val();
		let detail = $('#input_update_detailAddress').val();
		let phone = $('#input_update_phoneNumber').val();
		let defaultCheck;
		if($('#default_address').is(":checked")==true)
			defaultCheck = 1;
		else
			defaultCheck = 0;
		
		if(receiver=="")
			count++;
		if(zipcode=="")
			count++;	
		if(address=="")
			count++;
		if(detail=="")
			count++;
		if(phone=="")
			count++;
		if(count>0){
			alert("정보를 다 입력해주세요!")
		}else{
			$.ajax({
			type : 'post',
			dataType : 'json',
			data : {
				"command" : "order_update_destination",
				"receiver" : receiver,
				"productId" : productId,
				"zipcode" : zipcode,
				"address" : address,
				"detail" : detail,
				"phone" : phone,
				"defaultCheck" : defaultCheck
			},
			url : "Controller",
			success: function(data){
				alert(data.msg);
			},
			error: function(request,status,error){
				alert("에러코드"+request.status);
			}
		})
		
			modal_close();	
		}	
		
	})
	
	$('#btn_cancel').click(function(){
		modal_close();
	})
	
	$(document).on("click",".btn_modify",function(){
		
		const blank="";
		$('#input_update_productId').val(blank);
		$('#input_update_receiver').val(blank);
		$('#input_update_zipCode').val(blank);
		$('#input_update_address').val(blank);
		$('#input_update_detailAddress').val(blank);
		$('#input_update_phoneNumber').val(blank);
		
		
		let addressId = $(this).parent().parent().parent().data('addressid');
		let reciever = $(this).parent().parent().parent().data('reciever');
		let address = $(this).parent().parent().parent().data('address');
		let detail = $(this).parent().parent().parent().data('detail');
		let zipcode = $(this).parent().parent().parent().data('zipcode');
		let phonenumber = $(this).parent().parent().parent().data('phonenumber');
		
		$('.address_content').css(
			'display','none'
		);
		$('.address_newaddress_regist').css(
			'display','block'
		);
		
		$('#btn_save').css(
			'display','none'
		);
		
		$('#btn_update').css(
			'display','inline'
		);
		
		$('#input_update_productId').val(addressId);
		$('#input_update_receiver').val(reciever);
		$('#input_update_zipCode').val(zipcode);
		$('#input_update_address').val(address);
		$('#input_update_detailAddress').val(detail);
		$('#input_update_phoneNumber').val(phonenumber);
		
	})
	
	$('#btn_update').click(function(){
		let productId = Number($('#input_update_productId').val());
		let receiver = $('#input_update_receiver').val();
		let zipcode = $('#input_update_zipCode').val();
		let address = $('#input_update_address').val();
		let detail = $('#input_update_detailAddress').val();
		let phoneNumber = $('#input_update_phoneNumber').val();
		let defaultCheck;
		if($('#default_address').is(':checked')==true)
			defaultCheck = 1;
		else
			defaultCheck = 0;
		
		$.ajax({
			type: 'post',
			dataType: 'json',
			data : {
				'command' : 'order_update_existing_address',
				"receiver" : receiver,
				"productId" : productId,
				"zipcode" : zipcode,
				"address" : address,
				"detail" : detail,
				"phone" : phoneNumber,
				"defaultCheck" : defaultCheck
			},
			url : "Controller",
			success : function(data){
				alert(data.msg);
			},
			error : function(request,status,error){
				alert("에러코드"+request.status)
			}
		})
		
		
		modal_close();
	})
	
	$(document).on("click",".btn_delete",function(){
		let addressId = Number($(this).parent().parent().parent().data('addressid'));
		let defaultCheck =Number($(this).parent().parent().parent().data('defaultCheck'));
		if(defaultCheck==0){
		$.ajax({
			type: 'post',
			dataType: 'json',
			data: {
				'command' : 'order_delete_address',
				'addressId' : addressId
			},
			url: "Controller",
			success : function(data){
				alert(data.msg);
			},
			error : function(request,status,error){
				alert("에러코드"+request.status);
			}
		})
		modal_close();}else{
			alert("기본 배송지는 삭제 불가합니다. 다른 배송지로 기본 배송지 설정한 후에 삭제해주세요.");
		}
	})
	
	$('#a_zipcode').click(function(){
		new daum.Postcode({
        oncomplete: function(data) {
            console.log(data);
			$('#input_zipCode').val(data.zonecode);
			let fullAddress = "";
			let extraAddress = "";
			if(data.userSelectedType ==='R'){
				fullAddress = data.roadAddress;
			}else{
				fullAddress = data.jibunAddress;
			}
			if(data.userSelectedType ==='R'){
				if(data.bname!==''){
					extraAddress += data.bname;
				}
				if(data.buildingName !==''){
					extraAddress +=(extraAddress!==''?','+data.buildingName : data.buildingName);
				}
				fullAddress += (extraAddress!==''?'('+extraAddress+')' : '');
			}
			
			$('#output_address').val(fullAddress);
			$('#input_detailAddress').focus();
        }
    }).open();
	
	})
	
	$('.btn_zipcode').click(function(){
		
			new daum.Postcode({
        	oncomplete: function(data) {
            console.log(data);
			$('#input_update_zipCode').val(data.zonecode);
			let fullAddress = "";
			let extraAddress = "";
			if(data.userSelectedType ==='R'){
				fullAddress = data.roadAddress;
			}else{
				fullAddress = data.jibunAddress;
			}
			if(data.userSelectedType ==='R'){
				if(data.bname!==''){
					extraAddress += data.bname;
				}
				if(data.buildingName !==''){
					extraAddress +=(extraAddress!==''?','+data.buildingName : data.buildingName);
				}
				fullAddress += (extraAddress!==''?'('+extraAddress+')' : '');
			}
			
			$('#input_update_address').val(fullAddress);
			$('#input_update_detailAddress').focus();
        }
    }).open();
		
		
	})
	
	$('#a_sameAddress').click(function(){
		let reciever = $('#input_receiver').val();
		let phone = $('#input_phoneNumber').val();
		$('#input_order').val(reciever);
		$('#input_order_phoneNumber').val(phone);
	})
	
	//휘뚜루마뚜루 만든 기능입니다! 추후에 수정이 필요합니다. null값일때 반응, 필수값들 안들어갔을때 알람 설정, 적립금사용 기능 구현이 필요합니다.
	$('#all_order').click(function(){
		let orderId;
		let now = new Date();
		orderId = now.getMonth()+1+""+now.getDate() + now.getHours() + now.getMinutes()+ now.getSeconds();
		let receiver = $("#input_receiver").val();
		let zipcode = $("#input_zipCode").val();
		let address = $("#output_address").val();
		let detailAddress = $("#input_detailAddress").val();
		let phoneNumber = $("#input_phoneNumber").val();
		let order = $("#input_order").val();
		let orderPhoneNumber = $("#input_order_phoneNumber").val();
		let email_com = $("#select_email option:selected").val();
		let email = $("#email_id").val()+"@"+email_com;
		let memo = $("#input_text").val();
		
		$.ajax({
			type: 'post',
			dataType: 'json',
			url: 'Controller',
			data:{
				"command" : "order_insert_orderdetail",
				"orderId" : orderId,
				"receiver" : receiver,
				"zipcode" : zipcode,
				"address" : address,
				"detailAddress" : detailAddress,
				"phoneNumber" : phoneNumber,
				"order" : order,
				"orderPhoneNumber" : orderPhoneNumber,
				"email" : email,
				"reserves" : 0,
				"memo" : memo,
				"orderId" : orderId
			},
			success: function(data){
				console.log(data.msg);
			},
			error: function(status,request,error){
				alert("에러코드"+request.status);
			}
		});
		
		let productIdList = [];
		let optionList = [];
		let qtyList = [];
		let nameList = [];
		let total_qty = 0;
		$('.tr_goods').each(function(index,item){
			let productId = $(item).data('productid');
			let option = $(item).data('option');
			let qty = $(item).data('qty');
			let name = $(item).data('name');
			productIdList.push(productId);
			optionList.push(option);
			qtyList.push(qty);
			nameList.push(name);
			total_qty = total_qty + qty;
		});
		
		let payname = nameList[0]+(nameList.length==1?" ":"외"+nameList.length);
		
		$.ajax({
			type: 'post',
			dataType: 'json',
			url: 'Controller',
			traditional: true,
			data: {
				"command" : "order_insert_orderlist",
				"productIdList" : productIdList,
				"optionList" : optionList,
				"qtyList" : qtyList,
				"orderId" : orderId
			},
			success: function(data){
				console.log(data.msg);
			},
			error: function(status,request,error){
				alert("에러코드"+request.status);
			}
		})
		
		$.ajax({
			type: 'post',
			dataType: 'json',
			url: 'Controller',
			traditional: true,
			data:{
				"command" : "order_delete_cartlist",
				"optionList" : optionList
			},
			success: function(data){
				console.log(data.msg);
			},
			error: function(status,request,error){
				alert("에러코드"+request.status);
			}
		});
	
	let total_price = Number($('#span_price').text());
	let total_delivery = Number($('#span_delivery').text());
	let total = total_price + total_delivery ;
	IMP.init("imp01502010");
	IMP.request_pay(
		{
			pg: "kakaopay",
			pay_method: "card",
			merchant_uid: orderId,
			name: payname,
			quantity: total_qty,
			amount: total ,
		},
		function(rsp){
			if(rsp.success){
				console.log(rsp)
				$.ajax({
					type: 'post',
					dataType: 'json',
					url:"Controller",
					data:{
						"command" : "payment_check",
						"imp_uid" : rsp.imp_uid,
						"merchant_uid" : rsp.merchant_uid,
						"amount" : rsp.paid_amount
					},
					success: function(data){
						alert(data.msg);
						window.location.href="Controller?command=store_mainlist";	
					},
					error: function(status,request,error){
						alert("에러코드"+request.status);
					}
					
				});
				
			}else{
				alert("결제실패!");
			}
		}
		
	);
	
	})
	
})

function modal_close(){ // 주소 모달창 종료시 필요한 이벤트
	$('.address_box').css(
		'display','none'
		);
	$('.address_bg').css(
		'display','none'
	);
	$('.address_content').css(
		'display','block'
	);
	$('.address_newaddress_regist').css(
		'display','none'
	);
}