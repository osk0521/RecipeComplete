package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;

public class SocialLoginAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 소셜로그인 타입 받아오기
		String type = request.getParameter("type");
		// id, pw 변수 초기화
		String id = null;
		String pw = null;
		// 카카오 or 네이버
		if(type.equals("kakao")) {
			id = request.getParameter("id"); // 카카오 ID값 받아오기
			pw = "kakaotest1"; // 카카오계정 임시비밀번호
		} else if(type.equals("naver")) {
			id = request.getParameter("id"); // 네이버 ID값 받아오기
			pw = "navertest1"; // 네이버계정 임시비밀번호
			String naverLoginAccessToken = request.getParameter("naverLoginAccessToken");
			// 네이버 로그인 토큰을 세션에 저장
			HttpSession session = request.getSession();
			session.setAttribute("naverLoginAccessToken", naverLoginAccessToken); 
			System.out.println("네이버 로그인 토큰 : " + naverLoginAccessToken);
		}
		
		// 해당 ID값으로 로그인 진행
		boolean result = new MemberDao().login(id, pw);
		System.out.println("id : " + id);
		System.out.println("pw : " + pw);
		System.out.println("result: " + result);
		
		request.setAttribute("result", result);
		request.getRequestDispatcher("Controller?command=login_result").forward(request, response);
	}
}
