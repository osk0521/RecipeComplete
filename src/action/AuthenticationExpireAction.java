package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationExpireAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인증타입 받아오기
		String type = request.getParameter("type");
		// 인증번호를 세션에 저장하기 위해 세션 객체 생성
		HttpSession session = request.getSession();
		if(type.equals("phone")) {
			session.removeAttribute("phoneAuthenticationNumber");
			System.out.println("폰 인증번호 삭제");
		} else if(type.equals("email")) {
			session.removeAttribute("emailAuthenticationNumber");
			System.out.println("이메일 인증번호 삭제");
		} else if(type.equals("all")) { // 회원가입 등록할 때
			session.removeAttribute("phoneAuthenticationNumber");
			session.removeAttribute("emailAuthenticationNumber");
			System.out.println("폰, 이메일 인증번호 삭제");
		}
	}
}
