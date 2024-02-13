package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.OrderDao;
import vo.OrderAddressVo;


public class OrderGetUserAddressListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller에서 orderGetUserAddressListAction으로 넘어옴");
		
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId");
		
		ArrayList<OrderAddressVo> userAddressList = new ArrayList<OrderAddressVo>();
		userAddressList = OrderDao.getorderDao().getUserAddressList(loginId); 
		// 유저아이디 파라미터로 유저 주소 ArrayList 초기화하는 메서드
		
		response.setContentType("application/json"); // ajax로 전달
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONArray array = new JSONArray();
		for(OrderAddressVo vo : userAddressList) { // JSONArray로 전달
			JSONObject obj = new JSONObject();
			obj.put("adrressId", vo.getAddressId()); // 주소ID
			obj.put("reciever", vo.getReceiver()); // 받으실 분
			obj.put("zipCode",vo.getZipCode()); // 우편번호
			obj.put("address", vo.getAddress()); // 주소
			obj.put("detailAddress", vo.getDetailAddress()); //상세 주소
			obj.put("phoneNumber", vo.getPhoneNumber()); // 받으실 분 핸드폰 번호
			obj.put("defaultAddress", vo.getDefaultAddress()); // 기본 배송지 설정 여부 0or 1
			array.add(obj);
		}
		out.print(array);
	}

}
