package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class EmailAuthenticationAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션에 저장되어있는 이메일 인증번호 받아오기
		String emailAuthenticationNumber = (String)request.getSession().getAttribute("emailAuthenticationNumber");
		// 화면으로부터 입력된 인증번호 받아오기
		String paramEmailAuthenticationNumber = request.getParameter("email_authentication_number");
		// 인증  결과
		boolean result = ( emailAuthenticationNumber.equals(paramEmailAuthenticationNumber) ? true : false );
		
		// 인증결과를 JSON객체로 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("result", result);
		out.print(obj);
	}
}
