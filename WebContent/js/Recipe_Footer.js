$(function() {
	// 최근 본 레시피에서 레시피 위에 마우스를 올리면 초록 테두리가 시간에 걸쳐 생기는 기능
	$(".rr_recipe_thumbnail").hover(function() { // 마우스에 올렸을 때
		$(this).css("border", "1px solid #46ae4f");
		$(this).css("transition", "0.4s");
	},
	function() { // 마우스에서 내렸을 때
		$(".rr_recipe_thumbnail").css("border", "1px solid #ddd");
		$(".rr_recipe_thumbnail").css("transition", "0.4s");
	});
	
	// footer 하단 우측에 의견제출하는 구간에서 문의사항 게시글 작성하는 기능
	$("#f_send_cs_button").click(function() {
		let login_id = $("#a_login_id").attr("user");
		// 로그인이 되어있지 않으면 로그인을 요청한다.
		if(login_id == undefined || login_id == null) {
			if(confirm("로그인이 필요한 기능입니다. 로그인 하시겠습니까?")) {
				location.href = "Controller?command=login_form";
			}
		} else {
			let inquiry_content = $("#f_customer_send").val();
			// 문의내용이 하나도 없다면 의견제출 막기
			if(inquiry_content.length == 0) {
				alert("의견을 작성해주세요.");
			} else {
				// 입력되어있는 내용을 문의게시글로 등록
				$.ajax({
					url: "Controller",
					type: "post",
					data: {
						"inquiry_content" : inquiry_content,
						"command" : "inquiry_write_simple"
					},
					success: function(obj) {
						if(obj.result) {
							alert("의견이 제출되었습니다.");
							$("#f_customer_send").val("");
						}
					},
					error: function(response, status, error) {
						alert("에러코드 : " + response.status);
					}
				})
			}
		}
	});
})