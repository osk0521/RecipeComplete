<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	boolean result = (Boolean)request.getAttribute("result");
	String type = (String)request.getAttribute("type");
%>
<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
<%-- 	<% if(type.equals("kakao")) { %> --%>
// 		// 카카오 연동해제 로직
		
<%-- 	<% } else if(type.equals("naver")) { %> --%>
// 		// 네이버 연동해제 로직
		
<%-- 	<% } %> --%>
	alert("탈퇴되었습니다.");
	location.href = "Controller?command=login_form";
</script>