<%@page import="dto.RecentRecipeDto"%>
<%@page import="dto.ProfileDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="vo.RecipeMagazineVo"%>
<%@page import="dao.RecipeMagazineBoardDao"%>
<%@page import="java.util.ArrayList"%>
<%
	//로그인 계정 가져오기
	String loginId = (String)session.getAttribute("loginId");
	// 로그인 계정의 프로필에 필요한 변수 선언
	String myProfileImage = null, myNickname = null;
	// 페이지를 요청했을 때 로그인되어있는 상태라면 프로필 관련 DTO객체 생성
	if(loginId != null) {
		ProfileDto myProfileDto = (ProfileDto)request.getAttribute("myProfile");
		myProfileImage = myProfileDto.getProfileImage(); // 프로필 이미지
		myNickname = myProfileDto.getNickname(); // 닉네임
	}
	ArrayList<RecipeMagazineVo> magazineList = (ArrayList<RecipeMagazineVo>) request.getAttribute("magazineList");   // 자-부 (X)
	RecipeMagazineBoardDao RMBDao = new RecipeMagazineBoardDao();
	// 페이지 하단 최근 본 레시피 리스트 가져오기
	ArrayList<RecentRecipeDto> recentRecipeList = (ArrayList<RecentRecipeDto>)request.getAttribute("recentRecipeList");
%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8" />
	<title>요리를 즐겁게~ 만개의 레시피</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="Recipe_CSS/Recipe_Magazine.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Header.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Footer.css"/>
	<link href="slick-jw/slick_jw.css" rel="stylesheet"/>
	<link href="slick-jw/slick-theme_jw.css" rel="stylesheet"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
	<script src="js/Recipe_Header.js"></script>
	<script src="js/Recipe_Footer.js"></script>
</head>

<body>
<!-- 페이지 최상단 -->
	<div id="div_header">
		<div id="div_header_inner">
			<div id="div_logo_image">
				<a href="Controller?command=main_page">
					<img src="https://recipe1.ezmember.co.kr/img/logo4.png"/>
				</a>
			</div>
			<div id="div_search_bar">
				<form action="Controller" id="searchRecipe"> <!-- 검색 결과를 적용한 레시피 목록 페이지로 이동 -->
					<input type="hidden" name="command" value="recipe_search"/>
					<div id="div_input_group">
						<input type="text" name="sw" id="searchText"/>
						<span>
							<button id="search_button">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</span>
					</div>
				</form>
			</div>
			<div id="div_header_button">
				<div id="div_login_button" class="fl">
					<% if(loginId == null) { // 비로그인 상태라면 %>
						<a href="Controller?command=login_form">
							<img src="https://recipe1.ezmember.co.kr/img/ico_user.png"/>
						</a>
					<% } else { // 로그인 상태라면 %>
						<script>
							// 카카오 로그아웃
							function kakaoLogout() {
								Kakao.init('a86424ed6e8729286d657739f756e2e5');
								Kakao.isInitialized();
								
								if(!Kakao.Auth.getAccessToken()) {
									console.log('Not logged in.');
									return;
								}
								
								Kakao.Auth.logout(function() {
									console.log(Kakao.Auth.getAccessToken());
									deleteCookie();
								});
							}
							function deleteCookie() {
							    document.cookie = 'authorize-access-token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
							}

							function logout() {
								if(confirm("로그아웃 하시겠습니까?")) {
									kakaoLogout();
									location.href = "Controller?command=logout";
								}
							}
						</script>
						<a id="a_login_id" user="<%=loginId%>">
							<img src="<%=myProfileImage%>">
						</a>
						<!-- 로그인하면 위에 a태그 클릭했을 때 div태그 밑에 나옴 -->
						<div id="div_user_mybutton">
							<p id="p_layer_top"></p>
							<p id="p_layer_middle">
								<a href="Controller?command=mypage_recipe_view">MY홈</a>
	                            <a href="Controller?command=mypage_followingrecipe_view">팔로우 레시피</a>
	                            <a href="Controller?command=mypage_recipenote_view">레시피 노트</a>
	                            <a href="Controller?command=inquiry_view">문의내역</a>
	                            <a href="Controller?command=member_modify_view">회원정보수정</a>
	                            <a href="Controller?command=member_withdrawal_view">회원탈퇴</a>
	                            <a href="javascript:logout()">로그아웃</a>
							</p>
							<p id="p_layer_bottom"></p>
						</div>
					<% } %>
				</div>
				<div id="div_recipeWrite_button" class="fl">
					<% if(loginId == null) { // 비로그인 상태라면 %>
						<script>
							function move_login() {
								if(confirm("로그인이 필요한 페이지입니다. 로그인 하시겠습니까?")) {
									location.href = "Controller?command=login_form";
								}
							}
						</script>
						<a onclick="move_login();">
							<img src="https://recipe1.ezmember.co.kr/img/tmn_write.png"/>
						</a>
					<% } else { %>
						<a onclick="move_write_recipe();">
							<img src="https://recipe1.ezmember.co.kr/img/tmn_write.png"/>
						</a>
					<% } %>
				</div>
				<div id="div_store_button" class="fl">
					<a href="Controller?command=store_mainlist"> <!-- 스토어 메인페이지 -->
						<img src="https://recipe1.ezmember.co.kr/img/tmn_store2.png"/>
					</a>
				</div>
				<div style="clear:both;"></div>
			</div>
		</div>
		<div id="div_partner1">
			<img src="https://recipe1.ezmember.co.kr/img/partners/banner_web1.png?v.2"/>
		</div>
		<div id="div_partner2">
			<img src="https://recipe1.ezmember.co.kr/img/partners/banner_web2.png?v.2"/>
		</div>
	</div>
<!-- /페이지 최상단 -->
<!-- 페이지 상단 메뉴바 -->
	<div id="div_menubar">
		<div id="div_menu_content">
			<div class="div_menu_button">
				<a href="Controller?command=main_page">메인</a>
			</div>
			<div class="div_menu_button">
				<a href="Controller?command=recipe_search">분류</a>
			</div>
			<div class="div_menu_button">
				<a href="Controller?command=refrigerator_page">냉장고</a>
			</div>
			<div class="div_menu_button">
				<a href="Controller?command=ranking_page">랭킹</a>
			</div>
			<div class="div_menu_button">
				<a href="Controller?command=chef_view">쉐프</a>
			</div>
			<div class="div_menu_button">
				<a href="Controller?command=magazine_page">메거진</a>
			</div>
			<div style="clear:both;"></div>
		</div>
	</div>
<!-- /페이지 상단 메뉴바 -->
  <!------------ header ------------>
	<dl class="container">
		<div id="contents_area_full" style="clear:both;">
			<div style="padding:30px 21px 30px;">
				<ul class="Magazine_List">
				<%
					for(RecipeMagazineVo rmvo : magazineList) {
				%>
					<li>
						<a href="Controller?command=magazine_page_action&magazineID=<%=rmvo.getMID()%>">
							<img src="<%=rmvo.getMImage() %>" alt="<%=rmvo.getMName()%>"> <%=rmvo.getMName() %>
						</a>
					</li>
				<%
					}
				%>
				<li class="Magazine_empty"></li>
				<li class="Magazine_empty"></li>
				</ul>
			<div class="div_recent_magazine">최신 매거진</div>
				<div style="margin-left:3px;">
					<div class="theme_list st2">
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7014313" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/24/a13fc7ab2bd18abab9aeb63ff84b720a1_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">[만만셰] 오징어를 활용한 간단한 솥밥, ‘오징어솥밥’ by.승혜의야미로그</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7014128" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/22/2cbfdda6b7b372e9a455a1fe0cf88c221_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">특별한 날을 위한 호두 구겔호프</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7013634" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/15/e17dc5fbc0ddfaa80cdc14ac25dc71871_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">[만만셰] 꽁치로 만드는 뜨끈한 추어탕, 꽁치시래기추어탕 by.승혜의야미로그</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7013625" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/15/b06e6a3cd61fca894dd7592a41ca90011_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">고소함과 유럽의 향기 가득 호두토마토미트볼</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7013194" style="height: 250px;padding-right: 0;margin-right: 0px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/09/4978f22673760bb71f526d434d797c731_s.jpg" alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">가을에 어울리는 라떼한잔 간편하게 만들었어요 | 꿀밤라떼</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7013193" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/09/a102672d8f49ed9073552afa1a59f2611_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">통조림 파인애플만으로도 맛있게 피나콜라다만들기</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7013101" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/08/46990222729c561098e3b59e605ac3e81_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">[만만셰] Tina소울푸드 셰프의 팽이버섯 튀김 &amp; 연두부푸딩</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7012999" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/07/a4628b79ecafd7be9683059864a292791_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">고소하고 부드러운 호두크림뇨끼</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7012990" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/07/5a28f5fca501596dcad562da6144bf5b1_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">어디서 먹어본 맛인데 너무 부드럽고 맛있어요 | 부드러운달걀찜</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7012989" style="height: 250px;padding-right: 0;margin-right: 0px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/07/2d2ea9f452f20f1cc341fb4b8f7bc54a1_s.jpg" alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">맛집따라 만들어봤는데 쪽파싫어하는 사람도 맛있다네요 | 쪽파크림치즈베이글</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7012579" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/11/01/9e97bbfcf9f2d282f2e97d583255328d1_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">[만만셰] 'Tina소울푸드' 셰프님의 모두가 사랑했던 단골 메뉴 ‘국수샐러드’</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
            <a class="thumbnail" href="https://www.10000recipe.com/recipe/7012437" style="height: 250px;">
              <img src="https://recipe1.ezmember.co.kr/cache/recipe/2023/10/30/ed5834b8dc02b3a8c8a9d187b95967231_s.jpg"
                alt="recipe">
              <div class="Recipe_Introduction">
                <span class="caption title">자꾸 손이 가는 호두 비스코티</span>
                <span class="caption name">만개의레시피</span>
              </div>
            </a>
          </div>
        </div>
      </div>
    </div>
  </dl>
  <!------------ body ------------>
<!-- 페이지 하단 최근 본 레시피 -->
	<div id="div_recent_recipe_panel">
		<% if(!recentRecipeList.isEmpty()) { %>
			<div id="div_recent_recipe_content">
				<h3>최근 본 레시피</h3>
				<div id="div_recent_recipe_body">
					<% for(RecentRecipeDto recentRecipeObj : recentRecipeList) { %>
						<%
							int recentRecipeId = recentRecipeObj.getRecipeId();
							String recentRecipeTitle = recentRecipeObj.getRecipeTitle();
							String recentRecipeThumbnail = recentRecipeObj.getRecipeThumbnail();
						%>
						<div class="recent_recipe" recipe=<%=recentRecipeId%>>
							<a href="Controller?command=view_recipe_detail&recipeID=<%=recentRecipeId%>" class="rr_recipe_thumbnail">
								<img src="<%=recentRecipeThumbnail%>" class="rr_recipe_thumbnail_img"/>
								<div class="rr_recipe_title">
									<div><%=recentRecipeTitle%></div>
								</div>
							</a>
						</div>
					<% } %>
				</div>
			</div>
		<% } %>
	</div>
<!-- /페이지 하단 최근 본 레시피 -->
<!-- footer 흰색배경부분 -->
	<div id="footer1">
		<div id="footer1_panel">
			<div id="footer1_content1">
				<p class="f_content1_link">
					<a>회사소개</a>
					<span>|</span>
					<a>광고문의</a>
					<span>|</span>
					<a>개인정보처리방침</a>
					<span>|</span>
					<a>이용약관</a>
					<span>|</span>
					<a href="Controller?command=notice_view">고객센터</a>
				</p>
				<p class="f_content1_info">
					대표 : 이인경 / E : help@10000recipe.com / F : 02) 323-5049 <br/>
					서울 금천구 가산동 371-50 에이스하이엔드타워 3차 1106-1호 <br/>
					문의전화(운영시간 평일 10:00~18:00)<br/>
					쇼핑문의(만개스토어) : 02-6953-4433<br/>
					서비스 이용문의 : 070-4896-6416 
				</p>
				<p class="f_content1_info">
					(주)만개의레시피 / 사업자등록번호 291-81-02485 / 통신판매업신고 2022-서울금천-3089 / 벤처기업확인 / <a target="_self" style="color:#999; font-size:13px">사업자정보확인</a> <br>
					서울지방중소기업청 제 031134233-1-01643호
				</p>
				<p class="f_content1_info">
					Copyright <b style="font-size:13px;">만개의레시피</b> Inc. All Rights Reserved
				</p>
			</div>
			<div id="footer1_content2">
				<a id="app_download">
					<img src="https://recipe1.ezmember.co.kr/img/btm_app2.gif" alt="app다운로드"/>
				</a>
				<div id="banner_sns">
					<a>
						<img src="https://recipe1.ezmember.co.kr/img/btm_sns_1.gif" alt="페이스북"/>
					</a>
					<a>
						<img src="https://recipe1.ezmember.co.kr/img/btm_sns_2.gif" alt="인스타그램"/>
					</a>
					<a>
						<img src="https://recipe1.ezmember.co.kr/img/btm_sns_3.gif" alt="유튜브"/>
					</a>
					<a>
						<img src="https://recipe1.ezmember.co.kr/img/btm_sns_4_1.gif" alt="네이버블로그"/>
					</a>
					<a>
						<img src="https://recipe1.ezmember.co.kr/img/btm_sns_5.gif" alt="네이버포스트"/>
					</a>
					<a>
						<img src="https://recipe1.ezmember.co.kr/img/btm_sns_6.gif" alt="카카오TV"/>
					</a>
					<a>
						<img src="https://recipe1.ezmember.co.kr/img/btm_sns_7.gif" alt="카카오스토리"/>
					</a>
				</div>
			</div>
			<div id="footer1_content3">
				<textarea id="f_customer_send" name="cs_txt" placeholder="불편사항이나 제안사항이 있으신가요?
만개의레시피에 전하고 싶은 의견을 남겨주세요."></textarea>
				<button type="button" id="f_send_cs_button">의견제출</button>
				<p id="f_cs_notice">
					개별회신을 원하시면 <a style="text-decoration:underline; color:#666; font-size:13px">여기</a>에 문의하세요.
				</p>
			</div>
		</div>
	</div>
<!-- /footer 흰색배경부분 -->
<!-- footer 녹색배경부분 -->
	<div id="footer2">
		<div id="footer2_panel">
			<div id="f2_logo">
				<img src="https://recipe1.ezmember.co.kr/img/logo6.png"/>
			</div>
			<div id="f2_all_count">
				<div class="div_count_box">
					<a>
						<span style="background: url(//recipe1.ezmember.co.kr/img/btm_icon1.png) center no-repeat;"></span>
						제휴업체수
					</a>
					<b>575</b>
				</div>
				<div class="div_count_box">
					<a>
						<span style="background: url(//recipe1.ezmember.co.kr/img/btm_icon2.png) center no-repeat;"></span>
						총 쉐프수
					</a>
					<b>3,970</b>
				</div>
				<div class="div_count_box">
					<a>
						<span style="background: url(//recipe1.ezmember.co.kr/img/btm_icon3.png) center no-repeat;"></span>
						총 레시피수
					</a>
					<b>210,881</b>
				</div>
				<div class="div_count_box">
					<a>
						<span style="background: url(//recipe1.ezmember.co.kr/img/btm_icon4.png) center no-repeat;"></span>
						월 방문자수
					</a>
					<b>5,140,000</b>
				</div>
				<div class="div_count_box">
					<a>
						<span style="background: url(//recipe1.ezmember.co.kr/img/btm_icon5.png) center no-repeat;"></span>
						총 레시피 조회수
					</a>
					<b>2,875,362,521</b>
				</div>
			</div>
			<div id="f2_send_mail">
				<a>
					<img src="https://recipe1.ezmember.co.kr/img/btm_img2.png"/>
				</a>
			</div>
		</div>
<!-- /footer 녹색배경부분 -->
<!-- 페이지 최하단 since -->
		<div id="footer2_Since">
			<img src="https://recipe1.ezmember.co.kr/img/btm_since.png"/>
		</div>
	</div>
<!-- /페이지 최하단 since -->

<!-- slider -->
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="slick-jw/slick_jw.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function() {
			$("#div_recent_recipe_body").slick({
				infinite: false,
				slidesToShow: 6,
				slidesToScroll: 6
			});
		})
	</script>
<!------------ footer ------------>
</body>
</html>