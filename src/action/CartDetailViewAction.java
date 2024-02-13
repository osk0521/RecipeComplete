package action;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.CartDao;
import vo.CartDetailVo;

public class CartDetailViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			HttpSession session = request.getSession(); // 세션 선언
			String loginId = (String)session.getAttribute("loginId"); // 로그인된 아이디로 초기화
			
			ArrayList<CartDetailVo> cartList = new ArrayList<CartDetailVo>(); // 장바구니 정보 담는 ArrayList 선언
			cartList = CartDao.getCartdao().getCartListById(loginId); // 로그인된 아이디로 장바구니 ArrayList 받는 메서드
			request.setAttribute("cartList", cartList);
			
			request.getRequestDispatcher("HRK_Store_Cart.jsp").forward(request, response);// 장바구니 화면으로 전달
	}

}
