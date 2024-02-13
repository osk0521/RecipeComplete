function basic(){
	$("#ViewCookingOrder3").css("display", "block");
	$("#ViewCookingOrder2, #ViewCookingOrder1").css("display", "none");
			
	$("#tabStepView1").attr('src','https://recipe1.ezmember.co.kr/img/mobile/tab_view1.png');
	$("#tabStepView2").attr('src','https://recipe1.ezmember.co.kr/img/mobile/tab_view2.png');
	$("#tabStepView3").attr('src','https://recipe1.ezmember.co.kr/img/mobile/tab_view3_on.png');
}
function add_comments_form() { //댓글의 댓글 쓰기
	$(".reply_to_comment").on("click", function(){
		let parent_comment = $((this).closest("div.comment")).attr("cno");//댓글이 달린 댓들의 id
		console.log(parent_comment);
		/*let parent_comment_id = Number(parent_comment.substring(comment_id_.lastIndexOf("_")+1));
		console.log(parent_comment_id);*/
		let copied_reply_write = $("#re_reply_div_").clone();
		copied_reply_write.removeClass("extra");
		copied_reply_write.addClass("recomment");
		copied_reply_write.attr("id", "reply_comment_div_"+parent_comment)
		copied_reply_write.find("#insert_reply_comment_").attr("id", "insert_reply_comment_"+parent_comment)
		
		copied_reply_write.find("#comment_content_").attr("id", "comment_for_"+parent_comment);
		copied_reply_write.find("#comment_to").attr("id", $("comment_to_"+parent_comment).val());
		$("#comment_id_"+parent_comment).append(copied_reply_write);
		//copied_reply_write.insertAfter("#comment_id_"+parent_comment);
	});
}
function submitNote(){
		
}
function load_new_comment(comment_input){
	let copied_reply = $("#comment_id_").clone();
	copied_reply.attr("id", "comment_id_new");
	copied_reply.removeClass("extra");
	copied_reply.find("#comment_writer_id").attr("id", loginId);
	copied_reply.find("#comment_writer_profileImage").attr("src", myProfileImage);
	comment_input.appendTo(copied_reply.find(".comment-body"));
	copied_reply.prependTo("#recipe_comment_list");
	$("#comment_content").val(""); 	
}

$(function () {
	if(loginId!=null){
		$(".login").css("display", "none");
		$(".not-login").css("display", "flex");
	} else {
		$(".login").css("display", "");
		$(".not-login").css("display", "none");
	}
	$(document).on("click", ".view_comment_more", function() {
		$(this).addClass("open");
    });
	$(document).on("click", "#note_delete", function() {
		//$(this).addClass("open");
    });
	
	$(document).on("click", "#view_less", function() {
		$("#more_view_comment_list").slideUp();
		$("#view_more").css("display", "block");
		$(this).css("display", "none");
    });
	$(document).on("click", "#view_more", function() {
		$("#more_view_comment_list").slideDown();
		$("#view_more").css("display", "block");
		$(this).css("display", "none");
    });
	$(document).on("click", ".reply_to_comment", function() {
		add_comments_form();
		
    });

	$(document).on("click", ".comment_delete", function() {
		 if(confirm("정말 삭제할까요?")){
			//var commentParent = ($(this).parents('.comment')).attr("cno");
			let cno = ($(this).parents('.comment')).attr("cno");
			console.log(cno);
				$.ajax({
					url: 'Controller',
					type: 'post',
					data: {
						"cno":cno,
						"command":"recipe_comment_delete"
					},
					success: function(data) {
	                	if(data.result=='OKAY') {
	                    	alert("삭제되었습니다.");
		                    /*
							$("#recipe_comment_list").each(function(index, item) {
		                        if($(item).attr("cno")==cno) {
		                        	$(item).remove();
		                    	}
	                    	})
	                    	$("#recipe_comment_list > div[cno="+cno+"]").remove();
		                    */
	                    	location.reload();
	                    }
	               },
				error: function(request, status, error) {
					alert("에러 코드 : " + request.status);
				}
			});
		 } else {
			 return false;
		 }
	});
	$(document).on("click", ".comment_submit", function() {
		comment_input_btn = $(this);
		let parent_comment = $((this).closest("div.recomment")).attr("id");
		let comment_to = 0;
		console.log(loginId);
		console.log(parent_comment);
		if(loginId == null){
			if(confirm("로그인이 필요합니다. \r\n\r\n로그인 하시겠습니까?")) {
				location.href = "Controller?command=login_form";
			} else {
				return false;
			}
		} else { 
			if(parent_comment == null || parent_comment == 0 ){ 
				let comment_input = $("#comment_content").val();
				console.log(comment_to);
				console.log(comment_input);
				$.ajax({ 
					type: "POST",
					url: "Controller",
					data: {
						"command" : "recipe_comment_write_action",
						"content": comment_input, 
						"loginId" : loginId,
						"recipeID" : recipeID,
						"commentTo" : comment_to,
						"image" : null
					},
			        success: function (data) {
			            if (data.result == "success") {
			            	load_new_comment(comment_input);
							/*commentInput.val("");*/
			            }
			        },
			        error: function (request, status, error) {
			            alert("code: " + request.status + "\n" + "error: " + error);
			        }
			    });
			} else {
				comment_to = Number(parent_comment);
				console.log(comment_to);
				let content = $("comment_to_"+comment_to).val();
				$.ajax({
			        type: "post",
			        url: "Controller",

			        data: {
						"command" : "recipe_comment_write_action",
						"content": content,
						"loginId" : loginId,
						"recipeID" : recipeID,
						"comment_to" : comment_to, 
						"image" : null
					},
			        success: function (data) {
			            if (data.result == "success") {
			            	let copied_reply = $("#comment_id_").clone();
							copied_reply.attr("id", "comment_id_new");
							copied_reply.removeClass("extra");
							copied_reply.find("#comment_writer_id").attr("id", loginId);
							copied_reply.find("#comment_writer_profileImage").attr("src", myProfileImage);
							comment_input.appendTo(copied_reply.find(".comment-body"));
							copied_reply.prependTo("#recipe_comment_list");
							//152부터 대댓에 맞게 수정
			                commentInput.val(""); 
			            } else if (data.result == "FAIL") {
			            }
			        },
			        error: function (request, status, error) {
			        	console.log("댓글 등록 실패");
			            alert("code: " + request.status + "\n" + "error: " + error);
			        }
			    });
			}
	     } 
	});
	$("#viewStep_btn_1").click(function() {
		$("#ViewCookingOrder1").css("display", "block");
		$("#ViewCookingOrder2, #ViewCookingOrder3").css("display", "none");
		
		$("#tabStepView1").attr('src','https://recipe1.ezmember.co.kr/img/mobile/tab_view1_on.png');
		$("#tabStepView2").attr('src','https://recipe1.ezmember.co.kr/img/mobile/tab_view2.png');
		$("#tabStepView3").attr('src','https://recipe1.ezmember.co.kr/img/mobile/tab_view3.png');
	});
	
	$("#viewStep_btn_2").click(function() {
		$("#ViewCookingOrder2").css("display", "block");
		$("#ViewCookingOrder1, #ViewCookingOrder3").css("display", "none");
	
		$("#tabStepView1").attr('src','https://recipe1.ezmember.co.kr/img/mobile/tab_view1.png');
		$("#tabStepView2").attr('src','https://recipe1.ezmember.co.kr/img/mobile/tab_view2_on.png');
		$("#tabStepView3").attr('src','https://recipe1.ezmember.co.kr/img/mobile/tab_view3.png');
	});
	
	$("#viewStep_btn_3").click(function() {
		basic();
	});
	$("#btn_measure_modal").click(function() {
		$("#measure_modal").show();
		$("#measure_modal").css("overflow", "auto");
		$("body").css("overflow", "hidden");
	});
	$(".btn_copy_recipe_id").click(function() {
		$("#copyRecipeId").show();
	});
	$(".add_tool").click(function() {
		$("#search_equipment").css("display", "block");
	});
	
	$(".btn_memo, #note_modify").click(function() {
		$("#recipeMemo").css("display", "block");
	});
	//모달 창 닫기
	$(".close, .close_2, .cancle, #close").click(function() {
		$("body").css("overflow", "");
        $("#copyRecipeId").css("display", "none");
        $("#recipeMemo").css("display", "none");
        $("#measure_modal").css("display", "none");
        $("#search_equipment").css("display", "none");
    });
	basic();
});