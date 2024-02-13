function checkLogin() {
	// 아이디 유효성 검사
	if($("input[name=id]").val().length == 0) {
		alert("아이디를 입력하세요.");
		$("input[name=id]").select();
		return false;
	}
	// 비밀번호 유효성 검사
	if($("input[name=pw]").val().length == 0) {
		alert("비밀번호를 입력하세요.");
		$("input[name=pw]").select();
		return false;
	}
	
	return true;
}
$(function() {
	
});