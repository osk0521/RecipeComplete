<%@page import="dto.RecentRecipeDto"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="vo.RecipeVo"%>
<%@ page import ="vo.RecipeDetailVo"%>
<%@ page import ="vo.RecipeProcessVo"%>
<%@ page import ="vo.RecipeIngrediVo"%>
<%@ page import ="vo.RecipeCommentVo"%>
<%@ page import ="dao.RecipeBoardDao"%>
<%@ page import="dto.ProfileDto"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% 
    Date today = new Date(); 
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formatted_date = dateFormat.format(today);
%> 
<% 
	int recipeID = Integer.parseInt(request.getParameter("recipeID"));
	RecipeBoardDao rdao = new RecipeBoardDao();
	ArrayList<String> getCompleteImgAList = (ArrayList<String>)request.getAttribute("recipeCompleteImgList");
	ArrayList<RecipeVo> recipeList = (ArrayList<RecipeVo>)request.getAttribute("recipeList");
	RecipeDetailVo detailVo = (RecipeDetailVo)request.getAttribute("recipeDetailVo");
	RecipeProcessVo processVo = (RecipeProcessVo)request.getAttribute("recipeProcessVo");
	ArrayList<RecipeProcessVo> processList = (ArrayList<RecipeProcessVo>)request.getAttribute("recipeProcessList");
	
	ArrayList<RecipeIngrediVo> ingrediList = (ArrayList<RecipeIngrediVo>)request.getAttribute("recipeIngrideList");
	ArrayList<RecipeCommentVo> commentList = (ArrayList<RecipeCommentVo>)request.getAttribute("recipeCommentList");
	String loginId = (String)session.getAttribute("loginId");
	// 로그인 계정의 프로필에 필요한 변수 선언
	String myProfileImage = null, myNickname = null;
	// 페이지를 요청했을 때 로그인되어있는 상태라면 프로필 관련 DTO객체 생성
	String recipe_note = null;
	if(loginId!=null){
		recipe_note = (String)request.getAttribute("recipeNote");
		ProfileDto myProfileDto = (ProfileDto)request.getAttribute("myProfile");
		myProfileImage = myProfileDto.getProfileImage(); // 프로필 이미지
		myNickname = myProfileDto.getNickname(); // 닉네임
	}
%>
<%
	// 최근 본 레시피 저장을 위한 쿠키 객체 생성
	String recentRecipeId = "recipeId" + recipeID;
	String recipeIDString = String.valueOf(recipeID);
	Cookie c = new Cookie(recentRecipeId, recipeIDString);
	response.addCookie(c);
	
	// 페이지 하단 최근 본 레시피 리스트 가져오기
	ArrayList<RecentRecipeDto> recentRecipeList = (ArrayList<RecentRecipeDto>)request.getAttribute("recentRecipeList");
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8" />
	<title><%=rdao.getTitleByID(recipeID)%></title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="Recipe_CSS/Recipe_ViewRecipe.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Header.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Footer.css"/>
	<link href="slick-jw/slick_jw.css" rel="stylesheet"/>
	<link href="slick-jw/slick-theme_jw.css" rel="stylesheet"/>
	<script src="js/jquery-3.7.1.min.js"></script>
	<script src="js/Recipe_ViewRecipe.js"></script>
	<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
	<script src="js/Recipe_Header.js"></script>
	<script src="js/Recipe_Footer.js"></script>
	<script>
		let loginId = "<%=loginId%>";
		let recipeID = <%=recipeID%>;
		let myProfileImage = "<%=myProfileImage%>";
		let myNickname = "<%=myNickname%>";
		if(loginId != null){
			$(".login").css("display", "none");
			$(".not-login").css("display", "flex");
		} else {
			$(".login").css("display", "");
			$(".not-login").css("display", "none");
		}
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
<div class="container">
	<div id="contents_area_full" style="clear:both;">
		<div class="view_recipe_hits_and_photo" recipe="<%=detailVo.getRecipeID()%>">
			<div class="cart_icon">
				<a>
					<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_pdt.png?v3" id="shopListTopBtn" height="44">
				</a>
			</div>
			<div class="hits_container" style="z-index:10;">
				<div class="view_hit_num">
					<span class="hit_num"><%=detailVo.getHits()%></span>
				</div>
			</div>
			<div class="top_image">
				<img class="portrait" style="text-align: center;" src="<%=detailVo.getThumbnail()%>">
			</div>
			<div class="writer_user_profile">
				<a class="writer_user_profile_Img" href="/profile/index.html?uid=<%=detailVo.getUserID() %>">
					<img src="<%=detailVo.getWriterProfileImage()%>">
				</a>
				<span class="writer_user_profile_nickname"><%=detailVo.getWriterNickname()%>
					<button id="friendDiv" type="button" class="btn btn-xs login">소식받기</button>
				</span>
			</div>
		</div>
		<div class="view_summary">
			<h3><%=detailVo.getTitle()%></h3>
			<div class="view_summary_introduction">
				<%=detailVo.getIntroduce()%>
			</div>
			<div class="view_summary_info">
				<%
					if(detailVo.getServing() != 0){
				%>
					<span class="view_summary_info1"><%=detailVo.getServing()%> 인분</span>
				<%
					}
				%>
				<%
					if(detailVo.getTime() !=0){
				%>
					<span class="view_summary_info2"><%=detailVo.getTime()%> 분</span>
				<%
					}
				%>
				<span class="view_summary_info3">LV.<%=detailVo.getLv()%></span>
			</div>
			<div class="Sharing_SNS">
				<div class="btn_list">
					<div class = "not-login"><!-- 비 로그인상태 -->
						<a id="copy_URL">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_url_copy.gif" data-original-title="레시피 주소복사">
						</a>
						<a id="kakao-link-btn">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_sns_k.png" data-toggle="tooltip" title="카카오톡">
						</a>
					</div>
					<div class="login" style="/* display:inline-flex;  */flex-wrap: wrap; justify-content: space-around;"><!-- 로그인상태 -->
						<a id="copy_URL">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_url_copy.gif" data-original-title="레시피 주소복사">
						</a>
						<a id="kakao-link-btn">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_sns_k.png" data-toggle="tooltip" title="카카오톡">
						</a>
						<a href="">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_view_scrap.png">
							<span>스크랩
								<b class="st2"><%=detailVo.getLikes()%></b>
							</span>
						</a>
						<a href="">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_view_talk.png">
							<span>공유
							</span>
						</a>
						<a>
							<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_view_talk.png">
							<span>댓글
								<b class="st3"><%=detailVo.getCommentCnt()%></b>
							</span>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="view_btn">
			<div class ="copyRecipeId">
				<a style="float: left" class="btn_copy_recipe_id" recipe_id="<%=detailVo.getRecipeID()%>">
					<img src="https://recipe1.ezmember.co.kr/img/btn2_id.png" data-toggle="tooltip" title="" data-original-title="레시피ID">
				</a>
				<a style="float: left" class="btn_memo">
					<img src="https://recipe1.ezmember.co.kr/img/btn2_note.png" data-toggle="tooltip" title="" data-original-title="메모">
				</a>
			</div>	
			<!-- 레시피 ID 복사하는 창 시작-->
			<div id ="copyRecipeId" class="modal fade in" style="display: none;">
				<div class="modal-dialog">
					<div class="modal-content" style="padding:0;">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close" id ="close">
								<span aria-hidden="true">
									<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기" width="18px" height="18px">
								</span>
							</button>
							<h4 class = "modal-title">레시피ID 복사</h4>
						</div>
						<div class = "modal-body">
							<div class="modal_guide">
								<p class="list">레시피 마다 <b>숫자로된 고유 ID</b>를 가지고 있습니다.<br>
									<span>예시) http://www.10000recipe.com/recipe/6828826</span><br>
									<b>맨끝의 숫자</b> 5~7자리가 레시피ID입니다.</p>
								<p class="list">글 작성 시에 <b>'@'뒤에 레시피ID</b>를 붙이면 해당 레시피 링크가 생성됩니다.<br>
									<span>예시) 쯔유소스는 @6843190를 참고해주세요.</span></p>
								<p class="list">복사(Ctrl+C) 후 원하는 곳에 붙여넣기(Ctrl+V)하세요.</p>
								<p class="r_id" id="txtRecipeId">@<%=detailVo.getRecipeID()%></p>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn-lg btn-default" id ="close">닫기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
			<!-- 레시피 ID 복사하는 창 끝-->
		
		<%
		if(recipe_note!=null){
		%>
		<div class="view_comment_area">
			<dl id ="my_note_content" class="view_comment st2">
				<dt>
				<div class="view_comment_tit_note">
					<img src="https://recipe1.ezmember.co.kr/img/mobile/2023/comment_tit2.png">
				</div>
				<div class="view_comment_more">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<img src="https://recipe1.ezmember.co.kr/img/mobile/2023/icon_more.png">
					</a>
					<ul class="dropdown-menu dropdown-menu-right" data-role="menu">
						<li>
							<a id="note_modify">수정</a>
						</li>
						<li>
							<a id="note_delete">삭제</a>
						</li>
					</ul>
				</div>
				<dd>
				<div class="view_comment_item">
				
				</div>
			</dd>
			</dl>	
		</div>
		<%
		}
		%>
			<!--  레시피 노트 입력하는 창 시작-->
		<div class="memo">
			<script>
			$(".btn_memo").click(function() {
				<%
				if(loginId == null){
				%>
					if(confirm("로그인이 필요합니다. \r\n\r\n로그인 하시겠습니까?")) {
						location.href = "Controller?command=login_form";
					} else {
						location.reload();
					}
				<%}%>
			});
			</script>
			<div id ="recipeMemo" class="modal fade in" style = "display: none;">
				<div class="modal-dialog" style="width: 520px;">
					<div class ="modal-content">
						<div class = "modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">
									<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기" width="18px" height="18px">
								</span>
							</button>
							<h4 class = "modal-title"><%=detailVo.getTitle()%></h4>
						</div>
						<div class="modal-body">
							<textarea id="note_content" class="form-control" rows="3" placeholder="해당 레시피에 대해서 나만의 노트를 작성해 보세요." style="height:260px;" spellcheck="false"><% if(recipe_note != null){ %><%=recipe_note %><% } %></textarea>
						</div>
						<div class="modal-footer note-modal-footer">
							<button type="button" class="btn-lg btn-default" id="close">닫기</button>
							<button type="button" class="btn-lg btn-primary" id="note_submit">저장</button>
							<script>
								$("#note_submit").keyup(function (e) {
									let note_content = $(this).val();
								});
								$("#note_submit").click(function() {
									if(note_content < 4){
										alert("노트 내용은 4자 이상으로 입력해 주세요.");
									} else {
										alert("등록되었습니다.");
										location.href = "Controller?command=add_recipe_note";
									}
								});
							</script>
						</div>
					</div>
				</div>
			</div>
		</div> <!-- 레시피 노트 입력하는 창 끝 -->
	<div class="blank_bottom"></div>
	<div class="Recipe_Ingredients_List">
		<div class="best_tit" style="margin-right: -25px; margin-left: -25px;">
			<b>재료</b>
			<span>Ingredients</span>
			<div class="best_tit_rmn">
				<button id="btn_measure_modal" type="button" class="btn btn-default">계량법 안내</button>
				<div id="measure_modal" class="modal fade in" aria-hidden="false" style="display: none;">
					<div class="modal-dialog">
						<div class="modal-content" style="padding:0;">
							<div class="modal-header">
								<button type="button" class="close" id="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">
										<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기" width="18px" height="18px">
									</span>
								</button>
								<h4 class="modal-title">계량법 안내</h4>
							</div>
							<div class="modal-body">
								<table class="weighing_modal">
									<tbody>
										<tr>
											<th>1큰술(1T, 1Ts)<br>= 1숟가락</th>
											<td>15ml = 3t<br>(계량스푼이 없는 경우 밥숟가락으로 볼록하게 가득 담으면 1큰술)</td>
										</tr>
										<tr>
											<th>1작은술(1t, 1ts)</th>
											<td>5ml<br>(티스푼으로는 2스푼이 1작은술)</td>
										</tr>
										<tr>
											<th>1컵(1Cup, 1C)</th>
											<td>200ml = 16T(한국,중국,일본)<br>(미국 및 서양의 경우 1C가 240~250ml이므로 계량컵 구매 사용시 주의)</td>
										</tr>
										<tr>
											<th>1종이컵</th>
											<td>180ml</td>
										</tr>
										<tr>
											<th>1oz</th>
											<td>28.3g</td>
										</tr>
										<tr>
											<th>1파운드(lb)</th>
											<td>약 0.453 킬로그램(kg)</td>
										</tr>
										<tr>
											<th>1갤런(gallon)</th>
											<td>약 3.78 리터(ℓ)</td>
										</tr>
										<tr>
											<th>1꼬집</th>
											<td>약 2g 정도이며 '약간'이라고 표현하기도 함</td>
										</tr>
										<tr>
											<th>조금</th>
											<td>약간의 2~3배</td>
										</tr>
										<tr>
											<th>적당량</th>
											<td>기호에 따라 마음대로 조절해서 넣으란 표현</td>
										</tr>
										<tr>
											<th>1줌</th>
											<td>한손 가득 넘치게 쥐어진 정도<br>(예시 : 멸치 1줌 = 국멸치인 경우 12~15마리, 나물 1줌은 50g)</td>
										</tr>
										<tr>
											<th>크게 1줌 = 2줌</th>
											<td>1줌의 두배</td>
										</tr>
										<tr>
											<th>1주먹</th>
											<td>여자 어른의 주먹크기, 고기로는 100g</td>
										</tr>
										<tr>
											<th>1토막</th>
											<td>2~3cm두께 정도의 분량</td>
										</tr>
										<tr>
											<th>마늘 1톨</th>
											<td>깐 마늘 한쪽</td>
										</tr>
										<tr>
											<th>생강 1쪽</th>
											<td>마늘 1톨의 크기와 비슷</td>
										</tr>
										<tr>
											<th>생강 1톨</th>
											<td>아기 손바닥만한 크기의 통생강 1개</td>
										</tr>
										<tr>
											<th>고기 1근</th>
											<td>600g</td>
										</tr>
										<tr>
											<th>채소 1근</th>
											<td>400g</td>
										</tr>
										<tr>
											<th>채소 1봉지</th>
											<td>200g 정도</td>
										</tr>
									</tbody>
								</table>            
							</div>
							<div class="modal-footer" style="display: block;">
								<button type="button" class="btn-lg btn-default" id="close" data-dismiss="modal">닫기</button>
							</div>
						</div>
					</div> 
				</div>
			</div>
		</div>
	</div>
	<div class="ingre_list">
		<%
				int exbundlebum = 0;
			for(RecipeIngrediVo riVO : ingrediList) {
				if( exbundlebum != riVO.getBundleNum() ){
					
		%>
		<% 			if(riVO.getBundleNum() >= 2) { %>
			</ul>
		<% 			} %>
		<ul>
			<b>[<%=riVO.getBundleName() %>]</b>
			<%
					exbundlebum = riVO.getBundleNum();
				}
			%>
			<li>
				<a href=""><%=riVO.getIngrediName()%>
					<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_info.png">
				</a>
				<a href="Controller?command=store_mainlist"class="buy_btn">구매</a>
				<span class="ingre_unit"><%=riVO.getQTY()%></span>
			</li>
		<%
			
			}
		%>
		</ul>
	</div>
	<div class="blank_bottom"></div>
	<div class="KnowHow"><!-- 노하우 --></div>
	<div class="blank_bottom"></div>
	<div class="view_step">
		<div class="best_tit">
			<b>조리순서</b>
			<span>Steps</span>
			<div class="best_tit_rmn">
				<a id="viewStep_btn_1">
					<img id="tabStepView1" src="https://recipe1.ezmember.co.kr/img/mobile/tab_view1.png" alt="이미지크게보기">
				</a>
				<a id="viewStep_btn_2">
					<img id="tabStepView2" src="https://recipe1.ezmember.co.kr/img/mobile/tab_view2.png" alt="텍스트만보기">
				</a>
				<a id="viewStep_btn_3">
					<img id="tabStepView3" src="https://recipe1.ezmember.co.kr/img/mobile/tab_view3_on.png" alt="이미지작게보기">
				</a>
			</div>
		</div>
		<div id="ViewCookingOrder3"><!-- 기본 시작 -->
		<%
			int i = 0;
				for(RecipeProcessVo rpVO : processList) {
					i++;
					if(rpVO.getProcessID() < 10000){
		%>
			<div class="view_step_cont media step<%=i%>">
			<style>
				.view_step_cont.step<%=i%> { background: url(//recipe1.ezmember.co.kr/img/icon_step_<%=i%>.gif)no-repeat 88px 6px;background-size: 36px;}
			</style>
				<div class="media-body">
					<%=rpVO.getProcess()%>
					<%
						if(rpVO.getMaterial() != null){
					%>	
						<p class="step_add add_material"><%=rpVO.getMaterial()%></p>
					<%
						}
					%>
					<%
						if(rpVO.getCookEquipment() != null){
					%>
						<p class="step_add add_tool">
							<a href="" style="color:#74b243"><%=rpVO.getCookEquipment()%></a>
						</p>
					<%
						}
					%>
					<%
						if(rpVO.getFire() != null){
					%>
						<p class="step_add add_fire"><%=rpVO.getFire()%></p>
					<%
						}
					%>
					<%
						if(rpVO.getTip() != null){
					%>
						<p class="step_add add_tip2"><%=rpVO.getTip()%></p>
					<%
						}
					%>
				</div>
				<%
					if(rpVO.getImage() != null){
				%>
						<div class="media-right">
							<img src="<%=rpVO.getImage()%>">
						</div>
				<%
					}
				%>
			</div>
		<%
			}
				}
		%>
		</div><!-- 기본 끝 -->
		<div id="ViewCookingOrder2" style="display: none;"><!-- 사진 없는 버전 -->
			
			<%
			i = 0;
			for(RecipeProcessVo rpVO : processList) {
				i++;
				if(rpVO.getProcessID() < 10000){
			%>
			<div class="view_step_cont media step<%=i%>">
			<style>
				.view_step_cont.step<%=i%> { background: url(//recipe1.ezmember.co.kr/img/icon_step_<%=i%>.gif) no-repeat 88px 6px; background-size: 36px; }
			</style>
				<div class="media-body">
					<%=rpVO.getProcess()%>
					<%
						if(rpVO.getMaterial() != null){
					%>	
						<p class="step_add add_material"><%=rpVO.getMaterial()%></p>
					<%
						}
					%>
					<%
						if(rpVO.getCookEquipment() != null){
					%>
						<p class="step_add add_tool">
							<a href="" style="color:#74b243"><%=rpVO.getCookEquipment()%></a>
						</p>
					<%
						}
					%>
					<%
						if(rpVO.getFire() != null){
					%>
						<p class="step_add add_fire"><%=rpVO.getFire()%></p>
					<%
						}
					%>
					<%
						if(rpVO.getTip() != null){
					%>
						<p class="step_add add_tip2"><%=rpVO.getTip()%></p>
					<%
						}
					%>
				</div>
			</div>
		<%
				}
			}
		%>
		</div>
	</div><!-- 사진 없이 끝 -->
	<div id="ViewCookingOrder1" style="display: none;">
		<%
			i = 0;
		for(RecipeProcessVo rpVO : processList) {
			i++;
			if(rpVO.getProcessID() < 10000){
		%>
			<div class="view_step_cont media step<%=i%>">
				<style>
					.view_step_cont.step<%=i%> { background: url(//recipe1.ezmember.co.kr/img/icon_step_<%=i%>.gif)no-repeat 88px 6px;background-size: 36px;}
				</style>
				<div class="media-body">
					<%=rpVO.getProcess()%>
					<%
						if(rpVO.getMaterial() != null){
					%>	
						<p class="step_add add_material"><%=rpVO.getMaterial()%></p>
					<%
						}
					%>
					<%
						if(rpVO.getCookEquipment() != null){
							String cookEquipment[] = (rpVO.getCookEquipment()).split(", ");
					%>
						<p class="step_add add_tool">
							<a href="" style="color:#74b243"><%=rpVO.getCookEquipment()%></a>
						</p>
					<%
						}
					%>
					<%
						if(rpVO.getFire() != null){
					%>
						<p class="step_add add_fire"><%=rpVO.getFire()%></p>
					<%
						}
					%>
					<%
						if(rpVO.getTip() != null){
					%>
						<p class="step_add add_tip2"><%=rpVO.getTip()%></p>
					<%
						}
					%>
				</div>
				<%
					if(rpVO.getImage() != null){
				%>
						<div class="Big_Img_For_Information_Page">
							<img src="<%=rpVO.getImage()%>">
						</div>
				<%
					}
				%>
			</div>
			<%
				}
			}
			%> <!-- 사진 크게 끝 -->
	</div>
		<div id ="search_equipment" class="modal fade in" style="display: none;">
			<div class="modal-dialog">
				<div class="modal-content" style="padding:0;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close" id ="close">
							<span aria-hidden="true">
								<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기" width="18px" height="18px">
							</span>
						</button>
						<h4 class="modal-title"id="equipment_names"></h4>
					</div>
					<div class="modal-body">
						<div class="ingredient_wrap">
							<ul class="ul_goods_in_modal ea3">
								<li class="li_goods_in_modal">
									<a href="https://ad.planbplus.co.kr/adlanding/?adid=573&amp;subid=10000recipe43&amp;landing_url=https%3A%2F%2Flink.coupang.com%2Fre%2FAFFSDP%3Flptag%3DAF0831180%26subid%3D10000recipe43%26pageKey%3D33163912%26itemId%3D124645697%26vendorItemId%3D3254010155%26traceid%3DV0-153-1307ceb4e4569274%26requestid%3D20240111084341564125354779%26token%3D31850C%257CMIXED&amp;adtype=2" target="_blank" class="common_rcp_link">
										<div class="common_rcp_thumb">
											<div style="background:url('https://ads-partners.coupang.com/image1/TVNPF7C-NBupcSmGTf1dcij5TbBvIA0XB59QeHlQbgVZHYMLUtOvrXKGBehm4NjxgRie7KXoqBhFCmkhzVIDZoZkr7xcoE2IQ2jKle9W5TSn9kfQkH-paJfIduIaMQT6EDOzFubl0pD15FnSGxVZJVaqT8e8vRsp8Dgs9XIYQrC8qpBHBqRS2IVxFYExyeAnHF9iNvYy9V1AniVOFps7aVuDuGDNPLKviGw319McowOU9M7VJeLbH7_8sXMTiCHilMAvRn46lbPW6m8XYDG0DxE_P-_2J5tOmVafxA==') center no-repeat; background-size : contain; width:270px; height: 270px;">
											</div>
										</div>
										<div class="common_rcp_caption">
											<div class="common_rcp_caption_tit line2">상품이름</div>
										</div>
										<div class="price_box">
											<strong class="common_rcp_caption_price">가격
												<small>원</small>
											</strong>
										</div>
									</a>
								</li>
							</ul>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn-lg btn-default" id ="close">닫기</button>
					</div>
				</div>
			</div>
		</div>
			<% 
			RecipeProcessVo lastProcessVo = (RecipeProcessVo)request.getAttribute("recipeLastProcessVo");
			if(lastProcessVo!=null){
				if(lastProcessVo.getLastimage() != "없음") {
			%>
		<div id="completeimgs">
			<%
					String lastIMG = lastProcessVo.getLastimage();
					/* SELECT recipe_id FROM cooking_order WHERE PROCESS_ID = 10000 */
					String [] lastIMGList = (lastIMG).split(" /// "); 
					for(int k = 0; k < lastIMGList.length; k++) {
			%>
					<div class="completeimgs_item">
						<img src="<%=lastIMGList[k]%>">
					</div>
			<%
					}
			%>
		</div>
			<%
				}
			%>
			<%
				if(lastProcessVo.getLastTipCaution() != "없음") {
			%>
				<dl class="view_step_tips">
					<dt>
						<img src="https://recipe1.ezmember.co.kr/img/tit_tip.gif" alt="팁-주의사항">
					</dt>
					<dd>
						<%=lastProcessVo.getLastTipCaution()%>
					</dd>
				</dl>
			<%
				}
			}
			%>
	<div class="blank_bottom"></div>
	<div class="view_step" id="relationGoods">
		<div class="view_notice">
			<p class="view_notice_date">
				<b>등록일 : <%=detailVo.getWriteDate()%></b>
				<b>수정일 : <%=detailVo.getUpdateDate()%></b>
			</p>
			<span>저작자의 사전 동의 없이 이미지 및 문구의 무단 도용 및 복제를 금합니다.</span>
		</div>
	</div>
	<div class="blank_bottom"></div>
	<div class="view_step">
		<div class="best_tit">
			<b>레시피 작성자</b>
			<span>About the writer</span>
		</div>
		<div class="writer_profile">
			<div class="profile_photo">
				<a href="/Controller?command=mypage_recipe_view?uId=<%=detailVo.getUserID()%>">
					<img src="<%=detailVo.getWriterProfileImage()%>">
				</a>
			</div>
			<div class="nickname_and_introduce">
				<p class="nickname"><%=detailVo.getWriterNickname()%>
					<button style="display: none;" type="button" class="Follow" onclick="">
						<span class="glyphicon glyphicon-plus"></span>
						소식받기
					</button>
				</p>
				<%
					if(detailVo.getWriterIntroduce()!=null && !((detailVo.getWriterIntroduce()).equals("null")) ) {
				%>
					<p class="introduce"><%=detailVo.getWriterIntroduce()%></p>
				<%
					}
				%>
			</div>
		</div>
	</div>
		<div class="blank_bottom"></div>
	<div class="view_comment">
		<div class="comment_title">
			댓글 <span id="recipe_comment_list_count"><%=detailVo.getCommentCnt()%></span>
		</div>
		<div id="recipe_comment_list">
		<%
		for(RecipeCommentVo rcvo :commentList) {
			int cnt=0;					
		%>
			<%
			if(commentList.size() > 0) {
				if(rcvo.getCommentOrder() == 1) {//대댓글 아님
			%>
					<div class="Review comment" id="comment_id_<%=rcvo.getCommentID()%>" cno="<%=rcvo.getCommentID()%>">
						<div class="comment-left">
							<a id ="<%=rcvo.getUserID()%>">
								<img src="<%= rcvo.getProfileImage()%>" data-holder-rendered="true" >
							</a>
						</div>

						<div class="comment-body">
							<h4 class="comment-heading">
								<b class="comment_info_name"><%= rcvo.getNickname()%></b>
								<%= rcvo.getCommentWriteDate() %>
								<span>|</span>
								<a class="reply_to_comment">답글</a>
								<% if(rcvo.getUserID().equals(loginId)) { %>
								<span>|</span>
								<a class="comment_delete">삭제</a>
								<span>|</span>
								<a class="comment_modify">편집</a>
								<% } %>
							</h4>
							<%= rcvo.getConmmentContent()%>
						</div>
					</div>
			<%
					} else {//if(rcvo.getCommentOrder() == 1)의 else<!-- 대댓글 -->
			%>
				<div class="Review comment recomment" id="comment_id_<%=rcvo.getCommentID()%>" cno="<%=rcvo.getCommentID()%>"><!-- 대댓글 -->
					<div class="comment-left">
						<a id ="<%=rcvo.getUserID()%>">
							<img class="media-object" src="<%= rcvo.getProfileImage()%>" data-holder-rendered="true">
						</a>
					</div>
					<div class="comment-body">
						<h4 class="comment-heading">
						<%
							if(rcvo.getNickname() == detailVo.getWriterNickname()) {
						%>
							<b class="comment_info_name-W"><%=detailVo.getWriterNickname()%></b>
						<%
							} else{
						%>
							<b class="comment_info_name"><%= rcvo.getNickname() %></b>
						<%
							}
						%> 
						<%= rcvo.getCommentWriteDate() %>
							<span>|</span>
							<a class="reply_to_comment">답글</a>
							<% if(loginId == rcvo.getUserID()) { %>
								<span>|</span>
								<a class="comment_delete">삭제</a>
								<span>|</span>
								<a class="comment_modify">편집</a>
							<% } %>
						<%
							if(rcvo.getCommentStars() != 0) {
						%>
								<span class="reply_list_star">
								<%
									for(int s=1; s == rcvo.getCommentStars(); s++) {
								%>
									<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_star2_on.png">
								<%
									}
								%>
								</span>
						<%
							}
						%>
						</h4>
					
						<%
							if(rcvo.getConmmentImage() != null){
						%>
							<a>
								<img src="<%=rcvo.getConmmentImage() %>">
							</a>
						<%
							}
						%>
						<%= rcvo.getConmmentContent()%>
					</div>
				</div>
				
				<%
						} 
					}
				}
				%>
				
			<div class="view_btn_more_or_less" style="display: none;">
				<a class="btn_comments" id="view_less">줄여보기</a>
			</div>
			<% if(detailVo.getCommentCnt() > 5) { %>
			<div class="view_btn_more_or_less" style="display: none;">
				<a class="btn_comments" id="view_more">전체보기</a>
			</div>
			<% } %>
			
			<div id="insert_reply_comment" class="reply_write">
				<input type="file" id="file_2" style="display:none;"/>
				<div style="width:100px;display:inline-block;">
					<div id="add_imgs" class="complete_pic">
						<img src="https://recipe1.ezmember.co.kr/img/pic_none3.gif" alt="파일첨부" width="100" height="100"style="cursor:pointer;">
					</div>
					<div id="img_add" class="complete_pic" style="display:none;">
						<img id="attach_imgs2" src="" width="100" height="100">
					</div>
				</div>
				<div class="input-group" style="width:960px;">
					<textarea id="comment_content" class="form-control" style="height:100px; width:100%; resize:none;"></textarea>
					<span class="input-group-btn">
						<button class="btn btn-default comment_submit" type="button" style="height:100px; width:100px;">등록</button>
					</span>
				</div>
			</div>
			
		</div>
	</div>
	<% if(!(detailVo.getTag().equals("없음"))) { %>
	<div class="blank_bottom"></div>
	<div class="view_step">
		<div class="view_tag">
			<% String tags[] = (detailVo.getTag()).split(" ");
			for(int a=0; a<tags.length; a++){ 
				%>
			<a href="Controller?command=recipe_search&sw=<%=tags[a].substring(1)%>"><%=tags[a] %></a>
		<% } %>
		</div>
	</div>
	<% } %>
</div> <!-- contents_area_full -->

	<div id="re_reply_div_" class="extra" style="padding-left:50px;">
		<div class="reply_write">
			<div id="insert_reply_comment_" class="reply_write recomment">
				<input type="hidden" name="content" value="">
				<input type="hidden" name="reply_recipe" value="<%=recipeID%>"/>
				<input type="hidden" name="comment_writer" value="<%=loginId %>">
				<input type="hidden" name="comment_to" id="parent_comment" value="">
				<input type="file" id="file_2" style="display:none;"/>
				<div style="width:100px;display:inline-block;">
					<div id="add_imgs2" class="complete_pic">
						<img src="https://recipe1.ezmember.co.kr/img/pic_none3.gif" alt="파일첨부" width="100" height="100"style="cursor:pointer;">
					</div>
					<div id="img_dd2" class="complete_pic" style="display:none;">
						<img id="attach_imgs2" src="" width="100" height="100">
					</div>
				</div>
				<div class="input-group" style="width:960px;">
					<textarea id="comment_content_" class="form-control" style="height:100px; width:100%; resize:none;"></textarea>
					<span class="input-group-btn">
						<button class="btn btn-default reply_to_comment" type="button" style="height:100px; width:100px;">등록</button>
					</span>
				</div>
			</div>
		</div>
	</div>

	<div class="Review comment extra" id="comment_id_">
		<div class="comment-left">
			<a id ="comment_writer_id">
				<img src="" data-holder-rendered="true" id="comment_writer_profileImage">
			</a>
		</div>

		<div class="comment-body">
			<h4 class="comment-heading">
				<b class="comment_info_name"></b>
				<%= formatted_date %>
				<span>|</span>
				<a class="reply_to_comment">답글</a>
				<span>|</span>
				<a class="comment_delete">삭제</a>
				<span>|</span>
				<a class="comment_modify">편집</a>
			</h4>
		</div>
	</div>
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
							int recentRecipeId2 = recentRecipeObj.getRecipeId();
							String recentRecipeTitle = recentRecipeObj.getRecipeTitle();
							String recentRecipeThumbnail = recentRecipeObj.getRecipeThumbnail();
						%>
						<div class="recent_recipe" recipe=<%=recentRecipeId2%>>
							<a href="Controller?command=view_recipe_detail&recipeID=<%=recentRecipeId2%>" class="rr_recipe_thumbnail">
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
		$("#completeimgs").slick({
			dots: true,
			infinite: true,
			slidesToShow: 1,
			slidesToScroll: 1,
			autoplay: true,
			autoplaySpeed: 2000
		}); 

		$("#div_recent_recipe_body").slick({
			infinite: false,
			slidesToShow: 6,
			slidesToScroll: 6
		});
		/* function loadComments() {
		    $.ajax({
		        type: "GET",
		        url: "", 
		        success: function(data) {
		            comments = data;
		            displayComments();
		        }
		    });
		} */
		
	});
	</script>
</body>
</html>