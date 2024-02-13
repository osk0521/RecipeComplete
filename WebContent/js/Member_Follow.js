let follower_page = Number(1); // 팔로워 첫 로딩을 위한 페이지 번호
let following_page = Number(1); // 팔로잉 첫 로딩을 위한 페이지 번호
function add_follower() {
	// 팔로워 창에서 바닥을 칠 때마다 최대 20개씩 팔로워 요소(html)를 로딩
	let login_id = $("#div_login_button > a").attr("user"); // 현재 로그인되어있는 아이디
	follower_page++;
	
	$.ajax({
		url: "Controller",
		type: "get",
		data: {
			"follower_page" : follower_page,
			"command" : "get_follower"
		},
		success: function(obj) {
			console.log(obj);
			for(let i=0; i<=obj.length-1; i++) {
				let add_li = 
					'<li class="li_follow_user">'
					+ '<a href="Controller?command=mypage_recipe_view&uId=' + obj[i].followerUid + '">'
					+ '<img src="' + obj[i].followerProfileImage + '"/>'
					+ obj[i].followerNickname
					+ '</a>';
				if(login_id != obj[i].followerUid) {
					if(obj[i].isFollowing) {
						add_li += 
							'<button type="button" class="btn_follow_user delete" user="' + obj[i].followerUid + '">'
							+ '삭제'
							+ '</button>'
					} else {
						add_li += 
							'<button type="button" class="btn_follow_user add" user="' + obj[i].followerUid + '">'
							+ '추가'
							+ '</button>';
					}
				}
				add_li += '</li>';
				$("#follower_list").append(add_li);
			}
		},
		error: function(request, status, error) {
			alert("에러코드 : " + request.status);
		}
	});
}
function add_following() {
	// 팔로잉 창에서 바닥을 칠 때마다 최대 20개씩 팔로워 요소(html)를 로딩
	let login_id = $("#div_login_button > a").attr("user"); // 현재 로그인되어있는 아이디
	following_page++;
	
	$.ajax({
		url: "Controller",
		type: "get",
		data: {
			"following_page" : following_page,
			"command" : "get_following"
		},
		success: function(obj) {
			console.log(obj);
			for(let i=0; i<=obj.length-1; i++) {
				let add_li = 
					'<li class="li_follow_user">'
					+ '<a href="Controller?command=mypage_recipe_view&uId=' + obj[i].followingUid + '">'
					+ '<img src="' + obj[i].followingProfileImage + '"/>'
					+ obj[i].followingNickname
					+ '</a>';
				if(login_id != obj[i].followingUid) {
					if(obj[i].isFollowing) {
						add_li += 
							'<button type="button" class="btn_follow_user delete" user="' + obj[i].followingUid + '">'
							+ '삭제'
							+ '</button>'
					} else {
						add_li += 
							'<button type="button" class="btn_follow_user add" user="' + obj[i].followingUid + '">'
							+ '추가'
							+ '</button>';
					}
				}
				add_li += '</li>';
				$("#following_list").append(add_li);
			}
		},
		error: function(request, status, error) {
			alert("에러코드 : " + request.status);
		}
	});
}
$(function() {
	let login_id = $("#div_login_button > a").attr("user"); // 현재 로그인되어있는 아이디
	let is_login = ( (login_id != undefined ? true : false) || (login_id != null ? true : false) ); // 페이지를 로딩할 때 로그인되어있는지 아닌지를 판단
	
	let scroll_height_follower = 1400; // follower 창의 스크롤 높이
	let scroll_height_following = 1400; // following 창의 스크롤 높이
	let add_scroll = 1820; // 스크롤이 바닥을 찍을 때 마다 추가되어야하는 높이
	
	// 팔로워 창 무한스크롤을 이용한 팔로워 로딩
	$("#follower_list").scroll(function(){
	    let scroll_top = $("#follower_list").scrollTop();
		console.log(scroll_top);
		console.log(scroll_height_follower);
		
	    if (scroll_top >= scroll_height_follower) {
			add_follower();
	    	console.log("팔로워 창 바닥침");
			scroll_height_follower += add_scroll;
	    }
	});
	// 팔로잉 창 무한스크롤을 이용한 팔로잉 로딩
	$("#following_list").scroll(function(){
	    let scroll_top = $("#following_list").scrollTop();
		console.log(scroll_top);
		console.log(scroll_height_following);
		
	    if (scroll_top >= scroll_height_following) {
			add_following();
	    	console.log("팔로잉 창 바닥침");
			scroll_height_following += add_scroll;
	    }
	});
	
	// 마이페이지의 팔로워, 팔로잉 모달창에서 추가, 삭제 버튼 눌렀을 때 반응
	{
		if(is_login) { // 내가 로그인중일 때와 아닐 때의 처리를 다르게 해야 함. 쉐프페이지 구현 전까지 검증할 수 없으니 나중에 확인해보자
			$(".ul_follow_list").on("click", '.btn_follow_user', function() {
				let your_uid = $(this).attr("user"); // 팔로잉 관련 작업을 하려는 유저 아이디
				
				if($(this).hasClass("add")) {
					console.log("추가");
					// ajax로 DB에서 member_follow테이블에 데이터 insert후 결과값 받아오기
					$.ajax({
						url: "Controller",
						type: "post", 
						data: {
							"login_id" : login_id,
							"your_uid" : your_uid,
							"command" : "following"
						}, 
						success: function(data) {
							console.log("팔로잉 결과 : " + data.result);
						},
						error: function(request, status, error) {
							alert("에러코드 : " + request.status);
						}
					});
					alert(your_uid + "님을 소식받기에 추가했습니다.");
					$(this).removeClass("add");
					$(this).addClass("delete");
					$(this).text("소식끊기");
				} else if($(this).hasClass("delete")) {
					console.log("삭제");
					// ajax로 DB에서 member_follow테이블에 데이터 delete후 결과값 받아오기
					$.ajax({
						url: "Controller",
						type: "post",
						data: {
							"login_id" : login_id,
							"your_uid" : your_uid,
							"command" : "unfollowing"
						},
						success: function(data) {
							console.log("언팔로잉 결과 : " + data.result);
						},
						error: function(request, stats, error) {
							alert("에러코드 : " + request.status);
						}
					});
					alert(your_uid + "님을 소식받기에서 해제했습니다.");
					$(this).removeClass("delete");
					$(this).addClass("add");
					$(this).text("소식받기");
				}
			});
		} else {
			$(".ul_follow_list").on("click", '.btn_follow_user', function() {
				if(confirm("로그인이 필요한 기능입니다. 로그인 하시겠습니까?")) {
					location.href = "Controller?command=login_form";
				}
			});
		}
	}
	
	// 쉐프 페이지에서 소식받기와 소식해제 버튼 눌렀을 때 반응
	{
		let btn_follow = $(".div_chef_box_user_name > .btn_follow_user");
		
		if(is_login) { // 내가 로그인중일 때와 아닐 때의 처리를 다르게 해야 함.
			btn_follow.on("click", function() {
				let your_uid = $(this).attr("user"); // 팔로잉 관련 작업을 하려는 유저 아이디
				
				if($(this).hasClass("add")) {
					console.log("추가");
					console.log("내 아이디 : " + login_id);
					console.log("상대방 아이디 : " + your_uid);
					// ajax로 DB에서 member_follow테이블에 데이터 insert후 결과값 받아오기
					$.ajax({
						url: "Controller",
						type: "post", 
						data: {
							"login_id" : login_id,
							"your_uid" : your_uid,
							"command" : "following"
						}, 
						success: function(data) {
							console.log("팔로잉 결과 : " + data.result);
						},
						error: function(request, status, error) {
							alert("에러코드 : " + request.status);
						}
					});
					alert(your_uid + "님을 소식받기에 추가했습니다.");
					$(this).removeClass("add");
					$(this).addClass("delete");
					
					let change = "<span class=\"glyphicon glyphicon-minus\"></span> 소식끊기";
					$(this).html(change);
				} else if($(this).hasClass("delete")) {
					console.log("삭제");
					console.log("내 아이디 : " + login_id);
					console.log("상대방 아이디 : " + your_uid);
					// ajax로 DB에서 member_follow테이블에 데이터 delete후 결과값 받아오기
					$.ajax({
						url: "Controller",
						type: "post",
						data: {
							"login_id" : login_id,
							"your_uid" : your_uid,
							"command" : "unfollowing"
						},
						success: function(data) {
							console.log("언팔로잉 결과 : " + data.result);
						},
						error: function(request, stats, error) {
							alert("에러코드 : " + request.status);
						}
					});
					alert(your_uid + "님을 소식받기에서 해제했습니다.");
					$(this).removeClass("delete");
					$(this).addClass("add");
					
					let change = "<span class=\"glyphicon glyphicon-plus\"></span> 소식받기";
					$(this).html(change);
				}
			})
		} else {
			btn_follow.on("click", function() {
				if(confirm("로그인이 필요한 기능입니다. 로그인 하시겠습니까?")) {
					location.href = "Controller?command=login_form";
				}
			});
		}
	}
	
	// 마이페이지 다른사람 계정에 있는 소식받기와 소식해제 버튼 눌렀을 때 반응
	{
		let btn_follow = $("#div_profile_body > .btn_follow_user");
		
		if(is_login) { // 내가 로그인중일 때와 아닐 때의 처리를 다르게 해야 함.
			btn_follow.on("click", function() {
				let your_uid = $(this).attr("user"); // 팔로잉 관련 작업을 하려는 유저 아이디
				
				if($(this).hasClass("add")) {
					console.log("추가");
					console.log("내 아이디 : " + login_id);
					console.log("상대방 아이디 : " + your_uid);
					// ajax로 DB에서 member_follow테이블에 데이터 insert후 결과값 받아오기
					$.ajax({
						url: "Controller",
						type: "post", 
						data: {
							"login_id" : login_id,
							"your_uid" : your_uid,
							"command" : "following"
						}, 
						success: function(data) {
							console.log("팔로잉 결과 : " + data.result);
						},
						error: function(request, status, error) {
							alert("에러코드 : " + request.status);
						}
					});
					alert(your_uid + "님을 소식받기에 추가했습니다.");
					$(this).removeClass("add");
					$(this).addClass("delete");
					
					let change = "<span class=\"glyphicon glyphicon-minus\"></span> 소식끊기";
					$(this).html(change);
				} else if($(this).hasClass("delete")) {
					console.log("삭제");
					console.log("내 아이디 : " + login_id);
					console.log("상대방 아이디 : " + your_uid);
					// ajax로 DB에서 member_follow테이블에 데이터 delete후 결과값 받아오기
					$.ajax({
						url: "Controller",
						type: "post",
						data: {
							"login_id" : login_id,
							"your_uid" : your_uid,
							"command" : "unfollowing"
						},
						success: function(data) {
							console.log("언팔로잉 결과 : " + data.result);
						},
						error: function(request, stats, error) {
							alert("에러코드 : " + request.status);
						}
					});
					alert(your_uid + "님을 소식받기에서 해제했습니다.");
					$(this).removeClass("delete");
					$(this).addClass("add");
					
					let change = "<span class=\"glyphicon glyphicon-plus\"></span> 소식받기";
					$(this).html(change);
				}
			})
		} else {
			btn_follow.on("click", function() {
				if(confirm("로그인이 필요한 기능입니다. 로그인 하시겠습니까?")) {
					location.href = "Controller?command=login_form";
				}
			});
		}
	}
	
//	{
//		if(is_login) { // 내가 로그인중일 때와 아닐 때의 처리를 다르게 해야 함.
//			$(".ul_follow_list").on("click", '.btn_follow_user', function() {
//				let your_uid = $(this).attr("user"); // 팔로잉 관련 작업을 하려는 유저 아이디
//				
//				if($(this).hasClass("add")) {
//					console.log("추가");
//					// ajax로 DB에서 member_follow테이블에 데이터 insert후 결과값 받아오기
//					$.ajax({
//						url: "Controller",
//						type: "post", 
//						data: {
//							"login_id" : login_id,
//							"your_uid" : your_uid,
//							"command" : "following"
//						}, 
//						success: function(data) {
//							console.log("팔로잉 결과 : " + data.result);
//						},
//						error: function(request, status, error) {
//							alert("에러코드 : " + request.status);
//						}
//					});
//					alert(your_uid + "님을 소식받기에 추가했습니다.");
//					$(this).removeClass("add");
//					$(this).addClass("delete");
//					$(this).text("소식끊기");
//				} else if($(this).hasClass("delete")) {
//					console.log("삭제");
//					// ajax로 DB에서 member_follow테이블에 데이터 delete후 결과값 받아오기
//					$.ajax({
//						url: "Controller",
//						type: "post",
//						data: {
//							"login_id" : login_id,
//							"your_uid" : your_uid,
//							"command" : "unfollowing"
//						},
//						success: function(data) {
//							console.log("언팔로잉 결과 : " + data.result);
//						},
//						error: function(request, stats, error) {
//							alert("에러코드 : " + request.status);
//						}
//					});
//					alert(your_uid + "님을 소식받기에서 해제했습니다.");
//					$(this).removeClass("delete");
//					$(this).addClass("add");
//					$(this).text("소식받기");
//				}
//			});
//		} else {
//			$(".ul_follow_list").on("click", '.btn_follow_user', function() {
//				if(confirm("로그인이 필요한 기능입니다. 로그인 하시겠습니까?")) {
//					location.href = "Controller?command=login_form";
//				}
//			});
//		}
//	}
})
