<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="vo.RecipeIngredientVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@ page import="dto.ProfileDto"%>
<%
	String msg = (String) request.getAttribute("msg");
	request.setAttribute("msg", null);
	String loginId = (String)session.getAttribute("loginId");
	String myProfileImage = null, myNickname = null;
	// 페이지를 요청했을 때 로그인되어있는 상태라면 프로필 관련 DTO객체 생성
	if(loginId!=null){
		ProfileDto myProfileDto = (ProfileDto)request.getAttribute("myProfile");
		myProfileImage = myProfileDto.getProfileImage(); // 프로필 이미지
		myNickname = myProfileDto.getNickname(); // 닉네임
	}
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
 
	<link rel="stylesheet" href="Recipe_CSS/Recipe_Refrigerator.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Header.css"/>
	<link rel="stylesheet" href="Recipe_CSS/Public/Recipe_Footer.css"/>
	
	<link rel="stylesheet" type="text/css" href="https://ax5ui.axisj.com/assets/lib/ax5ui-combobox/dist/ax5combobox.css">
	<script src="js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="https://cdn.rawgit.com/ax5ui/ax5core/master/dist/ax5core.min.js"></script>
	<script type="text/javascript" src="https://ax5ui.axisj.com/assets/lib/ax5ui-combobox/dist/ax5combobox.js"></script> 
	<script src="js/Recipe_Refrigerator.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b82b197deba45c29740d45e8e16a26c9&libraries=services"></script>
	<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
	<script src="js/Recipe_Header.js"></script>
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
				<a href="Controller?command=mypage_recipe_view">
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
							let map;
							$(function() {
								$(".search_results").on("click", "tbody > tr > td.results_name",  function() {
									let r_id = $(this).parent().attr("recipe");
									console.log(r_id);
									location.href = "Controller?command=view_recipe_detail&recipeID="+r_id;
								});
								let mapContainer = document.getElementById('map_api'), // 지도를 표시할 div 
							    mapOption = { 
									center: new kakao.maps.LatLng(37.5540356, 126.9356962),// 지도의 중심좌표
							        level: 3 // 지도의 확대 레벨
							    };
								// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
								
								// 지도를 생성합니다    
								map = new kakao.maps.Map(mapContainer, mapOption); 
								
								// 장소 검색 객체를 생성합니다
								let ps = new kakao.maps.services.Places(map);
								
								// 카테고리로 은행을 검색합니다
								/* ps.keywordSearch("시장", placesSearchCB);  */
								ps.categorySearch("MT1", placesSearchCB, {useMapBounds:true}); 
								// 키워드 검색 완료 시 호출되는 콜백함수 입니다
								function placesSearchCB (data, status, pagination) {
								    if (status === kakao.maps.services.Status.OK) {
								        for (var i=0; i<data.length; i++) {
								            displayMarker(data[i]);    
								        }       
								    }
								}
								
								// 지도에 마커를 표시하는 함수입니다
								function displayMarker(place) {
								    // 마커를 생성하고 지도에 표시합니다
								    let marker = new kakao.maps.Marker({
								        map: map,
								        position: new kakao.maps.LatLng(place.y, place.x) 
								    });
								
								    // 마커에 클릭이벤트를 등록합니다
								    kakao.maps.event.addListener(marker, 'click', function() {
								        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
								        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
								        infowindow.open(map, marker);
								    });
								}
							});
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
						<a href="" onclick="move_write_recipe();">
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
<!-- 페이지 상단 메뉴바 -->
	<div class="container">
		<div class="Refrigerator">
			<div id="buy_missing_ingredients" class="modal" style="display: none;">
				<div class="modal-dialog">
					<div class="modal-content" style="padding:0;">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<img src="https://recipe1.ezmember.co.kr/img/btn_close.gif" alt="닫기" width="22px" height="22px" onclick="close();">
							</button>
							<h4 class="modal-title">재료 구매</h4>
						</div>
						<div class="modal-body">
							<div class="modal_guide">
			                    <p>레시피의 재료 중 <b class="not_have"></b>가 없으시군요.<br></p>
							</div>
							<span>근처 시장 찾기</span>
							<div class="map_api" id="map_api">
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn-lg btn-default close_2" onclick="close();">닫기</button>
						</div>
					</div>
				</div>
			</div>
			<img src="images/Refrigerator.png" alt="냉장고 일러스트">
			<table class="table_users_ingredi">
				<tbody>
					<tr>
						<td id="table_users_ingredi_td_1"></td>
						<td id="table_users_ingredi_td_2"></td>
						<td id="table_users_ingredi_td_3"></td>
					</tr>
					<tr>
						<td id="table_users_ingredi_td_4"></td>
						<td id="table_users_ingredi_td_5"></td>
						<td id="table_users_ingredi_td_6"></td>
					</tr>
					<tr>
						<td id="table_users_ingredi_td_7"></td>
						<td id="table_users_ingredi_td_8"></td>
						<td id="table_users_ingredi_td_9"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div style="max-height: 659.641px;">
			<div>
				<div class="input_users_ingredi">
					<div id="div_ingredi_search_bar">
						<script type="text/javascript">
							let ingredient_options = [];
							<%
								ArrayList<RecipeIngredientVo> ingredient_info_list = (ArrayList<RecipeIngredientVo>) request.getAttribute("ingredientInfoList");
								for(RecipeIngredientVo riVo : ingredient_info_list) {
							%>
							ingredient_options.push({value: <%=riVo.getIngredieID()%>, text:"<%=riVo.getingredieName()%>"});
						    <%
								}
							%>			 
							$(document.body).ready(function () {
								let i = 0;
								let j = 0;
								let selected_option = [];
								  // 요소 추가
								$('[data-ax5combobox]').ax5combobox({
						            options: ingredient_options,
									onStateChanged : function(){
										switch(this.state) {
										case "changeValue" :
											$(".search_results tr:not(:first)").remove();
											$(".table_users_ingredi td img").remove();
											let selected_ingredi_options = "";
											let arr_for_have_with_span = "";
											let arr_for_not_with_span = "";
											selected_option = $('[data-ax5combobox="combobox"]').ax5combobox("getSelectedOption");
											for(i = 0; i < selected_option.length; i++) {
												console.log(selected_option[i].value);
												if(i == selected_option.length - 1){
													selected_ingredi_options += selected_option[i].value;
													j++;
												} else {
													selected_ingredi_options += selected_option[i].value + " / ";
													j++;
												}
											}
											console.log(selected_ingredi_options);
											$.ajax({
												type: "POST",
												url: "Controller",
												data: {
													"command" : "refrigerator_add_ingredi",
													"loginId" : loginId,
													"selected_ingredi_options" : selected_ingredi_options
												},
												success: function (result) {
													console.log("Ajax request successful");
													console.log(result);
													console.log("result : "+result.length);
													console.log("selected_option : "+selected_option.length);
													console.log("result.length-selected_option.length : "+Number(result.length-selected_option.length));
													
													for (let k = 0; k < Number(result.length-selected_option.length); k++) {
													
														arr_for_have_with_span = $.map(result[k].arrForHave, function(ingredient) {
															return "<span>" + ingredient + "</span>";
														});
														arr_for_not_with_span = $.map(result[k].arrForNot, function(ingredient) {
															return "<span class='missing_material'>" + ingredient + "</span>";
														});
														const add_html = 
															"<tr recipe=" + result[k].recipeID + ">" +
															"<td class='results_thumbnail'> <img src=" + result[k].thumbnail + " alt=" + result[k].title + "/> </td>" +
															"<td class='results_name'>" + result[k].title + "</td>" +
															"<td class='correspond'>"+ arr_for_have_with_span + "</td>" +
															"<td class='inconsistent'>" + arr_for_not_with_span + "</td> </tr>";
														console.log(add_html);
														$(add_html).appendTo(".search_results > tbody ");
													}
													let h = 1;
													for (h; h <= selected_option.length; h++) {
														for (let k = Number(result.length-selected_option.length); k <= Number(result.length); k++) {
															const add_img_html = "<img src="+ result[k].image + " alt="+result[k].ingrediName+"/>";
															console.log("h : "+h);
															console.log("k : "+k);
															console.log("add_img_html : "+add_img_html);
															$(add_img_html).appendTo("#table_users_ingredi_td_"+h);
															break;
														}
													}
												},
												error: function(r,s,e) {
													alert("[에러] code:"+r.status + "message:"+r.responseText+"error:"+e);
												}
											});
										break;
										}
									}
								});
							});
						</script>
						<div class="form-group">
							<div data-ax5combobox="combobox" data-ax5combobox-config="{multiple: true}"> </div>
						</div>
					</div>
				</div>
			</div>
			<div class="div_search_results">
				<div style="float: right; display: none;">
					<input type="button" class="order active" id="titleASC" value="이름순" style="height: 27px"/> 
					<input type="button" class="order nonactive" id="hits" value="조회순" style="height: 27px"/> 
					<input type="button" class="order nonactive" id="writedateDESC"value="최신순" style="height: 27px"/> 
				</div>
				<table class="search_results">
					<colgroup>
						<col style="width:15%"/>
						<col/>
						<col style="width:25%"/>
						<col style="width:40%"/>
					</colgroup>
					<tbody>
						<tr>
							<td class="table_header">대표 이미지</td>
							<td class="table_header">레시피 제목</td>
							<td class="table_header">냉장고에 있는 재료</td>
							<td class="table_header">냉장고에 없는 재료</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
 <!------------ body ------------>
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
  <!------------ footer ------------>
</body>
</html>