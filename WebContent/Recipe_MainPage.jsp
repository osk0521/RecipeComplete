<%@page import="dto.RecentRecipeDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.RecipeVo"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="vo.RecipeMainPageVo"%>
<%@ page import="vo.RecipeUserRankVo"%>
<%@ page import="vo.RecipeRankVo"%>
<%@ page import="vo.RecipeMainPageShoppingVo"%>
<%@ page import="vo.RecipeMagazineItemVo"%>
<%@ page import="vo.RecipeManagerCategoryVo"%>
<%@ page import="dao.RecipeMainPageDao"%>
<%@ page import="dao.RecipeMagazineBoardDao"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.ProfileDto"%>
<%
	String loginId = (String)session.getAttribute("loginId");
	// 로그인 계정의 프로필에 필요한 변수 선언
	String myProfileImage = null, myNickname = null;
	// 페이지를 요청했을 때 로그인되어있는 상태라면 프로필 관련 DTO객체 생성
	if(loginId!=null){
		ProfileDto myProfileDto = (ProfileDto)request.getAttribute("myProfile");
		myProfileImage = myProfileDto.getProfileImage(); // 프로필 이미지
		myNickname = myProfileDto.getNickname(); // 닉네임
	}
 	ArrayList<RecipeMainPageVo> eventImgList = (ArrayList<RecipeMainPageVo>) request.getAttribute("mainImgList"); 
 	ArrayList<RecipeUserRankVo> userList = (ArrayList<RecipeUserRankVo>) request.getAttribute("mainBestUserList"); 
 	ArrayList<RecipeRankVo> recipeList = (ArrayList<RecipeRankVo>) request.getAttribute("mainBestRecipeList");  
 	ArrayList<RecipeMagazineItemVo> magazineList = (ArrayList<RecipeMagazineItemVo>) request.getAttribute("mainMagazineList");
 	
 	ArrayList<RecipeManagerCategoryVo> getCategoryWhatList = (ArrayList<RecipeManagerCategoryVo>) request.getAttribute("getCategoryWhatList");   // 자-부 (X)
	ArrayList<RecipeManagerCategoryVo> getCategoryKindList = (ArrayList<RecipeManagerCategoryVo>) request.getAttribute("getCategoryKindList");   // 자-부 (X)
	ArrayList<RecipeManagerCategoryVo> getCategorySituation = (ArrayList<RecipeManagerCategoryVo>) request.getAttribute("getCategorySituation");   // 자-부 (X)
	
 	ArrayList<RecipeMainPageShoppingVo> ShoppingBestList = (ArrayList<RecipeMainPageShoppingVo>) request.getAttribute("mainShoppingBestList");
 	RecipeMainPageDao rmpDao = new RecipeMainPageDao();
 	RecipeMagazineBoardDao RMBDao = new RecipeMagazineBoardDao();
 	// 페이지 하단 최근 본 레시피 리스트 가져오기
 	ArrayList<RecentRecipeDto> recentRecipeList = (ArrayList<RecentRecipeDto>)request.getAttribute("recentRecipeList");
 %>	
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8" />
	<title>요리를 즐겁게~ 만개의 레시피</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet"/>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet"/>
	<link rel="preconnect" href="https://fonts.googleapis.com"/>
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700&display=swap" rel="stylesheet"/>
	<link rel="stylesheet" href="Recipe_CSS/Recipe_MainPage.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Header.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Footer.css"/>
	<link href="slick-jw/slick_jw.css" rel="stylesheet"/>
	<link href="slick-jw/slick-theme_jw.css" rel="stylesheet"/>
	<script src="js/jquery-3.7.1.min.js"></script>
	<script src="js/Recipe_MainPageChart.js"></script>
	<script src="js/echarts.min.js"></script>  
	<script src="https://fastly.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js"></script>
	<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
	<script src="js/Recipe_Header.js"></script>
	<script src="js/Recipe_Footer.js"></script>
	<script>
	
	<%
	if(loginId != null && loginId.equals("admin"))  {
	%>
		$(".manager").css("display", "");
	<%
	}
	%>
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
<!------------ body ------------>
<!--매니저-->
	<nav class="manager">
		<table>
			<tr>
				<td class="now">
					<a href="Controller?command=main_page">메인페이지 편집</a>
				</td>
			</tr>
			<tr>
				<td class="notnow">
					<a href="Controller?command=main_page&manage=sw">검색 카테고리 편집</a>
				</td>
			</tr>
			<tr>
				<td class="notnow">
					<a href="Controller?command=manager_page&manage=content">게시물 관리</a>
				</td>
			</tr>
			<tr>
				<td class="notnow">
					<a href="Controller?command=manager_page&manage=member">회원 관리</a>
				</td>
			</tr>
			<tr>
				<td class="notnow">
					<a href="Controller?command=manager_page&manage=answer">문의 답변하기</a>
				</td>
			</tr>
			<tr>
				<td class="notnow">
					<a href="Controller?command=manager_page&manage=magazine">매거진 편집</a>
				</td>
			</tr>
		</table>
	</nav>
	<aside class="manager">
		<span>월별 이용자 그래프</span>
		<div id="chart-container" class="chart-view">
			<canvas>
		</div>
	</aside>
	<dd class="container">
		<div class="main_page_slide top">
			<ul class="exhibition_box" id="event_item_List">
				<li style="width: 374px;">
					<a>
						<img style="width:360px;" src="images/event/event01.jpg">
					</a>
				</li>
				<li style="width: 374px;">
					<a>
						<img style="width:360px;" src="images/event/event02.jpg">
					</a>
				</li>
				<li style="width: 374px;">
					<a>
						<img style="width:360px;" src="images/event/event03.jpg">
					</a>
				</li>
				<li style="width: 374px;">
					<a>
						<img style="width:360px;" src="images/event/event04.jpg">
					</a>
				</li>
				<li style="width: 374px;">
					<a>
						<img style="width:360px;" src="images/event/event05.jpg">
					</a>
				</li>
			</ul>
		</div>
	  <!--쇼핑베스트-->
		<div class="body_content">
			<div class="manager_btn manager">
				<div>
					<a target="_blank" class="edit">편집</a>
					<a target="_blank" class="onedit">추가</a>
					<a target="_blank" class="onedit">삭제</a>
					<a target="_blank" class="onedit">완료</a>
				</div>
			</div>
			<dl class="main_page_slide" id="Shopping_Best">
				<dt>
					<h3>
						<a href="Controller?command=store_mainlist">
							<span id="green_keyword">쇼핑</span> BEST
						</a>
					</h3>
					<div class="home_cont_r2">
						<a href="Controller?command=store_mainlist" target="_blank" class="btn_more">더보기</a>
						<svg class="manager" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
							<path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
						</svg>
					</div>
				</dt>
				<dd>
					<ul class="ul_shopping_best" id="shopping_best">
						<%
							for(RecipeMainPageShoppingVo mpsVO : ShoppingBestList) {
						%>
						<li class="li_shopping_best_items">
							<a href="Controller?command=store_goodsDetail&product_id=<%=mpsVO.getProductId()%>" class="link">
								<div class="thumb">
									<%if(mpsVO.getDeliveryFee() != 0 ) {%>
									<div class="icon_free">무료배송</div>
									<%} %>
										<img src="<%=mpsVO.getThumbnail() %>">
									</div>
								<div class="caption">
									<div class="caption_tit line2">
										<%=mpsVO.getTitle() %>
									</div>
									<div class="price_box">
										<strong class="price"><%=mpsVO.getPrice() %><small>원</small> </strong>
									</div>
									<div class="caption_rv">
										<%if(mpsVO.getHitcnt() != 0 ) { %>
										<span class="caption_rv_star">
											<%for(int i = 0; i<5; i++) { %>
												<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_star2_on.png">
											<% } %>
										</span>
										<span class="caption_rv_hits">(<%=mpsVO.getHitcnt() %>)</span>
										<% } %>
									</div>
								</div>
							</a>
						</li>
						<%
						}
						%>
					</ul>
				</dd>
			</dl>
			<dl class="main_page_slide" id="user_introduce">
				<dt>
					<h3>
						<a href="Controller?command=chef_view">
							<span id="green_keyword">쉐프</span> 소개
						</a>
					</h3>
					<div class="home_cont_r2">
						<a href="Controller?command=chef_view" class="btn_more">더보기</a>
						<svg class="manager" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
							<path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
						</svg>
					</div>
				</dt>
				<dd style="margin-top:-10px;">
					<div class="user_list" id="user_list">
						<%
						for(RecipeUserRankVo ruVO : userList) {
						%>
						<li class="li_user_list" style="text-align: center; width: 110px;">
							<a href="Controller?command=mypage_recipe_view&u_Id="<%=ruVO.getUserID() %> style="display:table-cell;padding:10px;">
								<img src="<%=ruVO.getProfile()%>">
							</a>
							<span class="user_list_name" id="chef_friend_name_<%=ruVO.getUserID() %>" style="width:auto;"><%=ruVO.getNickname()%></span>
							<a id="" class="alim_btn_st2" style="display:none;">+소식받기</a> <!-- 기능구현 전까지 숨기기 -->
						</li>
						<%
						}
						%>
					</div>
				</dd>
			</dl>
			<dl class="main_page_slide" id="pair-well-together”">
				<dt>
					<h3>
						<a href="Controller?command=magazine_detail_action&magazineID=1">
							<span id="green_keyword"><%=RMBDao.getMagazineNameByID(1) %></span>
						</a>
					</h3>
					<div class="home_cont_r2">
						<a href="Controller?command=magazine_detail_action&magazineID=1" class="btn_more">더보기</a>
						<svg class="manager" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
							<path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
						</svg>
					</div>
				</dt>
				<dd>
					<ul class="ul_convenient_combo_list" id="convenient_combo_list">
						<%
						for(RecipeMagazineItemVo vo : magazineList) {
						%>
						<li class="li_convenient_combo_items">
							<a href="Controller?command=view_recipe_detail&recipeID=<%=vo.getRecipeID()%>" class="link">
								<div class="thumb2">
									<img src="<%=vo.getThumbnail()%>"/>
								</div>
								<div class="caption">
									<div class="caption_tit line2">
										<%=vo.getTitle() %>
									</div>
								</div>
							</a>
						</li>
						<%
						}
						%>
					</ul>
				</dd>
			</dl>
			<dl class="main_page_slide" id="cate_list">
				<dt>
					<h3>
						<a href="Controller?command=recipe_search">
							레시피 <span id="green_keyword">분류</span>
						</a>
					</h3>
					<div class="home_cont_r2">
						<a href="Controller?command=recipe_search" class="btn_more">더보기</a>
						<svg class="manager" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
							<path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
						</svg>
					</div>
				</dt>
				<dd>
					<div class="cate_cont" style="height:100px;" id ="cate_cont">
						<li class="li_cate_list">
							<a href="Controller?command=recipe_search">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/cate1_01.png">
								<span>전체</span>
							</a>
						</li>
						<%
							for(RecipeManagerCategoryVo rmcVo : getCategoryWhatList) {
						%>
						<li class="li_cate_list">
							<a href="Controller?command=recipe_search&w=<%=rmcVo.getCategoryId()%>">
							<img src="<%=rmcVo.getImage() %>">
								<span><%=rmcVo.getName() %></span>
							</a>
						</li>
						<%
							}
						%>
						<%
							for(RecipeManagerCategoryVo rmcVo : getCategoryKindList) {
						%>
						<li class="li_cate_list">
							<a href="Controller?command=recipe_search&k=<%=rmcVo.getCategoryId()%>">
							<img src="<%=rmcVo.getImage() %>">
								<span><%=rmcVo.getName() %></span>
							</a>
						</li>
						<%
							}
						%>
						<%
							for(RecipeManagerCategoryVo rmcVo : getCategorySituation) {
						%>
						<li class="li_cate_list">
							<a href="Controller?command=recipe_search&s=<%=rmcVo.getCategoryId()%>">
							<img src="<%=rmcVo.getImage() %>">
								<span><%=rmcVo.getName() %></span>
							</a>
						</li>
						<%
							}
						%>
					</div>
				</dd>
			</dl>
			<dl class="main_page_slide" id="best_recipe_list">
			   	<dt>
			    	<h3>
						<a href="Controller?command=ranking_page&rtype=r&ptype=d">
							<span id="green_keyword">베스트 </span>레시피
						</a>
					</h3>
					<div class="home_cont_r2">
						<a href="Controller?command=ranking_page&rtype=r&ptype=d" class="btn_more">더보기</a>
						<svg class="manager" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
							<path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
						</svg>
					</div>
				</dt>
				<dd>
					<ul class="ul_best_recipe_list">
					<%
					int i=1;
					for(RecipeRankVo vo : recipeList) {
					%>
						<li class="best_recipe_list">
							<p class="best_recipe_list_num">
								<b><%=i %></b>
							</p>
							<div class="rank_thumbnail">
								<a href="Controller?command=view_recipe_detail&recipeID=<%=vo.getRecipeID()%>" class="Rank_link">
								<img src="<%=vo.getThumbnail()%>">
								</a>
							</div>
							<div class="Recipe_caption">
								<div class="recipe_caption_tit line2"><%=vo.getTitle()%></div>
								<div class="recipe_caption_rv_name">
									<a href="/MyPageRecipeView?uId="<%=vo.getUserID() %>>
									<img src="<%=vo.getProfileImage()%>"><%=vo.getNickname()%></a>
								</div>
								<div class="recipe_caption_rv">
								<% 
									if(vo.getCommentCnt() != 0 ){
								%>
									<span class="caption_rv_star">
										<% 
											for(int j = 1; j <= 5; j++) {
										%>
												<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_star2_on.png">
										<% 
											}
										%>
									</span>	
								<% 
									}
								%>
									<span class="recipe_caption_rv_ea">(<%=vo.getCommentCnt()%>)</span>
									<span class="recipe_caption_hits">조회수 <%=vo.getHits()%></span>
								</div>
							</div>
						</li>
					<%
						++i;
					}
					%>
					</ul>
				</dd>
			</dl>
		</div>
	</dd>
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
				<textarea id="f_customer_send" name="cs_txt" placeholder="불편사항이나 제안사항이 있으신가요? 만개의레시피에 전하고 싶은 의견을 남겨주세요."></textarea>
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
			$(".exhibition_box").slick({
				infinite: true,
				slidesToShow: 3,
				slidesToScroll: 3
			});
			$("#shopping_best").slick({
				infinite: true,
				slidesToShow: 5,
				slidesToScroll: 5
			});
			$("#user_list").slick({
				infinite: true,
				slidesToShow: 10,
				slidesToScroll: 10
			});
			$("#cate_cont").slick({
				infinite: true,
				slidesToShow: 10,
				slidesToScroll: 10
			});
			$("#convenient_combo_list").slick({
				infinite: true,
				slidesToShow: 4,
				slidesToScroll: 4
			});
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