<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.ArrayList" %>   
<%@page import="vo.StoreMainpageGoodVo"%>
<%
   	ArrayList<String> event = new ArrayList<>();
      	event = (ArrayList<String>) request.getAttribute("event");
      	
      	ArrayList<StoreMainpageGoodVo> hotDealInfo = new ArrayList<>();
      	hotDealInfo = (ArrayList<StoreMainpageGoodVo>)request.getAttribute("hotdealinfo");
      	
      	ArrayList<StoreMainpageGoodVo> bestDealInfo = new ArrayList<>();
      	bestDealInfo = (ArrayList<StoreMainpageGoodVo>)request.getAttribute("bestdealinfo");
      	
      	ArrayList<StoreMainpageGoodVo> recentDealInfo = new ArrayList<>();
      	recentDealInfo = (ArrayList<StoreMainpageGoodVo>)request.getAttribute("recentdealinfo");
      	
      	String loginId = (String)session.getAttribute("loginId");
   %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<link rel="stylesheet" type="text/css" href="http://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
	<link rel="stylesheet" href="http://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="Store_CSS/Store_Header.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Footer.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Right_Scroll.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_mainpage.css" type="text/css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="http://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script type="text/javascript" src="js/scroll_fixed.js"></script>
	<script type="text/javascript" src="js/Store_mainpage.js"></script>
	<script type="text/javascript" src="js/Store_header.js"></script>
</head>
<body>
	<div class="header_top">
		<div class="header_top_cont">
			<div class="logo">
				<img src = "https://recipe1.ezmember.co.kr/img/store/logo_store.png"/>
			</div>
			<ul class="first_nav">
				<%if(loginId!=null){ %>
				<li><a href="Controller?command=logout">로그아웃</a></li>
				<%} else{ %>
				<li><a href="Controller?command=login_form">로그인</a></li>
				<%} %>
				<%if(loginId!=null) {}else{%>
				<li><a href="Controller?command=regist_form">회원가입</a></li>
				<%} %>
				<li><a href="#">마이페이지</a></li>
				<%if(loginId!=null) {%>
				<li><a href="Controller?command=cart_detail_view">장바구니</a></li>
				<%} else{ %>
				<li><a href="Controller?command=login_form">장바구니</a></li>
				<%} %>
				<li><a href="#">고객센터</a></li>
			</ul>
		</div>
	</div>
	<div class="header_mn">
		<div class="header_mn_cont">
			<div class="total_category">
				<div class="private">
					<a><img src = 'https://recipe1.ezmember.co.kr/img/store/icon_cate_all.png'/></a>
				</div>
				<a href="#">전체카테고리</a>
			</div>
			<ul>
				<li><a href="#">스토어홈</a></li>	
				<li><a href="#">베스트</a></li>	
				<li><a href="#">핫딜</a></li>	
				<li><a href="#">신상</a></li>	
				<li><a href="#">기획전</a></li>	
				<li><a href="#">브랜드</a></li>	
			</ul>
			<div class="recipe">
				<img src = "https://recipe1.ezmember.co.kr/img/store/logo_rcp.png"/>
			</div>
			<div class="search">
				<form>
					<span></span>
					<input type="text" placeholder="상품 검색">
				</form>
				<div class="dbg">
					<img src = "https://recipe1.ezmember.co.kr/img/mobile/icon_search6.png"/>
				</div>
			</div>
			<div class="cart">
				<img src = "https://recipe1.ezmember.co.kr/img/mobile/icon_cart3.png"/>
			</div>
		</div>
	</div>
	<div class="main_cont_wrap">
		<div class = "scroll_right">
			<div class="scroll_right_cont">
				<h5>최근 본 상품</h5>
				<ul class="scroll_right_cont_img">
					<a href="#"><li><figure><img src = 'https://recipe1.ezmember.co.kr/cache/data/goods/23/11/47/1000041934/1000041934_list_037.jpg'/>
					</figure></li></a>
					<a href="#"><li><figure><img src = 'https://recipe1.ezmember.co.kr/cache/data/goods/23/10/41/1000040809/1000040809_list_022.jpg'/>
					</figure></li></a>
					<a href="#"><li><figure><img src = 'https://recipe1.ezmember.co.kr/cache/data/goods/20/06/24/1000008521/1000008521_list_036.jpg'/>
					</figure></li></a>
					<a href="#"><li><figure><img src = 'https://recipe1.ezmember.co.kr/cache/data/goods/23/06/24/1000037511/1000037511_list_028.jpg'/>
					</figure></li></a>
					<a href="#"><li><figure><img src = 'https://recipe1.ezmember.co.kr/cache/data/goods/23/11/46/1000041438/1000041438_list_032.jpg'/>
					</figure></li></a>
				</ul>
				<div class="scroll_right_cont_paging">
					<button type="button"><img src = 'https://shoptr3131.cdn-nhncommerce.com/data/skin/front/moment/img/common/btn/btn_scroll_prev.png'/></button>
					<span>1/2</span>
					<button type="button"><img src = 'https://shoptr3131.cdn-nhncommerce.com/data/skin/front/moment/img/common/btn/btn_scroll_next.png'/></button>
				</div>
			</div>
			<div class="scroll_right_top">
				<a href="#"><figure>
					<img src ='https://shoptr3131.cdn-nhncommerce.com/data/skin/front/moment/img/common/btn/btn_scroll_top.png'/>
				</figure></a>
			</div>
	</div>
		<div class="main_cont">
			<!-- 메인 내용 여기에 입력 -->
			<div class="event">
				<%
					for(String n : event) {
				%>
				<div class="figure_event"><img src = "<%=n%>"/></div>
				<%
					}
				%>
			</div>
			<div class="good_section">
				<div class="goods_title">
					<div class="goods_title1"><h2>핫딜 상품</h2></div>
					<div class="goods_title2"><a href="#">더보기</a></div>
				</div>
					<div class=goods_cont3>
						<%
							for(StoreMainpageGoodVo hotdealvo : hotDealInfo){
						%>
						<div class="hot_deal_cont1">
							<input class="product_id" type="hidden" value=<%=hotdealvo.getProductId() %>> 
							<div class="good_thumb">
								<img src = "<%=hotdealvo.getImage()%>"/>
							</div>
							<div class="good_caption">
								<span class="good_caption_title"><%=hotdealvo.getName()%></span>
								<div class="good_caption_price">
									<%
										if(hotdealvo.getSalePer()==0){} else{
									%>
									<div class="sale_per"><%=hotdealvo.getSalePer()%>%</div>
									<%
										}
									%>
									<span class="price"><b><%=hotdealvo.getSellCost()%></b>원</span>
								</div>
								<%
									if(hotdealvo.getRepleValue()==0){} else{
								%>
								<div class="good_caption_score">
									<figure class="star"><img src="Images/star.png"></figure>
									<div class="score"><b><%=hotdealvo.getScore()%></b></div>
									<div class="comment">(<%=hotdealvo.getRepleValue()%>)</div>
								</div>
								<%
									}
								%>
								<div class="good_caption_icon">
								<%
									if(hotdealvo.getDeliveryCharge()>1) {}
															else{
								%>
								<span class="icon_free">무료배송</span>
								<%
									}
								%>
									<span class="icon_hot">핫딜</span>
								</div>
							</div>
						</div>
						<%
							}
						%>
					</div>	
			</div>
			<div class="good_section">
				<div class="goods_title">
					<div class="goods_title1"><h2>어제 베스트 상품</h2></div>
					<div class="goods_title2"><a href="#">더보기</a></div>
				</div>
				<div class="goods_cont4">
					<%
						for(StoreMainpageGoodVo bestdealvo : bestDealInfo){
					%>
					<div class="goods_cont_section">
						<input class="product_id" type="hidden" value="<%=bestdealvo.getProductId() %>">
						<figure class="good_thumb">
							<img src = "<%=bestdealvo.getImage()%>"/>
						</figure>
						<span class="good_caption_title">
							<%=bestdealvo.getName()%>
						</span>
						<div class="good_caption_price">
							<%
								if(bestdealvo.getSalePer()==0){} else{
							%>
							<div class="sale_per"><%=bestdealvo.getSalePer()%>%</div>
							<%
								}
							%>
							<span class="price"><b><%=bestdealvo.getSellCost()%></b>원</span>
						</div>
						<%
							if(bestdealvo.getRepleValue()==0){} else{
						%>
						<div class="good_caption_score">
							<figure class="star"><img src="Images/star.png"></figure>
							<div class="score"><b><%=bestdealvo.getScore()%></b></div>
							<div class="comment">(<%=bestdealvo.getRepleValue()%>)</div>
						</div>
						<%
							}
						%>
						<div class="good_caption_icon">
							<%
								if(bestdealvo.getDeliveryCharge()>0){} else{
							%>
							<span class="icon_free">무료배송</span>
							<%
								}
							%>
							<%
								if(bestdealvo.getHotDeal()==0){} else{
							%>
							<span class="icon_hot">핫딜</span>
							<%
								}
							%>
						</div>
					</div>
					<%
						}
					%>	
				</div>
			</div>	
			<div class="good_section">
				<div class="goods_title">
					<div class="goods_title1"><h2>따끈따근한 신상</h2></div>
					<div class="goods_title2"><a href="#">더보기</a></div>
				</div>
				<div class="goods_cont4">
					<%
						for(StoreMainpageGoodVo recentdealvo : recentDealInfo) {
					%>
					<div class="goods_cont_section">
						<input class="product_id" type="hidden" value="<%=recentdealvo.getProductId() %>" >
						<figure class="good_thumb">
							<img src = "<%=recentdealvo.getImage()%>"/>
						</figure>
						<span class="good_caption_title">
							<%=recentdealvo.getName() %>
						</span>
						<div class="good_caption_price">
							<%if(recentdealvo.getSalePer()==0){} else{ %>
							<div class="sale_per"><%=recentdealvo.getSalePer()%>%</div>
							<%} %>
							<span class="price"><b><%=recentdealvo.getSellCost() %></b>원</span>
						</div>
							<%if(recentdealvo.getRepleValue()==0){} else{ %>
						<div class="good_caption_score">
							<figure class="star"><img src="Images/star.png"></figure>
							<div class="score"><b><%=recentdealvo.getScore()%></b></div>
							<div class="comment">(<%=recentdealvo.getRepleValue() %>)</div>
						</div>
						<%} %>
						<div class="good_caption_icon">
							<%if(recentdealvo.getDeliveryCharge()>0){} else{ %>
							<span class="icon_free">무료배송</span>
							<%} %>
							<%if(recentdealvo.getHotDeal()==0){} else{ %>
							<span class="icon_hot">핫딜</span>
							<%} %>
						</div>
					</div>
					<%} %>
				</div>
			</div>
			<div class="good_section">
				<div class="goods_title">
					<div class="goods_title1"><h2>최신 후기 상품</h2></div>
					<div class="goods_title2"><a href="#">더보기</a></div>
				</div>
				<div class="goods_cont4">
					<div class="goods_cont_section">
						<figure class="good_thumb">
							<img src = "https://recipe1.ezmember.co.kr/cache/data/goods/23/10/41/1000040832/1000040832_list_021.jpg"/>
						</figure>
						<div class="main_review_comment">
							<figure>
								<img src = "https://recipe1.ezmember.co.kr/img/store/icon_rv.png"/>
							</figure>
						<div class="main_review_comment_cont">
								양도많고 배송도빠르고 만족합니다 포장상태양호하고 좋으네요 재구매 가능 합니다 만족합니다
						</div>
						<div class="good_caption_icon">
							<span class="main_review_id">shap****</span>
							<span class="main_reveiw_date">2023-11-24</span>
						</div>
						</div>
					</div>
					<div class="goods_cont_section">
						<figure class="good_thumb">
							<img src = "https://recipe1.ezmember.co.kr/cache/data/goods/23/11/45/1000041327/1000041327_list_068.jpg"/>
						</figure>
						<div class="main_review_comment">
							<figure>
								<img src = "https://recipe1.ezmember.co.kr/img/store/icon_rv.png"/>
							</figure>
							<div class="main_review_comment_cont">
								터진게 꽤 있어서 좀 그렇긴한데 맛은 좋아요
							</div>
							<div class="good_caption_icon">
								<span class="main_review_id">shin****</span>
								<span class="main_reveiw_date">2023-11-24</span>
							</div>
						</div>
					</div>
					<div class="goods_cont_section">
						<figure class="good_thumb">
							<img src = "https://recipe1.ezmember.co.kr/cache/data/goods/19/05/22/1000001550/1000001550_list_040.png"/>
						</figure>
						<div class="main_review_comment">
							<figure>
								<img src = "https://recipe1.ezmember.co.kr/img/store/icon_rv.png"/>
							</figure>
							<div class="main_review_comment_cont">
								김장 양념에도 넣고 조힘에도 살짝 넣었습니다 감칠맛이 너무 좋습니다
							</div>
							<div class="good_caption_icon">
								<span class="main_review_id">cant****</span>
								<span class="main_reveiw_date">2023-11-23</span>
							</div>
						</div>
					</div>
					<div class="goods_cont_section">
						<figure class="good_thumb">
							<img src = "https://recipe1.ezmember.co.kr/cache/data/goods/23/09/39/1000040561/1000040561_list_047.jpg"/>
						</figure>
						<div class="main_review_comment">
							<figure>
								<img src = "https://recipe1.ezmember.co.kr/img/store/icon_rv.png"/>
							</figure>
							<div class="main_review_comment_cont">
								맛잇어요맛잇어요맛잇어요맛잇어요맛있어요맛있어요맜있어요
							</div>
							<div class="good_caption_icon">
								<span class="main_review_id">happ****</span>
								<span class="main_reveiw_date">2023-11-23</span>
							</div>
						</div>
					</div>
					<div class="goods_cont_section">
						<figure class="good_thumb">
							<img src = "https://recipe1.ezmember.co.kr/cache/data/goods/23/10/41/1000040832/1000040832_list_021.jpg"/>
						</figure>
						<div class="main_review_comment">
							<figure>
								<img src = "https://recipe1.ezmember.co.kr/img/store/icon_rv.png"/>
							</figure>
						<div class="main_review_comment_cont">
								양도많고 배송도빠르고 만족합니다 포장상태양호하고 좋으네요 재구매 가능 합니다 만족합니다
						</div>
						<div class="good_caption_icon">
							<span class="main_review_id">shap****</span>
							<span class="main_reveiw_date">2023-11-24</span>
						</div>
						</div>
					</div>
					<div class="goods_cont_section">
						<figure class="good_thumb">
							<img src = "https://recipe1.ezmember.co.kr/cache/data/goods/23/11/45/1000041327/1000041327_list_068.jpg"/>
						</figure>
						<div class="main_review_comment">
							<figure>
								<img src = "https://recipe1.ezmember.co.kr/img/store/icon_rv.png"/>
							</figure>
							<div class="main_review_comment_cont">
								터진게 꽤 있어서 좀 그렇긴한데 맛은 좋아요
							</div>
							<div class="good_caption_icon">
								<span class="main_review_id">shin****</span>
								<span class="main_reveiw_date">2023-11-24</span>
							</div>
						</div>
					</div>
					<div class="goods_cont_section">
						<figure class="good_thumb">
							<img src = "https://recipe1.ezmember.co.kr/cache/data/goods/19/05/22/1000001550/1000001550_list_040.png"/>
						</figure>
						<div class="main_review_comment">
							<figure>
								<img src = "https://recipe1.ezmember.co.kr/img/store/icon_rv.png"/>
							</figure>
							<div class="main_review_comment_cont">
								김장 양념에도 넣고 조힘에도 살짝 넣었습니다 감칠맛이 너무 좋습니다
							</div>
							<div class="good_caption_icon">
								<span class="main_review_id">cant****</span>
								<span class="main_reveiw_date">2023-11-23</span>
							</div>
						</div>
					</div>
					<div class="goods_cont_section">
						<figure class="good_thumb">
							<img src = "https://recipe1.ezmember.co.kr/cache/data/goods/23/09/39/1000040561/1000040561_list_047.jpg"/>
						</figure>
						<div class="main_review_comment">
							<figure>
								<img src = "https://recipe1.ezmember.co.kr/img/store/icon_rv.png"/>
							</figure>
							<div class="main_review_comment_cont">
								맛잇어요맛잇어요맛잇어요맛잇어요맛있어요맛있어요맜있어요
							</div>
							<div class="good_caption_icon">
								<span class="main_review_id">happ****</span>
								<span class="main_reveiw_date">2023-11-23</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	 <div class="main_foot_cs">
					<hr/>
						<div class="main_foot_cs_cont">
			<div class="main_foot_cs_cont_left">
				<div class="cc">고객센터</div>
				<b class="number">02-0000-0000</b>
				<div class="kakao_cc">
					<a href="#"><figure>
						<img src = "https://recipe1.ezmember.co.kr/img/mobile/2022/icon_sns_k2.png"/>
					</figure></a>
					<a href="#"><span>상담하기</span></a>
				</div>
				<a href="#">[전화상담 일시 중단 안내]</a>
				<span>
					<br>
					평일  10:00~17:00<br>
					점심시간 : 12:30~13:30<br>
					공휴일 및 주말은 휴무
				</span>
			</div>
			<div class="main_foot_cs_cont_right">
				<ul class="main_foot_link">
					<a href="#"><li>회사소개</li></a>
					<a href="#"><li>이용약관</li></a>
					<a href="#"><li>개인정보처리방침</li></a>
					<a href="#"><li>이용안내</li></a>
					<a href="#"><li>광고/제휴 문의</li></a>
				</ul>
				<div class="main_foot_info">
					(주)만개의레시피 서울특별시 금천구 가산디지털1로 145 (에이스하이엔드타워3차) 1106호<br>
					대표 : 이인경 사업자등록번호 : 291-81-02485 통신판매업신고번호 : 2022-서울금천-3089 개인정보관리자 : 이창득<br>
					대표번호 : 070-4896-6416 팩스번호 : 02-323-5049 메일 : help@10000recipe.com 호스팅제공 : 엔에이치엔커머스(주)<br>
					<br><br>
					㈜만개의레시피가 운영하는 만개스토어는 통신판매중개자로서 거래의 당사자가 아니며,<br>
					입점 판매자가 등록한 상품정보 및 거래에 대한 책임을 일체 지지 않습니다.
					<br><br>
					Copyright 만개의레시피 Inc. All Rights Reserved.
				</div>
			</div>		
		</div>
	</div>		
</body>
</html>