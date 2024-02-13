const g1_option="<option>카테고리2</option> <option value='G101'>과일/채소</option> <option value='G102'>고기</option> <option value='G103'>해산물</option> <option value='G104'>기타</option>"
const g2_option="<option>카테고리2</option> <option value='G201'>한식</option> <option value='G102'>중식</option> <option value='G203'>일식</option> <option value='G204'>서양식</option> <option value='G205'>분식</option> <option value='G206'>퓨전</option>"
const g3_option="<option>카테고리2</option> <option value='G301'>양념</option> <option value='G302'>조미료</option> <option value='G303'>음료</option> <option value='G304'>간식</option> <option value='G305'>기타</option>"
const g4_option="<option>카테고리2</option> <option value='G401'>주방조리도구</option> <option value='G402'>식기도구</option> <option value='G403'>주방잡화</option> <option value='G404'>캠핑주방용품</option>"                                                    
$(function() {
	$('#category1').on('change',function() {
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
	
	$('#good_display').on('change', function() {
		if($('#good_display').val()==1){
			$('#good_display_input').removeAttr('disabled')
		}else{
			$('#good_display_input').attr('disabled',true);
		}
	})
	
	$('#check_all').click(function(){
		let check = $('#check_all').is(':checked');
		if(check)
			$('input:checkbox').prop('checked',true);
		else
			$('input:checkbox').prop('checked',false);
	})
	
})