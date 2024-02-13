<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% boolean complete = (Boolean)request.getAttribute("complete"); %>
<% if(complete) { %>
	<script>
		alert("회원가입 되었습니다.");
		location.href = "Controller?command=login_form";
	</script>
<% } else { %>
	<script>
		alert("회원가입 실패");
		location.href = "Controller?command=login_form";
	</script>
<% } %>
