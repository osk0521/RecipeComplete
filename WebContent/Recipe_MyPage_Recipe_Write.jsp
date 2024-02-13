<%@page import="dto.RecentRecipeDto"%>
<%@page import="vo.AdvertisementGoodsVo"%>
<%@page import="dto.MyPageInCompleteRecipeViewDto"%>
<%@page import="dto.ProfileDto"%>
<%@page import="dto.UserFollowingDto"%>
<%@page import="dto.UserFollowerDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//현재 로그인중인지 아닌지에 따라 마이페이지의 프로필 표시 여부가 결정됨
	String loginId = (String)session.getAttribute("loginId");
	// 로그인 계정의 프로필에 필요한 변수 선언
	String myProfileImage = null, myNickname = null, mySelfIntroduce = null;
	int myTotalHits = 0, myFollower = 0, myFollowing = 0;
	// 로그인 계정의 팔로워에 필요한 변수 선언
	ArrayList<UserFollowerDto> myFollowerList = null;
	// 로그인 계정의 팔로잉에 필요한 변수 선언
	ArrayList<UserFollowingDto> myFollowingList = null;
	// 로그인 계정의 작성중인 레시피 정보에 필요한 변수 선언
	ArrayList<MyPageInCompleteRecipeViewDto> myWritingRecipeList = null;
	// 페이지를 요청했을 때 로그인되어있는 상태라면 프로필 관련 DTO객체 받아오기
	if(loginId != null) {
		// 프로필 DTO
		ProfileDto myProfileDto = (ProfileDto)request.getAttribute("myProfile"); // 요청받은 로그인 아이디 기반 프로필 DTO객체 생성
		myProfileImage = myProfileDto.getProfileImage(); // 프로필 이미지
		myNickname = myProfileDto.getNickname(); // 닉네임
		mySelfIntroduce = myProfileDto.getSelfIntroduce(); // 자기소개
		myTotalHits = myProfileDto.getTotalHits(); // 레시피 총조회수
		myFollower = myProfileDto.getFollower(); // 팔로워
		myFollowing = myProfileDto.getFollowing(); // 팔로잉
		// 팔로워 DTO
		myFollowerList = (ArrayList<UserFollowerDto>)request.getAttribute("myFollowerList");
		// 팔로잉 DTO
		myFollowingList = (ArrayList<UserFollowingDto>)request.getAttribute("myFollowingList");
		
		// 로그인 계정의 작성중인 레시피 정보
		myWritingRecipeList = (ArrayList<MyPageInCompleteRecipeViewDto>)request.getAttribute("myWritingRecipe");
	}
	// paging과 pagination을 위한 페이지 정보 받아오기
	int pageNum = (Integer)request.getAttribute("pageNum");
	int startPageNum = (Integer)request.getAttribute("startPageNum");
	int endPageNum = (Integer)request.getAttribute("endPageNum");
	int lastPageNum = (Integer)request.getAttribute("lastPageNum");
	// 서블릿에 있던 검색어 받아오기
	String searchWord = (String)request.getAttribute("searchWord");
	// 우측 하단 베스트상품 5종 가져오기
	ArrayList<AdvertisementGoodsVo> advertisementGoodsList = (ArrayList<AdvertisementGoodsVo>)request.getAttribute("advertisementGoodsList");
	// 페이지 하단 최근 본 레시피 리스트 가져오기
	ArrayList<RecentRecipeDto> recentRecipeList = (ArrayList<RecentRecipeDto>)request.getAttribute("recentRecipeList");
%>
<!DOCTYPE html>
<html>
<head>
	<!-- 마이페이지 본인계정일 때의 레시피 페이지(작성중) -->
	<meta charset="UTF-8">
	<title>요리를 즐겁게~ 만개의 레시피</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700&display=swap" rel="stylesheet">
	<link href="Recipe_CSS/Recipe_MyPage_Recipe_Write.css" rel="stylesheet">
	<link href="Recipe_CSS/Public/Recipe_Header.css" rel="stylesheet"/>
	<link href="Recipe_CSS/Public/Recipe_Footer.css" rel="stylesheet"/>
	<link href="Recipe_CSS/Public/Recipe_MyPage_Profile.css" rel="stylesheet"/>
	<link href="Recipe_CSS/Public/Recipe_Right_Advertise.css" rel="stylesheet"/>
	<link href="Recipe_CSS/Public/Recipe_Body_Frame.css" rel="stylesheet"/>
	<link href="Recipe_CSS/Public/Recipe_MyPage_Header.css" rel="stylesheet"/>
	<link href="Recipe_CSS/Public/Recipe_MyPage_Profile_Change.css" rel="stylesheet"/>
	<link href="Recipe_CSS/Public/Recipe_MyPage_View_Follow.css" rel="stylesheet"/>
	<link href="slick-jw/slick_jw.css" rel="stylesheet"/>
	<link href="slick-jw/slick-theme_jw.css" rel="stylesheet"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
	<script src="js/Recipe_MyPage.js"></script>
	<script src="js/Recipe_Header.js"></script>
	<script src="js/Recipe_Footer.js"></script>
	<script src="js/Member_Follow.js"></script>
	<script src="js/Recipe_MyPage_Profile.js"></script>
	<script>
		<% if(loginId == null) { // 로그인 없이 해당 페이지를 요청할 경우 %>
				if(confirm("로그인이 필요한 페이지입니다. \r\n\r\n로그인 하시겠습니까?")) {
					location.href = "Controller?command=login_form";
				} else {
					history.back(); 
				}
		<% } %>
	</script>
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
	<div id="div_content_outer">
		<div id="div_content_left" class="fl">
<!-- 마이페이지 헤더 -->
			<div id="div_mypage_header">
				<div class="div_mypage_header_button">
					<a href="Controller?command=mypage_recipe_view" class="button-active">
						<span class="glyphicon glyphicon-cutlery"></span>
						레시피
					</a>
				</div>
				<div class="div_mypage_header_button">
					<a href="Controller?command=mypage_comment_view">
						<span class="glyphicon glyphicon-star"></span>
						요리후기
					</a>
				</div>
				<div class="div_mypage_header_button">
					<a href="Controller?command=mypage_followingchef_view">
						<span class="glyphicon glyphicon-user"></span>
						쉐프
					</a>
				</div>
			</div>
<!-- /마이페이지 헤더 -->
<!-- 메인컨텐츠(마이페이지 레시피 작성중) -->
			<div id="div_mypage_recipe_panel">
				<div id="div_mypage_recipe_sort">
					<div id="div_mypage_recipe_sort1">
						<div>
							<a>공개중</a>
						</div>
						<div>
							<a class="open_active">작성중</a>
						</div>
					</div>
				</div>
				<% if(loginId != null) { %>
					<% if(myWritingRecipeList.isEmpty()) { %>
							<div id="div_mypage_recipe_body">
								<div id="div_mypage_recipe_none">
									<img src="https://recipe1.ezmember.co.kr/img/none_recipe.png"/>
									<p>
										레시피를 직접 올려보세요!
									</p>
									자랑하고 싶은 나만의 레시피! 공유하고 싶은 멋진 레시피를 올려 주세요.<br/>
									<button type="button" onclick="location.href='Controller?command=recipe_write_form';"> <!-- 레시피 등록페이지로 이동 -->
										레시피 등록하기
									</button>
								</div>
								<div id="div_mypage_recipe_search_bar">
									<form action="Controller?command=mypage_recipe_view" method="get">
										<input type="hidden" name="command" value="mypage_recipe_view"/> 
										<input type="hidden" name="mode" value="ing"/>
										<input type="text" name="searchWord"/>
										<span>
											<button>
												<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_search4.png"/>
											</button>
										</span>
									</form>
								</div>
							</div>
				<% } else { %>
						<div id="div_mypage_recipe_body">
				<%
					for(MyPageInCompleteRecipeViewDto myWritingRecipeObj : myWritingRecipeList) {
						int recipeId = myWritingRecipeObj.getRecipeId();
						String recipeThumbnail = myWritingRecipeObj.getRecipeThumbnail();
						String recipeTitle = myWritingRecipeObj.getRecipeTitle();
				%>
						<div class="div_mypage_recipe_writing_content">
							<div class="div_mypage_recipe_writing_content_img">
								<a href="Controller?command=recipe_modify_form&recipeID=<%=recipeId%>">
									<img src="<%=recipeThumbnail%>"/>
								</a>
							</div>
							<div class="div_mypage_recipe_writing_content_title">
								<h2>
									<a href="Controller?command=recipe_modify_form&recipeID=<%=recipeId%>"><%=recipeTitle%></a>
								</h2>
							</div>
						</div>
				<% } %>
<!-- pagination -->
						<div id="div_mypage_page_panel">
							<% if(searchWord == null) { %>
								<div id="div_mypage_page_bundle">
									<% if(startPageNum != 1) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_recipe_view&mode=ing&page=<%=startPageNum-3%>">&lt;&lt;</a>
										</div>
									<% } %>
									<% if(pageNum != 1) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_recipe_view&mode=ing&page=<%=pageNum-1%>">&lt;</a>
										</div>
									<% } %>
										<% for(int i=startPageNum; i<=endPageNum; i++) { %>
											<% if(i > lastPageNum) break; %>
											<% if(pageNum == i) { %>
												<div class="div_mypage_page_button">
													<a href="Controller?command=mypage_recipe_view&mode=ing&page=<%=i%>" class="button_active"><%=i%></a>
												</div>
											<% } else { %>
												<div class="div_mypage_page_button">
													<a href="Controller?command=mypage_recipe_view&mode=ing&page=<%=i%>"><%=i%></a>
												</div>
											<% } %>
										<% } %>
									<% if(pageNum < lastPageNum) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_recipe_view&mode=ing&page=<%=pageNum+1%>">&gt;</a>
										</div>
									<% } %>
									<% if(lastPageNum > endPageNum) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_recipe_view&mode=ing&page=<%=endPageNum+1%>">&gt;&gt;</a>
										</div>
									<% } %>
								</div>
							<% } else { %>
								<div id="div_mypage_page_bundle">
									<% if(startPageNum != 1) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_recipe_view&mode=ing&serachWord=<%=searchWord%>&page=<%=startPageNum-3%>">&lt;&lt;</a>
										</div>
									<% } %>
									<% if(pageNum != 1) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_recipe_view&mode=ing&serachWord=<%=searchWord%>&page=<%=pageNum-1%>">&lt;</a>
										</div>
									<% } %>
										<% for(int i=startPageNum; i<=endPageNum; i++) { %>
											<% if(i > lastPageNum) break; %>
											<% if(pageNum == i) { %>
												<div class="div_mypage_page_button">
													<a href="Controller?command=mypage_recipe_view&mode=ing&serachWord=<%=searchWord%>&page=<%=i%>" class="button_active"><%=i%></a>
												</div>
											<% } else { %>
												<div class="div_mypage_page_button">
													<a href="Controller?command=mypage_recipe_view&mode=ing&serachWord=<%=searchWord%>&page=<%=i%>"><%=i%></a>
												</div>
											<% } %>
										<% } %>
									<% if(pageNum < lastPageNum) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_recipe_view&mode=ing&serachWord=<%=searchWord%>&page=<%=pageNum+1%>">&gt;</a>
										</div>
									<% } %>
									<% if(lastPageNum > endPageNum) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_recipe_view&mode=ing&serachWord=<%=searchWord%>&page=<%=endPageNum+1%>">&gt;&gt;</a>
										</div>
									<% } %>
								</div>
							<% } %>
						</div>
<!-- /pagination -->
<!-- 검색창 -->
						<div id="div_mypage_recipe_search_bar">
							<form action="Controller" method="get">
								<input type="hidden" name="command" value="mypage_recipe_view"/>
								<input type="hidden" name="mode" value="ing"/>
								<input type="text" name="searchWord"/>
								<span>
									<button>
										<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_search4.png"/>
									</button>
								</span>
							</form>
						</div>
<!-- /검색창 -->
					</div>
<!-- /메인컨텐츠(마이페이지 레시피 작성중) -->
					<% } %>
				<% } %>
			</div>
		</div>
		<div id="div_content_right" class="fl">
<!-- 프로필사진과 자기소개 변경창 -->
			<div id="div_profile_change" class="div_mypage_extra">
				<div id="div_profile_change_area" class="div_mypage_extra_area">
					<div id="div_profile_change_box" class="div_mypage_extra_box">
						<div id="div_change_box_header" class="div_mypage_extra_box_title">
							<h4 class="h4_box_header_title">프로필 사진 / 자기소개 편집</h4>
							<button type="button" class="button_box_close">
								<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기"/>
							</button>
						</div>
						<div id="div_change_box_body" class="div_mypage_extra_box_body">
						<!-- DB에서 프로필쪽 데이터 가져오기 -->
							<form action="ModifyProfile" method="post" enctype="multipart/form-data">
								<div id="div_img_change_box">
									<input type="hidden" name="previous_image" id="input_previous_image" value=""/>
									<img id="profile_img_preview" src=""/>
									<div id="div_img_change_box_button">
										<label id="label_file_button" for="input_file_button">
											<img src="https://recipe1.ezmember.co.kr/img/mobile/2023/btn_pic.png" alt="사진첩"/>
										</label>
										<input type="file" id="input_file_button" name="profile_image" style="display:none"/>
										<a>
											<img src="https://recipe1.ezmember.co.kr/img/mobile/2023/btn_del.png" alt="휴지통"/>
										</a>
									</div>
								</div>
								<div id="div_text_change_box">
									<div id="div_text_input">
										<input type="text" id="input_self_intro" name="self_intro" placeholder="자기소개를 100자 이내로 작성해 주세요." value=""/>
										<a>
											<img src="https://recipe1.ezmember.co.kr/img/mobile/2022/icon_close2.png" alt="삭제"/>
										</a>
									</div>
									<div id="div_change_confirm">
										<button id="btn_change_cancel" type="button">취소</button>
										<button id="btn_change_profile" type="submit">저장</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
<!-- /프로필사진과 자기소개 변경창 -->
<!-- 팔로워 팔로잉 보여주는 창 -->
			<div id="div_view_following" class="div_mypage_extra">
				<div id="div_view_following_area" class="div_mypage_extra_area">
					<div id="div_view_following_box" class="div_mypage_extra_box">
						<div id="div_view_following_header" class="div_mypage_extra_box_title">
							<h4 class="h4_box_header_title" id="view_follow_uid"><%=myNickname%>님의 친구</h4>
							<button type="button" class="button_box_close">
								<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기"/>
							</button>
						</div>
						<div id="div_view_following_body" class="div_mypage_extra_box_body">
							<ul id="following_list" class="ul_follow_list">
								<% if(loginId != null) { %>
									<% 
										for(UserFollowingDto myFollowingObj : myFollowingList) { 
											String myFollowingUid = myFollowingObj.getFollowingUid();
											String myFollowingNickname = myFollowingObj.getFollowingNickname();
											String myFollowingProfileImage = myFollowingObj.getFollowingProfileImage();
											boolean isFollowing = myFollowingObj.getIsFollowing();
									%>
									<li class="li_follow_user">
										<a href="Controller?command=mypage_recipe_view&uId=<%=myFollowingUid%>">
											<img src="<%=myFollowingProfileImage%>"/>
											<%=myFollowingNickname%>
										</a>
										<% if(myFollowingUid.equals(loginId)) { %>
										<% } else { %>
											<% if(isFollowing) { %>
												<button type="button" class="btn_follow_user delete" user="<%=myFollowingUid%>">
													삭제
												</button>
											<% } else { %>
												<button type="button" class="btn_follow_user add" user="<%=myFollowingUid%>">
													추가
												</button>
											<% } %>
										<% } %>
									</li>
									<% } %>
								<% } %>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!-- 팔로잉 완성하고 일단은 팔로워 하나 더 만들어두기 -->
			<div id="div_view_follower" class="div_mypage_extra">
				<div id="div_view_follower_area" class="div_mypage_extra_area">
					<div id="div_view_follower_box" class="div_mypage_extra_box">
						<div id="div_view_follower_header" class="div_mypage_extra_box_title">
							<h4 class="h4_box_header_title" id="view_follow_uid"><%=myNickname%>님을 따르는 친구</h4>
							<button type="button" class="button_box_close">
								<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기"/>
							</button>
						</div>
						<div id="div_view_follower_body" class="div_mypage_extra_box_body">
							<ul id="follower_list" class="ul_follow_list">
								<% if(loginId != null) { %>
									<%
										for(UserFollowerDto myFollowerObj : myFollowerList) {
											String myFollowerUid = myFollowerObj.getFollowerUid();
											String myFollowerNickname = myFollowerObj.getFollowerNickname();
											String myFollowerProfileImage = myFollowerObj.getFollowerProfileImage();
											boolean isFollowing = myFollowerObj.getIsFollowing();
									 %>
									<li class="li_follow_user">
										<a href="Controller?command=mypage_recipe_view&uId=<%=myFollowerUid%>">
											<img src="<%=myFollowerProfileImage%>"/>
											<%=myFollowerNickname%>
										</a>
										<% if(myFollowerUid.equals(loginId)) { %>
											<% } else { %>
												<% if(isFollowing) { %>
													<button type="button" class="btn_follow_user delete" user="<%=myFollowerUid%>">
														삭제
													</button>
											<% } else { %>
													<button type="button" class="btn_follow_user add" user="<%=myFollowerUid%>">
														추가
													</button>
											<% } %>
										<% } %>
									</li>
									<% } %>
								<% } %>
							</ul>
						</div>
					</div>
				</div>
			</div>
<!-- /팔로워 팔로잉 보여주는 창 -->
<!-- 프로필 -->
		<% if(loginId != null) { // 로그인 상태에서만 프로필 패널이 보임 %>
			<div id="div_profile_panel">
				<div id="div_profile_background"></div>
				<div id="div_profile_body">
					<div id="div_profile_img">
						<a id="a_profile_img">
							<img src="<%=myProfileImage%>"/>
						</a>
						<a id="a_change_profile_img">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_camera2.png" alt="사진변경"/>
						</a>
					</div>
					<div id="div_profile_user_name">
						<b><%=myNickname%></b>
					</div>
					<div id="div_profile_user_introduce">
						<span class="glyphicon glyphicon-pencil"></span>
						<a>
							<u>
								<% if(mySelfIntroduce == null) { %>
										자기소개를 입력할 수 있습니다.
								<% } else { %>
										<%=mySelfIntroduce%>
								<% } %>
							</u>
						</a>
					</div>
					<div id="div_profile_figures">
						<a>
							총조회
							<b><%=myTotalHits%></b>
						</a>
						<span>·</span>
						<a>
							팔로워
							<b><%=myFollower%></b>
						</a>
						<span>·</span>
						<a>
							팔로잉
							<b><%=myFollowing%></b>
						</a>
					</div>
				</div>
			</div>
			<div class="blank_space"></div>
		<% } %>
<!-- /프로필 -->
<!-- 사이트 자체 광고 -->
			<div class="advertisement">
				<a>
					<img src="https://recipe1.ezmember.co.kr/cache/rpe/2023/11/10/7082137c2850ee4529a1fbf6dbef597e1.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a>
					<img src="https://recipe1.ezmember.co.kr/cache/rpe/2023/10/30/fced5774b69326a935e9f8885eb714ea1.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a>
					<img src="https://recipe1.ezmember.co.kr/cache/shop/2023/11/06/ace207f7d4a96271e21bea459c7e3ff9.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a>
					<img src="https://recipe1.ezmember.co.kr/cache/shop/2023/11/23/b0f1fa801ac2ba6e631318df934e5c95.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a>
					<img src="https://recipe1.ezmember.co.kr/cache/shop/2023/11/20/b21b9c6dc74284e648ea0c67d22b3c9e.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a>
					<img src="https://recipe1.ezmember.co.kr/cache/shop/2021/07/09/f0705fbc1e6c357c8f07817cddfe614e.jpg"/>
				</a>
			</div>
<!-- /사이트 자체 광고 -->
<!-- 스토어 베스트상품 -->
			<div id="div_store_panel">
				<div id="div_store_best">
					<div id="div_store_title">
						<b>만개스토어
							<span>BEST</span>
						</b>
						<a href="Controller?command=store_mainlist">더보기 &gt;</a>
						<div style="clear:both;"></div>
					</div>
					<% for(AdvertisementGoodsVo goodsObj : advertisementGoodsList) { %>
						<%
							int productId = goodsObj.getProductId();
							String productImage = goodsObj.getProductImage();
							String productName = goodsObj.getProductName();
							String productCost = goodsObj.getProductCost();
							int productScore = goodsObj.getProductScore();
							int productCommentQty = goodsObj.getProductCommentQty();
						%>
						<div id="div_store_body">
							<div class="div_product" product="<%=productId%>">
								<a href="Controller?command=store_goodsDetail&product_id=<%=productId%>"><!-- 스토어 상품 상세보기로 이동 -->
									<div class="div_product_img" style="background:url(<%=productImage%>) center no-repeat; background-size:cover; height:305px;"></div>
									<div class="div_product_text">
										<p class="product_title"><%=productName%></p>
										<p class="product_price"><%=productCost%><small>원</small></p>
										<p class="product_star">
											<% for(int i=1; i<=5; i++) { %>
												<% if(i > productScore) { %>
													<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_star2.png"/>
												<% } else { %>
													<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_star2_on.png"/>
												<% } %>
											<% } %>
											<span>(<%=productCommentQty%>)</span>
										</p>
									</div>
								</a>
							</div>
						</div>
					<% } %>
					<div id="div_store_more">
						<button type="button" id="store_more_button" onclick="javascript:location.href='Controller?command=store_mainlist';">더보기 +</button>
					</div>
				</div>
			</div>
		</div>
		<div style="border-top: 1px solid #e6e6e6; clear:both;"></div>
<!-- /스토어 베스트상품 -->
	</div>
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