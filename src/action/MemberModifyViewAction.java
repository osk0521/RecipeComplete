package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.MemberDto;

public class MemberModifyViewAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 아이디 받아오기
		String loginId = (String)request.getSession().getAttribute("loginId");
		// 로그인 유저의 회원정보 받아오기
		MemberDto memberInfo = new MemberDao().getMemberInfo(loginId);
		request.setAttribute("memberInfo", memberInfo);
		
		request.getRequestDispatcher("Member_Modify_MemberInfo.jsp").forward(request, response);
	}
}
