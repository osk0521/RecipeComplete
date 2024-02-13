$(function() {
	// 마이페이지 팔로우창에서 소식받기와 소식해제 버튼 눌렀을 때 반응
	// alert로 소식받기와 소식해제를 알리고 박스 안의 텍스트를 상반되는 내용으로 변경해야함
	{
		let btn_follow = $(".ul_follow_list > .li_follow_user > .btn_follow_user");
		
		btn_follow.on("click", function() {
			console.log("제발 돼라");
			if($(this).hasClass("add")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에 추가했습니다.");
				$(this).removeClass("add");
				$(this).addClass("delete");
				$(this).text("소식끊기");
			} else if($(this).hasClass("delete")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에서 해제했습니다.");
				$(this).removeClass("delete");
				$(this).addClass("add");
				$(this).text("소식받기");
			}
		})
	}
	
	// 쉐프 페이지에서 소식받기와 소식해제 버튼 눌렀을 때 반응
	// 내부에 들어가는 html이 다를 뿐 전체적인 기능은 같음
	{
		let btn_follow = $(".div_chef_box_user_name > .btn_follow_user");
		
		btn_follow.on("click", function() {
			if($(this).hasClass("add")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에 추가했습니다.");
				$(this).removeClass("add");
				$(this).addClass("delete");
				
				let change = "<span class=\"glyphicon glyphicon-minus\"></span> 소식끊기";
				$(this).html(change);
			} else if($(this).hasClass("delete")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에서 해제했습니다.");
				$(this).removeClass("delete");
				$(this).addClass("add");
				
				let change = "<span class=\"glyphicon glyphicon-plus\"></span> 소식받기";
				$(this).html(change);
			}
		})
	}
	
	// 마이페이지 다른사람 계정에 있는 소식받기와 소식해제 버튼 눌렀을 때 반응
	{
		let btn_follow = $("#div_profile_body > .btn_follow_user");
		
		btn_follow.on("click", function() {
			if($(this).hasClass("add")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에 추가했습니다.");
				$(this).removeClass("add");
				$(this).addClass("delete");
				
				let change = "<span class=\"glyphicon glyphicon-minus\"></span> 소식끊기";
				$(this).html(change);
			} else if($(this).hasClass("delete")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에서 해제했습니다.");
				$(this).removeClass("delete");
				$(this).addClass("add");
				
				let change = "<span class=\"glyphicon glyphicon-plus\"></span> 소식받기";
				$(this).html(change);
			}
		})
	}
	
	// 마이페이지 팔로우쉐프의 소식받기와 소식해제 버튼 눌렀을 때 반응
	{
		let btn_follow = $(".div_mypage_chef_user_name > .btn_follow_user");
		
		btn_follow.on("click", function() {
			if($(this).hasClass("add")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에 추가했습니다.");
				$(this).removeClass("add");
				$(this).addClass("delete");
				
				let change = "<span class=\"glyphicon glyphicon-minus\"></span> 소식받기 해제";
				$(this).html(change);
			} else if($(this).hasClass("delete")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에서 해제했습니다.");
				$(this).removeClass("delete");
				$(this).addClass("add");
				
				let change = "<span class=\"glyphicon glyphicon-plus\"></span> 소식받기";
				$(this).html(change);
			}
		})
	}
	
	// 알림페이지 쉐프추천의 소식받기와 소식해제 버튼 눌렀을 때 반응
	{
		let btn_follow = $(".mypage_recommendChef_item > .btn_follow_user");
		
		btn_follow.on("click", function() {
			if($(this).hasClass("add")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에 추가했습니다.");
				$(this).removeClass("add");
				$(this).addClass("delete");
				$(this).text("- 소식끊기");
			} else if($(this).hasClass("delete")) {
				let uId = "해당 유저의 닉네임";
				alert(uId + "님을 소식받기에서 해제했습니다.");
				$(this).removeClass("delete");
				$(this).addClass("add");
				$(this).text("+ 소식받기");
			}
		})
	}
})
