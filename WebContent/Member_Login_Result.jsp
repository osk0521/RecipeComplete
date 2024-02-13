<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	boolean result = (Boolean)request.getAttribute("result"); 
	String loginId = request.getParameter("id");
%>
<script>
	<% if(result) { %>
		// 로그인 성공했다면 파라미터로 받은 id값을 세션에 저장
		<% session.setAttribute("loginId", loginId); %>
		alert("로그인 되었습니다.");
		location.href = "Controller?command=main_page"; // 로그인화면에 들어오기 이전의 페이지로 이동하거나 (맨 처음부터 로그인 화면으로 왔다면)메인페이지로 이동. 지금은 임시로 마이페이지 레시피(로그인계정 기준)로 이동
	<% } else { %>
		// 로그인 실패했다면 로그인 페이지로 다시 이동
		alert("아이디 혹은 비밀번호를 다시 입력하세요.");
		location.href = "Controller?command=login_form";
	<% } %>
</script>