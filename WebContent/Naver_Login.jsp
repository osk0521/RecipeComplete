<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
	var naverLogin = new naver.LoginWithNaverId(
		{
			clientId: "24lrJuBOFgCE8vHfYjPx",
			callbackUrl: "http://localhost:9090/Recipe/Controller?command=naver_login",
			isPopup: false,
			callbackHandle: true
			/* callback 페이지가 분리되었을 경우에 callback 페이지에서는 callback처리를 해줄수 있도록 설정합니다. */
		}
	);
	naverLogin.init();
	
	window.addEventListener('load', function() {
		naverLogin.getLoginStatus(function (status) {
			if (status) {
				const naverid = naverLogin.user.getId();
				const email = naverLogin.user.getEmail();
				const nickname = naverLogin.user.getNickName();
				const phone = naverLogin.user.getMobile();
				const birthyear = naverLogin.user.getBirthyear();
				const birthday = naverLogin.user.getBirthday();
				const naverLoginAccessToken = naverLogin.accessToken.accessToken;

				$.ajax({
					url: "Controller",
					type: "post",
					data: {
						"command" : "member_check",
						"id" : naverid,
					},
					success: function(obj) {
						if(obj.idExists) {
							// 히든 로그인 폼 만들어서 로그인처리
							createHiddenLoginForm(naverid, "naver", naverLoginAccessToken);
						} else {
							// 회원가입 ajax처리
							$.ajax({
								url: "Controller",
								type: "post",
								data: {
									"command" : "naver_regist",
									"id" : naverid,
									"email" : email,
									"nickname" : nickname,
									"phone" : phone,
									"birth" : birthyear + "-" + birthday
								},
								success: function(obj) {
									// 회원가입 처리 이후 히든 로그인 폼 만들어서 로그인처리
									if(obj.success){
										createHiddenLoginForm(naverid, "naver", naverLoginAccessToken);
		    						} else {
		    							alert('네이버 회원가입 실패. 일반계정으로 로그인하시기 바랍니다.');
		    						}
								},
								error: function(request, status, error) {
									alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
								}
							})
						}
					},
					error: function(request, status, error) {
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
				})
			} else {
				console.log("네이버 로그인 대기중");
			}
		});
	});
	
	function createHiddenLoginForm(id, type, naverLoginAccessToken) {
		var frm = document.createElement('form');
		frm.setAttribute('method', 'post');
		frm.setAttribute('action', 'Controller');
		
		var hiddenInput = document.createElement('input');
		hiddenInput.setAttribute('type','hidden');
		hiddenInput.setAttribute('name','id');
		hiddenInput.setAttribute('value',id);
		frm.appendChild(hiddenInput);
		
		var hiddenInput2 = document.createElement('input');
		hiddenInput2.setAttribute('type','hidden');
		hiddenInput2.setAttribute('name','command');
		hiddenInput2.setAttribute('value','social_login');
		frm.appendChild(hiddenInput2);
		
		var hiddenInput3 = document.createElement('input');
		hiddenInput3.setAttribute('type','hidden');
		hiddenInput3.setAttribute('name','type');
		hiddenInput3.setAttribute('value',type);
		frm.appendChild(hiddenInput3);
		
		var hiddenInput4 = document.createElement('input');
		hiddenInput4.setAttribute('type','hidden');
		hiddenInput4.setAttribute('name','naverLoginAccessToken');
		hiddenInput4.setAttribute('value',naverLoginAccessToken);
		frm.appendChild(hiddenInput4);
		
		document.body.appendChild(frm);
		frm.submit();
		
	}
</script>
