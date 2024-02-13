<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>요리를 즐겁게~ 만개의 레시피</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="Member_CSS/Member_Create_Account.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="js/Member_Create_Account.js"></script>
</head>
<body>
	<div id="div_header">
		<a href="Controller?command=main_page">
			<img src="https://recipe1.ezmember.co.kr/img/logo3.png"/>
		</a>
	</div>
	<div id="div_content">
		<h2>회원가입</h2>
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="regist"/>
			<div id="div_panel">
				<div class="div_input_item">
					<input type="text" name="id" id="input_id" class="input" placeholder="아이디"/>
					<button type="button" id="test_dupli_id">중복검사</button>
				</div>
				<div class="white_space"></div>
				<input type="password" name="pw" id="input_password" class="input" placeholder="비밀번호"/>
				<div class="white_space"></div>
				<input type="password" id="input_password_check" class="input" placeholder="비밀번호확인"/>
				<div class="white_space"></div>
				<div class="div_input_item">
					<input type="text" name="nickname" id="input_nickname" class="input" placeholder="닉네임"/>
					<button type="button" id="test_dupli_nickname">중복검사</button>
				</div>
				<div class="white_space"></div>
				<div class="div_input_item">
					<input type="text" name="phone" id="input_phone" class="input" placeholder="전화번호"/>
					<button type="button" id="send_number_phone" style="font-size:14px;">인증번호 발송</button>
				</div>
				<div class="white_space"></div>
				<div id="div_phone_authentication" class="div_input_item">
					<input type="text" name="phone_authentication" id="input_phone_authentication" class="input" placeholder="인증번호" readonly/>
					<button type="button" id="phone_authentication">인증하기</button>
					<div id="div_phone_timer" class="div_timer">인증번호 만료시간 : 3분 0초</div>
				</div>     
				<div class="white_space"></div>
				<div class="div_input_item">
					<input type="text" name="email" id="input_email" class="input" placeholder="이메일"/>
					<button type="button" id="send_number_email" style="font-size:14px;">인증번호 발송</button>
				</div>
				<div class="white_space"></div>
				<div class="div_input_item">
					<input type="text" name="email_authentication" id="input_email_authentication" class="input" placeholder="인증번호" readonly/>
					<button type="button" id="email_authentication">인증하기</button>
					<div id="div_email_timer" class="div_timer">인증번호 만료시간 : 3분 0초</div>
				</div>
				<div class="white_space"></div>
				<div class="div_input_birth">
					<input type="text" id="input_birth" class="input" placeholder="생년월일" style="width:100px; text-align:center; outline:none;" readonly/>
					<select name="year" class="select_year">
						<% for(int i=2023; i>1899; i--) { %>
							<option value=<%=i%>><%=i%>년</option>
						<% } %>
					</select>
					<select name="month" class="select_month">
						<% for(int i=1; i<13; i++) { %>
							<option value=<%=i%>><%=i%>월</option>
						<% } %>
					</select>
					<select name="day" class="select_day"></select>
				</div>
				<div class="white_space_big"></div>
				<div class="white_space"></div>
				<div class="naver_captcha">
					<img src=""/> <br/>
					<input type="text" id="input_captcha_value" style="width:200px; height:30px; margin-bottom:8px;"/> <br/>
					<button type="button" id="button_submit_captcha">확인</button>
					<button type="button" id="button_request_img">새로고침</button>
				</div>
				<div class="white_space"></div>
				<div class="div_agree_box">
					<div>동의사항</div>
					<div>
						<input type="checkbox" name="agree1" id="essential_agree1" class="input_agree"/>
						<label for="essential_agree1">필수동의사항 1</label>
					</div>
					<div>
						<input type="checkbox" name="agree2" id="essential_agree2" class="input_agree"/>
						<label for="essential_agree2">필수동의사항 2</label>
					</div>
					<div>
						<input type="checkbox" name="agree3" id="select_agree1" class="input_agree"/>
						<label for="select_agree1">선택동의사항 1</label>
					</div>
					<div class="div_checkbox_all">
						<label for="select_all_agree">모두 동의</label>
						<input type="checkbox" id="select_all_agree"/>
					</div>
				</div>
				<div class="white_space"></div>
				<input type="submit" id="input_submit" value="회원가입" onclick="return check_regist();"/>
			</div>
		</form>
	</div>
</body>
</html>