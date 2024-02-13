function add_material_group() {
	$("#add_ingrede_group").on("click", function(){
		var last_material_group_id = $("#div_material_group >.li_material_group:last-child").attr("id");
		let idx = Number(last_material_group_id.substring(last_material_group_id.lastIndexOf("_")+1));
		let new_material_group_id_num = idx+1;
		let copied_ingrede_group = $("#li_material_group_extra").clone();
		
		copied_ingrede_group.css("display", "");
		copied_ingrede_group.removeClass("extra");
		copied_ingrede_group.find("li.li_material").removeClass("extra");
		copied_ingrede_group.find("li#li_material_extra").attr("id","li_material_"+new_material_group_id_num+"_1");
		
		copied_ingrede_group.attr("id", "li_material_group_"+new_material_group_id_num);
		
		copied_ingrede_group.find(".material_group_list").attr("id","material_group_list_"+new_material_group_id_num);
		copied_ingrede_group.find("#material_group_title_").attr("id","material_group_title_"+new_material_group_id_num);
		copied_ingrede_group.find("#material_group_title_"+new_material_group_id_num).attr("name","material_group_title_"+new_material_group_id_num);
		
		copied_ingrede_group.find("#add_ingredi_"+idx).attr("id","add_ingredi_"+new_material_group_id_num);

		//let copied_ingrede = copied_ingrede_group.find("li#li_material_"+new_material_group_id_num);
		
		copied_ingrede_group.find("input.material_nm").attr("name", "material_nm_"+new_material_group_id_num+"_1");
		copied_ingrede_group.find("input.material_amt").attr("name", "material_amt_"+new_material_group_id_num+"_1");
		copied_ingrede_group.appendTo("#div_material_group");
	});
}

function del_material_group() {
	$(".del_ingrede_group").on("click", function(){
		$(this).parent().parent().parent().remove();
    });
	//.div_material_group 내부의 자식 재정의
}

function add_step() {
    $("#add_step").on("click", function(){
		//#step_list의 자손들 중 클래스가 step인 마지막 아이
		var last_step_id_string = $("#step_list .step").last().attr("id");
		var last_step_id_num = parseInt(last_step_id_string.replace("div_step_item_", ""));
		var new_step_id_num = last_step_id_num+1;//ex)2
		var copied_step  = $(".step_list_extra").children().clone();
		
		copied_step.css("display", "");
		(copied_step.attr("id","step_"+new_step_id_num));
		(copied_step.find(".step")).attr("id","div_step_item_"+new_step_id_num);
		(copied_step.find(".step_num")).attr("id","div_step_num_"+new_step_id_num);
		
		(copied_step.find("p")).text("Step"+new_step_id_num);
		
		(copied_step.find("#div_step_text")).attr("id","div_step_text_"+new_step_id_num);
		
		(copied_step.find("#process_text")).attr("name","process_text_"+new_step_id_num);
		(copied_step.find("#process_text")).attr("id","process_text_"+new_step_id_num);
		
		(copied_step.find("#div_step_upload")).attr("name","div_step_upload_"+new_step_id_num);
		(copied_step.find("#div_step_upload")).attr("id","div_step_upload_"+new_step_id_num);
		
		(copied_step.find("#step_photo_upload_")).attr("name","step_photo_upload_"+new_step_id_num);
		(copied_step.find("#step_photo_upload_")).attr("id","step_photo_upload_"+new_step_id_num);
		
		(copied_step.find("#step_photo_holder")).attr("name","process_image_"+new_step_id_num);
		(copied_step.find("#step_photo_holder")).attr("id","step_photo_holder_"+new_step_id_num);
	
		(copied_step.find(".step_advice")).attr("id","step_advice_"+new_step_id_num);
		
		(copied_step.find("#step_material")).attr("name","step_material_"+new_step_id_num);
		(copied_step.find("#step_material")).attr("id","step_material_"+new_step_id_num);
		
		(copied_step.find("#step_cooker")).attr("name","step_cooker_"+new_step_id_num);
		(copied_step.find("#step_cooker")).attr("id","step_cooker_"+new_step_id_num);
		
		(copied_step.find("#step_fire")).attr("name","step_fire_"+new_step_id_num);
		(copied_step.find("#step_fire")).attr("id","step_fire_"+new_step_id_num);
		
		(copied_step.find("#step_tip")).attr("id","step_tip_"+new_step_id_num);
		(copied_step.find("#step_tip_"+new_step_id_num)).attr("name","step_tip_"+new_step_id_num);
		(copied_step.find("#input_file_button_for_step_")).attr("for","step_photo_upload_"+new_step_id_num);
		(copied_step.find("#input_file_button_for_step_")).attr("id","input_file_button_for_step_"+new_step_id_num);
		
		$(copied_step).appendTo(".step_list");
   });
}

function update_material_numbers(parent_list) {
	
	parent_list.find("li").each(function(idx, item) {
		let id = $(item).attr("id");
		id = id.substring(0, id.lastIndexOf("_")+1);  // "li_material_1_"
		id += (idx+1);
		$(item).attr("id", id);
		
		// <input type="text" name="material_nm_1_1"
		let name = $(item).find(".material_nm").attr("name");
		name = name.substring(0, name.lastIndexOf("_")+1);  // "material_nm_1_"
		name += (idx+1);
		$(item).find(".material_nm").attr("name", name);
		
		// <input type="text" name="material_amt_1_1"
		name = $(item).find(".material_amt").attr("name");
		name = name.substring(0, name.lastIndexOf("_")+1);  // "material_amt_1_"
		name += (idx+1);
		$(item).find(".material_amt").attr("name", name);
	});
}

function update_material_group_numbers(parent_group) {
	parent_group.find("li.li_material_group").each(function(idx, item) {
		
		// <li id="li_material_1_1"
		let id = $(item).attr("id");
		id = id.substring(0, id.lastIndexOf("_")+1);  // "li_material_group_"
		id += (idx+1);
		$(item).attr("id", id);
		
		// <input type="text" name="material_nm_1_1"
		let name = $(item).find(".material_nm").attr("name");
		
		name = name.substring(0, name.lastIndexOf("_")+1);  // "material_nm_1_"
		name += (idx+1);
		$(item).find(".material_nm").attr("name", name);
		
		// <input type="text" name="material_amt_1_1"
		name = $(item).find(".material_amt").attr("name");
		name = name.substring(0, name.lastIndexOf("_")+1);  // "material_amt_1_"
		name += (idx+1);
		$(item).find(".material_amt").attr("name", name);
	});
}

function update_step_numbers(parent_list) {
	parent_list.each(function(idx, item) {
	    let id = ""+$(item).find(".div_step").attr("id");
	    id = id.substring(0, id.lastIndexOf("_")+1);
	    id += (idx+1);
	    $(item).find(".div_step").attr("id", id);
	
	    let step_id = ""+$(item).find("div.step").attr("id");
	    step_id = step_id.substring(0, step_id.lastIndexOf("_")+1);
	    step_id += (idx+1);
	    $(item).find("div.step").attr("id", step_id);
	
	    let step_num_id = ""+$(item).find("#step_num").attr("id");
	    step_num_id = step_num_id.substring(0, step_num_id.lastIndexOf("_")+1);
	    step_num_id += (idx+1);
	    $(item).find("#step_num").attr("id", step_num_id);
	
	    let step_cont_name = ""+$(item).find(".step_cont").attr("name");
	    step_cont_name = step_cont_name.substring(0, step_cont_name.lastIndexOf("_")+1);
	    step_cont_name += (idx+1);
	    $(item).find(".step_cont").attr("name", step_cont_name);
	});
}

$(function () {
	//add_material();	//단일 재료 추가
	add_material_group();//재료 목록 추가
	add_step();//조리순서 추가
	
	$(document).on("mouseenter", ".btn-del", function() {
		$(this).css("opacity", "1");
	});
	$(document).on("mouseleave", ".btn-del", function() {
		$(this).css("opacity", "0");
	});
	
	$(document).on("click", ".btn-del", function() {//단일 재료 삭제
		let parent_list = $(this).parents("ul.material_group_list");
		$(this).parent().remove();
		update_material_numbers(parent_list);
	});
	
	$(document).on("click", ".add_ingredi", function() {//단일 재료 추가
	//부모들 중 가장 가까운 li태그의 id값 받아오기 
		var parent_id = $(this).closest("li").attr("id");//ex)li_material_group_1
		let idx = Number(parent_id.substring(parent_id.lastIndexOf("_")+1));//ex)1
	// #material_group_list_?의 마지막 자식의 아이디를 last_id_string에 저장
		var last_id_string = $("#material_group_list_"+idx + ">.li_material:last-child").attr("id");
		//alert(last_id_string);
		let idx2 = Number(last_id_string.substring(last_id_string.lastIndexOf("_")+1));  // 1
		last_id_string = last_id_string.substring(0, last_id_string.lastIndexOf("_")+1); // "li_material_1_"
		last_id_string += (idx2+1); // "li_material_1_4"
		
		// last_id_string을 "li_material_"+parent_id+"_"와 분리하여 변수 i에 저장
		//var last_id_num = parseInt(last_id_string.replace("li_material_"+parent_id+"_", ""));
		
		var copied_material  = $("#li_material_extra").clone();
		copied_material.css("display", "");
		copied_material.removeClass("extra");
		copied_material.attr("id", "li_material_"+idx+"_"+(idx2+1));
		//copied_material의 자식 중 input태그 찾아  이름 재정의
		//alert(idx, (idx+1));
		copied_material.children(":nth-child(1)").attr("name", "material_nm_"+idx+"_"+(idx2+1));
		copied_material.children(":nth-child(2)").attr("name", "material_amt_"+idx+"_"+(idx2+1));
		copied_material.appendTo("#material_group_list_"+idx);
	});
	
	$(document).on("click", ".del_ingrede_group", function() {// 재료 목록 삭제
		let parent_group = $(this).parents("#div_material_group");
		$(this).parents(".li_material_group").remove();
		update_material_group_numbers(parent_group);
	});
	
	$(document).on("click", ".del_step", function() {// 조리순서 삭제
		let parent_list = $(this).parents(".step_list");
		$(this).closest(".div_step").remove();
		update_step_numbers(parent_list);
	});
	
	// 요리 팁 추가
		var advice_list;
	$(document).on("click", ".step_btn_material", function() {
		advice_list = $(this).parents("div.step_advice");
		advice_list.css("display", "");
		advice_list.find(".step_form_material").css("display", "block");
    });
	$(document).on("click", ".step_btn_cooker", function() {
		advice_list = $(this).parents("div.step_advice");
		advice_list.css("display", "");
		advice_list.find(".step_form_cooker").css("display", "block");
    });
	$(document).on("click", ".step_btn_fire", function() {
		advice_list = $(this).parents("div.step_advice");
		advice_list.css("display", "");
		advice_list.find(".step_form_fire").css("display", "block");
    });
	$(document).on("click", ".step_btn_tip", function() {
		advice_list = $(this).parents("div.step_advice");
		advice_list.css("display", "");
		advice_list.find(".step_form_tip").css("display", "block");
    });
	$(document).on("click", ".step_btn_all", function() {
		advice_list = $(this).parents("div.step_advice");
		advice_list.css("display", "");
		advice_list.find(".step_form_material").css("display", "block");
		advice_list.find(".step_form_cooker").css("display", "block");
		advice_list.find(".step_form_fire").css("display", "block");
		advice_list.find(".step_form_tip").css("display", "block");
    });

	$(document).on("click", ".save_public", function() {
		$("#save_type").val("save_public");
    });
	$(document).on("click", ".save_private", function() {
		$("#save_type").val("save_private");
    });
	
});






