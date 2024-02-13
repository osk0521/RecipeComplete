package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartDao;

public class CartUpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("controller에서 cartUpdateAction으로 이동");
		
		String optionNum = request.getParameter("optionNum"); // 장바구니화면에서 전달된 option명으로 String 초기화
		String optionQty = request.getParameter("optionQty"); // 장바구니 화면에서 전달된 option 수량으로 String 초기화
		int productId = Integer.parseInt(request.getParameter("productId")); // 장바구니 화면에서 전달된 상품 번호로 int 초기화
		
		HttpSession session = request.getSession(); // 세션 선언
		String loginId = (String)session.getAttribute("loginId"); // 로그인 아이디 받기
		
		CartDao.getCartdao().updateCartDB(loginId, optionQty, optionNum, productId); // 로그인 아이디, 옵션명, 옵션 수량, 상품 번호로 장바구니 DB 업데이트 진행하는 메서드
		request.getRequestDispatcher("Controller?command=cart_detail_view").forward(request, response); // 콘트롤러 장바구니 화면 이동 command와 함께 전달
	}

}
