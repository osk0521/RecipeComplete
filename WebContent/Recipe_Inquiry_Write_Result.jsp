<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% boolean result = (Boolean)request.getAttribute("result"); %>
<script>
	<% if(result) { %>
		alert("작성되었습니다.");
		location.href = "Controller?command=inquiry_view";
	<% } else { %>
		alert("에러가 발생했습니다. 잠시 후 다시 시도해주세요.");
		history.back();
	<% } %>
</script>
