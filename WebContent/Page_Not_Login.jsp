<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	if(confirm("로그인이 필요한 페이지입니다\r\n\r\n로그인 하시겠습니까?")) {
		location.href = "Controller?command=login_form";
	} else {
		history.back();
	}
</script>