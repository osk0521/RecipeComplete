$(function() {
	// 로그인 유저 프로필 이미지
	let profile_image = $("#a_profile_img > img").attr("src"); 
	$("#input_previous_image").val(profile_image);
	// 로그인 유저 자기소개
	let self_introduce = $("#div_profile_user_introduce > a > u").text().trim();
	// 자기소개가 비어있을 때 
	if(self_introduce == "자기소개를 입력할 수 있습니다.") {
		self_introduce = "";
	}
	
	// 프로필 수정창 열었을 때 기존의 자기소개와 프로필 이미지로 내용 갱신
	function load_profile() {
		$("#profile_img_preview").attr("src", profile_image);
		$("input[name=self_intro]").val(self_introduce);
	}
	
	// 마이페이지에서 프로필사진이나 자기소개를 클릭하면 마이페이지 프로필사진 변경창의 display를 block으로 변경
	$("#div_profile_body").on("click", "#div_profile_img", function() {
		$("#div_profile_change").css("display", "block");
		load_profile();
		// 메인페이지 스크롤 막기
		$("html, body").css({"overflow":"hidden", "height":"100%"});
		$("body").css("padding-right", "17px");
	})
	$("#div_profile_user_introduce").on("click", "a", function() {
		$("#div_profile_change").css("display", "block");
		load_profile();
		// 메인페이지 스크롤 막기
		$("html, body").css({"overflow":"hidden", "height":"100%"});
		$("body").css("padding-right", "17px");
	})
	
	// 마이페이지 프로필사진 변경창에서 취소버튼 누르면 변경창의 display를 none으로 변경
	$("#div_change_confirm > button:first-child").on("click", function() {
		$("#div_profile_change").css("display", "none");
		event.stopPropagation();
		// 창을 열면서 변경했던 css속성 원상복귀
		$("html, body").css({"overflow":"visible", "height":""});
		$("body").css("padding-right", "");
	})
	
	// 마이페이지 프로필사진 변경창 밖을 클릭하면 변경창의 display를 none으로 변경
	$("#div_profile_change").on("click", function() {
		$("#div_profile_change").css("display", "none");
		// 창을 열면서 변경했던 css속성 원상복귀
		$("html, body").css({"overflow":"visible", "height":""});
		$("body").css("padding-right", "");
	})
	$("#div_profile_change_box").on("click", function(event) {
		$("#div_profile_change").css("display", "block");
		event.stopPropagation();
	})
	
	// 마이페이지 추가 박스에서 우측 상단 x버튼 누르면 해당 창의 display를 none으로 변경
	$(".button_box_close").on("click", function() {
		$(this).parent().parent().parent().parent().css("display", "none");
		event.stopPropagation();
		// 창을 열면서 변경했던 css속성 원상복귀
		$("html, body").css({"overflow":"visible", "height":""});
		$("body").css("padding-right", "");
	})
	
	// x버튼 누르면 자기소개 삭제
	$("#div_text_input > a").click(function() {
		$("input[name=self_intro]").val("");
	});
	
	// 변경할 프로필 이미지 미리보기
	var sel_file;
 
    $("#input_file_button").on("change", function(e) {
        var files = e.target.files;
        var filesArr = Array.prototype.slice.call(files);
 
        var reg = /(.*?)\/(jpg|jpeg|png|bmp)$/;
 
        filesArr.forEach(function(f) {
            if (!f.type.match(reg)) {
                alert("확장자는 이미지 확장자만 가능합니다.");
                return;
            }
 
            sel_file = f;
 
            var reader = new FileReader();
            reader.onload = function(e) {
                $("#profile_img_preview").attr("src", e.target.result);
            }
            reader.readAsDataURL(f);
        });
	});
	
//	// 프로필 변경 ajax
//	$("#btn_change_profile").click(function() {
//		var form = new FormData();
//        form.append( "file1", $("#input_file_button")[0].files[0] );
//        
//        $.ajax({
//        	url: "ModifyProfileServlet",
//            type : "post",
//            processData : false,
//            contentType : false,
//            data : form,
//            success:function(response) {
//            	alert("성공하였습니다.");
//            	console.log(response);
//            },
//            error: function (jqXHR) 
//            { 
//                alert(jqXHR.responseText); 
//            }
//        });
//	});
	
	// 쓰레기통 버튼 누르면 프로필 이미지 미리보기가 기존 프로필 이미지로 변경
	$("#div_img_change_box_button").on("click", "a > img", function() {
		$("#input_file_button").val("");
		$("#profile_img_preview").attr("src", profile_image);
	});
})