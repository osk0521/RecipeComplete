package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.EmailDao;

public class SendEmailAuthenticationMsgAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인증이 필요한 이메일 주소 받아오기
		String toEmail = request.getParameter("toEmail");
		// 인증번호 생성
		int randomNumber = (int)(Math.random() * 10000);
		String emailAuthenticationNumber = "";
		if(randomNumber < 10) {
			emailAuthenticationNumber = "000" + randomNumber;
		} else if(randomNumber < 100) {
			emailAuthenticationNumber = "00" + randomNumber;
		} else if(randomNumber < 1000) {
			emailAuthenticationNumber = "0" + randomNumber;
		} else {
			emailAuthenticationNumber = "" + randomNumber;
		}
		// 인증번호를 세션에 저장(String타입)
		request.getSession().setAttribute("emailAuthenticationNumber", emailAuthenticationNumber);
		// 이메일 제목과 내용 생성
		String title = "[자취생을 부탁해] 이메일 인증번호 입니다.";
		String content = "[자취생을 부탁해] 이메일 인증번호는 [" + emailAuthenticationNumber + "] 입니다.";
		
		System.out.println("받아온 이메일 : " + toEmail);
		System.out.println("보낼 이메일 제목 : " + title);
		System.out.println("보낼 이메일 내용 : " + content);
		
		// 이메일 전송을 위한 Dao객체 생성
		EmailDao eDao = new EmailDao();
		// 인증 이메일 전송
		boolean result = eDao.sendAuthenticationEmail(toEmail, title, content);
		
		// 인증 결과를 JSON객체로 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("result", result);
		out.print(obj);
	}
}
