<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String loginId = (String)session.getAttribute("loginId");
	long kakaoId = Long.parseLong(loginId);
%>
<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
	Kakao.init('a86424ed6e8729286d657739f756e2e5');
	Kakao.API.request({
		url: '/v1/user/unlink',
	}).then(function(response) {
		console.log(response);
		alert("카카오 연동이 해제되었습니다.");
		location.href = "Controller?command=member_withdrawal";
	}).catch(function(error) {
		console.log(error);
	});
</script>