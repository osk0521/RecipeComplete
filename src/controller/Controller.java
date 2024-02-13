package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.*;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String command = request.getParameter("command");
		System.out.println(command);
		Action action = null;
		// 파라미터로 받은 커맨드에 따라 실행할 액션을 정함
		switch(command) {
		// jw
		// 회원 관련 액션(로그인, 회원가입, 회원정보수정)
		case "regist_form" : action = new RegistFormAction(); break;
		case "regist" : action = new RegistAction(); break;
		case "regist_result" : action = new RegistResultAction(); break;
		case "send_phone_authentication_msg" : action = new SendPhoneAuthenticationMsgAction(); break;
		case "send_email_authentication_msg" : action = new SendEmailAuthenticationMsgAction(); break;
		case "phone_authentication" : action = new PhoneAuthenticationAction(); break;
		case "email_authentication" : action = new EmailAuthenticationAction(); break;
		case "authentication_expire" : action = new AuthenticationExpireAction(); break;
		case "api_captcha_getkey" : action = new ApiCaptchaNkeyAction(); break;
		case "api_captcha_getimg" : action = new ApiCaptchaImageAction(); break;
		case "api_captcha_result" : action = new ApiCaptchaNkeyResultAction(); break;
		case "login_form" : action = new LoginFormAction(); break;
		case "login" : action = new LoginAction(); break;
		case "naver_login" : action = new NaverLoginAction(); break;
		case "login_result" : action = new LoginResultAction(); break;
		case "logout" : action = new LogoutAction(); break;
		case "member_check" : action = new MemberCheckAction(); break;
		case "member_id_check" : action = new MemberIdCheckAction(); break;
		case "member_modify_view" : action = new MemberModifyViewAction(); break;
		case "member_password_check" : action = new MemberPasswordCheckAction(); break;
		case "member_modify_password" : action = new MemberModifyPasswordAction(); break;
		case "member_nickname_check" : action = new MemberNicknameCheckAction(); break;
		case "member_modify_nickname" : action = new MemberModifyNicknameAction(); break;
		case "member_modify_email" : action = new MemberModifyEmailAction(); break;
		case "member_modify_phone" : action = new MemberModifyPhoneAction(); break;
		case "member_withdrawal_view" : action = new MemberWithdrawalViewAction(); break;
		case "member_withdrawal" : action = new MemberWithdrawalAction(); break;
		case "member_withdrawal_kakao" : action = new MemberWithdrawalKakaoAction(); break;
		case "member_withdrawal_naver" : action = new MemberWithdrawalNaverAction(); break;
		case "social_login" : action = new SocialLoginAction(); break;
		case "kakao_regist" : action = new KakaoRegistAction(); break;
		case "naver_regist" : action = new NaverRegistAction(); break;
		// 레시피-마이페이지 액션
		case "mypage_recipe_view" : action = new MyPageRecipeViewAction(); break;
		case "mypage_comment_view" : action = new MyPageCommentViewAction(); break;
		case "mypage_followingchef_view" : action = new MyPageFollowingChefViewAction(); break;
		case "mypage_recipenote_view" : action = new MyPageRecipeNoteViewAction(); break;
		case "mypage_recipenote_modify" : action = new MyPageRecipeNoteModifyAction(); break;
		case "mypage_recipenote_delete" : action = new MyPageRecipeNoteDeleteAction(); break;
		case "mypage_followingrecipe_view" : action = new MyPageFollowingRecipeViewAction(); break;
		case "mypage_followingrecipe_delete" : action = new MyPageFollowingRecipeDeleteAction(); break;
		case "following" : action = new FollowingAction(); break;
		case "unfollowing" : action = new UnFollowingAction(); break;
		case "get_following" : action = new GetFollowingAction(); break;
		case "get_follower" : action = new GetFollowerAction(); break;
		case "modify_profile" : action = new ModifyProfileAction(); break;
		// 레시피-쉐프 액션
		case "chef_view" : action = new ChefViewAction(); break;
		// 레시피-고객센터 액션
		case "notice_view" : action = new NoticeViewAction(); break;
		case "help_view" : action = new HelpViewAction(); break;
		case "inquiry_view" : action = new InquiryViewAction(); break;
		case "inquiry_write" : action = new InquiryWriteAction(); break;
		case "inquiry_image_upload" : action = new InquiryImageUploadAction(); break;
		case "inquiry_write_simple" : action = new InquiryWriteSimpleAction(); break;
		case "inquiry_write_form" : action = new InquiryWriteFormAction(); break;
		case "inquiry_write_result" : action = new InquiryWriteResultAction(); break;
		case "inquiry_modify" : action = new InquiryModifyAction(); break;
		case "inquiry_modify_form" : action = new InquiryModifyFormAction(); break;
		case "inquiry_delete" : action = new InquiryDeleteAction(); break;
		// 예외페이지
		case "page_not_found" : action = new PageNotFoundAction(); break;
		case "page_not_login" : action = new PageNotLoginAction(); break;
		// case "get_profile" : action = new GetProfileAction(); break;
		
		// sk
		case "recipe_write_form": action = new RecipeWriteFormAction(); break;//글쓰기 페이지 이동
		case "recipe_write": action = new RecipeWriteAction(); break;//글쓰기
		case "main_page": action = new RecipeMainPageAction(); break;//메인페이지
		case "recipe_search": action = new RecipeSearchAndListAction(); break;//검색
		case "view_recipe_detail": action = new RecipeDetailAction(); break;//상세페이지 - 댓글 미완
		case "add_recipe_note": action = new AddRecipeNoteAction(); break;//상세페이지 - 댓글 미완
		case "ranking_page": action = new RecipeRankpageAction(); break;//랭킹
		case "magazine_page": action = new RecipeMagazinePageAction(); break;//매거진 
		case "magazine_detail_action": action = new RecipeMagazineDetailAction(); break;//매거진 상세 
		case "recipe_modify_form": action = new RecipeModifyFormAction(); break;//편집 페이지 이동
		case "recipe_modify": action = new RecipeModifyAction(); break;// 편집
		case "recipe_delete_action": action = new RecipeDeleteAction(); break;//레시피 삭제
		case "recipe_comment_write_action": action = new RecipeCommentWriteAction(); break;//AJAX댓글 쓰기
		case "recipe_comment_modify_action": action = new RecipeCommentModifyAction(); break;//AJAX댓글 수정
		case "recipe_comment_delete": action = new RecipeCommentDeleteAction(); break;//AJAX댓글 삭제
		case "refrigerator_page": action = new RecipeRefrigeratorPageAction(); break; //냉장고 페이지 이동
		case "refrigerator_add_ingredi": action = new RecipeRefrigeratorAddIngrediAction(); break;//AJAX 냉장고 재료 선택
		case "manager_page": action = new RecipeManagerPageAction(); break;//매니저 페이지 이동 //미완
		case "manager_action": action = new RecipeManagerAction(); break;//메니저 //미완
		// case "refrigerator_get_img": action = new RecipeRefrigeratorGetImgAction(); break;//AJAX 냉장고 검색결과
		
		// hr
		case "store_mainlist" : action = new StoreMainpageAction(); break;
		case "store_goodsDetail" : action = new StoreGoodsDetialAction(); break;
		case "go_to_cart" : action = new GoToCartAction(); break;
		case "cart_detail_view" : action = new CartDetailViewAction(); break;
		case "cart_delete" : action = new CartDeleteAction(); break;
		case "cart_get_update_option" : action = new CartGetUpdateOptionAction(); break;
		case "cart_update" : action = new CartUpdateAction(); break;
		case "order_form" : action = new OrderFormAction(); break;
		case "order_get_default_address" : action = new OrderGetDefaultAddressAction(); break;
		case "order_get_user_addressList" : action = new OrderGetUserAddressListAction(); break;
		case "order_update_destination" : action = new OrderUpdateDestination(); break;
		case "order_update_existing_address" : action = new OrderUpdateExistingAddressAction(); break;
		case "order_delete_address" : action = new OrderDeleteAddressAction(); break;
		case "order_insert_orderdetail" : action = new OrderInsertOrderDetail(); break;
		case "order_insert_orderlist" : action = new OrderInsertOrderListAction(); break;
		case "order_delete_cartlist" : action = new OrderDeleteCartListAction(); break;
		case "payment_check" : action = new PaymentCheckAction(); break;
		case "order_result" : action = new OrderResultAction(); break;
		case "order_delete_orderdetail" : action = new OrderDeleteOrderdetailAction(); break;
		
		default : action = new PageNotFoundAction(); break;
		}
		
		action.execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
