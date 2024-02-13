<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% session.removeAttribute("loginId"); // 세션에 저장된 로그인ID 삭제 %>
<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.6.0/kakao.min.js" integrity="sha384-6MFdIr0zOira1CHQkedUqJVql0YtcZA1P0nbPrQYJXVJZUkTk/oX4U9GhUIs3/z8" crossorigin="anonymous"></script>
<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
	let testPopUp = null;
	function openPopUp() {
	    testPopUp = window.open("https://nid.naver.com/nidlogin.logout", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,width=1,height=1");
	}
	function closePopUp(){
	    testPopUp.close();
	}
	function naverLogout() {
		openPopUp();
		alert("되긴하냐?");
		setTimeout(function() {
			closePopUp();
		}, 1000);
	}
	naverLogout();
	
	// 카카오 로그아웃
	Kakao.init('a86424ed6e8729286d657739f756e2e5');
	function kakaoLogout() {
	    Kakao.Auth.logout()
	    	.then(function() {
		        alert('logout ok\naccess token -> ' + Kakao.Auth.getAccessToken());
		        deleteCookie();
	    	})
	    	.catch(function() {
	    		alert('Not logged in');
	    	});
	}
	function deleteCookie() {
	    document.cookie = 'authorize-access-token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
	}
	
	kakaoLogout();
	location.href = "Controller?command=login_form";
</script>