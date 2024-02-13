<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String loginId = (String)session.getAttribute("loginId");
	if(loginId != null) {
		MemberDto memberInfo = (MemberDto)request.getAttribute("memberInfo");
		pageContext.setAttribute("nickname", memberInfo.getNickname());
		pageContext.setAttribute("email", memberInfo.getEmail());
		pageContext.setAttribute("phone", memberInfo.getPhone());
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>요리를 즐겁게~ 만개의 레시피</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="Member_CSS/Member_Modify_MemberInfo.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="js/Member_Modify.js"></script>
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
		<h2>회원정보 수정</h2>
		<div id="div_panel">
			<div id="div_memberinfo_body">
				<% if( !(loginId.charAt(0) >= '0' || loginId.charAt(0) <= '9') ) { %>
					<div id="div_membernickname" class="div_info_item">
						<div class="div_info_header">
							<div class="div_box_text">아이디</div>
						</div>
						<div class="div_info_body">
							<input type="text" id="id" value="${loginId}" style="margin-bottom:10px; outline:none; cursor:default;"readonly/>
						</div>
					</div>
				<% } %>
				<div id="div_memberpassword" class="div_info_item">
					<div class="div_info_header">
						<div class="div_box_text">비밀번호</div>
					</div>
					<div class="div_info_body">
						<input type="password" id="previous_password" placeholder="기존 비밀번호"/>
						<input type="password" id="change_password" placeholder="신규 비밀번호"/>
						<input type="password" id="change_password_check" placeholder="신규 비밀번호 확인"/>
						<button type="button" onclick="modify_password();">변경</button>
					</div>
				</div>
				<div id="div_membernickname" class="div_info_item">
					<div class="div_info_header">
						<div class="div_box_text">닉네임</div>
					</div>
					<div class="div_info_body">
						<input type="text" id="change_nickname" value="${nickname}" placeholder="닉네임"/>
						<button type="button" onclick="modify_nickname();">변경</button>
					</div>
				</div>
				<% if( !(loginId.charAt(0) >= '0' || loginId.charAt(0) <= '9') ) { %>
					<div id="div_memberemail" class="div_info_item">
						<div class="div_info_header">
							<div class="div_box_text">이메일</div>
						</div>
						<div class="div_info_body">
							<input type="text" id="change_email" value="${email}" placeholder="이메일"/>
							<input type="text" id="email_authentication" style="margin-top:5px;" placeholder="인증번호" readonly/>
							<div id="div_email_timer" class="div_timer">인증번호 만료시간 : 3분 0초</div>
							<button type="button" id="send_email">인증번호 전송</button>
							<button type="button" id="modify_email">변경</button>
							
						</div>
					</div>
				<% } %>
				<% if( !(loginId.charAt(0) >= '0' || loginId.charAt(0) <= '9') ) { %>
					<div id="div_memberphone" class="div_info_item">
						<div class="div_info_header">
							<div class="div_box_text">전화번호</div>
						</div>
						<div class="div_info_body">
							<input type="text" id="change_phone" value="${phone}" placeholder="전화번호"/>
							<input type="text" id="phone_authentication" style="margin-top:5px;" placeholder="인증번호" readonly/>
							<div id="div_phone_timer" class="div_timer">인증번호 만료시간 : 3분 0초</div>
							<button type="button" id="send_phone">인증번호 전송</button>
							<button type="button" id="modify_phone">변경</button>
						</div>
					</div>
				<% } %>
				<div id="div_exitmember" class="div_info_item">
					<div class="div_info_header">
						<div class="div_box_text">회원탈퇴</div>
						<div>
							<button onclick="javascript:location.href='Controller?command=member_withdrawal_view';" class="button_modify">탈퇴하기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>