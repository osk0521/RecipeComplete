<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String loginId = (String)session.getAttribute("loginId");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>요리를 즐겁게~ 만개의 레시피</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="Member_CSS/Member_Withdrawal.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		<% if(loginId == null) { %>
			if(confirm("로그인이 필요한 페이지입니다. 로그인 하시겠습니까?")) {
				location.href = "Controller?command=login_form";
			} else {
				history.back();
			}
		<% } %>
	</script>
</head>
<body>
	<div id="div_header">
		<a href="Controller?command=main_page">
			<img src="https://recipe1.ezmember.co.kr/img/logo3.png"/>
		</a>
	</div>
	<div id="div_content">
		<h2>회원탈퇴</h2>
		<div id="div_panel">
			<form action="Controller" method="post">
 				<% if(loginId.charAt(0) >= '0' && loginId.charAt(0) <= '9' && loginId.length() < 20) { %> 
 					<input type="hidden" name="command" value="member_withdrawal_kakao"/> 
 				<% } else if(loginId.length() > 20) { %> 
					<input type="hidden" name="command" value="member_withdrawal_naver"/> 
 				<% } else { %> 
					<input type="hidden" name="command" value="member_withdrawal"/>
 				<% } %> 
				<input type="hidden" name="id" value="${loginId}"/>
				<div id="div_withdrawal">
					<p class="p_help">
        탈퇴하면 앞으로 이 계정으로 로그인할 수 없고 이 계정을 다시 복구할 수 없습니다.<br>
        * 가입정보를 변경하고 싶다면 <a href="Controller?command=member_modify_view">회원정보수정</a>에서 변경할 수 있습니다.<br>
        * 회원 탈퇴 시 보유하신 적립금과 포인트는 소멸하고, 복구가 불가능합니다.<br><br>
                <strong>탈퇴하려면 아래 확인버튼을 클릭해주세요.</strong></p>
				</div>
				<div class="white_space"></div>
				<button id="agree_withdrawal">확 인</button>
			</form>
		</div>
	</div>
</body>
</html>