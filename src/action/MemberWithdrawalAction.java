package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;

public class MemberWithdrawalAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 현재 로그인중인 아이디 가져오기
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId");
		// 로그인 아이디의 회원정보 삭제하기
		boolean result = new MemberDao().memberWithdrawal(loginId);
		// 세션의 로그인 정보 삭제
		session.invalidate();
		// 결과 화면으로 이동
		request.getRequestDispatcher("Member_Withdrawal_Result.jsp").forward(request, response);
	}
}
