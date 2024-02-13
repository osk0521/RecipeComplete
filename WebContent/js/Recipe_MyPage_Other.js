$(function() {
	// 마이페이지 프로필 자기소개에서 url이 있다면 해당 url을 정규식을 이용하여 <a>태그로 감싸줌
	{
		let self_introduce = $("#div_profile_user_introduce").text();
		let urlReg = /(https?:\/\/[^ ]*)/; // url 정규식
		let url = null;
		try {
			url = self_introduce.match(urlReg)[1].trim();
		} catch(e) {
			console.log(e);
		}
		
		// 자기소개 안에 url이 없다면 밑의 과정을 진행할 필요가 없음
		if(url != null) {
			// 자기소개를 url 기준으로 쪼개는 과정
			let split_string = self_introduce.split(url);
			// url을 a태그로 감싸주는 과정
			let url_in_a_tag = "<a href=\"" + url + "\" target=\"_blank\">" + url + "</a>";
			// .split(url)의 배열 길이에 따른 삽입 문자열 조립과정
			let output_string = ""; // 삽입할 문자열 변수 초기화
			switch(split_string.length) {
				case 0:
					output_string = url_in_a_tag;
					break;
				case 1:
					if(self_introduce.indexOf(url) == 0) { // url이 맨 앞에 있을 경우
						output_string = url_in_a_tag + split_string[0];
					} else { // url이 맨 뒤에 있을 경우
						output_string = split_string[0] + url_in_a_tag;
					}
					break;
				case 2:
					output_string = split_string[0] + url_in_a_tag + split_string[1];
					break;
			}
			// 자기소개 부분에 조립한 문자열 삽입
			$("#div_profile_user_introduce").html(output_string);
		}
	}
	
})

