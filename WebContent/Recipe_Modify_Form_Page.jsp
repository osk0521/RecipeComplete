<%@page import="dto.RecentRecipeDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="vo.RecipeDetailVo"%>
<%@page import="vo.RecipeIngrediVo"%>
<%@page import="vo.RecipeProcessVo"%>
<%@page import="vo.RecipeLvVo"%>
<%@page import="vo.RecipeManagerCategoryVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.ProfileDto"%>

<%
	String loginId = (String)session.getAttribute("loginId");
	// 로그인 계정의 프로필에 필요한 변수 선언
	String myProfileImage = null, myNickname = null;
	// 페이지를 요청했을 때 로그인되어있는 상태라면 프로필 관련 DTO객체 생성
	if(loginId != null) {
		ProfileDto myProfileDto = (ProfileDto)request.getAttribute("myProfile");
		myProfileImage = myProfileDto.getProfileImage(); // 프로필 이미지
		myNickname = myProfileDto.getNickname(); // 닉네임
	}
	int recipeID = Integer.parseInt(request.getParameter("recipeID"));
	RecipeProcessVo vo = (RecipeProcessVo) request.getAttribute("vo");
	ArrayList<RecipeManagerCategoryVo> categoryListWhat = (ArrayList<RecipeManagerCategoryVo>) request.getAttribute("categoryListWhat");
	ArrayList<RecipeManagerCategoryVo> categoryListKind = (ArrayList<RecipeManagerCategoryVo>) request.getAttribute("categoryListKind");
	ArrayList<RecipeManagerCategoryVo> categoryListSituation = (ArrayList<RecipeManagerCategoryVo>) request.getAttribute("categoryListSituation");
	ArrayList<RecipeLvVo> getLvList =  (ArrayList<RecipeLvVo>) request.getAttribute("getLvList");
	RecipeDetailVo recipeDetailVo = (RecipeDetailVo) request.getAttribute("recipeDetailVo");
	ArrayList<RecipeIngrediVo> getIngrediList = (ArrayList<RecipeIngrediVo>) request.getAttribute("getIngrediList");
	ArrayList<RecipeProcessVo> getProcessList = (ArrayList<RecipeProcessVo>) request.getAttribute("getProcessList");
	RecipeProcessVo recipeLastProcessVo = (RecipeProcessVo) request.getAttribute("recipeLastProcessVo");
	int[] recipeCategory = (int[]) request.getAttribute("recipeCategory");
	String[] recipeCategoryName = (String[]) request.getAttribute("recipeCategoryName");
	String levelName = (String) request.getAttribute("levelName");
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
	<link rel="stylesheet" href="Recipe_CSS/Recipe_Write_And_Modify.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Header.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Footer.css"/>
	<link href="slick-jw/slick_jw.css" rel="stylesheet"/>
	<link href="slick-jw/slick-theme_jw.css" rel="stylesheet"/>
	<script src="js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b82b197deba45c29740d45e8e16a26c9&libraries=services"></script>
	<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
	<script src="js/Recipe_Write_And_Modify.js"></script>
	<script src="js/Recipe_Header.js"></script>
	<script src="js/Recipe_Footer.js"></script>
</head>
<body>
	<script>
		let loginId = "<%=session.getAttribute("loginId")%>";
		<%
		if(session.getAttribute("loginId") == null){
		%>
			if(confirm("로그인이 필요한 페이지입니다. \r\n\r\n로그인 하시겠습니까?")) {
				location.href = "Controller?command=login_form";
			} else {
				location.href = "Controller?command=main_page";
			}
		<%}%>
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
	<div class="container">
		<div class="regi_center">
			<div class="regi_title">
				레시피 등록
			</div>
		<form id ="recipe_form" action="Controller?command=recipe_modify&recipeID="<%=recipeID %> method="post">
			<div class="cont_box">
				<div class="div_main_photo_upload">
					<% if(recipeDetailVo.getThumbnail()==null) { %>
						<label class="input-file-button" for="main_photo_upload">
						</label>					
					<% } %>
					<input type="file" class="hidden" id="main_photo_upload" file_gubun="main" accept="jpeg,png,gif" name ="thumbnail">
					<img id="main_photo_holder" src="<%=recipeDetailVo.getThumbnail()%>"> 
				</div>
		  		<div class="cont_line">
					<p class="cont_tit">레시피 제목</p>
					<input style="width: 610px;"type="text" id="cok_title" name="title" class="form-control" placeholder="예) 소고기 미역국 끓이기" required="required" value="<%=recipeDetailVo.getTitle()%>" required="required">
				</div>
		  		<div class="cont_line">
					<p class="cont_tit">요리소개</p>
					<% if((recipeDetailVo.getIntroduce()).contains("<br>")) {
						(recipeDetailVo.getIntroduce()).replace("<br>", "");
					} %>
					<textarea id="cok_intro" class="form-control step_cont" name="introduce" spellcheck="false" placeholder="이 레시피의 탄생배경을 적어주세요. 예) 남편의 생일을 맞아 소고기 미역국을 끓여봤어요. 어머니로부터 배운 미역국 레시피를 남편의 입맛에 맞게 고안했습니다."><%=recipeDetailVo.getIntroduce()%></textarea>
		  		</div>
		  		<div class="cont_line">
		  			<p class="cont_tit">동영상</p>
		  			<textarea class="form-control step_cont" id="video_link" name="video_link" placeholder="동영상이 있으면 주소를 입력하세요.(Youtube,네이버tvcast,다음tvpot 만 가능) 예)http://youtu.be/lA0Bxo3IZmM">
		  			<% if(recipeDetailVo.getVideo()!=null){ %>
		  			<%=recipeDetailVo.getVideo() %>
		  			<% } %>
		  			</textarea>
		  			<div id="div_video_photo_box" class="thumbNail">
						<img id="video_photo_holder" src="https://recipe1.ezmember.co.kr/img/pic_none5.gif">
		  			</div>
		  		</div>
		  		<div class="cont_line">
		  			<p class="cont_tit">카테고리</p>
		  			<select class="cok_sq_category_4" name="what">
						<option value="<%=recipeCategory[0] %>"><%=recipeCategoryName[0]%></option>
						<%
							int i=1;
							for(RecipeManagerCategoryVo riVO : categoryListWhat) {
						%>
						<option value="<%=i%>"><%=riVO.getName() %></option>
						<% ++i; } %>
					</select>
					<select class="cok_sq_category_4" style="margin-left: 30px;" name="situation">
						<option value="<%=recipeCategory[1] %>"><%=recipeCategoryName[1] %></option>
						<%
							i=1;
						for(RecipeManagerCategoryVo riVO : categoryListSituation) {
						%>
						<option value="<%=i%>"><%=riVO.getName() %></option>
						<% ++i; } %>
					</select>
					<select class="cok_sq_category_4" style="margin-left: 30px;" name="kind">
						<option value="<%=recipeCategory[2] %>"><%=recipeCategoryName[2] %></option>
						<%
							i=1;
							for(RecipeManagerCategoryVo riVO : categoryListKind) {
						%>
						<option value="<%=i%>"><%=riVO.getName() %></option>
						<% ++i; } %>
					</select>
	<!-- 				<span class="guide" style="margin: -22px 0 0 146px;">분류를 바르게 설정해주시면, 이용자들이 쉽게 레시피를 검색할 수 있어요.</span> -->
		  		</div>
		  		<div class="cont_line" >
		  			<p class="cont_tit">요리정보</p>
		  			<div class="cont_line">
			        <select id="cok_portion" style="margin-left: 30px;" name="serving">
						<option value="<%=recipeDetailVo.getServing()%>"><%=recipeDetailVo.getServing()%>인분</option>
							<%
								for(int j = 1; j <=6 ; j++) {
									if(j < 6){
							%>
						<option value="<%=j%>"><%=j%>인분</option>
							<% } else { %>
						<option value="<%=j%>"><%=j%>인분이상</option>
							<% } } %>
					</select>
			        <select id="cok_time" style="margin-left: 30px;" name="time">
						<option value="<%=recipeDetailVo.getTime()%>"><%=recipeDetailVo.getTime()%>분 이내</option>
							<%
								int[] mins = {5, 10, 15, 20, 30, 60, 90};
								for (int j = 0; j < mins.length; j++) {
							%>
						<option value="<%=mins[j]%>"><%=mins[j]%>분이내</option>
							<% } %>
						<option value="<%=119%>">2시간이상</option>
						<option value="<%=120%>">2시간이상</option>
					</select>
			        <select id="cok_degree" style="margin-left: 30px;" name="lv">
						<option value="<%=recipeDetailVo.getLv()%>"><%=levelName%></option>
						<%
							for(RecipeLvVo rlVO : getLvList) {
						%>
						<option value="<%=rlVO.getLv()%>"><%=rlVO.getLevelName()%></option>
						<% } %>
					</select>
			      </div>
		  		</div>
		  	</div>
		  	<div class="cont_box">
		  		<span class="guide" id="ingridi" style="width:100%; margin-bottom: 15px;">재료가 남거나 부족하지 않도록 정확한 계량정보를 적어주세요.</span>
		  		<div class="material_group">
		  			<div class="div_material_group">
			  				<%  
			  					if(getIngrediList.size() >= 1) {
			  					int ex_ingredi_list_num = 0;
								i = 1;
							%>
							<%
									for(RecipeIngrediVo rivo : getIngrediList) { 
										int bundle_num = 1;
										int new_bundle_num = rivo.getBundleNum();
											if( bundle_num < new_bundle_num) {
												 i = 1;
											}
							%>
							<% if(ex_ingredi_list_num != rivo.getBundleNum()) { %>
								<% if(rivo.getBundleNum() >= 2) { %>
										</ul>
									</li>
									<div class="delete_li_material_group">
										<span class="delcont_tit_btn">
											<button type="button" class="btn-sm btn-default del_ingrede_group">
												<span class="glyphicon glyphicon-minus">
												</span> 재료 묶음 삭제
											</button>
										</span>
									</div>
								<% } %>
					  			<li class="li_material_group_<%=rivo.getBundleNum() %>">
									<p class="cont_tit6">
					  					<input type="text" id="material_group_title_<%=rivo.getBundleNum() %>" value="<%=rivo.getBundleName()%>" class="categorized form-control" name="bundle_name">
									</p>
									<ul class="material_group_list">
							<%
									}
							%>
										<li class="li_material">
											<input type="text" name="cok_material_nm_<%=rivo.getBundleNum()%>_<%=i%>[]" class="form-control cok_material_nm" placeholder="예) 돼지고기" value="<%=rivo.getIngrediName()%>">
											<input type="text" name="cok_material_amt_<%=rivo.getBundleNum()%>_<%=i%>[]" class="form-control cok_material_amt" placeholder="예) 300g"value="<%=rivo.getQTY()%>">
											<a class="btn-del"></a>
										</li>
							<% 
										++i;
										ex_ingredi_list_num = rivo.getBundleNum();
									} 
							%>
									</ul>
								</li>
								<%
								
			  					} else {
							%>
							<ul class="material_group_list">
								<li id="li_material_1_1" class="li_material">
									<input type="text" name="material_nm_1_1" class="form-control material_nm" placeholder="예) 돼지고기">
									<input type="text" name="material_amt_1_1" class="form-control material_amt" placeholder="예) 300g">
									<a class="btn-del"></a>
								</li>
								<li id="li_material_1_2" class="li_material">
									<input type="text" name="material_nm_1_2" class="form-control material_nm" placeholder="예) 양배추">
									<input type="text" name="material_amt_1_2" class="form-control material_amt" placeholder="예) 1/2개">
									<a class="btn-del"></a>
								</li>
								<li id="li_material_1_3" class="li_material">
									<input type="text" name="material_nm_1_3" class="form-control material_nm" placeholder="예) 참기름">
									<input type="text" name="material_amt_1_3" class="form-control material_amt" placeholder="예) 1T">
									<a class="btn-del"></a>
								</li>
							<% } %>
							</ul>
							<div class="delete_li_material_group">
								<span class="delcont_tit_btn">
									<button type="button" class="btn-sm btn-default del_ingrede_group">
										<span class="glyphicon glyphicon-minus">
										</span> 재료 묶음 삭제
									</button>
								</span>
							</div>
			  			</li>
		  			</div>
					<div class="btn_add">
						<button type="button" class="btn btn-default" id="add_ingredi">
							<span class="glyphicon glyphicon-plus-sign">
							</span>추가
						</button>
					</div>
					<div class="noti">※ 양념, 양념장, 소스, 드레싱, 토핑, 시럽, 육수 밑간 등으로 구분해서 작성해주세요.
						<div class="noti_btn">
							<button type="button" class="btn-lg btn-default" id ="add_ingrede_group">
								<span class="glyphicon glyphicon-plus">

								</span>재료/양념 묶음 추가
							</button>
						</div>
					</div>
		  		</div>
		  	</div>
		  	
  			<li id="li_material_group_extra" class="li_material_group extra"> 
  				<p class="cont_tit6">
  					<input type="text" id="material_group_title_" value="" class="categorized form-control" name="bundle_name">
				</p>
				<ul class="material_group_list">
					<li id="li_material_extra" class="li_material extra">
						<input type="text" name="material_nm_?_?" class="form-control material_nm" placeholder="예) 돼지고기">
						<input type="text" name="material_amt_?_?" class="form-control material_amt" placeholder="예) 300g">
						<a class="btn-del"></a>
					</li>
				</ul>
				<div class="btn_add">
					<button type="button" class="btn btn-default add_ingredi" id="add_ingredi_">
						<span class="glyphicon glyphicon-plus-sign">
						</span>추가
					</button>
				</div>
				<div class="delete_li_material_group">
					<span class="delcont_tit_btn">
						<button type="button" class="btn-sm btn-default del_ingrede_group">
							<span class="glyphicon glyphicon-minus">
							</span> 재료 묶음 삭제
						</button>
					</span>
				</div>
  			</li>
  			
  			<div class="step_list_extra extra">
				<div id="step_" class="div_step">
					<div id="div_step_item_" class="step">
						<p id="div_step_num_" class="step_num"></p>
						<div id="div_step_text" style="display:inline-block">
							<textarea name="process_text" id="process_text" class="form-control step_cont" placeholder="예) 소고기는 기름기를 떼어내고 적당한 크기로 썰어주세요."></textarea>
						</div>
						<div class="div_step_photo_box">
							<label id="input_file_button_for_step_" class="input_file_button_for_step" for="step_photo_upload_"></label>
							<input type="file" class="hidden" id="step_photo_upload_" file_gubun="step" accept="jpeg,png,gif,jpg" name ="step_photo_upload_">
							<img id="step_photo_holder_" name="process_image_" class="step_photo_holder" src="" style="display: none;">
						</div>
					</div>
					<div class="step_advice">
						<div style="padding:5px;">
							<a class="step_btn_material btn">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_material.png">재료</a>
							<a class="step_btn_cooker btn">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tool.png?v.1">도구</a>
							<a class="step_btn_fire btn">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_fire.png?v.1">불</a>
							<a class="step_btn_tip btn">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tip.png?v.1">팁</a>
							<a class="step_btn_all btn"> 전 체 </a>
							<div class="step_form_material">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_material.png">
								<input type="text" name="step_material" id="step_material" placeholder="밀가루 100g, 소금 2큰술, 물 100g" class="form-control">
							</div>
							<div class="step_form_cooker">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tool.png?v.1">
								<input type="text" name="step_cooker" id="step_cooker" placeholder="국자, 볼" class="form-control">
							</div>
							<div class="step_form_fire">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_fire.png?v.1"> 
								<input type="text" name="step_fire" id="step_fire" placeholder="약불" class="form-control">
							</div>
							<div class="step_form_tip">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tip.png?v.1">
								<textarea name="step_tip" id="step_tip" class="form-control">
								</textarea>
							</div>
						</div>
					</div>
					<div class="delete_step">
						<span class="delcont_tit_btn">
							<button type="button" class="btn-sm btn-default del_step">
								<span class="glyphicon glyphicon-minus">
								</span> 순서 삭제
							</button>
						</span>
					</div>
				</div>
			</div>
			
		  	<div class="cont_box">
				<p class="CStep" style="margin: 0px;">요리순서</p>
				<span class="guide" id="step">
					<b>요리의 맛이 좌우될 수 있는 중요한 부분은 빠짐없이 적어주세요.</b>
					<br>
					예) 10분간 익혀주세요 ▷ 10분간 약한불로 익혀주세요.<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;마늘편은 익혀주세요 ▷ 마늘편을 충분히 익혀주셔야 매운 맛이 사라집니다.<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;꿀을 조금 넣어주세요 ▷ 꿀이 없는 경우, 설탕 1스푼으로 대체 가능합니다.
				</span>
				<div class="step_list" id="step_list">
				<% if(getProcessList.size() >= 1) {
					for( RecipeProcessVo rpvo : getProcessList) { %>
				<div id="div_step_item_<%=rpvo.getProcessID()%>" class="step">
					<p id="div_step_num_<%=rpvo.getProcessID()%>" class="step_num" title="">Step<%=rpvo.getProcessID()%></p>
					<div id="div_step_text_<%=rpvo.getProcessID()%>" style="display:inline-block">
						<textarea name="step_text[]" id="step_text_<%=rpvo.getProcessID()%>" class="form-control step_cont" placeholder="예) 소고기는 기름기를 떼어내고 적당한 크기로 썰어주세요."><%=rpvo.getProcess() %></textarea>
					</div>
					<div class="div_step_photo_box">
						<% if(rpvo.getImage() == null) { %>
						<label class="input_file_button_for_step" for="step_photo_upload_<%=rpvo.getProcessID()%>"></label>
						<input type="file" class="hidden" id="step_photo_upload_<%=rpvo.getProcessID()%>" file_gubun="step" accept="jpeg,png,gif,jpg" name ="step_photo_upload_<%=rpvo.getProcessID()%>">
						<% } %>
						<img id="step_photo_holder_<%=rpvo.getProcessID()%>" name="process_image_<%=rpvo.getProcessID()%>" class="step_photo_holder" src="<%=rpvo.getImage()%>"/>
					</div>
				</div>
				<div style="width: 580px; border:2px solid #74b243; margin:5px 200px 5px;">
					<div style="padding:5px;">
						<a class="step_btn_material btn">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_material.png">재료</a>
						<a class="step_btn_cooker btn">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tool.png?v.1">도구</a>
						<a class="step_btn_fire btn">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_fire.png?v.1">불</a>
						<a class="step_btn_tip btn">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tip.png?v.1">팁</a>
						<a class="step_btn_all btn"> 전 체 </a>
						<div class="step_form_material">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_material.png">
							<input type="text" name="step_material[]" id="step_material_<%=rpvo.getProcessID()%>" placeholder="밀가루 100g,소금 2큰술,물 100g" class="form-control" value="<%=rpvo.getMaterial()%>">
						</div>
						<div class="step_form_cooker">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tool.png?v.1">
							<input type="text" name="step_cooker[]" id="step_cooker_<%=rpvo.getProcessID()%>" placeholder="국자, 볼" class="form-control" value="<%=rpvo.getCookEquipment()%>">
						</div>
						<div class="step_form_fire">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_fire.png?v.1"> 
							<input type="text" name="step_fire[]" id="step_fire_<%=rpvo.getProcessID()%>" placeholder="약불" class="form-control" value="<%=rpvo.getFire()%>">
						</div>
						<div class="step_form_tip">
							<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tip.png?v.1">
							<textarea name="step_tip[]" id="step_tip_<%=rpvo.getProcessID()%>" class="form-control"><%=rpvo.getTip()%>
							</textarea>
						</div>
					</div>
				</div>
				<div class="delete_step">
					<span class="delcont_tit_btn">
						<button type="button" class="btn-sm btn-default delete_step">
							<span class="glyphicon glyphicon-minus">
							</span> 순서 삭제
						</button>
					</span>
				</div>
			<% } } else { %>
				<div id="step_1" class="div_step">
					<div id="div_step_item_1" class="step">
						<p id="div_step_num_1" class="step_num">Step1</p>
						<div id="div_step_text_1" style="display:inline-block">
							<textarea name="process_text_1" id="process_text_1" class="form-control step_cont" placeholder="예) 소고기는 기름기를 떼어내고 적당한 크기로 썰어주세요."></textarea>
						</div>
						<div class="div_step_photo_box">
							<label class="input_file_button_for_step" for="step_photo_upload_1"></label>
							<input type="file" class="hidden" id="step_photo_upload_1" file_gubun="step" accept="jpeg,png,gif, jpg" name ="step_photo_upload_1">
							<img id="step_photo_holder_1" name="process_image_1" class="step_photo_holder" src="" style="display: none;">
						</div>
					</div>
					<div class="step_advice" id="step_advice_1">
						<div style="padding:5px;">
							<a class="step_btn_material btn">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_material.png">재료</a>
							<a class="step_btn_cooker btn">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tool.png?v.1">도구</a>
							<a class="step_btn_fire btn">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_fire.png?v.1">불</a>
							<a class="step_btn_tip btn">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tip.png?v.1">팁</a>
							<a class="step_btn_all btn"> 전 체 </a>
							<div class="step_form_material">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_material.png">
								<input type="text" name="step_material_1" id="step_material_1" placeholder="밀가루 100g, 소금 2큰술, 물 100g" class="form-control">
							</div>
							<div class="step_form_cooker">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tool.png?v.1">
								<input type="text" name="step_cooker_1" id="step_cooker_1" placeholder="국자, 볼" class="form-control">
							</div>
							<div class="step_form_fire">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_fire.png?v.1"> 
								<input type="text" name="step_fire_1" id="step_fire_1" placeholder="약불" class="form-control">
							</div>
							<div class="step_form_tip">
								<img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tip.png?v.1">
								<textarea name="step_tip_1" id="step_tip_1" class="form-control">
								</textarea>
							</div>
						</div>
					</div>
					<div class="delete_step">
						<span class="delcont_tit_btn">
							<button type="button" class="btn-sm btn-default del_step">
								<span class="glyphicon glyphicon-minus">
								</span> 순서 삭제
							</button>
						</span>
					</div>
				</div>
			<% } %>
			</div>
			<div class="step_btn_add">
				<button type="button" class="btn btn-default" id="add_step">
					<span class="glyphicon glyphicon-plus-sign">
					</span>순서추가
				</button>
			</div>
		  	<p class="cont_tit_3">요리완성사진</p>
			<div style="display:inline-block">
				<%	
					String last_image = recipeLastProcessVo.getLastimage();
					if(!(last_image.equals("없음"))) {
						String[] img_src = (recipeLastProcessVo.getLastimage()).split("  ///  ");
						for(int j=0; j<=img_src.length-1; j++) { %>
				<div id="div_work_upload_<%=j+1 %>" class="complete_pic">
					<label class="input_file_button_for_step" for="step_photo_upload_<%=j+1 %>"></label>
					<input type="file" class="hidden" id="step_photo_upload_<%=j+1 %>" file_gubun="step" accept="jpeg,png,gif,jpg" name ="step_photo_upload_<%=j+1 %>">
					<img id="last_photo_holder_<%=j+1 %>" name="last_image_<%=j+1 %>" class="step_photo_holder" src="<%=img_src[j] %>" style="display: none;">
				</div>
				<%} } else {
						for(int j=0; j<=3; j++) { %>
					<div id="div_work_upload_<%=j %>" class="complete_pic">
						<label class="input_file_button_for_step" for="step_photo_upload_<%=j %>"></label>
						<input type="file" class="hidden" id="step_photo_upload_<%=j %>" file_gubun="step" accept="jpeg,png,gif,jpg" name ="step_photo_upload_<%=j %>">
						<img id="last_photo_holder_<%=j %>" name="last_image_<%=j %>" class="step_photo_holder" src="https://recipe1.ezmember.co.kr/img/pic_none3.gif" style="display: none;">
					</div>
				<%} } %>
			</div>
	  	</div>
		  	<div class="cont_box">
		  		<p class="cont_tit_3">
		  		요리팁
				</p>
				
				
				<%
				if(!(recipeLastProcessVo.getLastTipCaution().equals("없음"))) {
				String last_tip_caution = "없음";
					last_tip_caution =(recipeLastProcessVo.getLastTipCaution()).trim();
				%>
				<textarea class="cook_tip form-control step_cont" placeholder="예) 고기요리에는 소금보다 설탕을 먼저 넣어야 단맛이 겉돌지 않고 육질이 부드러워요."><%= last_tip_caution%></textarea>
				<%} else {%>
				<textarea class="cook_tip form-control" name ="tip_caution" placeholder="예) 고기요리에는 소금보다 설탕을 먼저 넣어야 단맛이 겉돌지 않고 육질이 부드러워요."></textarea>
				<% } %>
		  	</div>
		  	<div class="cont_box" >
		  		<p class="cont_tit_3">태그
				</p>
				<ul class="tagit">
					<li>
					<input type="text" class="new-tag" autocomplete="off" name="tags" value="<% if(recipeDetailVo.getTag()!="없음") {String tag = (recipeDetailVo.getTag()).trim();%><%=tag%><%}%>">
					</li>
				</ul>
				<span class="tag-tip1">
						주재료, 목적, 효능, 대상 등을 태그로 남겨주세요. 
						<span class="tag-tip2">
							예) 돼지고기, 다이어트, 비만, 칼슘, 감기예방, 이유식, 초간단
						</span>
					</span>
		  	</div>
		  	
			<div class="save_btm">
				<button type="submit" name="delete" value="delete" class="edgebtn delete">삭제</button>
				<button type="submit" name="save_private" value="save_private" class="edgebtn save_private">저장</button>
				<button type="submit" name="save_public" value="save_public" class="edgebtn save_public">저장 후 공개하기</button>
				<button type="submit" onclick="history.back();" class="edgebtn cancel">취소</button>
				<input type="hidden" name="save_type" id="save_type" value="save"/>
				<!--<input type="hidden" name="recipeID" id="recipeID" value="<%=recipeID %>"/>
				 	<button id="login" type="button" class="edgebtn preview">미리보기</button> -->
			</div>
		</form>
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