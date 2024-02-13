package action;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import action.Action;
import dao.MemberDao;

public class MemberModifyPasswordAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 아이디 받아오기
		String loginId = (String)request.getSession().getAttribute("loginId");
		// 변경하고자 하는 새로운 비밀번호 받아오기
		String newPw = request.getParameter("new_pw");
		// 비밀번호를 수정하기 위한 Dao객체 생성
		MemberDao mDao = new MemberDao();
		boolean result = mDao.modifyPassword(loginId, newPw);
		
		// 결과를 JSON객체로 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		// 수정 성공 여부를 반환
		obj.put("result", result);
		out.print(obj);
	}
}
