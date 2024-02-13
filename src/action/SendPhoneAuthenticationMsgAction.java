package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MemberDao;

public class SendPhoneAuthenticationMsgAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인증이 필요한 휴대폰 번호 받아오기
		String toPhone = request.getParameter("toPhone");
		// 인증번호 생성
		int randomNumber = (int)(Math.random() * 10000);
		String phoneAuthenticationNumber = "";
		if(randomNumber < 10) {
			phoneAuthenticationNumber = "000" + randomNumber;
		} else if(randomNumber < 100) {
			phoneAuthenticationNumber = "00" + randomNumber;
		} else if(randomNumber < 1000) {
			phoneAuthenticationNumber = "0" + randomNumber;
		} else {
			phoneAuthenticationNumber = "" + randomNumber;
		}
		// 인증번호 세션에 저장(String타입)
		request.getSession().setAttribute("phoneAuthenticationNumber", phoneAuthenticationNumber);
		// 인증 메시지 생성
		String authenticationMessage = 
				"[자취생을 부탁해] 인증번호는 [" + phoneAuthenticationNumber + "]입니다.";
		
		System.out.println("받아온 폰번호 : " + toPhone);
		System.out.println("인증번호 : " + phoneAuthenticationNumber);
		System.out.println("인증메시지 : " + authenticationMessage);
		// 인증 메시지를 보내기 위한 MemberDao객체 생성
		MemberDao mDao = new MemberDao();
		// 인증 메시지를 보낸 후 응답코드 저장(2000 : 정상)
		int statusCode = mDao.sendAuthenticationMessage(toPhone, authenticationMessage);
		
		// JSON타입으로 응답코드 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		// 테스트
//		obj.put("statusCode", 2000);
//		out.print(obj);
		// 인증 메시지가 정상적으로 보내졌을 때
		if(statusCode == 2000) {
			obj.put("statusCode", statusCode);
			out.print(obj);
		// 인증 메시지가 비정상적으로 보내지지 않았을 때
		} else {
			obj.put("statusCode", statusCode);
			out.print(obj);
		}
	}
}
