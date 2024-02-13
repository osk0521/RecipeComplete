package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.OrderDao;

public class OrderUpdateDestination implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller에서 orderUpdateDestination으로 이동");
		
		String receiver = request.getParameter("receiver"); // 받으실분 파라미터로 받기
		String zipCode = request.getParameter("zipcode"); // 우편주소 파라미터 받기
		String address = request.getParameter("address"); // 주소 파라미터 받기
		String detailAddress = request.getParameter("detail"); // 상세주소 파라미터 받기
		String phoneNumber = request.getParameter("phone"); // 핸드폰 번호 파라미터 받기
		int defaultCheck = Integer.parseInt(request.getParameter("defaultCheck")); // 기본배송지 체크박스 on-->1 null-->0
		String loginId = (String)request.getSession().getAttribute("loginId"); // 로그인 아이디 받기
	
			
		if(defaultCheck==1) {
			OrderDao.getorderDao().resetDefaultAddress(loginId); // 기본 배송지 설정 체크 되었다면 기존 기본 배송지 설정된 주소 0으로 초기화 진행
		}
		
		OrderDao.getorderDao().insertNewAddress(loginId, receiver, zipCode, address, detailAddress, phoneNumber, defaultCheck);
		//신규 배송지 인서트 진행 메서드
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("msg","등록이 완료되었습니다.");
		out.print(obj);
	}

}
