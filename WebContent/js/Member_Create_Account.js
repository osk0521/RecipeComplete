function set_day() {
	// 회원가입 생년월일 선택 중 년도와 월에 따른 일수를 설정하는 함수
	let year = $(".select_year"); // 선택한 년도
	let month = $(".select_month"); // 선택한 월
	let day = $(".select_day"); // 선택한 일
	
	// 한 달에 30일인 달
	if(month.val()=="4" || month.val()=="6" || month.val()=="9"  || month.val()=="11") {
		let day_value = ""; // 선택한 월에 따른 일수
		for(let i=1; i<31; i++) {
			day_value += "<option value=\"" + i + "\">" + i + "일</option>";
		}
		day.html(day_value);
	// 2월인 경우
	} else if(month.val()=="2") {
		let day_value = ""; // 선택한 월에 따른 일수
		if(year.val()%4==0 && year.val()%100!=0 || year.val()%400==0) {
			for(let i=1; i<30; i++) {
				day_value += "<option value=\"" + i + "\">" + i + "일</option>";
			}
			day.html(day_value);
		} else {
			for(let i=1; i<29; i++) {
				day_value += "<option value=\"" + i + "\">" + i + "일</option>";
			}
			day.html(day_value);
		}
	// 나머지(한 달에 31일인 달)
	} else {
		let day_value = ""; // 선택한 월에 따른 일수
		for(let i=1; i<32; i++) {
			day_value += "<option value=\"" + i + "\">" + i + "일</option>";
		}
		day.html(day_value);
	}
}
function check_regist() {
	// 회원가입 유효성 검사 함수
	
	// 아이디 유효성 검사
	let reg_id = /^[A-za-z]/g;
	// 아이디의 길이는 4자 이상
	if($("input[name=id]").val().length < 4) {
		alert("아이디는 4자 이상이어야 합니다.");
		$("input[name=id]").select();
		return false;
	// 아이디의 첫 글자는 영문자로 시작해야 함.
	} else if($("input[name=id]").val().match(reg_id) == null) {
		alert("아이디는 영문자로 시작해야 합니다.");
		$("input[name=id]").select();
		return false;
	}
	
	// 아이디 중복체크 검사
	if($("#test_dupli_id").text() != "확인") {
		alert("아이디 중복체크를 확인해 주세요.");
		$("input[name=id]").select();
		return false;
	}
	
	// 비밀번호 유효성 검사
	let reg_pw = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$/ // 영어+숫자 정규식
	// 비밀번호의 길이는 8~20자리
	if($("input[name=pw]").val().length<8 || $("#input_password").val().length>20) {
		alert("비밀번호는 8~20자리로 설정해야 합니다.");
		$("input[name=pw]").select();
		return false;
	// 비밀번호는 영어+숫자 혼용이어야 함.
	} else if($("input[name=pw]").val().match(reg_pw) == null) {
		alert("비밀번호는 영어와 숫자를 혼용해야 합니다.");
		$("input[name=pw]").select();
		return false;
	}
	
	// 비밀번호확인 유효성 검사 - 비밀번호와 일치해야 함.
	if($("#input_password_check").val() != $("input[name=pw]").val()) {
		alert("비밀번호를 정확히 입력해 주세요.");
		$("#input_password_check").select();
		return false;
	}
	
	// 닉네임 유효성 검사
	if($("input[name=nickname]").val().length == 0) {
		alert("닉네임을 입력해 주세요.");
		$("input[name=nickname]").select();
		return false;
	}
	
	// 닉네임 중복체크 검사
	if($("#test_dupli_nickname").text() != "확인") {
		alert("닉네임 중복체크를 확인해 주세요.");
		$("input[name=nickname]").select();
		return false;
	}
	
	// 전화번호 유효성 검사
	let reg_phone = /^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$/; // 전화번호 정규식
	if($("input[name=phone]").val().length == 0) {
		alert("전화번호를 입력해 주세요.");
		$("input[name=phone]").select();
		return false;
	} else if($("input[name=phone]").val().match(reg_phone) == null) {
		alert("전화번호를 다시 입력해 주세요.");
		$("input[name=phone]").select();
		return false;
	}
	
	// 전화번호 인증 검사
	if($("#send_number_phone").text() != "확인") {
		alert("휴대폰 인증은 필수입니다.");
		$("input[name=phone]").select();
		return false;
	}
	
	// 이메일 유효성 검사
	let reg_email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; // 이메일 정규식
	if($("input[name=email]").val().length == 0) {
		alert("이메일을 입력해 주세요.");
		$("input[name=email]").select();
		return false;
	} else if($("input[name=email]").val().match(reg_email) == null) {
		alert("이메일을 다시 입력해 주세요.");
		$("input[name=email]").select();
		return false;
	}
	
	// 이메일 인증 검사
	if($("#send_number_email").text() != "확인") {
		alert("이메일 인증은 필수입니다.");
		$("input[name=email]").select();
		return false;
	}
	
	// 캡챠 인증 검사
	if($(".naver_captcha").css("display") != "none") {
		alert("캡챠 인증을 진행해 주세요.");
		return false;
	}
	
	// 필수동의사항 유효성 검사
	if($("input[name=agree1]").prop("checked") == false || $("input[name=agree2]").prop("checked") == false) {
		alert("필수동의사항을 전부 동의해 주세요.");
		return false;
	}
	
	return true;
}

function loading_captcha_img() {
	// 네이버 캡챠 이미지 로딩
	$.ajax({
		url: "Controller",
		type: "get",
		data: {
			"command" : "api_captcha_getkey"
		},
		success: function(obj) {
			key = obj.key;
			
			// 발급받은 키값으로 캡챠 이미지 생성
			$.ajax({
				url: "Controller",
				type: "post",
				data: {
					"command" : "api_captcha_getimg",
					"key" : key
				},
				success: function(obj2) {
					let img = obj2.img;
					$(".naver_captcha > img").attr("src", img);
				},
				error: function(r, s, e) {
					alert("[에러] code : " + r.status
						+ " message : " + r.responseText
						+ " error : " + e);
				}
			})
		},
		error: function(r, s, e) {
			alert("[에러] code : " + r.status
				+ " message : " + r.responseText
				+ " error : " + e);
		}
	})
}
let key = null; // 캡챠 발급 키
let phone_timer = null; // 휴대폰 인증 타이머 함수를 담을 변수
let email_timer = null; // 이메일 인증 타이머 함수를 담을 변수
let phone_time_out = false; // 휴대폰 인증 타이머 만료(true:만료, false:진행중)
let email_time_out = false; // 이메일 인증 타이머 만료(true:만료, false:진행중)

$(function() {
	loading_captcha_img();
	
	// 캡챠 이미지 새로고침
	$("#button_request_img").click(function() {
		loading_captcha_img();
		$("#input_captcha_value").val("");
	});
	// 캡챠 확인
	$("#button_submit_captcha").click(function() {
		let value = $("#input_captcha_value").val();
		
		$.ajax({
			url: "Controller",
			type: "post",
			data: {
				"command" : "api_captcha_result",
				"key" : key,
				"value" : value
			},
			success: function(obj3) {
				let result = obj3.result;
				if(result) {
					alert("인증이 완료되었습니다.");
					$(".naver_captcha").css("display", "none");
				} else {
					alert("틀렸습니다. 그림을 새로고침하여 다시 입력해주세요.");
				}
			},
			error: function(r, s, e) {
				alert("[에러] code : " + r.status
					+ " message : " + r.responseText
					+ " error : " + e);
			}
		})
	});
	
	// 홈페이지 로딩 시 생년월일 날짜생성
	set_day();
	// 생년월일 중 년, 월이 바뀔 때마다 날짜생성
	$(".select_year, .select_month").on("change", function() {
		set_day();
	});
	// 동의사항 전체선택
	$("#select_all_agree").on("click", function() {
		if($("#select_all_agree").prop("checked")) {
			$(".input_agree").prop("checked", true);
		} else {
			$(".input_agree").prop("checked", false);
		}
	});
	// input태그에 엔터로 서브밋 막기
	$("input").keydown(function() {
		if(event.keyCode == 13) {
			event.preventDefault();
		}
	});
	
	// 아이디 중복검사 -> 버튼의 글자가 "확인"이 되면 검사 완료
	$("#test_dupli_id").on("click", function() {
		if($(this).text() == "중복검사") {
			let id = $("input[name=id]").val();
			
			// 아이디 유효성 검사
			let reg_id = /^[A-za-z]/g; // 첫 글자는 영문자 정규식
			// 아이디의 길이는 4자 이상
			if(id.length < 4) {
				alert("아이디는 4자 이상이어야 합니다.");
				$("input[name=id]").select();
				return;
			// 아이디의 첫 글자는 영문자로 시작해야 함.
			} else if(id.match(reg_id) == null) {
				alert("아이디는 영문자로 시작해야 합니다.");
				$("input[name=id]").select();
				return;
			}
			
			// 중복검사
			$.ajax({
				url: 'Controller',
				type: 'post',
				data: {
					"command" : "member_id_check",
					"id" : id
				},
				success: function(result) {
					if(result.result) {
						alert("사용 가능한 아이디입니다.");
						$("#test_dupli_id").text("확인");
						// 중복확인된 아이디 변경 불가능하게 막기
						$("input[name=id]").attr("readonly", "");
						$("input[name=id]").css("outline", "none");
						$("input[name=id]").css("cursor", "default");
						$("#test_dupli_id").css("cursor", "default");
					} else {
						alert("이미 사용중인 아이디입니다.");
					}
				},
				error: function(r, s, e) {
					alert("[에러] code : " + r.status
						+ " message : " + r.responseText
						+ " error : " + e);
				}
			})
		}
	});
	
	// 닉네임 중복검사 -> 버튼의 글자가 "확인"이 되면 검사 완료
	$("#test_dupli_nickname").on("click", function() {
		if($(this).text() == "중복검사") {
			let nickname = $("input[name=nickname]").val();
			
			// 닉네임 유효성 검사
			if(nickname.length == 0) {
				alert("닉네임을 입력해 주세요.");
				$("input[name=nickname]").select();
				return;
			}
			
			// 중복검사
			$.ajax({
				url: 'Controller',
				type: 'post',
				data: {
					"command" : "member_nickname_check",
					"nickname" : nickname
				},
				success: function(result) {
					if(result.result) {
						alert("사용 가능한 닉네임입니다.");
						$("#test_dupli_nickname").text("확인");
						// 중복확인된 닉네임 변경 불가능하게 막기
						$("input[name=nickname]").attr("readonly", "");
						$("input[name=nickname]").css("outline", "none");
						$("input[name=nickname]").css("cursor", "default");
						$("#test_dupli_nickname").css("cursor", "default");
					} else {
						alert("이미 사용중인 닉네임입니다.");
					}
				},
				error: function(r, s, e) {
					alert("[에러] code : " + r.status
						+ " message : " + r.responseText
						+ " error : " + e);
				}
			})
		}
	});
	
	// 휴대폰 인증번호 전송하기
	$(".div_input_item").on("click", "#send_number_phone", function() {
		if($("#send_number_phone").text() != "확인") {
			// 전화번호 유효성 검사
			let reg_phone = /^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$/; // 전화번호 정규식
			if($("input[name=phone]").val().length == 0) {
				alert("전화번호를 입력해 주세요.");
				$("input[name=phone]").select();
				return;
			} else if($("input[name=phone]").val().match(reg_phone) == null) {
				alert("전화번호를 다시 입력해 주세요.");
				$("input[name=phone]").select();
				return;
			}
			
			// 화면으로부터 받아온 휴대폰 번호
			let to_phone = $("input[name=phone]").val();
			phone_time_out = false;
			// 휴대폰 인증
			$.ajax({
				url: "Controller",
				type: "post",
				data: {
					"toPhone" : to_phone,
					"command" : "send_phone_authentication_msg"
				},
				success: function(obj) {
					if(obj.statusCode == 2000) {
						if($(".div_input_item").find("#send_number_phone").text() != "확인") {
							// 입력한 휴대폰 번호 변경 불가능하게 막기
							$("input[name=phone]").attr("readonly", "");
							// 인증번호 발송 버튼 텍스트 변경
							$(".div_input_item").find("#send_number_phone").html("인증번호<br/>재발송");
							// 휴대폰 인증번호 박스 readonly 삭제
							$("input[name=phone_authentication]").removeAttr("readonly");
							// 휴대폰 인증 타이머 공개
							$("#div_phone_timer").css("display", "block");
							// 인증타이머 시작(3분 후에 인증번호 session에서 삭제)
							// 인증타이머 시작하는 메서드(3분)
							function phone_timer_func() {
								let time = 180; // 인증만료시간(3분)
								
								phone_timer = setInterval(function() {
									time--;
									let min = Math.floor(time / 60);
									let sec = time - min*60;
									$("#div_phone_timer").text("인증번호 만료시간 : " + min + "분 " + sec + "초")
									if(time == 0) {
										clearInterval(phone_timer);
										phone_time_out = true;
										$("#div_phone_timer").text('시간 만료');
										// 세션에 들어가서 인증값 없애버리기
										$.ajax({
											url: "Controller",
											type: "post",
											data: {
												"command" : "authentication_expire",
												"type" : "phone" // 인증 타입
											},
											success: function() { },
											error: function(response, status, error) {
												alert("에러코드 : " + response.status);
											}
										})
									}
								}, 1000);
							}
							phone_timer_func();
						}
					} else {
						alert("에러가 발생했습니다. 잠시 후에 다시 시도해주세요.");
					}
				},
				error: function(r, s, e) {
					alert("[에러] code : " + r.status
						+ " message : " + r.responseText
						+ " error : " + e);
				}
			})
		}
	});
	// 전송한 휴대폰 인증번호 확인하기
	$(".div_input_item").on("click", "#phone_authentication", function() {
		if($("#phone_authentication").text() != "확인") {
			// 휴대폰 인증시간 만료 여부 확인
			if(phone_time_out) {
				alert("인증번호가 만료되었습니다. 인증번호를 다시 발송해 주세요.");
				return;
			}
			// 인증번호 유효성 검사
			if($("input[name=phone_authentication]").val().length == 0) {
				alert("인증번호를 입력해 주세요.");
				$("input[name=phone_authentication]").select();
				return;
			} else if($("input[name=phone_authentication]").val().length != 4) {
				alert("인증번호를 올바르게 입력해 주세요.");
				$("input[name=phone_authentication]").select();
				return;
			}
			
			let phone_authentication_number = $("input[name=phone_authentication]").val(); 
			$.ajax({
				url: "Controller",
				type: "post",
				data: { 
					"phone_authentication_number" : phone_authentication_number,
					"command" : "phone_authentication"
				},
				success: function(obj) {
					if(obj.result) {
						alert("인증이 완료되었습니다.");
						// 휴대폰 타이머 동작 중지
						clearInterval(phone_timer);
						// 타이머 가리기
						$("#div_phone_timer").css("display", "none");
						// 인증된 휴대폰 번호 변경 불가능하게 막기
						$("input[name=phone]").attr("readonly", "");
						// 휴대폰 인증번호 변경 불가능하게 막기
						$("input[name=phone_authentication]").attr("readonly", "");
						// 휴대폰 중복 인증 방지
						$("#send_number_phone").css("display", "none");
						$("#send_number_phone").text("확인");
						$("#phone_authentication").css("display", "none");
						$("#phone_authentication").text("확인");
						$("input[name=phone]").css("cursor", "default");
						$("input[name=phone]").css("outline", "none");
						$("input[name=phone_authentication]").css("cursor", "default");
						$("input[name=phone_authentication]").css("outline", "none");
					} else {
						alert("인증번호를 다시 입력해 주세요.");
					}
				},
				error: function(r, s, e) {
					alert("[에러] code : " + r.status
						+ " message : " + r.responseText
						+ " error : " + e);
				}
			})
		}
	});
	
	// 이메일 인증번호 전송하기
	$(".div_input_item").on("click", "#send_number_email", function() {
		// 이메일 유효성 검사
		let reg_email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; // 이메일 정규식
		if($("input[name=email]").val().length == 0) {
			alert("이메일을 입력해 주세요.");
			$("input[name=email]").select();
			return;
		} else if($("input[name=email]").val().match(reg_email) == null) {
			alert("이메일을 다시 입력해 주세요.");
			$("input[name=email]").select();
			return;
		}
		
		// 화면으로부터 받아온 이메일 주소
		let email = $("input[name=email]").val();
		email_time_out = false;
		// 이메일 인증
		$.ajax({
			url: "Controller",
			type: "post",
			data: {
				"toEmail" : email,
				"command" : "send_email_authentication_msg"
			},
			success: function(obj) {
				if(obj.result) {
					if($(".div_input_item").find("#send_number_email").text() != "확인") {
						// 인증번호 발송 버튼 텍스트 변경
						$(".div_input_item").find("#send_number_email").html("인증번호<br/>재발송");
						// 이메일 인증번호 박스 readonly 삭제
						$("input[name=email_authentication]").removeAttr("readonly");
						// 이메일 인증 타이머 공개
						$("#div_email_timer").css("display", "block");
						// 인증타이머 시작(3분 후에 인증번호 session에서 삭제)
						// 인증타이머 시작하는 메서드(3분)
						function email_timer_func() {
							let time = 180; // 인증만료시간(3분)
							
							email_timer = setInterval(function() {
								time--;
								let min = Math.floor(time / 60);
								let sec = time - min*60;
								$("#div_email_timer").text("인증번호 만료시간 : " + min + "분 " + sec + "초")
								if(time == 0) {
									clearInterval(email_timer);
									email_time_out = true;
									$("#div_email_timer").text('시간 만료');
									// 세션에 들어가서 인증값 없애버리기
									$.ajax({
										url: "Controller",
										type: "post",
										data: {
											"command" : "authentication_expire",
											"type" : "email" // 인증 타입
										}, 
										success: function() { }, 
										error: function(r, s, e) {
											alert("[에러] code : " + r.status
												+ " message : " + r.responseText
												+ " error : " + e);
										}
									})
								}
							}, 1000);
						}
						email_timer_func();
					}
				} else {
					alert("에러가 발생했습니다. 잠시 후 다시 시도해주세요.");
				}
			},
			error: function(r, s, e) {
				alert("[에러] code : " + r.status
					+ " message : " + r.responseText
					+ " error : " + e);
			}
		})
	});
	// 전송한 이메일 인증번호 확인하기
	$(".div_input_item").on("click", "#email_authentication", function() {
		if($("#email_authentication").text() != "확인") {
			// 이메일 인증시간 만료 여부 확인
			if(email_time_out) {
				alert("인증번호가 만료되었습니다. 인증번호를 다시 발송해 주세요.");
				return;
			}
			// 인증번호 유효성 검사
			if($("input[name=email_authentication]").val().length == 0) {
				alert("인증번호를 입력해 주세요.");
				$("input[name=email_authentication]").select();
				return;
			} else if($("input[name=email_authentication]").val().length != 4) {
				alert("인증번호를 올바르게 입력해 주세요.");
				$("input[name=email_authentication]").select();
				return;
			}
			
			let email_authentication_number = $("input[name=email_authentication]").val(); 
			$.ajax({
				url: "Controller",
				type: "post",
				data: {
					"email_authentication_number" : email_authentication_number,
					"command" : "email_authentication"
				},
				success: function(obj) {
					if(obj.result) {
						alert("인증이 완료되었습니다.");
						// 이메일 타이머 동작 중지
						clearInterval(email_timer);
						// 타이머 가리기
						$("#div_email_timer").css("display", "none");
						// 인증된 이메일 변경 불가능하게 막기
						$("input[name=email]").attr("readonly", "");
						// 이메일 인증번호 변경 불가능하게 막기
						$("input[name=email_authentication]").attr("readonly", "");
						// 이메일 중복 인증 방지
						$("#send_number_email").css("display", "none");
						$("#send_number_email").text("확인");
						$("#email_authentication").css("display", "none");
						$("#email_authentication").text("확인");
						$("input[name=email]").css("cursor", "default");
						$("input[name=email]").css("outline", "none");
						$("input[name=email_authentication]").css("cursor", "default");
						$("input[name=email_authentication]").css("outline", "none");
					} else {
						alert("인증번호를 다시 입력해 주세요.");
					}
				},
				error: function(r, s, e) {
					alert("[에러] code : " + r.status
						+ " message : " + r.responseText
						+ " error : " + e);
				}
			})
		}
	});
});