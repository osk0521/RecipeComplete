package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import dao.OrderDao;

public class OrderInsertOrderDetail implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller에서 orderInsertOrderList으로 이동");
		int orderId = Integer.parseInt(request.getParameter("orderId")); // 주문번호
		String receiver = request.getParameter("receiver"); // 받는사람
		String zipcode = request.getParameter("receiver"); // 우편주소
		String address = request.getParameter("address"); // 주소
		String detailAddress = request.getParameter("detailAddress"); // 상세주소
		String phoneNumber = request.getParameter("phoneNumber"); // 핸드폰
		String order = request.getParameter("order");// 주문자
		String orderPhoneNumber = request.getParameter("orderPhoneNumber");
		String email = request.getParameter("email"); // 이메일
		String memo = request.getParameter("memo"); // 메모
		int reserves = Integer.parseInt(request.getParameter("reserves")); // 적립금
		String loginId = (String)request.getSession().getAttribute("loginId"); // 로그인아이디
		
		OrderDao.getorderDao().insertOrderDetailDB(orderId,loginId,receiver,zipcode,address,detailAddress,phoneNumber,order,orderPhoneNumber,email,memo,reserves);
		// 주문서 정보 입력하는 메서드
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("msg", "orderDetail 저장 성공"); // 화면에 메시지 전달
		out.print(obj);
	}

}
