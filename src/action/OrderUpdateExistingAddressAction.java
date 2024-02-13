package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.OrderDao;



public class OrderUpdateExistingAddressAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller에서 orderUpdateExistingAddressAction으로 이동");
		int productId = Integer.parseInt((String) request.getParameter("productId")); // 주소 id 받기
		String receiver = request.getParameter("receiver"); // 받으실분 받기
		String zipcode = request.getParameter("zipcode"); // 우편주소 받기
		String address = request.getParameter("address"); // 주소 받기
		String detail = request.getParameter("detail"); // 상세 주소 받기
		String phone = request.getParameter("phone"); // 핸드폰번호 받기
		int defaultCheck = Integer.parseInt(request.getParameter("defaultCheck")); // 기본 배송지 체크 받기
		String loginId = (String)request.getSession().getAttribute("loginId"); // 로그인 id 받기
		
		if(defaultCheck==1) {
			OrderDao.getorderDao().resetDefaultAddress(loginId); // 기본 배송지 체크되었다면 기존 기본 배송지 체크 해제를 위한 리셋 진행
		}
		OrderDao.getorderDao().updateExistingAddress(productId, loginId, receiver, zipcode, address, detail, phone, defaultCheck);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("msg", "수정이 완료되었습니다");
		out.print(obj);
	}

}
