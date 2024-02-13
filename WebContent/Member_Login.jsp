<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>요리를 즐겁게~ 만개의 레시피</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap">
	<link rel="stylesheet" href="Member_CSS/Member_Login.css">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="js/Member_Login.js"></script>
	<script>
		<% if(session.getAttribute("loginId") != null) { %>
			alert("로그인이 되어있습니다.");
			location.href = "Controller?command=mypage_recipe_view";
		<% } %>
		
	</script>
	<!-- 카카오 스크립트 -->
	<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script>
		// 카카오 초기화
		Kakao.init('a86424ed6e8729286d657739f756e2e5');
		
		function kakaoLogin() {
			
		    Kakao.Auth.login({
		        success: function(response) {
		            Kakao.API.request({ // 사용자 정보 가져오기 
		                url: '/v2/user/me',
		                success: function(response) {
		                	var kakaoid = response.id;
		                    $.ajax({
		    					url : "Controller", // ID중복체크를 통해 회원가입 유무를 결정한다.
		    					type : "post",
		    					data : {
		    						"command" : "member_check",
		    						"id" : kakaoid
		    					},
		    					dataType:"json",
		    					success : function(obj){   				
		    						if(obj.idExists){
		    							// 존재하는 경우 로그인 처리
		    							createHiddenLoginForm(kakaoid, "kakao", "null");
		    							
		    						} else{
		    							// 회원가입
		    							$.ajax({
		    		    					url : "Controller",
		    								type : "post",
		    		    					data : {
		    		    						"command" : "kakao_regist",
		    		    						"id" : kakaoid,
	    		    						    "name" : response.properties.nickname,
	    		    						    "email" : response.kakao_account.email,
	    		    						    "profile_image" : response.properties.profile_image
		    		    					},
		    		    					success : function(obj){
		    		    						// 회원가입 처리 이후 히든 로그인 폼 만들어서 로그인처리
		    		    						if(obj.success){
		    		    							createHiddenLoginForm(kakaoid, "kakao", "null");		    							
		    		    						} else {
		    		    							alert('카카오 회원가입 실패. 일반계정으로 로그인하시기 바랍니다.');
		    		    						}
		    		    					},
		    		    					error: function(request, status, error){
		    		    		                   alert("회원가입 단계에서의 에러\r\n"+"code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		    		    		                }
		    							});
		    						}						
		    					},
		    					error : function(request, status, error){
	    		                   alert("로그인 단계에서의 에러\r\n"+"code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	    		                }
		    				});
		                }
		            });d
		            // window.location.href='/ex/kakao_login.html' //리다이렉트 되는 코드
		        },
		        fail : function(error) {
		            alert(error);
		        }
		    });
		}
		
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
			
			if(naverLoginAccessToken != "null") {
				var hiddenInput4 = document.createElement('input');
				hiddenInput4.setAttribute('type','hidden');
				hiddenInput4.setAttribute('name','naverLoginAccessToken');
				hiddenInput4.setAttribute('value',naverLoginAccessToken);
				frm.appendChild(hiddenInput4);
			}
			
			document.body.appendChild(frm);
			frm.submit();
			
		}
	</script>
</head>
<body>
	<div id="div_header">
		<a href="Controller?command=main_page">
			<img src="https://recipe1.ezmember.co.kr/img/logo3.png"/>
		</a>
	</div>
	<div id="div_content_outer">
		<h2>로그인 / 회원가입</h2>
		<div id="div_panel">
			<div id="div_join_kakao">
				<button type="submit" class="btn" onclick="location.href='javascript:kakaoLogin()';">
					<span id="kakao_start">카카오로 간편 시작</span>
				</button>
			</div>
			<div id="div_login_button">
				<div id="naverIdLogin"></div>
			</div>
<!-- 네이버 로그인 스크립트 -->
<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
<script>
   	// 로그인버튼으로 네이버 로그인해서 정보 받고 로그아웃까지 됨
   	// 해당 로그인 정보로 회원여부 판단하고 회원가입 or 로그인 진행
   	const naverLogin = new naver.LoginWithNaverId(
           {
               clientId: "24lrJuBOFgCE8vHfYjPx",
               callbackUrl: "http://localhost:9090/Recipe/Controller?command=naver_login",
               loginButton: {color: "green", type: 3, height: 70}
           }
       );
	naverLogin.init(); // 로그인 설정
</script>
			<form action="Controller" method="post" onsubmit="return checkLogin()">
				<div id="div_iogin_box" class="textbox">
					<input type="text" name="id" placeholder="아이디"/>
				</div>
				<div id="div_password_box" class="textbox">
					<input type="password" name="pw" placeholder="비밀번호"/>
				</div>
				<div id="div_keep_login">
					<label>
						<input type="checkbox" name="login_perma"/>
						<span id="guide_text">로그인 상태 유지</span>
					</label>
				</div>
				<input type="hidden" name="command" value="login"/>
				<div id="div_login_button">
					<button type="submit">로그인</button>
				</div>
			</form>
			<div id="div_extra_button">
				<a onclick="alert('아이디를 잊으셨다면 새로 가입해주세요');">아이디/비밀번호 찾기</a>
				<a href="Controller?command=regist_form">회원가입</a>
			</div>
		</div>
	</div>
</body>
</html>