<%@page import="dto.RecentRecipeDto"%>
<%@page import="vo.AdvertisementGoodsVo"%>
<%@page import="dto.MyPageCommentViewDto"%>
<%@page import="dto.ProfileDto"%>
<%@page import="dto.UserFollowingDto"%>
<%@page import="dto.UserFollowerDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//현재 로그인중인지 아닌지에 따라 마이페이지의 프로필 표시 여부가 결정됨
	String loginId = (String)session.getAttribute("loginId");
	// 헤더에 로그인 계정 표시를 위한 변수 선언
	String myProfileImage = null;
	// 타인 계정의 uId를 받아오기
	String otherUid = (String)request.getAttribute("uId");
	// 타인 계정의 프로필에 필요한 변수 선언
	String otherProfileImage = null, otherNickname = null, otherSelfIntroduce = null;
	int otherTotalHits = 0, otherFollower = 0, otherFollowing = 0;
	// 타인 계정의 팔로워에 필요한 변수 선언
	ArrayList<UserFollowerDto> otherFollowerList = null;
	// 타인 계정의 팔로잉에 필요한 변수 선언
	ArrayList<UserFollowingDto> otherFollowingList = null;
	// 타인 계정의 작성 댓글 정보에 필요한 변수 선언
	ArrayList<MyPageCommentViewDto> otherCommentList = null;
	// 로그인 계정이 현재 타인 계정을 팔로잉하는지 여부.
	boolean checkFollowing = false;
	
	// 페이지를 요청했을 때 로그인되어있는 상태라면 프로필 관련 DTO객체 받아오기
	if(loginId != null) {
		// 로그인 계정의 프로필 DTO
		ProfileDto myProfileDto = (ProfileDto)request.getAttribute("myProfile");
		myProfileImage = myProfileDto.getProfileImage(); // 프로필 이미지
		// 타인 계정의 프로필 DTO
		ProfileDto otherProfileDto = (ProfileDto)request.getAttribute("otherProfile"); // 타인 uId 기반 프로필 DTO객체 생성
		otherProfileImage = otherProfileDto.getProfileImage(); // 프로필 이미지
		otherNickname = otherProfileDto.getNickname(); // 닉네임
		otherSelfIntroduce = otherProfileDto.getSelfIntroduce(); // 자기소개
		if(otherSelfIntroduce == null) { // 자기소개가 없을 경우
	otherSelfIntroduce = "";
		}
		otherTotalHits = otherProfileDto.getTotalHits(); // 레시피 총조회수
		otherFollower = otherProfileDto.getFollower(); // 팔로워
		otherFollowing = otherProfileDto.getFollowing(); // 팔로잉
		// 타인 계정의 팔로워 DTO
		otherFollowerList = (ArrayList<UserFollowerDto>)request.getAttribute("otherFollowerList");
		// 타인 계정의 팔로잉 DTO
		otherFollowingList = (ArrayList<UserFollowingDto>)request.getAttribute("otherFollowingList");
		// 로그인 계정이 현재 타인 계정을 팔로잉하는지 여부.
		checkFollowing = (Boolean)request.getAttribute("checkFollowing");
		
		// 로그인 계정의 작성 댓글 리스트
		otherCommentList = (ArrayList<MyPageCommentViewDto>)request.getAttribute("otherCommentList");
	}
	// paging과 pagination을 위한 페이지 정보 받아오기
	int pageNum = 0;
	int startPageNum = 0;
	int endPageNum = 0;
	int lastPageNum = 0;
	try {
		pageNum = (Integer)request.getAttribute("pageNum");
		startPageNum = (Integer)request.getAttribute("startPageNum");
		endPageNum = (Integer)request.getAttribute("endPageNum");
		lastPageNum = (Integer)request.getAttribute("lastPageNum");
	} catch(NullPointerException e) {
		e.printStackTrace();
	}
	// 우측 하단 베스트상품 5종 가져오기
	ArrayList<AdvertisementGoodsVo> advertisementGoodsList = (ArrayList<AdvertisementGoodsVo>)request.getAttribute("advertisementGoodsList");
	// 페이지 하단 최근 본 레시피 리스트 가져오기
	ArrayList<RecentRecipeDto> recentRecipeList = (ArrayList<RecentRecipeDto>)request.getAttribute("recentRecipeList");
%>
<!DOCTYPE html>
<html>
<head>
	<!-- 마이페이지 타인계정일 때의 후기 페이지 -->
	<meta charset="UTF-8">
	<title>요리를 즐겁게~ 만개의 레시피</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700&display=swap" rel="stylesheet">
	<link href="Recipe_CSS/Recipe_MyPage_Comment_Others.css" rel="stylesheet">
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
	<script src="js/Recipe_MyPage_Other.js"></script>
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
					<a href="Controller?command=mypage_recipe_view&uId=<%=otherUid%>">
						<span class="glyphicon glyphicon-cutlery"></span>
						레시피
					</a>
				</div>
				<div class="div_mypage_header_button">
					<a href="Controller?command=mypage_comment_view&uId=<%=otherUid%>" class="button-active">
						<span class="glyphicon glyphicon-star"></span>
						요리후기
					</a>
				</div>
				<div class="div_mypage_header_button">
					<a href="Controller?command=mypage_followingchef_view&uId=<%=otherUid%>">
						<span class="glyphicon glyphicon-user"></span>
						쉐프
					</a>
				</div>
			</div>
<!-- /마이페이지 헤더 -->
<!-- 메인컨텐츠(마이페이지 댓글 타인이 적은거) -->
			<div id="div_mypage_comment_panel">
				<% if(loginId != null) { %>
					<% if(otherCommentList.isEmpty()) { %>
						<div id="div_mypage_comment_body" style="text-align:center; margin:22px 10px; padding:0;">
							<div id="div_mypage_comment_none">
								<img src="https://recipe1.ezmember.co.kr/img/none_feel.png"/>
								<p>
									직접 요리해보셨나요?
								</p>
								직접 요리하시고 후기를 남겨보세요!
							</div>
						</div>
					<% } else { %>
						<div id="div_mypage_comment_body">
							<div id="div_mypage_comment_content">
								<% for(MyPageCommentViewDto commentViewObj : otherCommentList) { %>
									<%
										int recipeId = commentViewObj.getRecipeId();
										int commentId = commentViewObj.getCommentId();
										String recipeThumbnail = commentViewObj.getRecipeThumbnail();
										String commentContent = commentViewObj.getCommentContent();
										String commentWriterNickname = commentViewObj.getCommentWriterNickname();
										int commentStar = commentViewObj.getCommentStar();
										String commentWritedate = commentViewObj.getCommentWritedate();
									%>
									<div class="div_mypage_comment_item">
										<div>
											<a href="Controller?command=view_recipe_detail&recipeID=<%=recipeId%>"> <!-- 해당 레시피의 상세페이지로 이동 -->
												<img src="<%=recipeThumbnail%>"/>
											</a>
										</div>
										<div>
											<div class="div_comment_text"><%=commentContent%></div>
											<div class="div_comment_writer">
												<span class="span_comment_writer"><%=commentWriterNickname%></span>
												<!-- 별점에 따라서 개수 달라짐 -->
												<span class="span_comment_stars">
												<% for(int i=1; i<=5; i++) { %>
													<% if(commentStar == 0) break; %>
													<% if(i > commentStar) { %>
														<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_star2.png"/>
													<% } else { %>
														<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_star2_on.png"/>
													<% } %>
												<% } %>
												</span>
											</div>
											<div class="div_comment_writedate"><%=commentWritedate%></div>
										</div>
									</div>
								<% } %>
							</div>
<!-- pagination -->
							<div id="div_mypage_page_panel">
								<div id="div_mypage_page_bundle">
									<% if(startPageNum != 1) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_comment_view&uId=<%=otherUid%>&page=<%=startPageNum-3%>">&lt;&lt;</a>
										</div>
									<% } %>
									<% if(pageNum != 1) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_comment_view&uId=<%=otherUid%>&page=<%=pageNum-1%>">&lt;</a>
										</div>
									<% } %>
										<% for(int i=startPageNum; i<=endPageNum; i++) { %>
											<% if(i > lastPageNum) break; %>
											<% if(pageNum == i) { %>
												<div class="div_mypage_page_button">
													<a href="Controller?command=mypage_comment_view&uId=<%=otherUid%>&page=<%=i%>" class="button_active"><%=i%></a>
												</div>
											<% } else { %>
												<div class="div_mypage_page_button">
													<a href="Controller?command=mypage_comment_view&uId=<%=otherUid%>&page=<%=i%>"><%=i%></a>
												</div>
											<% } %>
										<% } %>
									<% if(pageNum < lastPageNum) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_comment_view&uId=<%=otherUid%>&page=<%=pageNum+1%>">&gt;</a>
										</div>
									<% } %>
									<% if(lastPageNum > endPageNum) { %>
										<div class="div_mypage_page_button">
											<a href="Controller?command=mypage_comment_view&uId=<%=otherUid%>&page=<%=endPageNum+1%>">&gt;&gt;</a>
										</div>
									<% } %>
								</div>
							</div>
<!-- pagination -->
						</div>
					<% } %>
				<% } %>
<!-- /메인컨텐츠(마이페이지 댓글 타인이 적은거) -->
			</div>
		</div>
		<div id="div_content_right" class="fl">
<!-- 팔로워 팔로잉 보여주는 창 -->
			<div id="div_view_following" class="div_mypage_extra">
				<div id="div_view_following_area" class="div_mypage_extra_area">
					<div id="div_view_following_box" class="div_mypage_extra_box">
						<div id="div_view_following_header" class="div_mypage_extra_box_title">
							<h4 class="h4_box_header_title" id="view_follow_uid"><%=otherNickname%>님의 친구</h4>
							<button type="button" class="button_box_close">
								<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기"/>
							</button>
						</div>
						<div id="div_view_following_body" class="div_mypage_extra_box_body">
							<ul class="ul_follow_list">
								<% if(loginId != null) { %>
									<% 
										for(UserFollowingDto otherFollowingObj : otherFollowingList) {
											String otherFollowingUid = otherFollowingObj.getFollowingUid();
											String otherFollowingNickname = otherFollowingObj.getFollowingNickname();
											String otherFollowingProfileImage = otherFollowingObj.getFollowingProfileImage();
											boolean isFollowing = otherFollowingObj.getIsFollowing();
									%>
									<li class="li_follow_user">
										<a href="Controller?command=mypage_recipe_view&uId=<%=otherFollowingUid%>">
											<img src="<%=otherFollowingProfileImage%>"/>
											<%=otherFollowingNickname%>
										</a>
										<% if(otherFollowingUid.equals(loginId)) { %> 
										<% } else { %>
											<% if(isFollowing) { %>
													<button type="button" class="btn_follow_user delete" user="<%=otherFollowingUid%>">
														삭제
													</button>
											<% } else { %>
													<button type="button" class="btn_follow_user add" user="<%=otherFollowingUid%>">
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
							<h4 class="h4_box_header_title" id="view_follow_uid"><%=otherNickname%>님을 따르는 친구</h4>
							<button type="button" class="button_box_close">
								<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기"/>
							</button>
						</div>
						<div id="div_view_follower_body" class="div_mypage_extra_box_body">
							<ul class="ul_follow_list">
								<% if(loginId != null) { %>
									<%
										for(UserFollowerDto otherFollowerObj : otherFollowerList) {
											String otherFollowerUid = otherFollowerObj.getFollowerUid();
											String otherFollowerNickname = otherFollowerObj.getFollowerNickname();
											String otherFollowerProfileImage = otherFollowerObj.getFollowerProfileImage();
											boolean isFollowing = otherFollowerObj.getIsFollowing();
									%>
									<li class="li_follow_user">
										<a href="Controller?command=mypage_recipe_view&uId=<%=otherFollowerUid%>">
											<img src="<%=otherFollowerProfileImage%>"/>
											<%=otherFollowerNickname%>
										</a>
										<% if(otherFollowerUid.equals(loginId)) { %> 
											<% } else { %>
												<% if(isFollowing) { %>
													<button type="button" class="btn_follow_user delete" user="<%=otherFollowerUid%>">
														삭제
													</button>
											<% } else { %>
													<button type="button" class="btn_follow_user add" user="<%=otherFollowerUid%>">
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
							<img src="<%=otherProfileImage%>"/>
						</a>
					</div>
					<div id="div_profile_user_name">
						<b><%=otherNickname%></b>
					</div>
					<div id="div_profile_user_introduce">
					<% if(otherSelfIntroduce == null) { // 타인계정 프로필은 자기소개안적으면 아무것도 없음 %>
					<% } else { %>
						<%=otherSelfIntroduce%>
					<% } %>
					</div>
					<div id="div_profile_figures">
						<a >
							총조회
							<b><%=otherTotalHits%></b>
						</a>
						<span>·</span>
						<a >
							팔로워
							<b><%=otherFollower%></b>
						</a>
						<span>·</span>
						<a >
							팔로잉
							<b><%=otherFollowing%></b>
						</a>
					</div>
					<% if(checkFollowing) { %>
						<button type="button" class="btn_follow_user delete" user="<%=otherUid%>">
							<span class="glyphicon glyphicon-minus"></span>
							소식끊기
						</button>
					<% } else { %>
						<button type="button" class="btn_follow_user add" user="<%=otherUid%>">
							<span class="glyphicon glyphicon-plus"></span>
							소식받기
						</button>
					<% } %>
				</div>
			</div>
			<div class="blank_space"></div>
		<% } %>
<!-- /프로필 -->
<!-- 사이트 자체 광고 -->
			<div class="advertisement">
				<a href="#">
					<img src="https://recipe1.ezmember.co.kr/cache/rpe/2023/11/10/7082137c2850ee4529a1fbf6dbef597e1.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a href="#">
					<img src="https://recipe1.ezmember.co.kr/cache/rpe/2023/10/30/fced5774b69326a935e9f8885eb714ea1.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a href="#">
					<img src="https://recipe1.ezmember.co.kr/cache/shop/2023/11/06/ace207f7d4a96271e21bea459c7e3ff9.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a href="#">
					<img src="https://recipe1.ezmember.co.kr/cache/shop/2023/11/23/b0f1fa801ac2ba6e631318df934e5c95.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a href="#">
					<img src="https://recipe1.ezmember.co.kr/cache/shop/2023/11/20/b21b9c6dc74284e648ea0c67d22b3c9e.jpg"/>
				</a>
			</div>
			<div class="advertisement">
				<a href="#">
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
					(주)만개의레시피 / 사업자등록번호 291-81-02485 / 통신판매업신고 2022-서울금천-3089 / 벤처기업확인 / <a href="#" target="_self" style="color:#999; font-size:13px">사업자정보확인</a> <br>
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
					개별회신을 원하시면 <a href="#" style="text-decoration:underline; color:#666; font-size:13px">여기</a>에 문의하세요.
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
					<a href="#">
						<span style="background: url(//recipe1.ezmember.co.kr/img/btm_icon1.png) center no-repeat;"></span>
						제휴업체수
					</a>
					<b>575</b>
				</div>
				<div class="div_count_box">
					<a href="#">
						<span style="background: url(//recipe1.ezmember.co.kr/img/btm_icon2.png) center no-repeat;"></span>
						총 쉐프수
					</a>
					<b>3,970</b>
				</div>
				<div class="div_count_box">
					<a href="#">
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