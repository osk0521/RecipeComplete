package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MemberDao;

public class MemberPasswordCheckAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 아이디 받아오기
		String loginId = (String)request.getSession().getAttribute("loginId");
		String previousPw = request.getParameter("previous_pw"); // 화면에서 건네받은 비밀번호
		// 로그인 유저의 비밀번호를 가져오기 위한 Dao객체 생성
		MemberDao mDao = new MemberDao();
		boolean result = mDao.checkPassword(loginId, previousPw);
		
		// 결과를 JSON객체로 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("result", result);
		out.print(obj);
	}
}
