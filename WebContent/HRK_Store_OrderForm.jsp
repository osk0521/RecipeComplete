<%@page import="vo.CartDetailVo"%>
<%@page import="vo.StoreGoodsDetailsOption1Vo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String loginId = (String)session.getAttribute("loginId");
	ArrayList<CartDetailVo> selectOrderList = new ArrayList<CartDetailVo>();
	selectOrderList = (ArrayList<CartDetailVo>)request.getAttribute("selectOrderList");
	int reserves = (int)request.getAttribute("reserves");
%>    
<!DOCTYPE html>
<html>
<head>
	<!-- 만개스토어 마이페이지 주문내역없는거 -->
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="Store_CSS/Store_OrderForm.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Header.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Footer.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Right_Scroll.css" type="text/css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="js/scroll_fixed.js"></script>
	<script type="text/javascript" src="js/Store_OrderForm.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
	<script type="text/javascript" src="js/Store_header.js"></script>
</head>
<body>
	<div class="total_wrap">
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
								<h3>주문서작성/결제</h3>
								<ol>
									<li>
										01 장바구니
									</li>
									<li>
										<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-right-fill" viewBox="0 0 16 16">
	  									<path d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z"/>
										</svg>
									</li>
									<li class="cont_now">
										02 주문서작성/결제
									</li>
									<li class="icon_now">
										<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-right-fill" viewBox="0 0 16 16">
	  									<path d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z"/>
										</svg>
									</li>
									<li>
										03  주문완료
									</li>
								</ol>
							</div>
							<div class="order_goods_tit">
								<span>주문상세내역</span>
							</div>
						</div>
						<div id="div_mypage_recent_order">
							<div id="div_mypage_recent_order_content">
								<table id="table_recent_order">
									<colgroup>
										<col style="width:30%;"/>
										<col style="width:13%;"/>
										<col style="width:13%;"/>
										<col style="width:13%;"/>
										<col style="width:13%;"/>
										<col style="width:13%;"/>
									</colgroup>
									<thead>
										<tr>
											<th>상품/옵션 정보</th>
											<th>수량</th>
											<th>상품금액</th>
											<th>적립</th>
											<th>합계금액</th>
											<th>배송비</th>
										</tr>
									</thead>
									<tbody>
										<%for(CartDetailVo vo : selectOrderList) {%>
										<tr class="tr_goods" data-name="<%=vo.getName() %>" data-productId=<%=vo.getProductId() %> data-price=<%=vo.getPrice() %> data-qty=<%=vo.getQty() %> data-option="<%=vo.getOptionNum()%>" data-delivery=<%=vo.getdeliveryCharge()%> data-reserves=<%=vo.getReserves()%>>
											<td class="good_opt_info">
												<div>
													<div class="good_opt_info1">
														<figure>
															<img src="<%=vo.getImage()%>"/>
														</figure>
													</div>
													<div class="good_opt_info2">
														<a href="#"><%=vo.getName() %></a><br/>
														<span><%=vo.getOptionContent() %></span>
													</div>
												</div>
											</td>
											<td class="qty">
												<div class="qty1"><span><%=vo.getQty() %></span>개</div>
											</td>
											<td><span><%=vo.getPrice() %></span>원</td>
											<td>+<span><%=vo.getReserves() %></span>원</td>
											<td><span><%=vo.getPrice() %></span>원</td>
											<td><span><%=vo.getdeliveryCharge() %></span>원</td>
										</tr>
										<%} %>
									</tbody>
								</table>
							</div>
						</div>
						<div class="order_goods_tit">
							<span>배송정보</span>
						</div>
						<div class="grid_wrap_7row">
							<div class="main1">배송지 확인</div>
							<div class="main2">
								<ul>
									<li>
										<input type="radio" name="address" id="base_address">
										<label for="base_address">기본 배송지</label>
									</li>
									<li>
										<input type="radio" name="address" id="direct_input">
										<label for="direct_input">직접 입력</label>
									</li>
									<li>
										<a class="a_btn_address">
											배송지 관리
										</a>
									</li>
								</ul>
							</div>
							<div class="main1"><span>*</span>받으실분</div>
							<div class="main2">
								<input type="text" id="input_receiver" name="receiver">
							</div>
							<div class="main1"><span>*</span>받으실 곳</div>
							<div class="main2">
								<ul>
									<li>
										<input type="text" id="input_zipcode" name="zipCode" readonly> 
									</li>
									<li>
										<a id="a_zipcode">
											우편번호 검색
										</a>
									</li>
								</ul> 
							</div>
							<div class="main1">받으실 곳(상세주소)</div>
							<div class="main2">
								<ul>
									<li>
										<input type="text" id="output_address" name="address"> 
									</li>
									<li>
										<input type="text" id="input_detailaddress" name="detailAddress">
									</li> 
								</ul>
							</div>
							<div class="main1"><span>*</span>휴대폰 번호</div>
							<div class="main2"><input type="text" id="input_phonenumber" name="phoneNumber"></div>
							<div class="main1">남기실 말씀</div>
							<div class="main2"><input type="text" id="input_text"></div>
						</div>
						<div class="order_goods_tit">
							<span>주문자 정보</span>
							<a id="a_sameAddress">
								배송정보와 동일
							</a>
						</div>
						<div class="grid_wrap_4row">
							<div class="main1"><span>*</span>주문하시는 분</div>
							<div class="main2"><input type="text" id="input_order"></div>
							<div class="main1"><span>*</span>휴대폰 번호</div>
							<div class="main2"><input type="text" id="input_order_phonenumber"></div>
							<div class="main1"><span>*</span>이메일</div>
							<div class="main2">
								<ul>
									<li>
										<input type="text" id="email_id">
									</li>
									<li>	
										<select id="select_email">
											<option>직접입력</option>
											<option>naver.com</option>
											<option>hanmail.net</option>
											<option>daum.com</option>
											<option>nate.com</option>
											<option>hotmail.com</option>
											<option>gmail.com</option>
											<option>icloud.com</option>
										</select>
									</li>
								</ul>
							</div>
						</div>
						<div class="order_goods_tit">
							<span>결제정보</span>
						</div>
						<div class="grid_wrap_5row">
							<div class="main1">상품 합계 금액</div>
							<div class="main2"><span id="span_price">36800</span>원</div>
							<div class="main1">배송비</div>
							<div class="main2"><span id="span_delivery">0</span>원</div>
							<div class="main1">적립금사용</div>
							<div class="main2">
								<ul>
									<li>
										<input type="text"> 원
									</li>
									<li>
										<input type="checkbox" id="input_reserver">
										<label for="input_reserver">
											전액사용하기 
										</label>
									</li>
									<li>
										<p>
											사용가능한 적립금 : <span><%=reserves %></span> 원
										</p>
									</li>
								</ul>
							</div>
							<div class="main1">예상 적립금</div>
							<div class="main2"><span id="span_reserves">184</span>원</div>
							<div class="main1">최종 결제 금액</div>
							<div class="main2"><span id="span_total">36800</span>원</div>
						</div>
						<div class="order_goods_tit">
							<span>결제수단 선택 / 결제</span>
						</div>
						<div class="payment">
							<ul>
								<li>
									<figure>
										<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_pay1_v4_off.png"/>
									</figure>
								</li>
								<li>
									<figure>
										<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_pay4_v4_off.png"/>
									</figure>
								</li>
								<li>
									<figure>
										<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_pay5_v4_off.png"/>
									</figure>
								</li>
								<li>
									<figure>
										<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_pay7_v4new_off.png"/>
									</figure>
								</li>
								<li>
									<figure>
										<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_pay3_v4_off.png"/>
									</figure>
								</li>
								<li>
									<figure>
										<img src="https://recipe1.ezmember.co.kr/img/mobile/btn_pay2_v4_off.png"/>
									</figure>
								</li>
								<li>
									<figure>
										<img src="	https://recipe1.ezmember.co.kr/img/mobile/btn_pay6_v4_off.png"/>
									</figure>
								</li>
							</ul>
						</div>
						<div class="order_agree">
							<div class="order_agree1">
								<input type="checkbox" id="agree">
								<label for="agree">결제 필수사항 동의</label>
							</div>
							<div class="order_agree2">
								상품 공급사 개인정보 제공 동의에 대한 내용을 확인하였으며 이에 동의합니다.
							</div>
							<div class="order_agree3">
								<pre>
	- 제공받는 자 : (주)만개의레시피 / 상품공급사
	- 이용목적 : 판매자와 구매자의 거래의 원활한 진행, 본인의사의확인, 고객 상담 및 불만처리, 상품과 경품 배송을 위한 배송지 확인 등
	- 제공항목 : 구매자 이름, 전화번호, ID, 휴대폰번호, 이메일주소, 상품 구매정보, 상품 수취인 정보(이름, 주소, 전화번호)
	- 보유/이용기간 : 배송완료 후 한달
								</pre>
							</div>
							<div class="order_agree4">
								구매하실 상품의 결제정보를 확인하였으며, 구매진행에 동의합니다.
							</div>
						</div>
						<div class="order_box">
							<button id="all_order">결제하기</button>
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
		<div class="address_box">
			<div class="address_title">
				<h4>나의 배송지 관리</h4>
				<div class="div_address_cancel">
					X
				</div>
			</div>
			<div class="address_content">
				<div class="address_content_title">
					<p>배송지 목록</p>
					<a class="a_address_btn_newaddress">+새 배송지 추가</a>
				</div>
				<div class="overflow_wrap">
					<table class="table_address_cont">
						<colgroup>
							<col style="width:10%">
							<col style="width:13%">
							<col style="width:12%">
							<col style="width:22%">
							<col style="width:22%">
							<col style="width:10%">
						</colgroup>
						<thead>
							<tr>
								<th>선택</th>
								<th>배송지이름</th>
								<th>받으시분</th>
								<th>주소</th>
								<th>연락처</th>
								<th>수정/삭제</th>
							</tr>
						</thead>
						<tbody class="tbody_user_addresslist">
							<tr data-addressId=+addressId+>
								<td class="td_select">
									<a class="a_select">선택</a>
								</td>
								<td>
									<span>+defaultAddress+</span><br/>
									<strong class="reciever">+reciever+</strong>
								</td>
								<td>+reciever+</td>
								<td>
									<span class="zipcode">+zipcode+</span><br/>
									<span class="address">+address+</span><span class="detailaddress">detailAddress+</span>
								</td>
								<td>
									<span class="phonenumber">휴대폰:+phoneNumber+</span>
								</td>
								<td>
									<div class="div_btn_modify"><a>수정</a></div>
									<div class="div_btn_delete"><a>삭제</a></div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="address_newaddress_regist">
				<div class="newaddress_regist">
					배송지등록
				</div>
				<table class="table_newaddress_cont">
					<colgroup>
						<col style="width:20%;">
						<col style="width:80%;">
					</colgroup>
					<tbody>
						<tr>
							<th>받으실 분</th>
							<td>
								<input type="text" name="receiver" id="input_update_receiver" required>
							</td>
						</tr>
						<tr>
							<th>받으실 곳</th>
							<td>
								<div class="div_zip_code_wrap">
									<input type="hidden" name="productId" id="input_update_productId" value=0>
									<input type="text" id="input_update_zipcode" name="zipCode" required> 
									<button type="button" class="btn_zipcode">우편주소검색</button>
								</div>
								<div class="div_address_wrap">
									<input type="text" name="address" id="input_update_address" required>
								</div>
								<div class="div_address_detail_wrap">
									<input type="text" name="detailAddress" id="input_update_detailaddress" required>
								</div>
							</td>
						</tr>
						<tr>
							<th>휴대폰번호</th>
							<td>
								<input type="text" id="input_update_phonenumber" name="phoneNumber" required>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="checkbox_wrap">
					<input type="checkbox" id="default_address" name="check_dafault">
					<label for="default_address">
						기본 배송지로 설정 합니다.
					</label>
				</div>
				<div class="btn_wrap">
					<button id="btn_cancel">취소</button>
					<button id="btn_save">저장</button>
					<button id="btn_update">수정</button>
				</div>
			</div>
		</div>
		<div class="address_bg"></div>
	</div>	
</body>
</html>