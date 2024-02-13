<%@page import="vo.RelatedGoodsVo"%>
<%@page import="vo.StoreMainpageGoodVo"%>
<%@page import="vo.StoreGoodsDetailsOption1Vo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="vo.StoreGoodsDetailsVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	StoreGoodsDetailsVo detailvo = (StoreGoodsDetailsVo)request.getAttribute("goodsdetail"); 
	ArrayList<String> goodsImgs = (ArrayList<String>)request.getAttribute("goodsimgs");
	ArrayList<StoreGoodsDetailsOption1Vo> goodsOptions = (ArrayList<StoreGoodsDetailsOption1Vo>)request.getAttribute("goodsOptions");
	ArrayList<String> goodsDetailImgs = (ArrayList<String>)request.getAttribute("goodsDetailImages");
	ArrayList<RelatedGoodsVo> relatedGoods = (ArrayList<RelatedGoodsVo>)request.getAttribute("relatedGoods");
	ArrayList<String> goodsTag = (ArrayList<String>)request.getAttribute("goodsTag");
	String loginId = (String)session.getAttribute("loginId");
%>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="Store_CSS/Store_goodsdetail.css" type="text/css"/>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="http://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
	<link rel="stylesheet" type="text/css" href="http://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Header.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Footer.css" type="text/css"/>
	<link rel="stylesheet" href="Store_CSS/Store_Right_Scroll.css" type="text/css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="http://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script type="text/javascript" src="js/scroll_fixed.js"></script>
	<script type="text/javascript" src="js/Store_GoodDetail.js"></script>
	<script type="text/javascript" src="js/Store_header.js"></script>
	<script src="jquery-animate-css-rotate-scale.js"></script>
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
		<div class="main_cont">
			<!-- 메인 내용 여기에 입력 -->
			<div class="main_cont_goodsdetailfirst">
					<div class="main_cont_goodsdetailfirst_left">
						<figure>
							<img id="thumb_img" src = '<%=detailvo.getImg()%>'/>
						</figure>
						<div class="main_cont_goodsdetailfirst_left_slick">
							<%for(String img : goodsImgs) {%>
							<div class="slick_cont">
								<figure>
									<img class="img" src ='<%=img%>'/>
								</figure>
							</div>
							<%} %>
						</div>
					</div>
					<div class="main_cont_goodsdetailfirst_right">
						<div class="main_cont_goodsdetailfirst_right_name">
								<p><%=detailvo.getName() %></p>
							<div class="main_cont_goodsdetailfirst_right_name_info">
								<p><%=detailvo.getSimpleInfo() %></p>
							</div>
						</div>
						<div class="main_cont_goodsdetailfirst_right_price">
							<div class="main_cont_goodsdetailfirst_right_price_1">
								<div class="main_cont_goodsdetailfirst_right_price_1_left">
									<span class="good_sale"><%=detailvo.getSalePer()%>%</span>
									<span class="good_price"><%=detailvo.getSellCost() %>원</span>
								</div>
								<div class="main_cont_goodsdetailfirst_right_price_1_right">
									<div class ="btn_wishlist">
										<figure>
											<img src ='https://recipe1.ezmember.co.kr/img/mobile/icon_zzim2.png'/>
										</figure>
									</div>
									<div class="btn_share">
										<figure>
											<img src ='	https://recipe1.ezmember.co.kr/img/mobile/icon_share10.png'/>
										</figure>
									</div>
								</div>
							</div>
							<div class="main_cont_goodsdetailfirst_right_price_2">
								<p class="good_first_price"><%=detailvo.getFirstCost() %>원</p>
							</div>
							<div class="main_cont_goodsdetailfirst_right_price_3">
								<div class="main_cont_goodsdetailfirst_right_price_34_icon">
									<figure><img src ='https://recipe1.ezmember.co.kr/img/mobile/icon_delivery3.png'/></figure>
									<span>배송</span>
								</div>
								<%if(detailvo.getdeliveryCharge()>0){ %>
								<span class="deli_char"><%=detailvo.getdeliveryCharge() %></span>
								<span class="deli_char_detail">원 (30,000이상 무료배송)</span>
								<%} else{%>
								<span class="deli_char">무료배송</span>
								<%} %>
								
							</div>
							<div class="main_cont_goodsdetailfirst_right_price_4">
								<div class="main_cont_goodsdetailfirst_right_price_34_icon">
									<figure><img src ='https://recipe1.ezmember.co.kr/img/mobile/icon_point.png'/></figure>
									<span>적립</span>
								</div>
								<span class="reserver"><%=detailvo.getReserves() %></span>
								<span class="deli_char_detail">원 적립 (모든 회원 구매액의 0.5% 적립)</span>
							</div>
							<div class="main_cont_goodsdetailfirst_right_price_5">
								<div class="main_cont_goodsdetailfirst_right_price_5_opt">
									<select id="select_opt">
										<option>[선택]옵션을 선택해주세요 </option>
										<%for(StoreGoodsDetailsOption1Vo optionvo : goodsOptions) {%>
										<option data-option="<%=optionvo.getOp1Num() %>" data-name="<%=optionvo.getoptionContent() %>" data-price="<%=optionvo.getPrice() %>" data-qty="<%=optionvo.getQty()%>">
											<%=optionvo.getoptionContent() %>
											<%=optionvo.getPrice() %>원
											(재고수량: <%=optionvo.getQty() %>개)
										</option>
										<%} %>
									</select>
								</div>
								<div class="main_cont_goodsdetailfirst_right_price_5_optqty">
									<ul class="ul_optqty">
										
									</ul>
								</div>
								<div class="main_cont_goodsdetailfirst_right_price_5_totalprice remove">
									<div class="total_price">
										주문금액 <span><b id="total_price"></b>원</span>
									</div>
								</div>
							</div>
							<div class="main_cont_goodsdetailfirst_right_price_6">
								<input type="hidden" id="input_productid" value="<%=detailvo.getProuductId() %>">
								<%if(loginId==null) {%>
								<button id="cart" onClick="cart_unlogin()">장바구니</button>
								<button id="pay" onClick="pay_unlogin()">바로구매</button>
								<%} else{ %>
								<button id="cart" onClick="cart_login()">장바구니</button>
								<button id="pay" onClick="pay_login()">바로구매</button>
								<%} %>
							</div>
							<div class="n_pay_box">
							</div>
						</div>
					</div>
				</div>
				<div class="good_section">
					<div class="goodsdetail_slick_title">
						<span class="goodsdetail_slick_title1"><b><%=detailvo.getCategory() %></b> 상품 추천</span>
						<span class="goodsdetail_slick_title2"><a href="#">더보기</a></span>
					</div>
					<div class="goodsdetail_slick">
						<%for(RelatedGoodsVo goods : relatedGoods) {%>
						<div class="goodsdetail_slick_wrap">
							<div class="goodsdetail_slick_thumb">
								<figure>
									<img src="<%=goods.getImage()%>">
								</figure>
							</div>
							<div class="goodsdetail_slick_cont">	
								<div class="goodsdetail_slick_cont_title">
									<%=goods.getName() %>
								</div>
								<div class="goodsdetail_slick_cont_price">
									<%if(goods.getSalePer()>0) {%>
									<span class="sale_per"><b><%=goods.getSalePer() %></b>%</span>
									<%} else{} %>
									<span class="price"><b><%=goods.getFirstCost() %></b>원</span>
								</div>
								<%if(goods.getScore()>0) {%>
								<div class="goodsdetail_slick_cont_score">
									<figure class="star"><img src="Images/star.png"></figure>
									<span class="score"><b><%=goods.getScore() %></b></span>
									<span class="ea"><b>(<%=goods.getHits() %>)</b></span>
								</div>
								<%} else{}%>
							</div>
						</div>
						<%} %>
					</div>
				</div>
				<div class="main_cont_goodsdetailsecond">
					<ul class="main_cont_goodsdetailsecond_box">
						<li><span class="li_detailinfo">상세정보</span></li>
						<li><span class="li_review">후기 <b>(97)</b></span></li>
						<li><span class="li_refund">배송환불</span></li>
						<li><span class="li_inquir">문의 <b>(32)</b></span></li>
					</ul>
				</div>
				<%if(detailvo.getVideo()!=null){ %>
				<div class="main_cont_goodsdetailthird_video">
					<iframe width="560" height="315" src="<%=detailvo.getVideo() %>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
				</div>
				<%} %>
				<div class="main_cont_goodsdetailthird_img">
					<ul>
					<%for(String img : goodsDetailImgs) {%>
						<li>
							<figure>
								<img src="<%=img%>">
							</figure>
						</li>
					<%} %>
					</ul>
					<%if(detailvo.getVideo()!=null){ %>
					<div class="main_cont_goodsdetailthird_video">
						<iframe width="560" height="315" src="<%=detailvo.getVideo() %>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
					</div>
					<%} %>
				</div>
				<div class="main_cont_goodsdetailthird_detail_show">
					<a class="show_detailinfo">
						상세정보 더보기
					</a>
				</div>
				<div class="main_cont_goodsdetailthird_detail_close remove">
					<a class="close_detailinfo">
						상세정보 닫기
					</a>
				</div>
				<div class="main_cont_goodsdetailfour_line"></div>
				<div class="main_cont_goodsdetailfour_star">
					<span class="main_cont_goodsdetailfour_star_point"><%=detailvo.getScore() %></span>
					<div class="rating" data-rate="<%=detailvo.getScore()%>">
						<div class="star_wrap">
							<div class="star"><i class="bi bi-star-fill"></i></div>
						</div>
						<div class="star_wrap">
							<div class="star"><i class="bi bi-star-fill"></i></div>
						</div>
						<div class="star_wrap">
							<div class="star"><i class="bi bi-star-fill"></i></div>
						</div>
						<div class="star_wrap">
							<div class="star"><i class="bi bi-star-fill"></i></div>
						</div>
						<div class="star_wrap">
							<div class="star"><i class="bi bi-star-fill"></i></div>
						</div>
					</div>
				</div>
				<div class="main_cont_goodsdetailfour_review_title">
					<span>후기</span><span> 5</span>
				</div>
				<ul class="main_cont_goodsdetailfour_review_ul">
					<li>
						<a href="#">
							<figure>
								<img src="https://phinf.pstatic.net/checkout.phinf/20231201_102/1701400605292IAfC4_JPEG/1701400590374.jpg?type=w640"/>
							</figure>
						</a>
					</li>
					<li>
						<a href="#">
							<figure>
								<img src="https://phinf.pstatic.net/checkout.phinf/20231201_102/1701400605292IAfC4_JPEG/1701400590374.jpg?type=w640"/>
							</figure>
						</a>
					</li>
					<li>
						<a href="#">
							<figure>
								<img src="https://phinf.pstatic.net/checkout.phinf/20231201_102/1701400605292IAfC4_JPEG/1701400590374.jpg?type=w640"/>
							</figure>
						</a>
					</li>
				</ul>
				<div class="main_cont_goodsdetailfour_review_title2">
					<div class="main_cont_goodsdetailfour_review_title2_left">
						<input type="checkbox" id="photo_review">
						<label for="photo_review">포토후기</label>
						<div class="review_sort">
							<span class="first_sort" id="on"><a href="#">베스트순</a></span>
							<span class="second_sort"><a href="#">최신순</a></span>
						</div>
					</div>
					<div class="main_cont_goodsdetailfour_review_title2_right">
						<div class="review_write_btn_wrap">
							<a id="btn_write_review">
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
  								<path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
  								<path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
								</svg>
								<span>후기작성</span>
							</a>
						</div>
					</div>
				</div>
				<ul class="main_cont_goodsdetailfour_review_ul2">
					<li>
						<div class="review_name" id="review">성슈</div>
						<div class="review_score" id="review">
							<div class="rating" data-rate="4.6">
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
							</div>
							<span class="review_score_date">2023-11-29</span>
						</div>
						<div class="review_img" id="review">
							<figure>
								<img src="https://godomall.speedycdn.net/80274c85de7606020be963ac556612a4/order/3177208/review/thumb/2a4bc4e15fbe7d23806"/>
							</figure>
						</div>
						<div class="review_comment" id="review">
							팔팔 잘 끓습니다 이쁘고 좋네요 생각보다 안시끄럽습니다 외부는 뜨끈하네요 ㅎㅎ
						</div>
					</li>
					<li>
						<div class="review_name" id="review">jyou***</div>
						<div class="review_score" id="review">
							<div class="rating" data-rate="4.8">
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
							</div>
							<span class="review_score_date">2023-11-29</span>
						</div>
						<div class="review_comment" id="review">
							겨울되니 건조해서 건조기 틀면 춥고 분무량도 많아 바닥이 젖었는데 스팀 제품이라 춥지도 않고 분무량도 적당해서 바닥젖는 일도 없네요~다만 온도 올라가기까지 시간이 걸려서 저는 처음부터 따뜻한물 넣고 작동시킵니다~
						</div>
					</li>
					<li>
						<div class="review_name" id="review">성슈</div>
						<div class="review_score" id="review">
							<div class="rating" data-rate="4.1">
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
							</div>
							<span class="review_score_date">2023-11-29</span>
						</div>
						<div class="review_img" id="review">
							<figure>
								<img src="https://godomall.speedycdn.net/80274c85de7606020be963ac556612a4/order/3177208/review/thumb/2a4bc4e15fbe7d23806"/>
							</figure>
						</div>
						<div class="review_comment" id="review">
							팔팔 잘 끓습니다 이쁘고 좋네요 생각보다 안시끄럽습니다 외부는 뜨끈하네요 ㅎㅎ
						</div>
					</li>
					<li>
						<div class="review_name" id="review">jyou***</div>
						<div class="review_score" id="review">
							<div class="rating" data-rate="4.3">
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
							</div>
							<span class="review_score_date">2023-11-29</span>
						</div>
						<div class="review_comment" id="review">
							겨울되니 건조해서 건조기 틀면 춥고 분무량도 많아 바닥이 젖었는데 스팀 제품이라 춥지도 않고 분무량도 적당해서 바닥젖는 일도 없네요~다만 온도 올라가기까지 시간이 걸려서 저는 처음부터 따뜻한물 넣고 작동시킵니다~
						</div>
					</li>
					<li>
						<div class="review_name" id="review">jyou***</div>
						<div class="review_score" id="review">
							<div class="rating" data-rate="4.0">
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
								<div class="star_wrap">
									<div class="star"><i class="bi bi-star-fill"></i></div>
								</div>
							</div>
							<span class="review_score_date">2023-11-29</span>
						</div>
						<div class="review_comment" id="review">
							겨울되니 건조해서 건조기 틀면 춥고 분무량도 많아 바닥이 젖었는데 스팀 제품이라 춥지도 않고 분무량도 적당해서 바닥젖는 일도 없네요~다만 온도 올라가기까지 시간이 걸려서 저는 처음부터 따뜻한물 넣고 작동시킵니다~
						</div>
					</li>
				</ul>
				<div class="main_cont_goodsdetailfour_review_next">
					<a class="moreview_review">
					더보기 <span class="current_review">5</span>/<span class="total_review">32</span>
					</a>
				</div>
				<div class="main_cont_goodsdetailfifth">
					<ul>
						<li class="goods_inform">
							<div class="main_cont_goodsdetailfifth_title">
								상품정보
							</div>
							<div class="main_cont_goodsdetailfifth_arrow">
								<svg class="arrow" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-arrow-right-short" viewBox="0 0 16 16">
  									<path fill-rule="evenodd" d="M4 8a.5.5 0 0 1 .5-.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5A.5.5 0 0 1 4 8z"/>
								</svg>
							</div>
						</li>
						<li class="goods_inform_cont">
							<div class="div_goods_inform_cont">
								<pre>
<%=detailvo.getGoodsinfo() %>							
								</pre>
							</div>
						</li>
						<li class="deli_exchange_refund">
							<div class="main_cont_goodsdetailfifth_title">
								배송/환불/교환
							</div>
							<div class="main_cont_goodsdetailfifth_arrow">
								<svg class="arrow" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-arrow-right-short" viewBox="0 0 16 16">
  									<path fill-rule="evenodd" d="M4 8a.5.5 0 0 1 .5-.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5A.5.5 0 0 1 4 8z"/>
								</svg>
							</div>
						</li>
						<li id="deli_exchange_refund_cont">
							<div class="deli_exchange_refund_cont1">
								<pre>
<%=detailvo.getRelatedInfo() %> 
								</pre>
							</div>
							<div class="deli_exchange_refund_cont2">
								판매자 정보
								<table>
									<tr>
										<th>상호/대표</th>
										<td><%=detailvo.getsName() %></td>
										<th>사업장 소재지</th>
										<td><%=detailvo.getsWorkplace() %></td>
									</tr>
									<tr>
										<th>e-mail</th>
										<td><%=detailvo.getsEmail() %></td>
										<th>연락처</th>
										<td><%=detailvo.getsPhone() %></td>
									</tr>
									<tr>
										<th>통신판매업 신고번호</th>
										<td><%=detailvo.getsReportNum() %></td>
										<th>사업자번호</th>
										<td><%=detailvo.getsCompanyNum() %></td>
									</tr>
								</table>
							</div>
							<div class="deli_exchange_refund_cont3">
								본 상품과 컨텐츠는 입점 판매자가 등록한 것으로 (주)만개의레시피는 통신판매중개자로서 거래 당사자가 아니기 때문에 그 내용과 거래에 대한 책임을 일체 지지 않습니다.
								<span>만개의레시피 쇼핑몰 내 모든 사진 및 컨텐츠를 무단 사용 시 법적 조치를 받을 수 있습니다.</span>
							</div>
						</li>
						<li id="qna">
							<div class="main_cont_goodsdetailfifth_title">
								문의
							</div>
							<div class="main_cont_goodsdetailfifth_arrow">
								<svg class="arrow" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-arrow-right-short" viewBox="0 0 16 16">
  									<path fill-rule="evenodd" d="M4 8a.5.5 0 0 1 .5-.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5A.5.5 0 0 1 4 8z"/>
								</svg>
							</div>
						</li>
						<li id="qna_cont">
							<div class="qna_cont1">
								<div class="qna_cont1_title">
									<b>상품문의</b> 총 <span id="qna_cont1_title_ea">27</span> 개
								</div>
								<div class="qna_cont1_notice">
									상품과 관계없는 글, 양도, 광고성, 욕설, 비방, 도배 등의 글은 예고없이 삭제됩니다.
								</div>
								<div class="qna_cont1_wrap">
									<div class="qna_cont1_write">
										<form>
											<textarea rows="30" cols="10"></textarea>
										</form>
									</div>
									<ul>
										<li id="qna_write">
											<button type="submit">등록하기</button>
										</li>
										<li id="qna_Cancel">
											<button type="submit">취소하기</button>
										</li>
									</ul>
								</div>
								<div class="qna_cont1_chkbox">
									<input type="checkbox" id="qna_chkbox">
									<label for="qna_chkbox">비밀글로 문의하기</label>
								</div>
							</div>
							<div class="qna_cont2">
								<ul>
									<li class="qna_cont2_buyer">
										<div class="qna_cont2_buyer_1">
											<span class="id">sky*****</span>
											<span class="date">2023-00-00</span>
										</div>
										<div class="qna_cont2_buyer_2">
											배송은 언제부터 시작되나요?? 물건이 너무 좋고 싸네용
										</div>
										<div class="qna_cont2_buyer_3">
											<button type="button">댓글보기 <span> 1</span></button>
										</div>
									</li>
									<li class="qna_cont2_manager">
										<div class="qna_cont2_manager_wrap">
											<div class="qna_cont2_manager_1">
												<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-return-right" viewBox="0 0 16 16">
	  											<path fill-rule="evenodd" d="M1.5 1.5A.5.5 0 0 0 1 2v4.8a2.5 2.5 0 0 0 2.5 2.5h9.793l-3.347 3.346a.5.5 0 0 0 .708.708l4.2-4.2a.5.5 0 0 0 0-.708l-4-4a.5.5 0 0 0-.708.708L13.293 8.3H3.5A1.5 1.5 0 0 1 2 6.8V2a.5.5 0 0 0-.5-.5z"/>
												</svg>
												<span>2023-00-00</span>
											</div>
											<div class="qna_cont2_manager_2">
												안녕하세요. 고객님 그냥 드시면 안될까요? 저희도 흙파서 장사하는 것도 아니고 자꾸 이런 컴플레인 주시면 업무에 지장이 생깁니다.
												감사합니다.
											</div>
										</div>
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
				<div class="main_cont_goodsdetailsix">
					<div class="tag_title">
						<b>관련 태그</b>
					</div>
					<div class="tag_cont">
						<ul>
						<%for(String tag: goodsTag){%>
							<li>
								<a href="#">
									<%=tag %>
								</a>
							</li>
							<%} %>
						</ul>
					</div>
				</div>
		</div>
		<div class="review_box">
			<div class="review_tit">
				<h4>상품후기 쓰기</h4>
			</div>
			<div class="review_cont">
				<form>
					<div class="review_photo_info">
						<figure>
							<img src="https://shoptr3131.cdn-nhncommerce.com/data/goods/22/06/25/1000028838/1000028838_detail_090.jpg"/>
						</figure>
						<div class="review_main_tit">
							<P class="p_main_tit">코스카 터키 구름 솜사탕 피스마니에</P>
							<p class="p_sub_tit">혀에 닿자마자 사르르!터키식 구름 솜사탕!</p>
						</div>
					</div>
					<table class="table_review">
						<colgroup>
							<col style="width:15%">
							<col style="width:85%">
						</colgroup>
						<tbody>
							<tr id="tr_review_id">
								<th>작성자</th>
								<td>유저아이디</td>
							</tr>
							<tr id="tr_review_rating">
								<th>평가</th>
								<td>
									<ul class="ul_review_ratingstar">
										<li>
											<input type="radio" name="rating" id="rating1">
											<label for="rating1">
												<img src="Images/star_1point.png">
											</label>
										</li>
										<li>
											<input type="radio" name="rating" id="rating2">
											<label for="rating2">
												<img src="Images/star_2point.png">
											</label>
										</li>
										<li>
											<input type="radio" name="rating" id="rating3">
											<label for="rating3">
												<img src="Images/star_3point.png">
											</label>
										</li>
										<li>
											<input type="radio" name="rating" id="rating4">
											<label for="rating4">
												<img src="Images/star_4point.png">
											</label>
										</li>
										<li>
											<input type="radio" name="rating" id="rating5">
											<label for="rating5">
												<img src="Images/star_5point.png">
											</label>
										</li>
									</ul>
								</td>
							</tr>
							<tr id="tr_review_content">
								<th>
									내용
								</th>
								<td>
									<textarea></textarea>
								</td>
							</tr>
							<tr>
								<th>
									파일
								</th>
								<td>
									<input type="file">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<div class="btn_wrap">
					<button id="btn_review_cancel">취소</button>
					<button id="btn_review_regist">등록</button>
				</div>
			</div>
		</div>
		<div class="review_bg remove"></div>
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