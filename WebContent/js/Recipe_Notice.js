$(function() {
	// 게시글 클릭했을 때 게시글 내용이 슬라이드하며 보여지는 기능. 클릭한 게시글만 내용이 보인다.
	$(".div_inquiry_header > a").on("click", function() {
		$(".div_inquiry_body").slideUp();
		if($(this).parent().parent().find(".div_inquiry_body").css("display") == "none") {
			$(this).parent().parent().find(".div_inquiry_body").slideDown();
		} else {
			$(this).parent().parent().find(".div_inquiry_body").slideUp();
		}
	});
	$(".cs_post_header > a").on("click", function() {
		$(".cs_post_body").slideUp();
		if($(this).parent().parent().find(".cs_post_body").css("display") == "none") {
			$(this).parent().parent().find(".cs_post_body").slideDown();
		} else {
			$(this).parent().parent().find(".cs_post_body").slideUp();
		}
	});
	
	// 문의글 등록버튼 눌렀을 때 로그인 되어있지 않다면 로그인 화면으로 넘김
	$("#div_inquiry_reg_button").click(function() {
		let login_id = $("#a_login_id").attr("user");
		
		if(login_id == "undefined" || login_id == "null") {
			if(confirm("로그인이 필요한 페이지입니다. 로그인 하시겠습니까?")) {
				location.href = "Controller?command=login_form";
			}
		} else {
			location.href = "Controller?command=inquiry_write_form";
		}
	});
	
	// 문의글 수정
	$(".button_inquiry_button.modify").on("click", function() {
		let inquiry_id = Number( $(this).parent().parent().parent().attr("inquiry") );
		location.href = "Controller?command=inquiry_modify_form&inquiry_id=" + inquiry_id;
	})
	// 문의글 삭제
	$(".button_inquiry_button.delete").on("click", function() {
		if(confirm("삭제하시겠습니까?")) {
			let inquiry_id = Number( $(this).parent().parent().parent().attr("inquiry") );
			$.ajax({
				url: "Controller",
				type: "post",
				data: {
					"inquiry_id" : inquiry_id,
					"command" : "inquiry_delete"
				}, 
				success: function(obj) {
					alert("삭제되었습니다.");
					location.href = location.href;
				},
				error: function(response, status, error) {
					alert("에러코드 : " + response.status);
				}
			})
		}
	});
})