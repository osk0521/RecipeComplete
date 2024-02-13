package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class PhoneAuthenticationAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 문자로 보낸 인증번호
		String phoneAuthenticationNumber = (String)request.getSession().getAttribute("phoneAuthenticationNumber");
		// 회원가입 폼에서 받아온 인증번호
		String paramPhoneAuthenticationNumber = request.getParameter("phone_authentication_number");
		// 인증 결과
		boolean result = ( phoneAuthenticationNumber.equals(paramPhoneAuthenticationNumber) ? true : false );
		
		// 인증결과를 JSON객체로 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("result", result);
		out.print(obj);
	}
}
