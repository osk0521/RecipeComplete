<%@page import="java.util.ArrayList"%>
<%@page import="vo.CartDetailVo"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<CartDetailVo> cartList = (ArrayList<CartDetailVo>)request.getAttribute("cartList");
	String loginId = (String)session.getAttribute("loginId");
%>    
<!DOCTYPE html>
<html>
<head>
	<!-- 만개스토어 마이페이지 주문내역없는거 -->
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="Store_CSS/Store_cart.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Header.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Footer.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Right_Scroll.css" type="text/css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="js/scroll_fixed.js"></script>
	<script type="text/javascript" src="js/Store_cart.js"></script>
	<script type="text/javascript" src="js/Store_header.js"></script>
</head>
<body>
	<div id=total_wrap>
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
					<a href="Controller?command=store_mainlist"><img src = 'https://recipe1.ezmember.co.kr/img/store/icon_cate_all.png'/></a>
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
			<div id="div_store_location_wrap">
				<div id="div_store_location_content">
					<span>
						<a href="#">HOME</a>
						> 마이페이지
					</span>
				</div>
			</div>
			<div id="div_mypage">
				<div id="div_mypage_right">
					<div id="div_mypage_main">
						<div id="div_mypage_order">
							<div class="div_mypage_title">
								<h3>장바구니</h3>
								<ol>
									<li class="cont_now">
										01 장바구니
									</li>
									<li class="icon_now">
										<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-right-fill" viewBox="0 0 16 16">
	  									<path d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z"/>
										</svg>
									</li>
									<li>
										02 주문서작성/결제
									</li>
									<li>
										<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-right-fill" viewBox="0 0 16 16">
	  									<path d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z"/>
										</svg>
									</li>
									<li>
										03  주문완료
									</li>
								</ol>
							</div>
							<div class="cont_cart_all">
								<div class="total_cont">
									<input type="checkbox" id="all_cont">
									<label for="all_cont">전체상품</label>
									<span>(<b>2</b>)</span>
								</div>
								<div class="select_delete">
									<span>선택삭제</span>
									<figure>
										<img src="https://recipe1.ezmember.co.kr/img/mobile/2022/icon_close2.png"/>
									</figure>
								</div>
							</div>
						</div>
						<div id="div_mypage_recent_order">
							<div id="div_mypage_recent_order_content">
								<table id="table_recent_order">
									<colgroup>
										<col style="width:5%;"/>
										<col style="width:30%;"/>
										<col style="width:13%;"/>
										<col style="width:13%;"/>
										<col style="width:13%;"/>
										<col style="width:13%;"/>
										<col style="width:13%;"/>
									</colgroup>
									<thead>
										<tr>
											<th>선택</th>
											<th>상품/옵션 정보</th>
											<th>수량</th>
											<th>상품금액</th>
											<th>적립</th>
											<th>합계금액</th>
											<th>배송비</th>
										</tr>
									</thead>
									<tbody>
										<%for(CartDetailVo cart : cartList) {%>
										<tr class="tr_option" data-productId=<%=cart.getProductId() %> data-option="<%=cart.getOptionNum() %>" data-qty=<%=cart.getQty() %> data-reserves=<%=cart.getReserves() %> data-total=<%=cart.getPrice() %> data-deli=<%=cart.getdeliveryCharge() %>>
											<td>
												<input class="goods_check" type="checkbox"/>
											</td>
											<td class="good_opt_info">
												<div>
													<div class="good_opt_info1">
														<figure>
															<img class="image" src="<%=cart.getImage() %>"/>
														</figure>
													</div>
													<div class="good_opt_info2">
														<input class="input_optionnum" type="hidden" value="<%=cart.getOptionNum() %>">
														<input class="input_productid" type="hidden" value="<%=cart.getProductId()%>">
														<a href="#"><%=cart.getName() %></a><br/>
														<span><%=cart.getOptionContent()%></span>
													</div>
												</div>
											</td>
											<td class="qty">
												<div class="qty1"><span><%=cart.getQty() %></span>개</div>
												<div class="qty2"><a class="a_option_qty">옵션/수량변경</a></div>
											</td>
											<td><span class="comma"><%=cart.getPrice() %></span>원</td>
											<td>+<span class="comma"><%=cart.getReserves() %></span>원</td>
											<td><span class="comma"><%=cart.getPrice() %></span>원</td>
											<%if(cart.getdeliveryCharge()>0) {%>
											<td><span class="comma"><%=cart.getdeliveryCharge() %></span>원</td>
											<%} else{ %>
											<td><span>무료배송</span></td>
											<%} %>
										</tr>
										<%} %>
									</tbody>
								</table>
							</div>
						</div>
						<div class="return_shopping">
							<a href="#">< 쇼핑 계속하기</a>
						</div>
						<div class="price_sum">
							<div class="price_sum_cont">
								<dl>
									<dt>총 <span id="sum_qty">0</span>개의 상품금액</dt>
									<dd><span id="sum_total">0</span>원</dd>
								</dl>
								<figure>
									<img src="https://shoptr3131.cdn-nhncommerce.com/data/skin/front/moment/img/order/order_price_plus.png"/>
								</figure>
								<dl>
									<dt>배송비</dt>
									<dd><span id="sum_deli">0</span>원</dd>
								</dl>
								<figure>
									<img src="	https://shoptr3131.cdn-nhncommerce.com/data/skin/front/moment/img/order/order_price_total2.png"/>
								</figure>
								<dl>
									<dt>합계</dt>
									<dd><span id="total_order">0</span>원</dd>
								</dl>
							</div>
							<div class="price_sum_reserver">
								<em>적립예상 적립금: <span id="sum_reserves">0</span>원</em>
							</div>
						</div>
						<div class="order_box">
							<button id="select_order">선택 상품 주문</button>
							<button id="all_order">전체 상품 주문</button>
						</div>
						<div class="order_box_comment">
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-exclamation-circle" viewBox="0 0 16 16">
	  						<path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
	  						<path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
							</svg>                                                                                                                                                                                                   
							<span>
								주문서 작성단계에서 적립금 적용을 하실 수 있습니다.
							</span>
						</div>
						<div  class="npay">
							<!-- 네이버페이 들어오는 자리 -->
						</div>
					</div>
				</div>
			</div>
			<div class = "scroll_right">
				<div class="scroll_right_cont">
					<h5>최근 본 상품</h5>
					<ul class="scroll_right_cont_img">
						<a href="#">
							<li>
								<figure>
									<img src = 'https://shoptr3131.cdn-nhncommerce.com/data/goods/22/09/39/1000030757/1000030757_main_090.jpg'/>
								</figure>
							</li>
						</a>
						<a href="#">
							<li>
								<figure>
									<img src = 'https://shoptr3131.cdn-nhncommerce.com/data/goods/23/06/25/1000037664/1000037664_main_090.jpg'/>
								</figure>
							</li>
						</a>
						<a href="#">
							<li>
								<figure>
									<img src = 'https://shoptr3131.cdn-nhncommerce.com/data/goods/23/10/41/1000040809/1000040809_main_031.jpg'/>
								</figure>
							</li>
						</a>
						<a href="#">
							<li>
								<figure>
									<img src = 'https://shoptr3131.cdn-nhncommerce.com/data/goods/23/02/09/1000034404/1000034404_main_045.jpg'/>
								</figure>
							</li>
						</a>
						<a href="#">
							<li>
								<figure>
									<img src = 'https://shoptr3131.cdn-nhncommerce.com/data/goods/23/05/22/1000037274/1000037274_main_018.jpg'/>
								</figure>
							</li>
						</a>
					</ul>
					<div class="scroll_right_cont_paging">
							<button type="button"><img src = 'https://shoptr3131.cdn-nhncommerce.com/data/skin/front/moment/img/common/btn/btn_scroll_prev.png'/></button>
							<span>1/2</span>
							<button type="button"><img src = 'https://shoptr3131.cdn-nhncommerce.com/data/skin/front/moment/img/common/btn/btn_scroll_next.png'/></button>
					</div>
				</div>
				<div class="scroll_right_top">
					<a href="#">
						<figure>
							<img src ='https://shoptr3131.cdn-nhncommerce.com/data/skin/front/moment/img/common/btn/btn_scroll_top.png'/>
						</figure>
					</a>
				</div>
			</div>
		</div>
		<div class="main_foot_cs">
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
		<div class="option_box">
				<h4>옵션선택</h4>
				<form action="Controller?command=cart_update" method="post">
					<div class="option_title">
						<div class="title_photo">
							<figure>
								<img id="option_image" src = "https://shoptr3131.cdn-nhncommerce.com/data/goods/23/07/28/1000038104/t50_1000038104_detail_023.jpg">
							</figure>
						</div>
						<div class="title">
							<p id="p_title">[특가]나랑드 사이다 제로 페트 12입</p>
							<select class="select_option">
								<option> 옵션을 선택해주세요 </option>
							</select>
						</div>
					</div>
					<div class="option">
						<div class="option_optiondetail_wrap">
							<div class="optiondetail_title">
								<p class="optiondetail_name">제목</p>
								<p class="optiondetail_remove">X</p>
							</div>
							<div class="optiondetail_price_qty">
								<div class="optiondetail_price_cont">
									11900원
								</div>
								<div class="optiondetail_qty_cont">
									<input id="optionnum_input" type="hidden" name="optionNum">
									<input id="productid_input" type="hidden" name="productId">
									<button id="qty_btn_minus" type="button">-</button>
									<input id="qty_input" type="text" name="optionQty" value=1>
									<button id="qty_btn_plus" type="button">+</button>
								</div>
							</div>
						</div>
					</div>
					<div class="option_btn_wrap">
						<button id="option_btn_cancel" type="button">취소</button>
						<button id="option_btn_ok">확인</button>
					</div>
				</form>
			</div>
			<div class="option_boxbg"></div>
	</div>
</body>
</html>