const g1_option="<option>카테고리2</option> <option value='G101'>과일/채소</option> <option value='G102'>고기</option> <option value='G103'>해산물</option> <option value='G104'>기타</option>"
const g2_option="<option>카테고리2</option> <option value='G201'>한식</option> <option value='G102'>중식</option> <option value='G203'>일식</option> <option value='G204'>서양식</option> <option value='G205'>분식</option> <option value='G206'>퓨전</option>"
const g3_option="<option>카테고리2</option> <option value='G301'>양념</option> <option value='G302'>조미료</option> <option value='G303'>음료</option> <option value='G304'>간식</option> <option value='G305'>기타</option>"
const g4_option="<option>카테고리2</option> <option value='G401'>주방조리도구</option> <option value='G402'>식기도구</option> <option value='G403'>주방잡화</option> <option value='G404'>캠핑주방용품</option>"

const opt_ul="<h2>헤이헤이</h2>"
const infooffer_1 = "-품명 및 모델명\n-KC 인증 필 유무\n전기용품안전관리법 상 안전인증대상전기용품, 안전확인대상전기용품, 공급자적합성확인대상전기용품에 한함\n-정격전압, 소비전력\n-에너지소비효율등급\n에너지이용합리화법 상 의무대상상품에 한함\n-동일모델의 출시년월\n-제조자\n수입품의 경우 수입자를 함께 표기 (병행수입의 경우 병행수입 여부로 대체 가능)\n-제조국\n-크기\n용량, 형태 포함\n-품질보증기준\n-A/S 책임자와 전화번호\n";
const infooffer_2 = "-품명 및 모델명\n-KC 인증 필 유무\n전기용품안전관리법 상 안전인증대상전기용품, 안전확인대상전기용품, 공급자적합성확인대상전기용품에 한함\n-정격전압, 소비전력\n-에너지소비효율등급\n에너지이용합리화법 상 의무대상상품에 한함\n-동일모델의 출시년월\n-제조자\n수입품의 경우 수입자를 함께 표기 (병행수입의 경우 병행수입 여부로 대체 가능)\n-제조국\n-크기\n형태 및 실외기 포함\n-냉난방면적\n-추가설치비용\n-품질보증기준\n-A/S 책임자와 전화번호\n";
const infooffer_3 = "-품명 및 모델명\n-재질\n-구성품\n-크기\n-동일모델의 출시년월\n-제조자\n수입품의 경우 수입자를 함께 표기 (병행수입의 경우 병행수입 여부로 대체 가능)\n-제조국\n-수입신고\n식품위생법에 따른 수입신고가 필요한 기구/용기의 경우 식품위생법에 따른 수입신고를 필함 으로 기재\n-품질보증기준\n-A/S 책임자와 전화번호\n";
const infooffer_4 = "-식품의 유형\n-생산자 및 소재지\n수입품의 경우 생산자, 수입자 및 제조국\n-제조연월일, 유통/품질유지기한\n-포장단위별 용량(중량), 수량\n-원재료명 및 함량\n농산산물의 원산지 표시에 관한 법률에 따른 원산지 표시 포함\n-영양성분\n식품위생법에 따른 영양성분표시 대상 식품에 한함\n-유전자변형식품에 해당하는 경우 표시\n-표시광고사전심의필 유무 및 부작용 가능성\n영유아식 또는 체중조절식품 등에 해당하는 경우\n-수입신고\n수입식품의 경우 식품위생법에 따른 수입신고를 필함 으로 기재\n-소비자상담 관련 전화번호";
const infooffer_5 = "-식품의 유형\n-제조업소의 명칭과 소재지\n수입품의 경우 수입업소명, 제조업소명 및 수출국명\n-제조연월일, 유통/품질유지기한\n-포장단위별 용량(중량), 수량\n-원재료명 및 함량\n농산산물의 원산지 표시에 관한 법률에 따른 원산지 표시 포함\n-영양정보\n-기능정보\n-섭취량, 섭취방법 및 주의사항, 부작용 가능성\n-질병의 예방 및 치료를 위한 의약품이 아니라는 내용의 문구\n-유전자변형식품에 해당하는 경우 표시\n-표시광고 사전심의필\n-수입신고\n수입식품의 경우 식품위생법에 따른 수입신고를 필함 으로 기재\n-소비자상담 관련 전화번호";
const infooffer_6 = "-품명 및 모델명\n-KC 인증 필 유무\n어린이제품 안전 특별법 상 안전인증대상어린이제품, 안전확인대상어린이제품, 공급자적합성확인대상어린이제품에 대한 KC 인증 필 유무\n-크기, 중량\n-색상\n-재질\n섬유의 경우 혼용률\n-사용연령 또는 체중범위\n어린이제품 안전 특별법에 따라 표시해야 하는 사항은 반드시 표기\n-동일모델의 출시년월\n-제조자\n수입품의 경우 수입자를 함께 표기 (병행수입의 경우 병행수입 여부로 대체 가능)\n-제조국\n-취급방법 및 취급시 주의사항, 안전표시\n주의, 경고 등\n-품질보증기준\n-A/S 책임자와 전화번호";
$(function() {
	$('#category1').on('change',function(){
			if($('#category1').val()=='g1'){
			$('#category2').html(g1_option);
		}
		if($('#category1').val()=='g2'){
			$('#category2').html(g2_option);
		}
		if($('#category1').val()=='g3'){
			$('#category2').html(g3_option);
		}
		if($('#category1').val()=='g4'){
			$('#category2').html(g4_option);
		}
	})
	

	$('#sell_price, #original_price').on('change',function(){
		
		let orig_price = Number($('#original_price').val());
		let sell_price = Number($('#sell_price').val());
		let result = (orig_price-sell_price)/orig_price*100;
		$('#sale_percent').text(result);
	
	})
	
	$('#deli_char_free').on('click',function() {
		let check = $('#deli_char_free').is(':checked')
		if(check)
			$('#deli_char').attr('disabled',true);
		else
			$('#deli_char').removeAttr('disabled');	
	})
	
	$("#deli_char").on('change',function() {
		let check = Number($('#deli_char').val())
		if(check>0)
			$('#deli_char_free').attr('disabled',true);
		if(check===0)
			$('#deli_char_free').removeAttr('disabled');
	})	
	
	$('#good_name, #sell_price').on('change',function() {
		let good_name = $('#good_name').val();
		let sell_price = Number($('#sell_price').val());
		
		$('#opt_ul1>li>.opt_name').val(good_name);
		$('#opt_ul1>li>.opt_price').val(sell_price);
	})
	
	$('#btn_addopt').on('click',function() {
		$('.add_opt').append('<ul><li><span>옵션명</span><input type="text" class="opt_name" value=""></li><li><span>옵션가격</span><input type="text" class="opt_price"></li><li><span>수량</span><input type="text" class="opt_qty"></li></ul>');
		
	})
	
	$('#btn_removeopt').on('click', function() {
		$('.add_opt>ul').empty();
	})
	
	$('#btn_add_2stopt').on('click', function() {
		$('.add_2stopt').append('<ul><li><span>옵션명</span><input type="text" class="2stopt_name" value=""></li><li><span>옵션가격</span><input type="text" class="2stopt_price"></li><li><span>수량</span><input type="text" class="2stopt_qty"></li></ul>');
		$('#no_2stopt').attr('disabled',true);
	})
	
	$('#btn_remove_2stopt').on('click', function() {
		$('.add_2stopt>ul').empty();
		$('#no_2stopt').removeAttr('disabled');
	})
	
	$('#add_img').on('click',function() {
		$('.good_images').append('<input type="file" name="good_images">');
	})
	
	$('#remove_img').on('click',function() {
		$('.good_images').empty();
	})
	
	$('#add_detailimg').on('click',function() {
		$('.good_detailimages').append('<input type="file" name="good_detailimages">');
	})
	
	$('#remove_detailimg').on('click', function() {
		$('.good_detailimages').empty();
	})
	
	$("#Goodinfooffer").change(function(){
		if($(this).val()==1)
			$(this).parent().find(".Goodinfooffer_textarea>textarea").html(infooffer_1);
		if($(this).val()==2)
			$(this).parent().find(".Goodinfooffer_textarea>textarea").html(infooffer_2);
		if($(this).val()==3)
			$(this).parent().find(".Goodinfooffer_textarea>textarea").html(infooffer_3);
		if($(this).val()==4)
			$(this).parent().find(".Goodinfooffer_textarea>textarea").html(infooffer_4);
		if($(this).val()==5)
			$(this).parent().find(".Goodinfooffer_textarea>textarea").html(infooffer_5);
		if($(this).val()==6)
			$(this).parent().find(".Goodinfooffer_textarea>textarea").html(infooffer_6);				
			
		
	})
		

})


//	if($('#category1').val()=='g1'){
//			$('#category2').html(g1_option);
//		}
//		if($('#category1').val()=='g2'){
//			$('#category2').html(g2_option);
//		}
//		if($('#category1').val()=='g3'){
//			$('#category2').html(g3_option);
//		}
//		if($('#category1').val()=='g4'){
//			$('#category2').html(g4_option);
//		}

//let category_val = $('#category1').val()
//		switch(category_val){
//			case 'g1' : $('#category2').html(g1_option);