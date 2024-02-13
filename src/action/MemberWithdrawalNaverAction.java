package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberWithdrawalNaverAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 네이버 로그인 토큰 삭제요청
		request.getRequestDispatcher("Member_Withdrawal_Naver.jsp").forward(request, response);
	}
}
 