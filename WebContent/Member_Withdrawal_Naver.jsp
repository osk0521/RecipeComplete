<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
	const tokenDeleteUrl = 
		"https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=24lrJuBOFgCE8vHfYjPx&client_secret=hGOuwId92B&access_token="
				+ "${naverLoginAccessToken}"
				+ "&service_provider=NAVER";
	$.ajax({
		url: tokenDeleteUrl,
		type: "get",
		data: {},
		success: function(e) {
			if(e.result) {
				alert("됐니?");
				location.href = "Controller?command=member_withdrawal";
			} else {
				alert("네이버 연동해제에 실패하였습니다.");
				history.back();
			}
		},
		error: function(request, status, error) {
			// 해당 요청 발생 후 errorcode : 0 발생. 하지만 네이버 연동은 해제가 되어있음
			// alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			alert("네이버 연동이 해제되었습니다.");
			location.href = "Controller?command=member_withdrawal";
		}
	})
	// location.href = "Controller?command=member_withdrawal";
</script>

