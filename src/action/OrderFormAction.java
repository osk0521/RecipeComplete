package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OrderDao;
import vo.CartDetailVo;

public class OrderFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("controller에서 orderFormAction으로 이동");
		
		String[] optionNumList = request.getParameter("optionNumList").split(","); // 전달받은 옵션명 ,으로 구분후 String 배열로 초기화
		HttpSession session = request.getSession(); // 세션 선언
		String loginId = (String)session.getAttribute("loginId"); // 로그인 아이디 받기
		
		System.out.println(Arrays.toString(optionNumList));
		 ArrayList<CartDetailVo> selectOrderList = new ArrayList<CartDetailVo>(); // 장바구니 정보 담는 ArrayList 선언
		 selectOrderList = OrderDao.getorderDao().getSelectOrderListFromCartDB(loginId, optionNumList); // 로그인 아이디와 옵션명 배열 파라미터로 장바구니 정보 ArrayList 초기화하는 메서드
		 
		 for(CartDetailVo vo : selectOrderList) {
			 System.out.println(vo.getOptionContent());
		 }
		 
		 int reserves = OrderDao.getorderDao().getreservesById(loginId); // 로그인 아이디 적립금으로 int 변수 초기화하는 메서드
		 System.out.println(reserves);
		 
		 request.setAttribute("selectOrderList", selectOrderList);
		 request.setAttribute("reserves", reserves);
		 
		 request.getRequestDispatcher("HRK_Store_OrderForm.jsp").forward(request, response); // 주문서 화면으로 이동
		 
		
		
	}
}
