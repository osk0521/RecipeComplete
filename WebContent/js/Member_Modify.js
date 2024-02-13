function modify_password() {
	// 비밀번호 변경 메서드
	let pre_pw = $("#previous_password").val(); // 이전 비밀번호
	let new_pw = $("#change_password").val(); // 바꿀 비밀번호
	let new_pw_check = $("#change_password_check").val(); // 바꿀 비밀번호 확인
	
	// 비밀번호 유효성 검사(기존 비밀번호)
	if(pre_pw.length == 0) {
		alert("기존 비밀번호를 입력해 주세요.");
		$("#previous_password").select();
		return;
	}
	// 비밀번호 유효성 검사(신규 비밀번호)
	let reg_pw = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$/ // 영어+숫자 정규식
	// 비밀번호의 길이는 8~20자리
	if(new_pw.length<8 || new_pw.length>20) {
		alert("비밀번호는 8~20자리로 설정해야 합니다.");
		$("#change_password").select();
		return;
	// 비밀번호는 영어+숫자 혼용이어야 함.
	} else if(new_pw.match(reg_pw) == null) {
		alert("비밀번호는 영어와 숫자를 혼용해야 합니다.");
		$("#change_password").select();
		return;
	}
	// 비밀번호확인 유효성 검사 - 비밀번호와 일치해야 함.
	if(new_pw != new_pw_check) {
		alert("비밀번호를 정확히 입력해 주세요.");
		$("#change_password_check").select();
		return;
	}
	// 기존 비밀번호와 일치하는지 검증
	$.ajax({
		url: "Controller",
		type: "post",
		data: {
			"command" : "member_password_check",
			"previous_pw" : pre_pw
		},
		success: function(obj) {
			if(obj.result) {
				// 비밀번호 유효성 검사(기존 비밀번호와 신규 비밀번호의 일치)
				if(pre_pw == new_pw) {
					alert("기존 비밀번호와 같은 비밀번호를 사용할 수 없습니다.");
					return;
				} else {
					// 비밀번호 변경
					$.ajax({
						url: "Controller",
						type: "post",
						data: {
							"command" : "member_modify_password",
							"new_pw" : new_pw
						},
						success: function(obj) {
							if(obj.result) {
								alert("비밀번호가 변경되었습니다.");
								location.href = location.href;
							} else {
								alert("에러가 발생했습니다. 잠시 후에 다시 시도해주세요.");
							}
						},
						error: function(response, status, error) {
							alert("에러코드 : " + response.status);
						}
					})
				}
			} else {
				alert("기존 비밀번호가 올바르지 않습니다.");
			}
		},
		error: function(response, status, error) {
			alert("에러코드 : " + response.status);
		}
	})
}

function modify_nickname() {
	// 닉네임 변경 메서드
	// 화면에서 넘겨받은 닉네임
	let nickname = $("#change_nickname").val();
	// 닉네임 유효성 검사
	if(nickname.length == 0) {
		alert("닉네임을 입력해 주세요.");
		return;
	}
	// 변경하고자 하는 닉네임의 중복 검사
	$.ajax({
		url: "Controller",
		type: "post",
		data: {
			"command" : "member_nickname_check",
			"nickname" : nickname
		},
		success: function(obj) {
			if(obj.result) {
				// 닉네임 중복이 아닐 경우 닉네임 변경
				$.ajax({
					url: "Controller",
					type: "post",
					data: {
						"command" : "member_modify_nickname",
						"nickname" : nickname
					},
					success: function(obj) {
						alert("변경되었습니다.");
						location.href = location.href;
					},
					error: function(response, status, error) {
						alert("에러코드 : " + response.status);
					}
				})
			} else {
				alert("중복된 닉네임입니다. 다른 닉네임을 설정해 주세요.");
			}
		}, 
		error: function(response, status, error) {
			alert("에러코드 : " + response.status);
		}
	})
}

let phone_timer = null; // 휴대폰 인증 타이머 함수를 담을 변수
let email_timer = null; // 이메일 인증 타이머 함수를 담을 변수
let phone_time_out = false; // 휴대폰 인증 타이머 만료(true:만료, false:진행중)
let email_time_out = false; // 이메일 인증 타이머 만료(true:만료, false:진행중)
$(function() {
	$(".div_info_body").on("click", "#send_email", function() {
		// 이메일 인증번호 전송
		let email = $("#change_email");
		// 이메일 유효성 검사
		let reg_email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; // 이메일 정규식
		if(email.val().length == 0) {
			alert("이메일을 입력해 주세요.");
			email.select();
			return;
		} else if(email.val().match(reg_email) == null) {
			alert("이메일을 올바르게 입력해 주세요.");
			email.select();
			return;
		}
		// 이메일 인증번호 발송
		email_time_out = false;
		// 이메일 인증
		$.ajax({
			url: "Controller",
			type: "post",
			data: {
				"command" : "send_email_authentication_msg",
				"toEmail" : email.val()
			},
			success: function(obj) {
				if(obj.result) {
					// 인증번호 전송 버튼 텍스트 변경
					$("#send_email").text("인증하기");
					// 이메일 인증번호 박스 readonly 삭제
					$("#email_authentication").removeAttr("readonly");
					// 인증번호 전송 버튼 id값 변경 
					$("#send_email").attr("id", "authentication_email");
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
									error: function(response, status, error) {
										alert("에러코드 : " + response.status);
									}
								})
							}
						}, 1000);
					}
					email_timer_func();
				} else {
					alert("에러가 발생했습니다. 잠시 후 다시 시도해주세요.");
				}
			},
			error: function(response, status, error) {
				alert("에러코드 : " + response.status);
			}
		})
	});
	$(".div_info_body").on("click", "#authentication_email", function() {
		// 이메일 인증번호 검증
		let authentication = $("#email_authentication");
		// 이메일 인증시간 만료 여부 확인
		if(email_time_out) {
			alert("인증번호가 만료되었습니다. 인증번호를 다시 발송해 주세요.");
			return;
		}
		// 인증번호 유효성 검사
		if(authentication.val().length == 0) {
			alert("인증번호를 입력해 주세요.");
			authentication.select();
			return;
		} else if(authentication.val().length != 4) {
			alert("인증번호를 올바르게 입력해 주세요.");
			authentication.select();
			return;
		}
		
		$.ajax({
			url: "Controller",
			type: "post",
			data: {
				"email_authentication_number" : authentication.val(),
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
					$("#change_email").attr("readonly", "");
					// 이메일 인증번호 변경 불가능하게 막기
					$("#email_authentication").attr("readonly", "");
					// 이메일 중복 인증 방지
					$("#authentication_email").css("display", "none");
					$("#authentication_email").text("확인");
					$("#change_email").css("cursor", "default");
					$("#change_email").css("outline", "none");
					$("#email_authentication").css("cursor", "default");
					$("#email_authentication").css("outline", "none");
				} else {
					alert("인증번호를 다시 입력해 주세요.");
				}
			},
			error: function(response, status, error) {
				alert("에러코드 : " + response.status);
			}
		})
	});
	$(".div_info_body").on("click", "#modify_email", function() {
		// 이메일 변경
		if($("#authentication_email").text() == "확인") {
			let email = $("#change_email").val();
			$.ajax({
				url: "Controller",
				type: "post",
				data: {
					"command" : "member_modify_email",
					"email" : email
				},
				success: function(obj) {
					if(obj.result) {
						alert("변경되었습니다.");
						location.href = location.href;
					} else {
						alert("이메일 변경 실패. 잠시 후에 다시 시도해 주세요.");
					}
				},
				error: function(response, status, error) {
					alert("에러코드 : " + response.status);
				}
			})
		} else {
			alert("이메일 인증을 진행해 주세요.");
		}
	});
	$(".div_info_body").on("click", "#send_phone", function() {
		// 휴대폰 인증번호 전송
		let phone = $("#change_phone");
		// 휴대폰 유효성 검사
		let reg_phone = /^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$/; // 전화번호 정규식
		if(phone.val().length == 0) {
			alert("전화번호를 입력해 주세요.");
			phone.select();
			return;
		} else if(phone.val().match(reg_phone) == null) {
			alert("전화번호를 다시 입력해 주세요.");
			phone.select();
			return;
		}
		// 휴대폰 인증번호 발송
		phone_time_out = false;
		// 휴대폰 인증
		$.ajax({
			url: "Controller",
			type: "post",
			data: {
				"command" : "send_phone_authentication_msg",
				"toPhone" : phone.val()
			},
			success: function(obj) {
				if(obj.statusCode == 2000) {
					// 인증번호 전송 버튼 텍스트 변경
					$("#send_phone").text("인증하기");
					// 휴대폰 인증번호 박스 readonly 삭제
					$("#phone_authentication").removeAttr("readonly");
					// 인증번호 전송 버튼 id값 변경 
					$("#send_phone").attr("id", "authentication_phone");
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
				} else {
					alert("에러가 발생했습니다. 잠시 후에 다시 시도해주세요.");
				}
			},
			error: function(response, status, error) {
				alert("에러코드 : " + response.status);
			}
		})
	});
	$(".div_info_body").on("click", "#authentication_phone", function() {
		// 휴대폰 인증번호 검증
		let authentication = $("#phone_authentication");
		// 이메일 인증시간 만료 여부 확인
		if(phone_time_out) {
			alert("인증번호가 만료되었습니다. 인증번호를 다시 발송해 주세요.");
			return;
		}
		// 인증번호 유효성 검사
		if(authentication.val().length == 0) {
			alert("인증번호를 입력해 주세요.");
			authentication.select();
			return;
		} else if(authentication.val().length != 4) {
			alert("인증번호를 올바르게 입력해 주세요.");
			authentication.select();
			return;
		}
		
		$.ajax({
			url: "Controller",
			type: "post",
			data: {
				"command" : "phone_authentication",
				"phone_authentication_number" : authentication.val()
			},
			success: function(obj) {
				if(obj.result) {
					alert("인증이 완료되었습니다.");
					// 휴대폰 타이머 동작 중지
					clearInterval(phone_timer);
					// 타이머 가리기
					$("#div_phone_timer").css("display", "none");
					// 인증된 phone 변경 불가능하게 막기
					$("#change_phone").attr("readonly", "");
					// 휴대폰 인증번호 변경 불가능하게 막기
					$("#phone_authentication").attr("readonly", "");
					// 휴대폰 중복 인증 방지
					$("#authentication_phone").css("display", "none");
					$("#authentication_phone").text("확인");
					$("#change_phone").css("cursor", "default");
					$("#change_phone").css("outline", "none");
					$("#phone_authentication").css("cursor", "default");
					$("#phone_authentication").css("outline", "none");
				} else {
					alert("인증번호를 다시 입력해 주세요.");
				}
			},
			error: function(response, status, error) {
				alert("에러코드 : " + response.status);
			}
		})
	});
	$(".div_info_body").on("click", "#modify_phone", function() {
		// 휴대폰 변경
		if($("#authentication_phone").text() == "확인") {
			let phone = $("#change_phone").val();
			$.ajax({
				url: "Controller",
				type: "post",
				data: {
					"command" : "member_modify_phone",
					"phone" : phone
				},
				success: function(obj) {
					if(obj.result) {
						alert("변경되었습니다.");
						location.href = location.href;
					} else {
						alert("휴대폰 변경 실패. 잠시 후에 다시 시도해 주세요.");
					}
				},
				error: function(response, status, error) {
					alert("에러코드 : " + response.status);
				}
			})
		} else {
			alert("휴대폰 인증을 진행해 주세요.");
		}
	});
});
