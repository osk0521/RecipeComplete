package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import dao.OrderDao;
import vo.OrderAddressVo;

public class OrderGetDefaultAddressAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller에서 orderGetDefaultAddressAction으로 이동");
		
		HttpSession session = request.getSession(); // 세션 선언
		String loginId = (String)session.getAttribute("loginId"); // 로그인 아이디 받기
		
		OrderAddressVo defaultAddress = OrderDao.getorderDao().getDefaultAddressById(loginId); //로그인 아이디 이용하여 기본 배송지 vo 가져오기 메서드
		
		response.setContentType("application/json"); // ajax로 전달
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject(); // JSONObject로 담아서 전달
		obj.put("receiver", defaultAddress.getReceiver());
		obj.put("zipCode",defaultAddress.getZipCode());
		obj.put("address",defaultAddress.getAddress());
		obj.put("detailAddress",defaultAddress.getDetailAddress());
		obj.put("phoneNumber",defaultAddress.getPhoneNumber());
		out.print(obj);
	}

}
