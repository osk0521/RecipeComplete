$(function() {
	// 본인계정 마이페이지 레시피에서 공개중, 작성중 누를 때 요청하는 경로
	$("#div_mypage_recipe_sort1").find("div:nth-child(1)").on("click", function() {
		location.href = "Controller?command=mypage_recipe_view&mode=public";
	});
	$("#div_mypage_recipe_sort1").find("div:nth-child(2)").on("click", function() {
		location.href = "Controller?command=mypage_recipe_view&mode=ing";
	});
	
	// 마이페이지 추가 박스에서 우측 상단 x버튼 누르면 해당 창의 display를 none으로 변경
	$(".button_box_close").on("click", function() {
		$(this).parent().parent().parent().parent().css("display", "none");
		event.stopPropagation();
		// 창을 열면서 변경했던 css속성 원상복귀
		$("html, body").css({"overflow":"visible", "height":""});
		$("body").css("padding-right", "");
	})
	
	// 마이페이지 팔로워 누르면 내 팔로워 보여주는 창의 display를 block로
	$("#div_profile_figures > a:nth-child(3)").on("click", function() {
		$("#div_view_follower").css("display", "block");
		// 메인페이지 스크롤 막기
		$("html, body").css({"overflow":"hidden", "height":"100%"});
		$("body").css("padding-right", "17px");
		// 팔로워 창 열면 기존에 소식받기, 소식끊기로 바뀌어있던 텍스트를 원래상태인 추가, 삭제로 복귀
		$("#div_view_follower .btn_follow_user.add").text("추가");
		$("#div_view_follower .btn_follow_user.delete").text("삭제");
	})
	// 마이페이지 팔로잉 누르면 내 팔로잉 보여주는 창의 display를 block로
	$("#div_profile_figures > a:nth-child(5)").on("click", function() {
		$("#div_view_following").css("display", "block");
		// 메인페이지 스크롤 막기
		$("html, body").css({"overflow":"hidden", "height":"100%"});
		$("body").css("padding-right", "17px");
		// 팔로잉 창 열면 기존에 소식받기, 소식끊기로 바뀌어있던 텍스트를 원래상태인 추가, 삭제로 복귀
		$("#div_view_following .btn_follow_user.add").text("추가");
		$("#div_view_following .btn_follow_user.delete").text("삭제");
	})
	// 마이페이지 팔로워 밖에부분 누르면 창 사라짐
	$("#div_view_follower").on("click", function() {
		$("#div_view_follower").css("display", "none");
		// 창을 열면서 변경했던 css속성 원상복귀
		$("html, body").css({"overflow":"visible", "height":""});
		$("body").css("padding-right", "");
	})
	$("#div_view_follower_box").on("click", function() {
		$("#div_view_follower").css("display", "block");
		event.stopPropagation();
	})
	// 마이페이지 팔로잉 밖에부분 누르면 창 사라짐
	$("#div_view_following").on("click", function() {
		$("#div_view_following").css("display", "none");
		// 창을 열면서 변경했던 css속성 원상복귀
		$("html, body").css({"overflow":"visible", "height":""});
		$("body").css("padding-right", "");
	})
	$("#div_view_following_box").on("click", function() {
		$("#div_view_following").css("display", "block");
		event.stopPropagation();
	})
	
	// 마이페이지 팔로우레시피에서 레시피 위에 마우스를 올리면 초록 테두리가 시간에 걸쳐 생기는 기능
	$(".a_mypage_followrecipe_img").hover(function() { // 마우스에 올렸을 때
		$(this).css("border", "1px solid #46ae4f");
		$(this).css("transition", "0.4s");
	}, function() { // 마우스에서 내렸을 때
		$(".a_mypage_followrecipe_img").css("border", "1px solid #ddd");
		$(".a_mypage_followrecipe_img").css("transition", "0.4s");
	})
	
	// 마이페이지 레시피에서 레시피 위에 마우스를 올리면 초록 테두리가 시간에 걸쳐 생기는 기능
	$(".div_mypage_recipe_item > a").hover(function() { // 마우스에 올렸을 때
		$(this).css("border", "1px solid #46ae4f");
		$(this).css("transition", "0.4s");
	},
	function() { // 마우스에서 내렸을 때
		$(".div_mypage_recipe_item > a").css("border", "1px solid #ddd");
		$(".div_mypage_recipe_item > a").css("transition", "0.4s");
	})
	
	// 마이페이지 팔로우레시피에서 정렬 방식 버튼을 누르면 'sort-active' 클래스가 활성화
	// 해당 버튼의 css가 변하면서 정렬 방식이 바뀜
	{
		let sort_table = $("#div_mypage_followrecipe_header > div > div > button:nth-child(1)");
		let sort_line = $("#div_mypage_followrecipe_header > div > div > button:nth-child(2)");
		
		sort_table.on("click", function() {
			if(!sort_table.hasClass("sort_active")) {
				sort_table.addClass("sort_active");
			}
			if(sort_line.hasClass("sort_active")) {
				sort_line.removeClass("sort_active");
			}
			if($("#div_mypage_followrecipe_content_line").css("display") == "block") {
				$("#div_mypage_followrecipe_content_line").css("display", "none");
			}
			if($("#div_mypage_followrecipe_content_table").css("display") == "none") {
				$("#div_mypage_followrecipe_content_table").css("display", "flex");
			}
		})
		sort_line.on("click", function() {
			if(!sort_line.hasClass("sort_active")) {
				sort_line.addClass("sort_active");
			}
			if(sort_table.hasClass("sort_active")) {
				sort_table.removeClass("sort_active");
			}
			if($("#div_mypage_followrecipe_content_table").css("display") == "flex") {
				$("#div_mypage_followrecipe_content_table").css("display", "none");
			}
			if($("#div_mypage_followrecipe_content_line").css("display") == "none") {
				$("#div_mypage_followrecipe_content_line").css("display", "block");
			}
		})
	}
	
	// 마이페이지 팔로우레시피에서 체크박스를 누르면 두 정렬방식의 같은 개체에 동시에 체크되어야함
	{
		let check = $("input[name=followrecipe_checkbox]"); // 레시피 선택하는 체크박스
		
		check.on("change", function() {
			let val = $(this).val(); // 체크한 input의 value를 저장
			let recipe_id = $("input[value=" + val + "]"); // 다른 정렬방식의 같은 레시피의 input의 value를 찾는 선택자 
			
			if($(this).is(":checked")) { // 체크박스 클릭했을 때 checked상태라면
				recipe_id.each(function(idx, item) {
					if(!$(item).is(":checked")) {
						$(item).prop("checked", true);
					}
				});
			} else { // 체크박스 클릭했을 때 unchecked상태라면
				recipe_id.each(function(idx, item) {
					if($(item).is(":checked")) {
						$(item).prop("checked", false);
					}
				});
			}
		})
	}
	
	// 마이페이지 팔로우레시피에서 전체선택 누르면 input[type:checkbox]의 모든 체크박스가 on, off되는 기능
	{
		let all_select = $("#button_followrecipe_all_select");
		let check = $("input[name=followrecipe_checkbox]");
		
		$(all_select).on("click", function() {
			if(all_select.hasClass("on")) {
				all_select.removeClass("on");
				check.prop("checked", false);
			} else {
				$(all_select).addClass("on");
				check.prop("checked", true);
			}
		})
	}
	
	// 마이페이지 레시피노트에서 쓰레기통 버튼 누르면 해당 게시글 삭제(임시로 display:none)
	{
		let delete_note = $(".mypage_note_header > a:first-child > img");
		
		delete_note.on("click", function() {
			if(confirm("정말 삭제하시겠습니까?")) {
				let recipe_id = Number($(".input_recipe_id").val());
				location.href = "Controller?command=mypage_recipenote_delete&recipeId=" + recipe_id;
			}
		})
	}
	
	// 마이페이지 레시피노트에서 공구버튼 누르면 해당 레시피 노트의 수정창이 등장
	// 레시피노트의 텍스트를 가지고 생성됨
	{
		let update_note = $(".mypage_note_header > a:last-child > img");
		
		update_note.on("click", function() {
			$("#div_mypage_note_update").css("display", "block");
			$("#div_mypage_note_update").addClass("on");
			// 메인페이지 스크롤 막기
			$("html, body").css({"overflow":"hidden", "height":"100%"});
			$("body").css("padding-right", "17px");
			// 노트 수정창에 제목과 내용 채우기
			let note_title = $(this).parent().parent().parent().find(".mypage_note_footer").find("a").find("span").text().trim();
			let note_content = $(this).parent().parent().parent().find(".mypage_note_body").text();
			$(".h4_box_header_title").text(note_title);
			$("#textarea_note_content").text(note_content);
			// 노트 수정창을 열면 해당 노트의 레시피ID를 이식함
			let recipe_id = Number($(this).parent().parent().parent().find(".input_recipe_id").val());
			$("#input_modify_recipe_id").val(recipe_id);
		})
	}
	
	// 저장버튼 누르면 변경된 내용 저장
	{
		$("#div_mypage_note_update_footer > button:nth-child(2)").on("click", function() {
			let recipe_id = Number($("#input_modify_recipe_id").val());
			alert(recipe_id + " " + modify_note_content);
		})
	}
	
	// 마이페이지 레시피노트에서 취소버튼 누르면 해당 레시피 노트의 수정창이 닫힘
	{
		let close_button = $("#div_mypage_note_update_footer > button:first-child");
		
		close_button.on("click", function() {
			$("#textarea_note_content").text("");
			$("#div_mypage_note_update").removeClass("on");
			$("#div_mypage_note_update").css("display", "none");
			event.stopPropagation();
			// 창을 열면서 변경했던 css속성 원상복귀
			$("html, body").css({"overflow":"visible", "height":""});
			$("body").css("padding-right", "");
		})
	}
})

// 마이페이지 팔로우레시피에서 삭제 버튼 누르면 선택된 개수에 따라 confirm창 열림
function delete_followrecipe() {
	if($("input:checkbox[name=followrecipe_checkbox]:checked").length == 0) {
		alert("삭제할 레시피를 선택하세요.");
		return false;
	} else {
		return confirm("삭제하시겠습니까?");
	}
}