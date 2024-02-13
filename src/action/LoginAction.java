package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;

public class LoginAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramId = request.getParameter("id");
		String paramPw = request.getParameter("pw");
		
		// 로그인 메서드를 실행하기 위한 MemberDAO객체 생성
		MemberDao memberDao = new MemberDao();
		// 로그인 성공 여부
		boolean result = memberDao.login(paramId, paramPw);
		// 성공 여부를 request에 저장
		request.setAttribute("result", result);
		// 성공 여부에 따른 액션을 결정하기 위해 페이지 이동
		request.getRequestDispatcher("Controller?command=login_result").forward(request, response);
	}
}
